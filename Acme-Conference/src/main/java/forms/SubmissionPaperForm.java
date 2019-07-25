
package forms;

import java.util.Collection;

import javax.persistence.ElementCollection;
import javax.persistence.ManyToOne;
import javax.validation.Valid;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.URL;

import domain.Conference;

public class SubmissionPaperForm {

	//Atributes
	private String				title;
	private Collection<String>	aliases;
	private String				summary;
	private String				documentUrl;

	//Relationship
	private Conference			conference;


	@Valid
	@ManyToOne(optional = false)
	public Conference getConference() {
		return this.conference;
	}

	public void setConference(final Conference conference) {
		this.conference = conference;
	}

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
	public String getDocumentUrl() {
		return this.documentUrl;
	}

	public void setDocumentUrl(final String documentUrl) {
		this.documentUrl = documentUrl;
	}

}
