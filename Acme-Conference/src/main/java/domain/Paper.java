
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.validation.Valid;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.URL;

@Entity
@Access(AccessType.PROPERTY)
public class Paper extends DomainEntity {

	//Atributes
	private String				title;
	private Collection<String>	aliases;
	private String				summary;
	private String				documentUrl;
	private boolean				cameraReadyPaper;

	//Relationship
	private Collection<Author>	authors;
	private Submission			submission;


	@NotBlank
	@SafeHtml
	public String getTitle() {
		return this.title;
	}

	public void setTitle(final String title) {
		this.title = title;
	}

	@ElementCollection
	public Collection<String> getAliases() {
		return this.aliases;
	}

	public void setAliases(final Collection<String> aliases) {
		this.aliases = aliases;
	}

	@NotBlank
	@SafeHtml
	public String getSummary() {
		return this.summary;
	}

	public void setSummary(final String summary) {
		this.summary = summary;
	}

	@URL
	@SafeHtml
	@NotBlank
	public String getDocumentUrl() {
		return this.documentUrl;
	}

	public void setDocumentUrl(final String documentUrl) {
		this.documentUrl = documentUrl;
	}

	public boolean getCameraReadyPaper() {
		return this.cameraReadyPaper;
	}

	public void setCameraReadyPaper(final boolean cameraReadyPaper) {
		this.cameraReadyPaper = cameraReadyPaper;
	}

	@Valid
	@ManyToMany
	@NotEmpty
	public Collection<Author> getAuthors() {
		return this.authors;
	}

	public void setAuthors(final Collection<Author> authors) {
		this.authors = authors;
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
