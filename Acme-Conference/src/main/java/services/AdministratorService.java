
package services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.AdministratorRepository;
import security.UserAccount;
import utiles.AuthorityMethods;
import domain.Administrator;

@Service
@Transactional
public class AdministratorService {

	@Autowired
	private AdministratorRepository	adminRepository;


	// @Autowired
	// private AdminConfigService adminConfigService;
	//
	// @Autowired
	// private MessageBoxService messageBoxService;
	//
	// @Autowired
	// private Validator validator;
	//
	//
	// public Administrator create() {
	// final Administrator res = new Administrator();
	// res.setSpammer(null);
	// res.setBanned(false);
	// res.setMessageBoxes(this.messageBoxService.initializeNewUserBoxes());
	// return res;
	// }
	//
	// public Administrator save(final Administrator admin) {
	//
	// Assert.isTrue(admin != null);
	// Assert.isTrue(AuthorityMethods.checkIsSomeoneLogged());
	// Assert.isTrue(AuthorityMethods.chechAuthorityLogged(Authority.ADMINISTRATOR));
	// Assert.isTrue(!admin.getBanned());
	//
	// if (admin.getId() == 0) {
	// final UserAccount userAccount = admin.getUserAccount();
	//
	// final Md5PasswordEncoder encoder = new Md5PasswordEncoder();
	// final String pass = encoder.encodePassword(userAccount.getPassword(), null);
	// userAccount.setPassword(pass);
	//
	// final UserAccount finalAccount = this.accountRepository.save(userAccount);
	//
	// admin.setUserAccount(finalAccount);
	// }
	//
	// final Administrator res = this.adminRepository.save(admin);
	//
	// return res;
	//
	// }
	//
	// public Administrator findOne(final int actorId) {
	// return this.adminRepository.findOne(actorId);
	// }
	//
	public Administrator findByPrincipal(final UserAccount principal) {
		return this.adminRepository.findByPrincipal(principal.getId());
	}
	//
	// public Administrator reconstruct(final AdministratorForm adminForm, final BindingResult binding) {
	//
	// if (!EmailValidator.validateEmail(adminForm.getEmail(), Authority.ADMINISTRATOR))
	// binding.rejectValue("email", "administrator.edit.email.error");
	// if (!adminForm.getUserAccount().getPassword().equals(adminForm.getConfirmPassword()))
	// binding.rejectValue("confirmPassword", "administrator.edit.confirmPassword.error");
	// if (this.accountRepository.findByUsername(adminForm.getUserAccount().getUsername()) != null)
	// binding.rejectValue("userAccount.username", "administrator.edit.userAccount.username.error");
	// if (!adminForm.getTermsAndConditions())
	// binding.rejectValue("termsAndConditions", "administrator.edit.termsAndConditions.error");
	//
	// final Administrator result;
	// result = this.create();
	//
	// final UserAccount account = adminForm.getUserAccount();
	//
	// final Authority a = new Authority();
	// a.setAuthority(Authority.ADMINISTRATOR);
	// account.addAuthority(a);
	//
	// result.setUserAccount(account);
	// result.setAddress(adminForm.getAddress());
	// result.setEmail(adminForm.getEmail());
	// result.setName(adminForm.getName());
	// result.setPhoneNumber(AddPhoneCC.addPhoneCC(this.adminConfigService.getAdminConfig().getCountryCode(), adminForm.getPhoneNumber()));
	// result.setPhotoURL(adminForm.getPhotoURL());
	//
	// result.setSurname(adminForm.getSurname());
	//
	// this.validator.validate(result, binding);
	//
	// if (binding.hasErrors())
	// throw new ValidationException();
	// return result;
	// }
	// public Administrator reconstruct(final Administrator admin, final BindingResult binding) {
	//
	// if (!EmailValidator.validateEmail(admin.getEmail(), Authority.ADMINISTRATOR))
	// binding.rejectValue("email", "administrator.edit.email.error");
	//
	// final Administrator result;
	// result = this.findByPrincipal(LoginService.getPrincipal());
	// result.setAddress(admin.getAddress());
	// result.setEmail(admin.getEmail());
	// result.setName(admin.getName());
	// result.setPhoneNumber(AddPhoneCC.addPhoneCC(this.adminConfigService.getAdminConfig().getCountryCode(), admin.getPhoneNumber()));
	// result.setPhotoURL(admin.getPhotoURL());
	// result.setSurname(admin.getSurname());
	//
	// this.validator.validate(result, binding);
	//
	// if (binding.hasErrors())
	// throw new ValidationException();
	// return result;
	// }
	//
	// public void flush() {
	// this.adminRepository.flush();
	// }
	//
	// public Administrator getOne() {
	// return this.adminRepository.findAll().get(0);
	// }
	//
	// public Collection<Administrator> findAll() {
	// return this.adminRepository.findAll();
	// }
	//
	//DASHBOARD----------------------------------------------------------

