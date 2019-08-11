
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

import services.CommentService;
import services.ConferenceService;
import services.SponsorshipService;
import domain.Conference;

@Controller
@RequestMapping("/conference")
public class ConferenceController extends AbstractController {

	@Autowired
	private ConferenceService	conferenceService;

	@Autowired
	private CommentService		commentService;

	@Autowired
	private SponsorshipService	sponsorshipService;


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

		/////////////Parte de comment/////////////
		//		Collection<Comment> comments = null;
		//		final int idEntity = idConference;
		//		comments = this.commentService.getCommentsByConference(idEntity);
		//
		//		result.addObject("comments", comments);
		//
		//		if (lang == null)
		//			result.addObject("anonim", "anonymous");
		//		else
		//			result.addObject("anonim", "anónimo");

		//////////////////////////////////////////

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
