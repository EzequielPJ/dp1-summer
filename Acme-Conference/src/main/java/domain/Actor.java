
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToOne;
import javax.validation.Valid;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.URL;

import security.UserAccount;

@Entity
@Access(AccessType.PROPERTY)
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
//@Table(indexes = {
////	@Index(columnList = "spammer")
//	})
public abstract class Actor extends DomainEntity {

	//Atributes
	private String		name;
	private String		middlename;
	private String		surname;
	private String		photoURL;
	private String		email;
	private String		phoneNumber;
	private String		address;

	//Relationship
	private UserAccount	userAccount;


	//Atributes getters and setters
	@NotBlank
	@SafeHtml
	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	@SafeHtml
	public String getMiddlename() {
		return this.middlename;
	}

	public void setMiddlename(final String middlename) {
		this.middlename = middlename;
	}

	@SafeHtml
	@NotBlank
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

	@SafeHtml
	public String getAddress() {
		return this.address;
	}

	public void setAddress(final String address) {
		this.address = address;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(final String email) {
		this.email = email;
	}

	@SafeHtml
	public String getPhoneNumber() {
		return this.phoneNumber;
	}

	public void setPhoneNumber(final String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	//Relationship getters and setters
	@Valid
	@OneToOne(optional = false)
	public UserAccount getUserAccount() {
		return this.userAccount;
	}

	public void setUserAccount(final UserAccount userAccount) {
		this.userAccount = userAccount;
	}

}
