
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;

@Entity
@Access(AccessType.PROPERTY)
public class Reviewer extends Actor {

	private Collection<String>	expertiseKeywordsList;


	@ElementCollection
	public Collection<String> getExpertiseKeywordsList() {
		return this.expertiseKeywordsList;
	}

	public void setExpertiseKeywordsList(final Collection<String> expertiseKeywordsList) {
		this.expertiseKeywordsList = expertiseKeywordsList;
	}

}
