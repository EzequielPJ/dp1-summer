
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;
import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.ReviewerRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import security.UserAccountRepository;
import utiles.AddPhoneCC;
import utiles.AuthorityMethods;
import utiles.EmailValidator;
import utiles.ValidatorCollection;
import domain.Reviewer;
import domain.Submission;
import forms.ReviewerForm;

@Service
@Transactional
public class ReviewerService {

	@Autowired
	private UserAccountRepository	accountRepository;

	@Autowired
	private ReviewerRepository		reviewerRepository;

	@Autowired
	private ConferenceService		conferenceService;

	@Autowired
	private AdminConfigService		adminConfigService;

	@Autowired
	private Validator				validator;


	public Reviewer create() {
		final Reviewer res = new Reviewer();

		//TODO: setMessageBoxes()

		return res;
	}

	public Reviewer save(final Reviewer reviewer) {
		Assert.isTrue(reviewer != null);

		if (reviewer.getId() == 0) {

			Assert.isTrue(!AuthorityMethods.checkIsSomeoneLogged());
			final UserAccount userAccount = reviewer.getUserAccount();

			final Md5PasswordEncoder encoder = new Md5PasswordEncoder();
			final String pass = encoder.encodePassword(userAccount.getPassword(), null);
			userAccount.setPassword(pass);

			final UserAccount finalAccount = this.accountRepository.save(userAccount);

			reviewer.setUserAccount(finalAccount);

		} else {

			Assert.isTrue(AuthorityMethods.checkIsSomeoneLogged());
			Assert.isTrue(AuthorityMethods.chechAuthorityLogged(Authority.REVIEWER));

		}
		final Reviewer res = this.reviewerRepository.save(reviewer);
		return res;
	}

	public void flush() {
		this.reviewerRepository.flush();
	}

	public Reviewer findOne(final int reviewerId) {
		return this.reviewerRepository.findOne(reviewerId);
	}

	public Collection<Reviewer> findAll() {
		return this.reviewerRepository.findAll();
	}

	public Reviewer reconstruct(final ReviewerForm reviewerForm, final BindingResult binding) {

		if (!EmailValidator.validateEmail(reviewerForm.getEmail(), Authority.REVIEWER))
			binding.rejectValue("email", "reviewer.edit.email.error");
		if (!reviewerForm.getUserAccount().getPassword().equals(reviewerForm.getConfirmPassword()))
			binding.rejectValue("confirmPassword", "reviewer.edit.confirmPassword.error");
		if (this.accountRepository.findByUsername(reviewerForm.getUserAccount().getUsername()) != null)
			binding.rejectValue("userAccount.username", "reviewer.edit.userAccount.username.error");
		if (!reviewerForm.getTermsAndConditions())
			binding.rejectValue("termsAndConditions", "reviewer.edit.termsAndConditions.error");

		final Reviewer result;
		result = this.create();

		final UserAccount account = reviewerForm.getUserAccount();

		final Authority a = new Authority();
		a.setAuthority(Authority.REVIEWER);
		account.addAuthority(a);

		result.setUserAccount(account);
		result.setAddress(reviewerForm.getAddress());
		result.setEmail(reviewerForm.getEmail());
		result.setName(reviewerForm.getName());
		result.setMiddlename(reviewerForm.getMiddlename());
		result.setPhoneNumber(AddPhoneCC.addPhoneCC(this.adminConfigService.getAdminConfig().getCountryCode(), reviewerForm.getPhoneNumber()));
		result.setPhotoURL(reviewerForm.getPhotoURL());
		result.setSurname(reviewerForm.getSurname());
		result.setExpertiseKeywordsList(ValidatorCollection.deleteStringsBlanksInCollection(reviewerForm.getExpertiseKeywordsList()));

		this.validator.validate(result, binding);

		if (binding.hasErrors())
			throw new ValidationException();

		return result;
	}
	public Reviewer reconstruct(final Reviewer reviewer, final BindingResult binding) {

		if (!EmailValidator.validateEmail(reviewer.getEmail(), Authority.REVIEWER))
			binding.rejectValue("email", "reviewer.edit.email.error");

		final Reviewer result;
		result = this.findByPrincipal(LoginService.getPrincipal());

		result.setAddress(reviewer.getAddress());
		result.setEmail(reviewer.getEmail());
		result.setName(reviewer.getName());
		result.setMiddlename(reviewer.getMiddlename());
		result.setPhoneNumber(AddPhoneCC.addPhoneCC(this.adminConfigService.getAdminConfig().getCountryCode(), reviewer.getPhoneNumber()));
		result.setPhotoURL(reviewer.getPhotoURL());
		result.setSurname(reviewer.getSurname());

		this.validator.validate(result, binding);

		if (binding.hasErrors())
			throw new ValidationException();

		return result;
	}

	public Collection<Reviewer> getReviewersToAssign(final int size, final Submission submission) {
		Assert.isTrue(AuthorityMethods.chechAuthorityLogged("ADMINISTRATOR"));
		List<Reviewer> res;
		final List<Reviewer> availableReviewers = new ArrayList<>();
		final Collection<Reviewer> allReviewers = this.reviewerRepository.findAll();
		for (final Reviewer reviewer : allReviewers)
			for (final String string : reviewer.getExpertiseKeywordsList())
				if (this.conferenceService.getContainsExpertiseKeywords(string, submission.getConference().getId())) {
					availableReviewers.add(reviewer);
					break;
				}
		availableReviewers.removeAll(submission.getReviewers());
		if (availableReviewers.size() > size)
			res = availableReviewers.subList(0, size);
		else
			res = availableReviewers;
		return res;
	}

	public Reviewer findByPrincipal(final UserAccount principal) {
		Assert.isTrue(AuthorityMethods.chechAuthorityLogged("REVIEWER"));
		return this.reviewerRepository.findByPrincipal(principal.getId());
	}
}
