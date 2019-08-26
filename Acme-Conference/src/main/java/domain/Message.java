
package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.SafeHtml;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Message extends DomainEntity {

	private Date				moment;
	private String				subject;
	private String				body;

	//Relationships
	private Collection<Topic>	topics;
	private Actor				sender;
	private Collection<Actor>	recipients;
	private Collection<Actor>	actors;


	@NotFound(action = NotFoundAction.IGNORE)
	@Valid
	@ManyToOne(optional = false, fetch = FetchType.EAGER)
	public Actor getSender() {
		return this.sender;
	}

	public void setSender(final Actor sender) {
		this.sender = sender;
	}

	@ManyToMany(fetch = FetchType.EAGER)
	@Valid
	public Collection<Actor> getRecipients() {
		return this.recipients;
	}

	public void setRecipients(final Collection<Actor> recipients) {
		this.recipients = recipients;
	}

	@ManyToMany(fetch = FetchType.EAGER)
	@Valid
	public Collection<Actor> getActors() {
		return this.actors;
	}

	public void setActors(final Collection<Actor> actors) {
		this.actors = actors;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy/MM/dd HH:mm:ss")
	@NotNull
	public Date getMoment() {
		return this.moment;
	}

	public void setMoment(final Date moment) {
		this.moment = moment;
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
	@NotEmpty
	public Collection<Topic> getTopics() {
		return this.topics;
	}

	public void setTopics(final Collection<Topic> topics) {
		this.topics = topics;
	}

}
