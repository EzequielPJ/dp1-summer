
package forms;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.URL;

public class AdminConfigForm {

	private String	systemName;
	private String	bannerURL;
	private String	welcomeMsgEN;
	private String	welcomeMsgES;
	private String	countryCode;


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

}
