
package services;

import java.util.Collection;

import javax.transaction.Transactional;
import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.ConferenceSponsorRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import security.UserAccountRepository;
import utiles.AddPhoneCC;
import utiles.AuthorityMethods;
import utiles.EmailValidator;
import domain.ConferenceSponsor;
import forms.ConferenceSponsorForm;

@Service
@Transactional
public class ConferenceSponsorService {

	@Autowired
	private ConferenceSponsorRepository	sponsorRepository;

	@Autowired
	private UserAccountRepository		accountRepository;

	@Autowired
	private AdminConfigService			adminConfigService;

	@Autowired
	private Validator					validator;


	public ConferenceSponsor create() {
		final ConferenceSponsor res = new ConferenceSponsor();

		//TODO: res.setMessageBoxes(this.messageBoxService.initializeNewUserBoxes());

		return res;
	}

	public ConferenceSponsor save(final ConferenceSponsor sponsor) {
		Assert.isTrue(sponsor != null);

		if (sponsor.getId() == 0) {
			Assert.isTrue(!AuthorityMethods.checkIsSomeoneLogged());
			final UserAccount userAccount = sponsor.getUserAccount();

			final Md5PasswordEncoder encoder = new Md5PasswordEncoder();
			final String pass = encoder.encodePassword(userAccount.getPassword(), null);
			userAccount.setPassword(pass);

			final UserAccount finalAccount = this.accountRepository.save(userAccount);

			sponsor.setUserAccount(finalAccount);
		} else {
			Assert.isTrue(AuthorityMethods.checkIsSomeoneLogged());
			Assert.isTrue(AuthorityMethods.chechAuthorityLogged(Authority.SPONSOR));
			//TODO: this.activateSponsorship(sponsor);
		}

		final ConferenceSponsor res = this.sponsorRepository.save(sponsor);
		return res;
	}

	/*
	 * private void activateSponsorship(final ConferenceSponsor sponsor) {
	 * final Collection<Sponsorship> sponsorships = this.sponsorshipService.findAllBySponsor(sponsor.getId());
	 * 
	 * for (final Sponsorship sponsorship : sponsorships)
	 * sponsorship.setCancelled(false);
	 * this.sponsorshipService.save(sponsorships);
	 * 
	 * }
	 */

	public void flush() {
		this.sponsorRepository.flush();
	}

	public Collection<ConferenceSponsor> findAll() {
		return this.sponsorRepository.findAll();
	}

	public ConferenceSponsor reconstruct(final ConferenceSponsorForm sponsorForm, final BindingResult binding) {

		if (!EmailValidator.validateEmail(sponsorForm.getEmail(), Authority.SPONSOR))
			binding.rejectValue("email", "sponsor.edit.email.error");
		if (!sponsorForm.getUserAccount().getPassword().equals(sponsorForm.getConfirmPassword()))
			binding.rejectValue("confirmPassword", "sponsor.edit.confirmPassword.error");
		if (this.accountRepository.findByUsername(sponsorForm.getUserAccount().getUsername()) != null)
			binding.rejectValue("userAccount.username", "sponsor.edit.userAccount.username.error");
		if (!sponsorForm.getTermsAndConditions())
			binding.rejectValue("termsAndConditions", "sponsor.edit.termsAndConditions.error");

		final ConferenceSponsor result;
		result = this.create();

		final UserAccount account = sponsorForm.getUserAccount();

		final Authority a = new Authority();
		a.setAuthority(Authority.SPONSOR);
		account.addAuthority(a);

		result.setUserAccount(account);
		result.setAddress(sponsorForm.getAddress());
		result.setEmail(sponsorForm.getEmail());
		result.setName(sponsorForm.getName());
		result.setPhoneNumber(AddPhoneCC.addPhoneCC(this.adminConfigService.getAdminConfig().getCountryCode(), sponsorForm.getPhoneNumber()));
		result.setPhotoURL(sponsorForm.getPhotoURL());
		result.setSurname(sponsorForm.getSurname());

		this.validator.validate(result, binding);

		if (binding.hasErrors())
			throw new ValidationException();

		return result;
	}

	public ConferenceSponsor reconstruct(final ConferenceSponsor sponsor, final BindingResult binding) {

		if (!EmailValidator.validateEmail(sponsor.getEmail(), Authority.SPONSOR))
			binding.rejectValue("email", "sponsor.edit.email.error");

		final ConferenceSponsor result;
		result = this.findByPrincipal(LoginService.getPrincipal());

		result.setAddress(sponsor.getAddress());
		result.setEmail(sponsor.getEmail());
		result.setName(sponsor.getName());
		result.setPhoneNumber(AddPhoneCC.addPhoneCC(this.adminConfigService.getAdminConfig().getCountryCode(), sponsor.getPhoneNumber()));
		result.setPhotoURL(sponsor.getPhotoURL());
		result.setSurname(sponsor.getSurname());
		this.validator.validate(result, binding);

		if (binding.hasErrors())
			throw new ValidationException();

		return result;
	}

	public ConferenceSponsor findOne(final int auditorId) {
		return this.sponsorRepository.findOne(auditorId);
	}

	public ConferenceSponsor findByPrincipal(final UserAccount principal) {
		return this.sponsorRepository.findByPrincipal(principal.getId());
	}

}
