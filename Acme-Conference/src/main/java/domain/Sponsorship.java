
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

@Entity
@Access(AccessType.PROPERTY)
public class Sponsorship extends DomainEntity {

	private String					bannerURL;
	private String					targetURL;
	private CreditCard				creditCard;

	private ConferenceSponsor		conferenceSponsor;
	private Collection<Conference>	conferences;


	@URL
	@NotBlank
	@Lob
	public String getBannerURL() {
		return this.bannerURL;
	}

	public void setBannerURL(final String bannerURL) {
		this.bannerURL = bannerURL;
	}

	@URL
	@NotBlank
	@Lob
	public String getTargetURL() {
		return this.targetURL;
	}

	public void setTargetURL(final String targetURL) {
		this.targetURL = targetURL;
	}

	@Valid
	@NotNull
	public CreditCard getCreditCard() {
		return this.creditCard;
	}

	public void setCreditCard(final CreditCard creditCard) {
		this.creditCard = creditCard;
	}

	@Valid
	@ManyToOne(optional = false)
	public ConferenceSponsor getConferenceSponsor() {
		return this.conferenceSponsor;
	}

	public void setConferenceSponsor(final ConferenceSponsor conferenceSponsor) {
		this.conferenceSponsor = conferenceSponsor;
	}

	@Valid
	@ManyToMany
	public Collection<Conference> getConferences() {
		return this.conferences;
	}

	public void setConferences(final Collection<Conference> conferences) {
		this.conferences = conferences;
	}

}
