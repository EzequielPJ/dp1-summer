
package forms;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.URL;

import security.UserAccount;

public class ActorForm {

	private String		name;
	private String		middlename;
	private String		surname;
	private String		photoURL;
	private String		phoneNumber;
	private String		address;
	private String		email;

	//Relationship
	private UserAccount	userAccount;
	private String		confirmPassword;
	private boolean		termsAndConditions;


	//Atributes getters and setters
	@NotBlank
	@SafeHtml
	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	@NotBlank
	@SafeHtml
	public String getSurname() {
		return this.surname;
	}

	public void setSurname(final String surname) {
		this.surname = surname;
	}

	@URL
	@SafeHtml
	public String getPhotoURL() {
		return this.photoURL;
	}

	public void setPhotoURL(final String photoURL) {
		this.photoURL = photoURL;
	}

	@NotBlank
	@SafeHtml
	public String getAddress() {
		return this.address;
	}

	public void setAddress(final String address) {
		this.address = address;
	}

	@NotBlank
	@SafeHtml
	public String getPhoneNumber() {
		return this.phoneNumber;
	}

	public void setPhoneNumber(final String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	//Relationship getters and setters
	public UserAccount getUserAccount() {
		return this.userAccount;
	}

	public void setUserAccount(final UserAccount userAccount) {
		this.userAccount = userAccount;
	}

	@NotBlank
	@Size(min = 5, max = 32)
	@SafeHtml
	public String getConfirmPassword() {
		return this.confirmPassword;
	}

	public void setConfirmPassword(final String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	public boolean getTermsAndConditions() {
		return this.termsAndConditions;
	}

	public void setTermsAndConditions(final boolean termsAndConditions) {
		this.termsAndConditions = termsAndConditions;
	}

	@SafeHtml
	public String getMiddlename() {
		return this.middlename;
	}

	public void setMiddlename(final String middlename) {
		this.middlename = middlename;
	}

	@NotBlank
	@Pattern(regexp = "^([0-9a-zA-Z ]{1,}[ ]{1}[<]{1}[0-9a-zA-Z ]{1,}[@]{1}[0-9a-zA-Z.]{1,}[>]{1}|[0-9a-zA-Z ]{1,}[@]{1}[0-9a-zA-Z.]{1,})$")
	@SafeHtml
	public String getEmail() {
		return this.email;
	}

	public void setEmail(final String email) {
		this.email = email;
	}

}
