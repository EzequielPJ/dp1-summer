
package controllers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.ConferenceService;
import services.GomelaService;
import services.RegistrationService;
import services.SponsorshipService;
import utiles.AuthorityMethods;
import domain.Conference;
import domain.Gomela;

@Controller
@RequestMapping("/conference")
public class ConferenceController extends AbstractController {

	@Autowired
	private ConferenceService	conferenceService;

	@Autowired
	private RegistrationService	registrationService;

	@Autowired
	private SponsorshipService	sponsorshipService;

	@Autowired
	private GomelaService		gomelaService;

	@Autowired
	private ActorService		actorService;


	@RequestMapping(value = "/listConferencePast", method = RequestMethod.GET)
	public ModelAndView listConferencePast(@CookieValue(value = "language", required = false) final String lang) {
		final ModelAndView result = this.listModelAndView();
		final Collection<Conference> confFinalMode = this.conferenceService.getConferencesFinalMode();
		final Collection<Conference> myConferences = new ArrayList<Conference>();
		for (final Conference conference : confFinalMode)
			if (conference.getEndDate().before(new Date()))
				myConferences.add(conference);
		result.addObject("conferences", myConferences);
		result.addObject("requestURI", "conference/listConferencePast.do");
		result.addObject("general", true);
		if (lang == null) {
			result.addObject("lang", "en");
			result.addObject("tit", "List conference in the past");
		} else {
			result.addObject("lang", lang);
			result.addObject("tit", "Lista de conferencias pasadas");
		}

		return result;
	}

	@RequestMapping(value = "/listConferenceNow", method = RequestMethod.GET)
	public ModelAndView listConferenceNow(@CookieValue(value = "language", required = false) final String lang) {
		final ModelAndView result = this.listModelAndView();
		final Collection<Conference> confFinalMode = this.conferenceService.getConferencesFinalMode();
		final Collection<Conference> myConferences = new ArrayList<Conference>();
		for (final Conference conference : confFinalMode)
			if (conference.getStartDate().before(new Date()) && conference.getEndDate().after(new Date()))
				myConferences.add(conference);
		result.addObject("conferences", myConferences);
		result.addObject("requestURI", "conference/listConferenceNow.do");
		result.addObject("general", true);
		if (lang == null) {
			result.addObject("lang", "en");
			result.addObject("tit", "List conference now");
		} else {
			result.addObject("lang", lang);
			result.addObject("tit", "Lista de conferencias actuales");
		}

		return result;
	}
	@RequestMapping(value = "/listConferenceFuture", method = RequestMethod.GET)
	public ModelAndView listConferenceFuture(@CookieValue(value = "language", required = false) final String lang) {
		final ModelAndView result = this.listModelAndView();
		final Collection<Conference> confFinalMode = this.conferenceService.getConferencesFinalMode();
		final Collection<Conference> myConferences = new ArrayList<Conference>();
		for (final Conference conference : confFinalMode)
			if (conference.getStartDate().after(new Date()))
				myConferences.add(conference);
		result.addObject("conferences", myConferences);
		result.addObject("requestURI", "conference/listConferenceFuture.do");
		result.addObject("general", true);
		if (lang == null) {
			result.addObject("lang", "en");
			result.addObject("tit", "List conference in the future");
		} else {
			result.addObject("lang", lang);
			result.addObject("tit", "Lista de conferencias en el futuro");
		}
		return result;
	}

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int idConference, @CookieValue(value = "language", required = false) final String lang, @RequestParam final String url) {
		ModelAndView result = null;

		final Conference conference = this.conferenceService.findOne(idConference);

		result = new ModelAndView("conference/display");

		result.addObject("conference", conference);
		result.addObject("sponsorshipRandom", this.sponsorshipService.getRandomOfAConference(idConference));
		result.addObject("requestURI", "conference/display.do?idConference=" + idConference);
		result.addObject("url", url);
		result.addObject("general", true);
		if (lang == null)
			result.addObject("lang", "en");
		else
			result.addObject("lang", lang);

		if (conference.getStartDate().before(new Date()))
			result.addObject("avaliable", false);
		else if (AuthorityMethods.checkIsSomeoneLogged())
			if (AuthorityMethods.chechAuthorityLogged("AUTHOR"))
				result.addObject("avaliable", !this.registrationService.alreadyRegister(conference));
			else
				result.addObject("avaliable", false);

		//CONTROL CHECK gomela/////////////////////////

		Collection<Gomela> gomelas = new ArrayList<>();

		gomelas = this.gomelaService.getGomelasOfConferenceFinalMode(idConference);

		result.addObject("gomelas", gomelas);

		result.addObject("bot", false);

		final Date currentDate = new Date();
		final Date aMonthAgo = new Date(currentDate.getTime() - 2629746000l);
		result.addObject("aMonthAgo", aMonthAgo);
		final Date twoMonthAgo = new Date(currentDate.getTime() - 5259492000l);
		result.addObject("twoMonthAgo", twoMonthAgo);

		////////////////////////////////

		this.configValues(result);
		return result;
	}
	protected ModelAndView listModelAndView() {
		return this.listModelAndView(null);
	}

	protected ModelAndView listModelAndView(final String message) {
		final ModelAndView result = new ModelAndView("conference/list");

		result.addObject("message", message);

		this.configValues(result);

		return result;
	}
}
