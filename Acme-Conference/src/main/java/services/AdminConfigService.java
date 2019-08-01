
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


	public AdminConfig getAdminConfig() {
		return this.adminConfigRepository.findAll().get(0);
	}

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

		return adminConfig;
	}

	//	public boolean existVoidWord(final Message message) {
	//		boolean exist = false;
	//		final Collection<String> voidWords = this.getAdminConfig().getVoidWords();
	//		final Boolean tagIsEmpty = message.getTags().isEmpty();
	//
	//		for (final String voidWord : voidWords) {
	//			final Integer void = this.messageService.existsVoidWordInMessage(message.getId(), voidWord, tagIsEmpty);
	//			if (void != null) {
	//				exist = true;
	//				break;
	//			}
	//		}
	//
	//		return exist;
	//	}

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

	public void flush() {
		this.adminConfigRepository.flush();
	}

	public AdminConfig addVoidWord(final VoidWordForm voidWordForm, final BindingResult binding) {
		final AdminConfig adminConfig;

		adminConfig = this.getAdminConfig();

		final Collection<String> voidWords = adminConfig.getVoidWords();

		if (!(voidWordForm.getVoidWord().trim().isEmpty())) {
			if (voidWords.contains(voidWordForm.getVoidWord().trim().toLowerCase()))
				binding.rejectValue("voidWord", "adminConfig.error.existVoidWord");

			voidWords.add(voidWordForm.getVoidWord().toLowerCase());
		}
		adminConfig.setVoidWords(voidWords);

		this.validator.validate(adminConfig, binding);

		if (binding.hasErrors())
			throw new ValidationException();

		return adminConfig;
	}

	public AdminConfig addCreditCardMake(final CreditCardMakeForm creditCardMakeForm, final BindingResult binding) {
		final AdminConfig adminConfig;

		adminConfig = this.getAdminConfig();

		final Collection<String> creditCardMakes = adminConfig.getCreditCardMakes();

		if (!(creditCardMakeForm.getCreditCardMake().trim().isEmpty())) {
			if (creditCardMakes.contains(creditCardMakeForm.getCreditCardMake().trim().toUpperCase()))
				binding.rejectValue("creditCardMake", "adminConfig.error.existCreditCardMake");

			creditCardMakes.add(creditCardMakeForm.getCreditCardMake().toUpperCase());
		}
		adminConfig.setCreditCardMakes(creditCardMakes);

		this.validator.validate(adminConfig, binding);

		if (binding.hasErrors())
			throw new ValidationException();

		return adminConfig;
	}
}
