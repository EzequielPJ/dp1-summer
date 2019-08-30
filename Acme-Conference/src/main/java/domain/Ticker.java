
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
@Access(AccessType.PROPERTY)
public class Ticker extends DomainEntity {

	private String	identifier;


	//Quolet: añadir con un | el patron que toque 
	//@Pattern(regexp = "^[A-Z0-9]{3}-[A-Z0-9]{4}$")
	@Column(unique = true)
	public String getIdentifier() {
		return this.identifier;
	}

	public void setIdentifier(final String identifier) {
		this.identifier = identifier;
	}
}
