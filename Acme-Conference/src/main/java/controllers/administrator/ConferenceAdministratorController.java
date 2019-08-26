
package controllers.administrator;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import services.CategoryService;
import services.ConferenceService;
import services.SubmissionService;
import controllers.AbstractController;
import domain.Administrator;
import domain.Conference;

@Controller
@RequestMapping("/conference/administrator")
public class ConferenceAdministratorController extends AbstractController {

	@Autowired
	private ConferenceService	conferenceService;

	@Autowired
	private CategoryService		categoryService;

	@Autowired
	private SubmissionService	submissionService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@CookieValue(value = "language", required = false) final String lang) {
		final ModelAndView result = this.listModelAndView();
		final Collection<Conference> myConferences = this.conferenceService.getYoursConference(this.conferenceService.findByPrincipal(LoginService.getPrincipal()).getId());
		result.addObject("conferences", myConferences);
		result.addObject("requestURI", "conference/administrator/list.do");
		result.addObject("categories", this.categoryService.findAll());
		result.addObject("sel", true);
		if (lang == null)
			result.addObject("lang", "en");
		else
			result.addObject("lang", lang);

		return result;
	}

	@RequestMapping(value = "/listByCategory", method = RequestMethod.GET)
	public ModelAndView listByCatgeory(@RequestParam final int idCategory, @CookieValue(value = "language", required = false) final String lang) {
		final ModelAndView result = this.listModelAndView();
		final Collection<Conference> conferenceList = this.conferenceService.getYoursConferenceByCategory(this.conferenceService.findByPrincipal(LoginService.getPrincipal()).getId(), idCategory);
		result.addObject("conferences", conferenceList);
		result.addObject("requestURI", "conference/administrator/listByCategory.do?idCategory=" + idCategory);
		result.addObject("categories", this.categoryService.findAll());
		result.addObject("sel", true);
		if (lang == null)
			result.addObject("lang", "en");
		else
			result.addObject("lang", lang);
		return result;
	}

	@RequestMapping(value = "/listSubmissionDeadline", method = RequestMethod.GET)
	public ModelAndView listSubmissionDeadline(@CookieValue(value = "language", required = false) final String lang) {
		final ModelAndView result = this.listModelAndView();

		final Administrator adm = this.conferenceService.findByPrincipal(LoginService.getPrincipal());

		final Date date1 = new Date();
		final Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.DAY_OF_YEAR, -6);
		final Date date2 = calendar.getTime();

		final ArrayList<Conference> myConferencesF = new ArrayList<Conference>();
		final Collection<Conference> myConferences = this.conferenceService.getConferencesBetweenSubmissionDeadline(date1, date2, adm.getId());
		for (final Conference conf : myConferences)
			if (conf.getAdministrator().equals(this.conferenceService.findByPrincipal(LoginService.getPrincipal())))
				myConferencesF.add(conf);
		result.addObject("conferences", myConferencesF);
		result.addObject("requestURI", "conference/administrator/listSubmissionDeadline.do");
		if (lang == null) {
			result.addObject("lang", "en");
			result.addObject("tit", "Conference whose submission deadline elapsed in the last five days");
		} else {
			result.addObject("lang", lang);
			result.addObject("tit", "Conferencias cuya fecha de presentación haya vencido en los últimos 5 días");
		}
		return result;
	}
	@RequestMapping(value = "/listNotificationDeadline", method = RequestMethod.GET)
	public ModelAndView listNotificationDeadline(@CookieValue(value = "language", required = false) final String lang) {
		final ModelAndView result = this.listModelAndView();

		final Administrator adm = this.conferenceService.findByPrincipal(LoginService.getPrincipal());

		final Date date1 = new Date();
		final Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.DAY_OF_YEAR, -5);
		final Date date2 = calendar.getTime();

		final ArrayList<Conference> myConferencesF = new ArrayList<Conference>();
		final Collection<Conference> myConferences = this.conferenceService.getConferencesBetweenNotificationDeadline(date1, date2, adm.getId());
		for (final Conference conf : myConferences)
			if (conf.getAdministrator().equals(this.conferenceService.findByPrincipal(LoginService.getPrincipal())))
				myConferencesF.add(conf);
		result.addObject("conferences", myConferencesF);
		result.addObject("requestURI", "conference/administrator/listNotificationDeadline.do");
		if (lang == null) {
			result.addObject("lang", "en");
			result.addObject("tit", "Conference whose notification deadline elapsed in less than five days");
		} else {
			result.addObject("lang", lang);
			result.addObject("tit", "Conferencias cuya fecha de notificaión expire en menos de 5 días");
		}
		return result;
	}

	@RequestMapping(value = "/listCameraReadyDeadline", method = RequestMethod.GET)
	public ModelAndView listCameraReadyDeadline(@CookieValue(value = "language", required = false) final String lang) {
		final ModelAndView result = this.listModelAndView();

		final Administrator adm = this.conferenceService.findByPrincipal(LoginService.getPrincipal());

		final Date date1 = new Date();
		final Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.DAY_OF_YEAR, -5);
		final Date date2 = calendar.getTime();

		final ArrayList<Conference> myConferencesF = new ArrayList<Conference>();
		final Collection<Conference> myConferences = this.conferenceService.getConferencesBetweenCameraReadyDeadline(date1, date2, adm.getId());
		for (final Conference conf : myConferences)
			if (conf.getAdministrator().equals(this.conferenceService.findByPrincipal(LoginService.getPrincipal())))
				myConferencesF.add(conf);
		result.addObject("conferences", myConferencesF);
		result.addObject("requestURI", "conference/administrator/listCameraReadyDeadline.do");
		if (lang == null) {
			result.addObject("lang", "en");
			result.addObject("tit", "Conference whose camera-ready deadline elapsed in less than five days");
		} else {
			result.addObject("lang", lang);
			result.addObject("tit", "Conferencias cuya fecha de presentación final expire en menos de 5 días");
		}
		return result;
	}

	@RequestMapping(value = "/listNextConference", method = RequestMethod.GET)
	public ModelAndView listNextConference(@CookieValue(value = "language", required = false) final String lang) {
		final ModelAndView result = this.listModelAndView();

		final Administrator adm = this.conferenceService.findByPrincipal(LoginService.getPrincipal());

		final Date date1 = new Date();
		final Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.DAY_OF_YEAR, 5);
		final Date date2 = calendar.getTime();

		final ArrayList<Conference> myConferencesF = new ArrayList<Conference>();
		final Collection<Conference> myConferences = this.conferenceService.getConferencesBetweenStartDate(date1, date2, adm.getId());
		for (final Conference conf : myConferences)
			if (conf.getAdministrator().equals(this.conferenceService.findByPrincipal(LoginService.getPrincipal())))
				myConferencesF.add(conf);
		result.addObject("conferences", myConferencesF);
		result.addObject("requestURI", "conference/administrator/listNextConference.do");
		if (lang == null) {
			result.addObject("lang", "en");
			result.addObject("tit", "Conference that are going to be organised in less than five days");
		} else {
			result.addObject("lang", lang);
			result.addObject("tit", "Conferencias que se organizarán en menos de 5 días");
		}
		return result;
	}

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int idConference, @CookieValue(value = "language", required = false) final String lang, @RequestParam final String url) {
		ModelAndView result = null;

		final Conference conference = this.conferenceService.findOne(idConference);

		if (!conference.getAdministrator().equals(this.conferenceService.findByPrincipal(LoginService.getPrincipal())))
			result = new ModelAndView("redirect:list.do");
		else
			result = new ModelAndView("conference/display");

		result.addObject("conference", conference);
		result.addObject("requestURI", "conference/administrator/display.do?idConference=" + idConference);
		result.addObject("url", url);
		if (lang == null)
			result.addObject("lang", "en");
		else
			result.addObject("lang", lang);

		this.configValues(result);
		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@CookieValue(value = "language", required = false) final String lang) {
		ModelAndView result;
		final Conference conference = this.conferenceService.create();
		result = this.createEditModelAndView(conference);
		if (lang == null)
			result.addObject("lang", "en");
		else
			result.addObject("lang", lang);
		result.addObject("categoriesList", this.categoryService.findAll());
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam(required = true) final int idConference, @CookieValue(value = "language", required = false) final String lang) {
		ModelAndView result;

		final Conference conference = this.conferenceService.findOne(idConference);

		if (!conference.getAdministrator().equals(this.conferenceService.findByPrincipal(LoginService.getPrincipal())))
			result = this.listModelAndView("security.error.accessDenied");
		else if (conference.getFinalMode() == true)
			result = this.listModelAndView("security.error.accessDenied");
		else
			result = this.createEditModelAndView(conference);

		if (lang == null)
			result.addObject("lang", "en");
		else
			result.addObject("lang", lang);
		result.addObject("categoriesList", this.categoryService.findAll());
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(final Conference conference, final BindingResult binding, @CookieValue(value = "language", required = false) final String lang) {
		ModelAndView result;
		try {
			final Conference conferenceRect = this.conferenceService.reconstruct(conference, binding);
			this.conferenceService.save(conferenceRect);
			result = new ModelAndView("redirect:list.do");
		} catch (final ValidationException oops) {
			result = this.createEditModelAndView(conference);
			if (lang == null)
				result.addObject("lang", "en");
			else
				result.addObject("lang", lang);
			result.addObject("categoriesList", this.categoryService.findAll());
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(conference, "conference.edit.commit.error");
			if (lang == null)
				result.addObject("lang", "en");
			else
				result.addObject("lang", lang);
			result.addObject("categoriesList", this.categoryService.findAll());
		}
		return result;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam(required = true) final int idConference) {
		ModelAndView result;
		try {
			if (this.conferenceService.findOne(idConference).getFinalMode() == true)
				result = this.listModelAndView("security.error.accessDenied");
			else if (!this.conferenceService.findOne(idConference).getAdministrator().equals(this.conferenceService.findByPrincipal(LoginService.getPrincipal())))
				result = this.listModelAndView("security.error.accessDenied");
			else {
				this.conferenceService.delete(idConference);
				result = new ModelAndView("redirect:list.do");
			}
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:list.do");
		}
		return result;
	}

	@RequestMapping(value = "/decisionMaking", method = RequestMethod.GET)
	public ModelAndView conferenceDecisionMaking(@RequestParam final int idConference) {
		ModelAndView result;

		try {
			if (!this.conferenceService.getConferencesToDecisionMaking().contains(this.conferenceService.findOne(idConference)))
				result = this.listModelAndView("decisionMaking.commit.error");
			else {
				this.submissionService.conferenceDecisionMaking(idConference);
				result = new ModelAndView("redirect:list.do");
			}
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:list.do");
		}
		return result;

	}

	@RequestMapping(value = "/assignReviewers", method = RequestMethod.GET)
	public ModelAndView assignReviewers(@RequestParam final int idConference) {
		ModelAndView result;
		try {
			if (!this.conferenceService.getConferencesToAssingReviewers().contains(this.conferenceService.findOne(idConference)))
				result = this.listModelAndView("decisionMaking.commit.error");
			else {
				this.submissionService.assignReviewers(idConference);
				result = new ModelAndView("redirect:list.do");
			}
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:list.do");
		}
		return result;

	}

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
		final Collection<Conference> conferencesToDecisionMaking = this.conferenceService.getConferencesToDecisionMaking();
		final Collection<Conference> conferencesToAssingReviewers = this.conferenceService.getConferencesToAssingReviewers();
		final Collection<Conference> myConferencesF = this.conferenceService.getYoursConference(this.conferenceService.findByPrincipal(LoginService.getPrincipal()).getId());

		result.addObject("message", message);
		result.addObject("conferences", myConferencesF);
		result.addObject("conferencesToDecisionMaking", conferencesToDecisionMaking);
		result.addObject("conferencesToAssingReviewers", conferencesToAssingReviewers);

		this.configValues(result);

		return result;
	}

}
