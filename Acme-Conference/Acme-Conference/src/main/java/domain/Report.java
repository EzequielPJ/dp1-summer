
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.SafeHtml;

@Entity
@Access(AccessType.PROPERTY)
public class Report extends DomainEntity {

	//Atributes
	private Integer				originalityScore;
	private Integer				qualityScore;
	private Integer				readibilityScore;
	private String				decision;
	private Collection<String>	comments;

	//Relationship

	private Submission			submission;


	@Range(min = 0, max = 10)
	public Integer getOriginalityScore() {
		return this.originalityScore;
	}

	public void setOriginalityScore(final Integer originalityScore) {
		this.originalityScore = originalityScore;
	}

	@Range(min = 0, max = 10)
	public Integer getQualityScore() {
		return this.qualityScore;
	}

	public void setQualityScore(final Integer qualityScore) {
		this.qualityScore = qualityScore;
	}

	@Range(min = 0, max = 10)
	public Integer getReadibilityScore() {
		return this.readibilityScore;
	}

	public void setReadibilityScore(final Integer readibilityScore) {
		this.readibilityScore = readibilityScore;
	}

	@NotBlank
	@SafeHtml
	@Pattern(regexp = "^BORDER-LINE|REJECT|ACCEPT$")
	public String getDecision() {
		return this.decision;
	}

	public void setDecision(final String decision) {
		this.decision = decision;
	}

	@ElementCollection
	public Collection<String> getComments() {
		return this.comments;
	}

	public void setComments(final Collection<String> comments) {
		this.comments = comments;
	}

	@Valid
	@ManyToOne(optional = false)
	public Submission getSubmission() {
		return this.submission;
	}

	public void setSubmission(final Submission submission) {
		this.submission = submission;
	}

}
