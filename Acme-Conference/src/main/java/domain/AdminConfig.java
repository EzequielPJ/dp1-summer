
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.URL;

import forms.AdminConfigForm;

@Entity
@Access(AccessType.PROPERTY)
public class AdminConfig extends DomainEntity {

	private String				systemName;
	private String				bannerURL;
	private String				welcomeMsgEN;
	private String				welcomeMsgES;
	private String				countryCode;
	private Collection<String>	creditCardMakes;
	private Collection<String>	voidWordsES;
	private Collection<String>	voidWordsEN;


	@NotBlank
	@SafeHtml
	public String getSystemName() {
		return this.systemName;
	}

	public void setSystemName(final String systemName) {
		this.systemName = systemName;
	}

	@NotBlank
	@URL
	@SafeHtml
	public String getBannerURL() {
		return this.bannerURL;
	}

	public void setBannerURL(final String bannerURL) {
		this.bannerURL = bannerURL;
	}

	@NotBlank
	@SafeHtml
	public String getWelcomeMsgEN() {
		return this.welcomeMsgEN;
	}

	public void setWelcomeMsgEN(final String welcomeMsgEN) {
		this.welcomeMsgEN = welcomeMsgEN;
	}

	@NotBlank
	@SafeHtml
	public String getWelcomeMsgES() {
		return this.welcomeMsgES;
	}

	public void setWelcomeMsgES(final String welcomeMsgES) {
		this.welcomeMsgES = welcomeMsgES;
	}

	@NotBlank
	@SafeHtml
	@Pattern(regexp = "^(\\+[1-9]|\\+[1-9][0-9]|\\+[1-9][0-9][0-9])$")
	public String getCountryCode() {
		return this.countryCode;
	}

	public void setCountryCode(final String countryCode) {
		this.countryCode = countryCode;
	}

	@ElementCollection
	public Collection<String> getVoidWordsES() {
		return this.voidWordsES;
	}

	public void setVoidWordsES(final Collection<String> voidWordsES) {
		this.voidWordsES = voidWordsES;
	}

	@ElementCollection
	public Collection<String> getVoidWordsEN() {
		return this.voidWordsEN;
	}

	public void setVoidWordsEN(final Collection<String> voidWordsEN) {
		this.voidWordsEN = voidWordsEN;
	}

	@ElementCollection
	public Collection<String> getCreditCardMakes() {
		return this.creditCardMakes;
	}

	public void setCreditCardMakes(final Collection<String> creditCardMakes) {
		this.creditCardMakes = creditCardMakes;
	}

	public AdminConfigForm castToForm() {
		final AdminConfigForm adminConfigForm = new AdminConfigForm();
		adminConfigForm.setBannerURL(this.getBannerURL());
		adminConfigForm.setCountryCode(this.getCountryCode());
		adminConfigForm.setSystemName(this.getSystemName());
		adminConfigForm.setWelcomeMsgEN(this.getWelcomeMsgEN());
		adminConfigForm.setWelcomeMsgES(this.getWelcomeMsgES());

		return adminConfigForm;

	}

}
