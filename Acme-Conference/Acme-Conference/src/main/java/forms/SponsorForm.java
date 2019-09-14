
package forms;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;

import domain.CreditCard;

public class SponsorForm extends ActorForm {

	private String		email;
	private String		companyName;
	private CreditCard	creditCard;


	@NotBlank
	@SafeHtml
	public String getCompanyName() {
		return this.companyName;
	}

	public void setCompanyName(final String companyName) {
		this.companyName = companyName;
	}

	@Valid
	public CreditCard getCreditCard() {
		return this.creditCard;
	}

	public void setCreditCard(final CreditCard creditCard) {
		this.creditCard = creditCard;
	}

	@NotBlank
	@Pattern(regexp = "^([0-9a-zA-Z ]{1,}[ ]{1}[<]{1}[0-9a-zA-Z ]{1,}[@]{1}[0-9a-zA-Z.]{1,}[>]{1}|[0-9a-zA-Z ]{1,}[@]{1}[0-9a-zA-Z.]{1,})$")
	public String getEmail() {
		return this.email;
	}

	public void setEmail(final String email) {
		this.email = email;
	}
}
