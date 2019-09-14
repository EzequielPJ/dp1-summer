
package services;

import java.util.Collection;
import java.util.Date;

import javax.transaction.Transactional;
import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.RegistrationRepository;
import security.LoginService;
import utiles.AuthorityMethods;
import utiles.ValidateCreditCard;
import domain.Author;
import domain.Conference;
import domain.CreditCard;
import domain.Registration;

@Service
@Transactional
public class RegistrationService {

	@Autowired
	private RegistrationRepository	registrationRepository;

	@Autowired
	private AuthorService			authorService;

	@Autowired
	private AdminConfigService		adminConfigService;

	@Autowired
	private Validator				validator;


	public Registration create(final Conference conference) {
		final Registration res = new Registration();

		res.setAuthor(this.authorService.findByPrincipal(LoginService.getPrincipal()));
		res.setConference(conference);
		res.setMoment(new Date());

		return res;
	}

	public Registration save(final Registration registration) {
		Assert.isTrue(registration != null);
		Assert.isTrue(AuthorityMethods.checkIsSomeoneLogged());
		Assert.isTrue(registration.getAuthor().getId() == this.authorService.findByPrincipal(LoginService.getPrincipal()).getId());

		Assert.isTrue(!ValidateCreditCard.isCaducate(registration.getCreditCard()));
		Assert.isTrue(registration.getConference().getStartDate().after(registration.getMoment()));

		Assert.isTrue(!this.alreadyRegister(registration.getConference()));

		registration.setMoment(new Date());
		final Registration res = this.registrationRepository.save(registration);
		this.registrationRepository.flush();
		return res;
	}

	public Registration reconstruct(final Registration registration, final BindingResult binding) {

		final Registration res = new Registration();

		res.setId(0);
		res.setAuthor(registration.getAuthor());
		res.setConference(registration.getConference());

		final CreditCard creditCard = this.reconstructCVV(registration.getCreditCard());
		res.setCreditCard(creditCard);
		ValidateCreditCard.checkGregorianDate(res.getCreditCard(), binding);

		if (!this.adminConfigService.getAdminConfig().getCreditCardMakes().contains(registration.getCreditCard().getBrandName()))
			binding.rejectValue("creditCard.brandName", "registration.create.brandName.error");

		res.setMoment(new Date());

		this.validator.validate(res, binding);

		if (binding.hasErrors())
			throw new ValidationException();

		return res;

	}

	public void flush() {
		this.registrationRepository.flush();
	}

	public Registration findOne(final int registrationId) {
		return this.registrationRepository.findOne(registrationId);
	}

	public Collection<Registration> findRegistrationFromConference(final int conferenceId) {
		return this.registrationRepository.findRegistrationFromConference(conferenceId);
	}

	public Collection<Registration> findRegistrationFromAuthor(final int authorId) {
		return this.registrationRepository.findRegistrationFromAuthor(authorId);
	}

	public void delete(final Registration registration) {
		Assert.isTrue(registration != null);
		Assert.isTrue(AuthorityMethods.checkIsSomeoneLogged());
		Assert.isTrue(registration.getAuthor().getId() == this.authorService.findByPrincipal(LoginService.getPrincipal()).getId());

		this.registrationRepository.delete(registration);
	}

	public boolean alreadyRegister(final Conference conference) {
		final Author author = this.authorService.findByPrincipal(LoginService.getPrincipal());
		final Collection<Registration> registrations = this.findRegistrationFromAuthor(author.getId());

		for (final Registration r : registrations)
			if (r.getConference().getId() == conference.getId())
				return true;

		return false;
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

}
