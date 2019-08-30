
package services;

import java.util.Collection;
import java.util.Date;

import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.QuoletRepository;
import security.LoginService;
import utiles.AuthorityMethods;
import utiles.IntermediaryBetweenTransactions;
import domain.Administrator;
import domain.Conference;
import domain.Quolet;
import domain.Ticker;

@Service
@Transactional
public class QuoletService {

	@Autowired
	QuoletRepository				quoletRepository;

	@Autowired
	AdministratorService			adminService;

	@Autowired
	ConferenceService				conferenceService;

	@Autowired
	Validator						validator;

	@Autowired
	IntermediaryBetweenTransactions	intermediaryBetweenTransactions;


	public Quolet create(final int idConference) {
		Assert.isTrue(AuthorityMethods.chechAuthorityLogged("ADMINISTRATOR"), "Debe ser un administrador");

		final Conference conference = this.conferenceService.findOne(idConference);

		Assert.isTrue(conference.getFinalMode(), "La conferencia debe estar en modo final");

		final Administrator administratorLogged = this.adminService.findByPrincipal(LoginService.getPrincipal());
		Assert.isTrue(conference.getAdministrator().equals(administratorLogged), "Debe ser el dueño de la conferencia");

		final Quolet quolet = new Quolet();
		quolet.setConference(conference);

		return quolet;
	}

	public Quolet findOne(final int idQuolet) {
		final Quolet quolet = this.quoletRepository.findOne(idQuolet);

		if (!quolet.getFinalMode()) {
			final Administrator administratorLogged = this.adminService.findByPrincipal(LoginService.getPrincipal());
			Assert.isTrue(quolet.getConference().getAdministrator().equals(administratorLogged), "Debe ser el dueño de la quolet");
		}

		return quolet;
	}

	public Quolet reconstruct(final Quolet quolet, final BindingResult bindingResult) {
		Quolet quoletRes;
		if (quolet.getId() == 0) {
			final Ticker ticker = this.intermediaryBetweenTransactions.generateTickerQuolet();
			quoletRes = new Quolet();
			quoletRes.setTicker(ticker);
			quoletRes.setConference(quolet.getConference());
		} else {
			quoletRes = this.findOne(quolet.getId());
			Assert.isTrue(!quoletRes.getFinalMode());
		}

		quoletRes.setAtributoDos(quolet.getAtributoDos());
		quoletRes.setBody(quolet.getBody());
		quoletRes.setFinalMode(quolet.getFinalMode());
		quoletRes.setTitle(quolet.getTitle());

		if (quolet.getFinalMode())
			quoletRes.setPublicationMoment(new Date());

		this.validator.validate(quoletRes, bindingResult);

		if (bindingResult.hasErrors())
			throw new ValidationException();

		return this.save(quoletRes);
	}

	public Quolet save(final Quolet quolet) {

		Assert.isTrue(AuthorityMethods.chechAuthorityLogged("ADMINISTRATOR"), "Debe ser un administrador");

		final Administrator administratorLogged = this.adminService.findByPrincipal(LoginService.getPrincipal());
		Assert.isTrue(quolet.getConference().getAdministrator().equals(administratorLogged), "Debe ser el dueño de la conferencia");

		Assert.isTrue(quolet.getConference().getFinalMode(), "La conferencia debe estar en modo final");

		return this.quoletRepository.save(quolet);
	}

	public void delete(final int idQuolet) {

		final Quolet quolet = this.quoletRepository.findOne(idQuolet);

		Assert.isTrue(AuthorityMethods.chechAuthorityLogged("ADMINISTRATOR"), "Debe ser un administrador");

		final Administrator administratorLogged = this.adminService.findByPrincipal(LoginService.getPrincipal());
		Assert.isTrue(quolet.getConference().getAdministrator().equals(administratorLogged), "Debe ser el dueño de la quolet");

		Assert.isTrue(!quolet.getFinalMode(), "Debe estar en modo borrador");

		this.quoletRepository.delete(quolet);
	}

	public Collection<Quolet> getQuoletsOfAdminLogged() {
		Assert.isTrue(AuthorityMethods.chechAuthorityLogged("ADMINISTRATOR"));
		final Administrator administrator = this.adminService.findByPrincipal(LoginService.getPrincipal());
		return this.quoletRepository.getQuoletsOfAdmin(administrator.getId());
	}

	public Collection<Quolet> getQuoletsOfConferenceFinalMode(final int idConference) {
		return this.quoletRepository.getQuoletsOfConferenceFinalMode(idConference);
	}

	public Collection<Quolet> getQuoletsOfConferenceAll(final int idConference) {
		return this.quoletRepository.getQuoletsOfConferenceAll(idConference);
	}

}
