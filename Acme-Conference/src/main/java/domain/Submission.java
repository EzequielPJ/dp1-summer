
package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Submission extends DomainEntity {

	//Atributes
	private Date					moment;
	private String					status;
	private boolean					notified;

	//Relationship
	private Conference				conference;
	private Collection<Reviewer>	reviewers;
	private Ticker					ticker;
	private Author					author;


	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy/MM/dd HH:mm:ss")
	public Date getMoment() {
		return this.moment;
	}

	public void setMoment(final Date moment) {
		this.moment = moment;
	}

	@Pattern(regexp = "^REJECTED|ACCEPTED|UNDER-REVIEW$")
	@NotBlank
	@SafeHtml
	public String getStatus() {
		return this.status;
	}

	public void setStatus(final String status) {
		this.status = status;
	}

	@Valid
	@ManyToOne(optional = false)
	public Conference getConference() {
		return this.conference;
	}

	public void setConference(final Conference conference) {
		this.conference = conference;
	}

	@Valid
	@ManyToMany
	public Collection<Reviewer> getReviewers() {
		return this.reviewers;
	}

	public void setReviewers(final Collection<Reviewer> reviewers) {
		this.reviewers = reviewers;
	}

	@Valid
	@OneToOne(fetch = FetchType.LAZY, optional = false)
	public Ticker getTicker() {
		return this.ticker;
	}

	public void setTicker(final Ticker ticker) {
		this.ticker = ticker;
	}

	@Valid
	@ManyToOne(optional = false)
	public Author getAuthor() {
		return this.author;
	}

	public void setAuthor(final Author author) {
		this.author = author;
	}

	public boolean getNotified() {
		return this.notified;
	}

	public void setNotified(final boolean notified) {
		this.notified = notified;
	}

}
