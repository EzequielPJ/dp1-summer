
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.Pattern;

@Entity
@Access(AccessType.PROPERTY)
public class Ticker extends DomainEntity {

	private String	identifier;


	//Gomela: añadir con un | el patron que toque 
	@Pattern(regexp = "^([A-Z0-9]{3}-[A-Z0-9]{4})|([a-zA-Z0-9]{2}[0-9]{2}-(0[1-9]|1[0-2])(0[1-9]|1[0-9]|2[0-9]|3[01])[0-9]{2})$")
	@Column(unique = true)
	public String getIdentifier() {
		return this.identifier;
	}

	public void setIdentifier(final String identifier) {
		this.identifier = identifier;
	}
}
