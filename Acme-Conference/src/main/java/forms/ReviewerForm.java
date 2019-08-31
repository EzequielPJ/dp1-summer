
package forms;

import java.util.Collection;

import javax.persistence.ElementCollection;

public class ReviewerForm extends ActorForm {

	private Collection<String>	expertiseKeywordsList;


	@ElementCollection
	public Collection<String> getExpertiseKeywordsList() {
		return this.expertiseKeywordsList;
	}

	public void setExpertiseKeywordsList(final Collection<String> expertiseKeywordsList) {
		this.expertiseKeywordsList = expertiseKeywordsList;
	}

}