	public Double getAvgOfSubmissionsPerConference() {
		Assert.isTrue(AuthorityMethods.chechAuthorityLogged("ADMINISTRATOR"));
		return this.adminRepository.getAvgOfSubmissionsPerConference();
	}

	public Integer getMinimumOfSubmissionsPerConference() {
		Assert.isTrue(AuthorityMethods.chechAuthorityLogged("ADMINISTRATOR"));
		return this.adminRepository.getMinimumOfSubmissionsPerConference();
	}

	public Integer getMaximumOfSubmissionsPerConference() {
		Assert.isTrue(AuthorityMethods.chechAuthorityLogged("ADMINISTRATOR"));
		return this.adminRepository.getMaximumOfSubmissionsPerConference();
	}

	public Double getSDOfSubmissionsPerConference() {
		Assert.isTrue(AuthorityMethods.chechAuthorityLogged("ADMINISTRATOR"));
		return this.adminRepository.getSDOfSubmissionsPerConference();
	}

	//----------------------------------------------------------

	public Double getAvgOfRegistrationsPerConference() {
		Assert.isTrue(AuthorityMethods.chechAuthorityLogged("ADMINISTRATOR"));
		return this.adminRepository.getAvgOfRegistrationsPerConference();
	}

	public Integer getMinimumOfRegistrationsPerConference() {
		Assert.isTrue(AuthorityMethods.chechAuthorityLogged("ADMINISTRATOR"));
		return this.adminRepository.getMinimumOfRegistrationsPerConference();
	}

	public Integer getMaximumOfRegistrationsPerConference() {
		Assert.isTrue(AuthorityMethods.chechAuthorityLogged("ADMINISTRATOR"));
		return this.adminRepository.getMaximumOfRegistrationsPerConference();
	}

	public Double getSDOfRegistrationsPerConference() {
		Assert.isTrue(AuthorityMethods.chechAuthorityLogged("ADMINISTRATOR"));
		return this.adminRepository.getSDOfRegistrationsPerConference();
	}

	//----------------------------------------------------------

	public Double getAvgOfConferenceFees() {
		Assert.isTrue(AuthorityMethods.chechAuthorityLogged("ADMINISTRATOR"));
		return this.adminRepository.getAvgOfConferenceFees();
	}

	public Integer getMinimumOfConferenceFees() {
		Assert.isTrue(AuthorityMethods.chechAuthorityLogged("ADMINISTRATOR"));
		return this.adminRepository.getMinimumOfConferenceFees();
	}

	public Integer getMaximumOfConferenceFees() {
		Assert.isTrue(AuthorityMethods.chechAuthorityLogged("ADMINISTRATOR"));
		return this.adminRepository.getMaximumOfConferenceFees();
	}

	public Double getSDOfConferenceFees() {
		Assert.isTrue(AuthorityMethods.chechAuthorityLogged("ADMINISTRATOR"));
		return this.adminRepository.getSDOfConferenceFees();
	}

	//----------------------------------------------------------

	public Double getAvgOfDaysPerConference() {
		Assert.isTrue(AuthorityMethods.chechAuthorityLogged("ADMINISTRATOR"));
		return this.adminRepository.getAvgOfDaysPerConference();
	}

