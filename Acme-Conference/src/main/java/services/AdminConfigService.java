
package services;

import java.util.Collection;

import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.AdminConfigRepository;
import utiles.AuthorityMethods;
import domain.AdminConfig;
import forms.AdminConfigForm;
import forms.CreditCardMakeForm;
import forms.VoidWordForm;

@Service
@Transactional
public class AdminConfigService {

	@Autowired
	private AdminConfigRepository	adminConfigRepository;

	@Autowired
	private Validator				validator;


	// CRUD methods
	//---------------------------------------------------------------------------------------
	public AdminConfig save(final AdminConfig adminConfig) {
		Assert.isTrue(AuthorityMethods.chechAuthorityLogged("ADMINISTRATOR"));
		return this.adminConfigRepository.save(adminConfig);
	}
	public AdminConfig reconstruct(final AdminConfigForm adminConfigForm, final BindingResult binding) {
		final AdminConfig adminConfig;

		adminConfig = this.getAdminConfig();

		adminConfig.setBannerURL(adminConfigForm.getBannerURL());
		adminConfig.setCountryCode(adminConfigForm.getCountryCode());
		adminConfig.setSystemName(adminConfigForm.getSystemName());
		adminConfig.setWelcomeMsgEN(adminConfigForm.getWelcomeMsgEN());
		adminConfig.setWelcomeMsgES(adminConfigForm.getWelcomeMsgES());

		this.validator.validate(adminConfig, binding);

		if (binding.hasErrors())
			throw new ValidationException();

		return this.save(adminConfig);
	}

	public void deleteVoidWord(final String voidWord) {
		Assert.isTrue(AuthorityMethods.chechAuthorityLogged("ADMINISTRATOR"));
		final AdminConfig adminConfig = this.getAdminConfig();
		final Collection<String> voidWords = adminConfig.getVoidWords();
		Assert.isTrue(voidWords.contains(voidWord));
		voidWords.remove(voidWord);
		adminConfig.setVoidWords(voidWords);
		this.save(adminConfig);
	}

	public void deleteCreditCardMake(final String creditCardMake) {
		Assert.isTrue(AuthorityMethods.chechAuthorityLogged("ADMINISTRATOR"));
		final AdminConfig adminConfig = this.getAdminConfig();
		final Collection<String> creditCardMakes = adminConfig.getCreditCardMakes();
		Assert.isTrue(creditCardMakes.contains(creditCardMake));
		Assert.isTrue(creditCardMakes.size() > 1);
		creditCardMakes.remove(creditCardMake);
		adminConfig.setCreditCardMakes(creditCardMakes);
		this.save(adminConfig);
	}

	public AdminConfig addVoidWord(final VoidWordForm voidWordForm, final BindingResult binding) {
		final AdminConfig adminConfig;

		adminConfig = this.getAdminConfig();

		final Collection<String> voidWords = adminConfig.getVoidWords();
		final String voidWordName = voidWordForm.getVoidWord().trim().toLowerCase().replaceAll(" +", " ");

		if (!(voidWordName.isEmpty()))
			if (voidWords.contains(voidWordName))
				binding.rejectValue("voidWord", "adminConfig.error.existVoidWord");

		voidWords.add(voidWordName);
		adminConfig.setVoidWords(voidWords);

		this.validator.validate(adminConfig, binding);

		if (binding.hasErrors())
			throw new ValidationException();

		return this.save(adminConfig);
	}

	public AdminConfig addCreditCardMake(final CreditCardMakeForm creditCardMakeForm, final BindingResult binding) {
		Assert.isTrue(AuthorityMethods.chechAuthorityLogged("ADMINISTRATOR"));
		final AdminConfig adminConfig;

		adminConfig = this.getAdminConfig();

		final Collection<String> creditCardMakes = adminConfig.getCreditCardMakes();
		final String makeName = creditCardMakeForm.getCreditCardMake().trim().toUpperCase().replaceAll(" +", " ");

		if (!(makeName.isEmpty()))
			if (creditCardMakes.contains(makeName))
				binding.rejectValue("creditCardMake", "adminConfig.error.existCreditCardMake");

		creditCardMakes.add(makeName);
		adminConfig.setCreditCardMakes(creditCardMakes);

		this.validator.validate(adminConfig, binding);

		if (binding.hasErrors())
			throw new ValidationException();

		return this.save(adminConfig);
	}

	//---------------------------------------------------------------------------------------

	// Auxiliar methods
	//---------------------------------------------------------------------------------------
	public AdminConfig getAdminConfig() {
		return this.adminConfigRepository.findAll().get(0);
	}

	public void flush() {
		this.adminConfigRepository.flush();
	}
	//---------------------------------------------------------------------------------------

}
