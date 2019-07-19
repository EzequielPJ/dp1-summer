
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;

@Entity
@Access(AccessType.PROPERTY)
public class Topic extends DomainEntity {

	private String	topicES;
	private String	topicEN;


	@NotBlank
	@SafeHtml
	public String getTopicES() {
		return this.topicES;
	}

	public void setTopicES(final String topicES) {
		this.topicES = topicES;
	}

	@NotBlank
	@SafeHtml
	public String getTopicEN() {
		return this.topicEN;
	}

	public void setTopicEN(final String topicEN) {
		this.topicEN = topicEN;
	}
}
