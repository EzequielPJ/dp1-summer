
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

import repositories.GomelaRepository;
import security.LoginService;
import utiles.AuthorityMethods;
import utiles.IntermediaryBetweenTransactions;
import domain.Administrator;
import domain.Conference;
import domain.Gomela;
import domain.Ticker;

@Service
@Transactional
public class GomelaService {

	@Autowired
	GomelaRepository				gomelaRepository;

	@Autowired
	AdministratorService			adminService;

	@Autowired
	ConferenceService				conferenceService;

	@Autowired
	Validator						validator;

	@Autowired
	IntermediaryBetweenTransactions	intermediaryBetweenTransactions;


	public Gomela create(final int idConference) {
		Assert.isTrue(AuthorityMethods.chechAuthorityLogged("ADMINISTRATOR"), "Debe ser un administrador");

		final Conference conference = this.conferenceService.findOne(idConference);

		Assert.isTrue(conference.getFinalMode(), "La conferencia debe estar en modo final");

		final Administrator administratorLogged = this.adminService.findByPrincipal(LoginService.getPrincipal());
		Assert.isTrue(conference.getAdministrator().equals(administratorLogged), "Debe ser el dueño de la conferencia");

		final Gomela gomela = new Gomela();
		gomela.setConference(conference);

		return gomela;
	}

	public Gomela findOne(final int idGomela) {
		final Gomela gomela = this.gomelaRepository.findOne(idGomela);

		if (!gomela.getFinalMode()) {
			final Administrator administratorLogged = this.adminService.findByPrincipal(LoginService.getPrincipal());
			Assert.isTrue(gomela.getConference().getAdministrator().equals(administratorLogged), "Debe ser el dueño de la gomela");
		}

		return gomela;
	}

	public Gomela reconstruct(final Gomela gomela, final BindingResult bindingResult) {
		Gomela gomelaRes;
		if (gomela.getId() == 0) {
			final Ticker ticker = this.intermediaryBetweenTransactions.generateTickerGomela();
			gomelaRes = new Gomela();
			gomelaRes.setTicker(ticker);
			gomelaRes.setConference(gomela.getConference());
		} else {
			gomelaRes = this.findOne(gomela.getId());
			Assert.isTrue(!gomelaRes.getFinalMode());
		}

		gomelaRes.setBody(gomela.getBody());
		gomelaRes.setFinalMode(gomela.getFinalMode());
		gomelaRes.setPicture(gomela.getPicture());

		//		final Pattern pattern1 = Pattern.compile("^\\w{2,4}:(\\d{2}):([0]?[1-9]|[1][0-2])([0]?[1-9]|[1|2][0-9]|[3][0|1])$");
		//		final Matcher matcher1 = pattern1.matcher((CharSequence) gomelaRes.getTicker());
		//		if (!matcher1.find())
		//			bindingResult.rejectValue("ticker", "ticker.error");

		if (gomela.getFinalMode())
			gomelaRes.setPublicationMoment(new Date());

		this.validator.validate(gomelaRes, bindingResult);

		if (bindingResult.hasErrors())
			throw new ValidationException();

		return this.save(gomelaRes);
	}
	public Gomela save(final Gomela gomela) {

		Assert.isTrue(AuthorityMethods.chechAuthorityLogged("ADMINISTRATOR"), "Debe ser un administrador");

		final Administrator administratorLogged = this.adminService.findByPrincipal(LoginService.getPrincipal());
		Assert.isTrue(gomela.getConference().getAdministrator().equals(administratorLogged), "Debe ser el dueño de la conferencia");

		Assert.isTrue(gomela.getConference().getFinalMode(), "La conferencia debe estar en modo final");

		return this.gomelaRepository.save(gomela);
	}

	public void delete(final int idGomela) {

		final Gomela gomela = this.gomelaRepository.findOne(idGomela);

		Assert.isTrue(AuthorityMethods.chechAuthorityLogged("ADMINISTRATOR"), "Debe ser un administrador");

		final Administrator administratorLogged = this.adminService.findByPrincipal(LoginService.getPrincipal());
		Assert.isTrue(gomela.getConference().getAdministrator().equals(administratorLogged), "Debe ser el dueño de la gomela");

		Assert.isTrue(!gomela.getFinalMode(), "Debe estar en modo borrador");

		this.gomelaRepository.delete(gomela);
	}

	public Collection<Gomela> getGomelasOfAdminLogged() {
		Assert.isTrue(AuthorityMethods.chechAuthorityLogged("ADMINISTRATOR"));
		final Administrator administrator = this.adminService.findByPrincipal(LoginService.getPrincipal());
		return this.gomelaRepository.getGomelasOfAdmin(administrator.getId());
	}

	public Collection<Gomela> getGomelasOfConferenceFinalMode(final int idConference) {
		return this.gomelaRepository.getGomelasOfConferenceFinalMode(idConference);
	}

	public Collection<Gomela> getGomelasOfConferenceAll(final int idConference) {
		return this.gomelaRepository.getGomelasOfConferenceAll(idConference);
	}

}
