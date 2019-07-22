
package controllers.administrator;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import services.ConferenceService;
import controllers.AbstractController;
import domain.Conference;

@Controller
@RequestMapping("/conference/administrator")
public class ConferenceAdministratorController extends AbstractController {

	@Autowired
	private ConferenceService	conferenceService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result = this.listModelAndView();
		final Collection<Conference> myConferences = this.conferenceService.getYoursConference(this.conferenceService.findByPrincipal(LoginService.getPrincipal()).getId());
		result.addObject("conferences", myConferences);
		result.addObject("requestURI", "conference/administrator/list.do");

		return result;
	}

	@RequestMapping(value = "/listSubmissionDeadline", method = RequestMethod.GET)
	public ModelAndView listSubmissionDeadline() {
		final ModelAndView result = this.listModelAndView();

		final Date date1 = new Date();
		final Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.DAY_OF_YEAR, -5);
		final Date date2 = calendar.getTime();

		final Collection<Conference> myConferences = this.conferenceService.getConferencesBetweenSubmissionDeadline(date1, date2);
		result.addObject("conferences", myConferences);
		result.addObject("requestURI", "conference/administrator/listSubmissionDeadline.do");

		return result;
	}

	@RequestMapping(value = "/listNotificationDeadline", method = RequestMethod.GET)
	public ModelAndView listNotificationDeadline() {
		final ModelAndView result = this.listModelAndView();

		final Date date1 = new Date();
		final Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.DAY_OF_YEAR, -4);
		final Date date2 = calendar.getTime();

		final Collection<Conference> myConferences = this.conferenceService.getConferencesBetweenNotificationDeadline(date1, date2);
		result.addObject("conferences", myConferences);
		result.addObject("requestURI", "conference/administrator/listNotificationDeadline.do");

		return result;
	}

	@RequestMapping(value = "/listCameraReadyDeadline", method = RequestMethod.GET)
	public ModelAndView listCameraReadyDeadline() {
		final ModelAndView result = this.listModelAndView();

		final Date date1 = new Date();
		final Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.DAY_OF_YEAR, -4);
		final Date date2 = calendar.getTime();

		final Collection<Conference> myConferences = this.conferenceService.getConferencesBetweenCameraReadyDeadline(date1, date2);
		result.addObject("conferences", myConferences);
		result.addObject("requestURI", "conference/administrator/listCameraReadyDeadline.do");

		return result;
	}

	@RequestMapping(value = "/listNextConference", method = RequestMethod.GET)
	public ModelAndView listNextConference() {
		final ModelAndView result = this.listModelAndView();

		final Date date1 = new Date();
		final Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.DAY_OF_YEAR, 4);
		final Date date2 = calendar.getTime();

		final Collection<Conference> myConferences = this.conferenceService.getConferencesBetweenStartDate(date1, date2);
		result.addObject("conferences", myConferences);
		result.addObject("requestURI", "conference/administrator/listNextConference.do");

		return result;
	}

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int idConference) {
		ModelAndView result = null;

		final Conference conference = this.conferenceService.findOne(idConference);

		//		if (!conference.getAdministrator().equals(this.conferenceService.findByPrincipal(LoginService.getPrincipal())))
		//			result = new ModelAndView("redirect:list.do");
		//		else {

		result = new ModelAndView("conference/display");

		//		String saved;
		//		saved = (String) result.getModel().get("requestURI");

		result.addObject("conference", conference);
		result.addObject("requestURI", "conference/administrator/display.do?idConference=" + idConference);
		//		}
		this.configValues(result);
		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		final Conference conference = this.conferenceService.create();
		result = this.createEditModelAndView(conference);
		return result;
	}
	//
	//	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	//	public ModelAndView edit(@RequestParam(required = true) final int idOpinion) {
	//		ModelAndView result;
	//
	//		final Opinion opinion = this.opinionService.findOne(idOpinion);
	//
	//		if (!opinion.getReader().equals(this.readerService.findByPrincipal(LoginService.getPrincipal())))
	//			result = new ModelAndView("redirect:list.do");
	//		else
	//			result = this.createEditModelAndView(opinion);
	//		return result;
	//	}
	//
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(final Conference conference, final BindingResult binding) {
		ModelAndView result;
		try {
			final Conference conferenceRect = this.conferenceService.reconstruct(conference, binding);
			this.conferenceService.save(conferenceRect);
			result = new ModelAndView("redirect:list.do");
		} catch (final ValidationException oops) {
			result = this.createEditModelAndView(conference);
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(conference, "conference.edit.commit.error");

		}
		return result;
	}
	//
	//	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	//	public ModelAndView delete(@RequestParam(required = true) final int idOpinion) {
	//		ModelAndView result;
	//		try {
	//			this.opinionService.delete(idOpinion);
	//			result = new ModelAndView("redirect:list.do");
	//		} catch (final Throwable oops) {
	//			result = new ModelAndView("redirect:list.do");
	//		}
	//		return result;
	//	}
	//	
	protected ModelAndView createEditModelAndView(final Conference conference) {
		return this.createEditModelAndView(conference, null);
	}

	protected ModelAndView createEditModelAndView(final Conference conference, final String message) {
		final ModelAndView result = new ModelAndView("conference/edit");

		result.addObject("conference", conference);
		result.addObject("message", message);

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
