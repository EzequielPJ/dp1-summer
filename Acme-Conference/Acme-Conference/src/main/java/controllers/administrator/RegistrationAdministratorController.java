
package controllers.administrator;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ConferenceService;
import services.RegistrationService;
import controllers.AbstractController;
import domain.Conference;
import domain.Registration;

@Controller
@RequestMapping("/registration/adminsitrator")
public class RegistrationAdministratorController extends AbstractController {

	@Autowired
	private RegistrationService	registrationService;

	@Autowired
	private ConferenceService	conferenceService;


	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int registrationId) {
		return this.displayModelAndView(registrationId);
	}

	@RequestMapping(value = "/listByConference", method = RequestMethod.GET)
	public ModelAndView listByConference(@RequestParam final int conferenceId) {
		return this.listByConferenceModelAndView(conferenceId);
	}

	protected ModelAndView listByConferenceModelAndView(final int conferenceId) {
		final ModelAndView res = new ModelAndView("registration/list");

		final Conference conference = this.conferenceService.findOne(conferenceId);

		final Collection<Registration> registrations = this.registrationService.findRegistrationFromConference(conference.getId());

		res.addObject("registrations", registrations);
		res.addObject("requestURI", "registration/administrator/listByConference.do");

		this.configValues(res);
		return res;
	}

	protected ModelAndView displayModelAndView(final int registrationId) {
		final ModelAndView res = new ModelAndView("registration/display");

		res.addObject("registration", this.registrationService.findOne(registrationId));

		this.configValues(res);
		return res;
	}

}
