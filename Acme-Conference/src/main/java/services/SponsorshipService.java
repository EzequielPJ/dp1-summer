
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.SponsorshipRepository;
import security.LoginService;
import utiles.ValidateCreditCard;
import domain.Conference;
import domain.CreditCard;
import domain.Sponsorship;

@Service
@Transactional
public class SponsorshipService {

	@Autowired
	private SponsorshipRepository		sponsorshipRepository;

	@Autowired
	private ConferenceSponsorService	sponsorService;

	@Autowired
	private ConferenceService			conferenceService;

	@Autowired
	private AdminConfigService			adminConfigService;

	@Autowired
	private Validator					validator;


	// CRUD methods
	//---------------------------------------------------------------------------------------
	public Sponsorship create() {
		return new Sponsorship();
	}

	public void save(final Sponsorship sponsorship) {
		if (sponsorship.getConferences() == null)
			sponsorship.setConferences(new ArrayList<Conference>());
		Assert.isTrue(LoginService.getPrincipal().equals(sponsorship.getConferenceSponsor().getUserAccount()));
		Assert.isTrue(this.conferenceService.getConferencesFinalMode().containsAll(sponsorship.getConferences()));
		Assert.isTrue(!ValidateCreditCard.isCaducate(sponsorship.getCreditCard()));

		final Boolean correctMake = this.adminConfigService.getAdminConfig().getCreditCardMakes().contains(sponsorship.getCreditCard().getBrandName());
		if (sponsorship.getId() == 0)
			Assert.isTrue(correctMake);
		else
			Assert.isTrue(correctMake || this.findOne(sponsorship.getId()).getCreditCard().getBrandName() == sponsorship.getCreditCard().getBrandName());

		this.sponsorshipRepository.save(sponsorship);
	}
	public Sponsorship reconstruct(final Sponsorship sponsorship, final BindingResult binding) {
		Sponsorship result;

		if (sponsorship.getId() == 0) {
			result = this.create();
			result.setConferenceSponsor(this.sponsorService.findByPrincipal(LoginService.getPrincipal()));
		} else
			result = this.findOne(sponsorship.getId());

		result.setBannerURL(sponsorship.getBannerURL());
		result.setTargetURL(sponsorship.getTargetURL());
		final CreditCard creditCard = this.reconstructCVV(sponsorship.getCreditCard());
		result.setCreditCard(creditCard);
		result.setConferences(sponsorship.getConferences());

		ValidateCreditCard.checkGregorianDate(result.getCreditCard(), binding);

		this.validator.validate(result, binding);

		if (binding.hasErrors())
			throw new ValidationException();

		return result;
	}
	//---------------------------------------------------------------------------------------

	// Auxiliar methods
	//---------------------------------------------------------------------------------------
	public Collection<Sponsorship> findAllBySponsor(final int idSponsor) {
		Assert.isTrue(this.sponsorService.findOne(idSponsor).getUserAccount().equals(LoginService.getPrincipal()));
		return this.sponsorshipRepository.findAllBySponsor(idSponsor);
	}

	public Sponsorship findOne(final int idSponsorship) {
		final Sponsorship sponsorship = this.sponsorshipRepository.findOne(idSponsorship);
		Assert.isTrue(LoginService.getPrincipal().equals(sponsorship.getConferenceSponsor().getUserAccount()));
		return sponsorship;
	}

	public Sponsorship getRandomOfAConference(final int idConference) {
		Sponsorship res = null;
		final List<Sponsorship> sponsorships = this.findAllByConference(idConference);

		if (sponsorships.size() != 0) {
			final int index = ThreadLocalRandom.current().nextInt(sponsorships.size());
			res = sponsorships.get(index);
		}

		return res;
	}

	public List<Sponsorship> findAllByConference(final int idConference) {
		return this.sponsorshipRepository.findAllByConference(idConference);
	}

	public void delete(final Sponsorship sponsorship) {
		Assert.isTrue(LoginService.getPrincipal().equals(sponsorship.getConferenceSponsor().getUserAccount()));
		this.sponsorshipRepository.delete(sponsorship);
	}

	public void flush() {
		this.sponsorshipRepository.flush();
	}

	public Collection<Sponsorship> getSponsorshipsOfConference(final int idConference) {
		return this.sponsorshipRepository.getSponsorshipsOfConference(idConference);
	}

	private CreditCard reconstructCVV(final CreditCard creditCard) {
		String cvv = creditCard.getCvv();

		switch (cvv.length()) {
		case 1:
			cvv = "00" + cvv;
			break;

		case 2:
			cvv = "0" + cvv;
			break;

		default:
			break;
		}

		creditCard.setCvv(cvv);

		return creditCard;
	}
	//---------------------------------------------------------------------------------------

}