	public Integer getMinimumOfDaysPerConference() {
		Assert.isTrue(AuthorityMethods.chechAuthorityLogged("ADMINISTRATOR"));
		return this.adminRepository.getMinimumOfDaysPerConference();
	}

	public Integer getMaximumOfDaysPerConference() {
		Assert.isTrue(AuthorityMethods.chechAuthorityLogged("ADMINISTRATOR"));
		return this.adminRepository.getMaximumOfDaysPerConference();
	}

	public Double getSDOfDaysPerConference() {
		Assert.isTrue(AuthorityMethods.chechAuthorityLogged("ADMINISTRATOR"));
		return this.adminRepository.getSDOfDaysPerConference();
	}

	//----------------------------------------------------------

	public Double getAvgOfConferencesPerCategory() {
		Assert.isTrue(AuthorityMethods.chechAuthorityLogged("ADMINISTRATOR"));
		return this.adminRepository.getAvgOfConferencesPerCategory();
	}

	public Integer getMinimumOfConferencesPerCategory() {
		Assert.isTrue(AuthorityMethods.chechAuthorityLogged("ADMINISTRATOR"));
		return this.adminRepository.getMinimumOfConferencesPerCategory();
	}

	public Integer getMaximumOfConferencesPerCategory() {
		Assert.isTrue(AuthorityMethods.chechAuthorityLogged("ADMINISTRATOR"));
		return this.adminRepository.getMaximumOfConferencesPerCategory();
	}

	public Double getSDOfConferencesPerCategory() {
		Assert.isTrue(AuthorityMethods.chechAuthorityLogged("ADMINISTRATOR"));
		return this.adminRepository.getSDOfConferencesPerCategory();
	}

	//----------------------------------------------------------

	public Double getAvgOfCommentsPerConference() {
		Assert.isTrue(AuthorityMethods.chechAuthorityLogged("ADMINISTRATOR"));
		return this.adminRepository.getAvgOfCommentsPerConference();
	}

	public Integer getMinimumOfCommentsPerConference() {
		Assert.isTrue(AuthorityMethods.chechAuthorityLogged("ADMINISTRATOR"));
		return this.adminRepository.getMinimumOfCommentsPerConference();
	}

	public Integer getMaximumOfCommentsPerConference() {
		Assert.isTrue(AuthorityMethods.chechAuthorityLogged("ADMINISTRATOR"));
		return this.adminRepository.getMaximumOfCommentsPerConference();
	}

	public Double getSDOfCommentsPerConference() {
		Assert.isTrue(AuthorityMethods.chechAuthorityLogged("ADMINISTRATOR"));
		return this.adminRepository.getSDOfCommentsPerConference();
	}

	//----------------------------------------------------------

	public Double getAvgOfCommentsPerActivity() {
		Assert.isTrue(AuthorityMethods.chechAuthorityLogged("ADMINISTRATOR"));
		return this.adminRepository.getAvgOfCommentsPerActivity();
	}

	public Integer getMinimumOfCommentsPerActivity() {
		Assert.isTrue(AuthorityMethods.chechAuthorityLogged("ADMINISTRATOR"));
		return this.adminRepository.getMinimumOfCommentsPerActivity();
	}

	public Integer getMaximumOfCommentsPerActivity() {
		Assert.isTrue(AuthorityMethods.chechAuthorityLogged("ADMINISTRATOR"));
		return this.adminRepository.getMaximumOfCommentsPerActivity();
	}

	public Double getSDOfCommentsPerActivity() {
		Assert.isTrue(AuthorityMethods.chechAuthorityLogged("ADMINISTRATOR"));
		return this.adminRepository.getSDOfCommentsPerActivity();
	}

	//
	// public void saveAnonymize(final Administrator anonymousAdmin) {
	// Assert.isTrue(anonymousAdmin != null);
	// Assert.isTrue(AuthorityMethods.chechAuthorityLogged(Authority.ADMINISTRATOR) || AuthorityMethods.chechAuthorityLogged(Authority.BAN));
	// this.adminRepository.save(anonymousAdmin);
	// }

}
