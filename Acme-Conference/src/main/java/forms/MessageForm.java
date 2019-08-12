
package forms;

import java.util.Collection;

import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;

import domain.Actor;
import domain.DomainEntity;
import domain.Topic;

public class MessageForm extends DomainEntity {

	private String	subject;
	private String	body;
	private String	broadcastType;


	@Pattern(regexp = "^ALL-ACTORS|ALL-AUTHORS|AUTHORS-WITH-REGISTRATIONS|AUTHORS-WITH-SUBMISSIONS$")
	public String getBroadcastType() {
		return this.broadcastType;
	}

	public void setBroadcastType(final String broadcastType) {
		this.broadcastType = broadcastType;
	}


	//Relationships
	private Collection<Topic>	topics;
	private Collection<Actor>	recipients;


	@ManyToMany
	@NotNull
	@Valid
	public Collection<Actor> getRecipients() {
		return this.recipients;
	}

	public void setRecipients(final Collection<Actor> recipients) {
		this.recipients = recipients;
	}

	@NotBlank
	@SafeHtml
	public String getSubject() {
		return this.subject;
	}

	public void setSubject(final String subject) {
		this.subject = subject;
	}

	@NotBlank
	@Lob
	@SafeHtml
	public String getBody() {
		return this.body;
	}

	public void setBody(final String body) {
		this.body = body;
	}

	@Valid
	@ManyToMany
	public Collection<Topic> getTopics() {
		return this.topics;
	}

	public void setTopics(final Collection<Topic> topics) {
		this.topics = topics;
	}

}
