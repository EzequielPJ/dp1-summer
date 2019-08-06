
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

import repositories.AuthorRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import security.UserAccountRepository;
import utiles.AuthorityMethods;
import utiles.EmailValidator;
import domain.Author;
import forms.ActorForm;

@Service
@Transactional
public class AuthorService {

	@Autowired
	private UserAccountRepository	accountRepository;

	@Autowired
	private AuthorRepository		authorRepository;

	@Autowired
	private Validator				validator;


	public Author create() {
		final Author res = new Author();

		//TODO: setMessageBoxes()

		return res;
	}

	public Author save(final Author author) {
		Assert.isTrue(author != null);

		if (author.getId() == 0) {

			Assert.isTrue(!AuthorityMethods.checkIsSomeoneLogged());
			final UserAccount userAccount = author.getUserAccount();

			final Md5PasswordEncoder encoder = new Md5PasswordEncoder();
			final String pass = encoder.encodePassword(userAccount.getPassword(), null);
			userAccount.setPassword(pass);

			final UserAccount finalAccount = this.accountRepository.save(userAccount);

			author.setUserAccount(finalAccount);

		} else {

			Assert.isTrue(AuthorityMethods.checkIsSomeoneLogged());
			Assert.isTrue(AuthorityMethods.chechAuthorityLogged(Authority.AUTHOR));

		}
		final Author res = this.authorRepository.save(author);
		return res;
	}

	public void flush() {
		this.authorRepository.flush();
	}

	public Author findOne(final int authorId) {
		return this.authorRepository.findOne(authorId);
	}

	public Author findByPrincipal(final UserAccount principal) {
		return this.authorRepository.findByPrincipal(principal.getId());
	}

	public Collection<Author> findAll() {
		return this.authorRepository.findAll();
	}

	public Author reconstruct(final ActorForm authorForm, final BindingResult binding) {

		if (!EmailValidator.validateEmail(authorForm.getEmail(), Authority.AUTHOR))
			binding.rejectValue("email", "author.edit.email.error");
		if (!authorForm.getUserAccount().getPassword().equals(authorForm.getConfirmPassword()))
			binding.rejectValue("confirmPassword", "author.edit.confirmPassword.error");
		if (this.accountRepository.findByUsername(authorForm.getUserAccount().getUsername()) != null)
			binding.rejectValue("userAccount.username", "author.edit.userAccount.username.error");
		if (!authorForm.getTermsAndConditions())
			binding.rejectValue("termsAndConditions", "author.edit.termsAndConditions.error");

		final Author result;
		result = this.create();

		final UserAccount account = authorForm.getUserAccount();

		final Authority a = new Authority();
		a.setAuthority(Authority.AUTHOR);
		account.addAuthority(a);

		result.setUserAccount(account);
		result.setAddress(authorForm.getAddress());
		result.setEmail(authorForm.getEmail());
		result.setName(authorForm.getName());
		result.setMiddlename(authorForm.getMiddlename());
		//result.setPhoneNumber(AddPhoneCC.addPhoneCC(this.adminConfigService.getAdminConfig().getCountryCode(), authorForm.getPhoneNumber()));
		result.setPhotoURL(authorForm.getPhotoURL());
		result.setSurname(authorForm.getSurname());

		this.validator.validate(result, binding);

		if (binding.hasErrors())
			throw new ValidationException();

		return result;
	}
	public Author reconstruct(final Author author, final BindingResult binding) {

		if (!EmailValidator.validateEmail(author.getEmail(), Authority.AUTHOR))
			binding.rejectValue("email", "author.edit.email.error");

		final Author result;
		result = this.findByPrincipal(LoginService.getPrincipal());

		result.setAddress(author.getAddress());
		result.setEmail(author.getEmail());
		result.setName(author.getName());
		result.setMiddlename(author.getMiddlename());
		//result.setPhoneNumber(AddPhoneCC.addPhoneCC(this.adminConfigService.getAdminConfig().getCountryCode(), author.getPhoneNumber()));
		result.setPhotoURL(author.getPhotoURL());
		result.setSurname(author.getSurname());

		this.validator.validate(result, binding);

		if (binding.hasErrors())
			throw new ValidationException();

		return result;
	}

}
