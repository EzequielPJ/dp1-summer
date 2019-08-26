
package controllers.administrator;

import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import services.ActivityService;
import services.ConferenceService;
import services.SectionService;
import controllers.AbstractController;
import domain.Section;

@Controller
@RequestMapping("/section/administrator")
public class SectionAdministratorController extends AbstractController {

	@Autowired
	private SectionService		sectionservice;

	@Autowired
	private ActivityService		activityService;

	@Autowired
	private ConferenceService	conferenceService;


	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int idActivity) {
		final ModelAndView result;
		if (!this.activityService.findOne(idActivity).getType().equals("TUTORIAL"))
			result = new ModelAndView("redirect:../../activity/administrator/display.do?idActivity=" + idActivity + "&url=activity/administrator/list.do?idConference=" + this.activityService.findOne(idActivity).getConference().getId());
		else if (!this.activityService.findOne(idActivity).getConference().getAdministrator().equals(this.conferenceService.findByPrincipal(LoginService.getPrincipal())))
			result = new ModelAndView("redirect:../../conference/administrator/list.do");
		else {
			final Section section = this.sectionservice.create(idActivity);
			result = this.createEditModelAndView(section);
			result.addObject("idActivity", idActivity);
			result.addObject("idConference", this.activityService.findOne(idActivity).getConference().getId());
			result.addObject("url", "activity/administrator/list.do?idConference=" + this.activityService.findOne(idActivity).getConference().getId());
		}
		return result;
	}
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(final Section section, final BindingResult binding) {
		ModelAndView result;
		try {
			final Section sectionRect = this.sectionservice.reconstruct(section, binding);
			this.sectionservice.save(sectionRect);
			result = new ModelAndView("redirect:../../activity/administrator/display.do?idActivity=" + sectionRect.getActivity().getId() + "&url=activity/administrator/list.do?idConference=" + sectionRect.getActivity().getConference().getId());
		} catch (final ValidationException oops) {
			result = this.createEditModelAndView(section);
			result.addObject("idActivity", section.getActivity().getId());
			result.addObject("idConference", section.getActivity().getConference().getId());
			result.addObject("url", "activity/administrator/list.do?idConference=" + section.getActivity().getConference().getId());
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(section, "section.edit.commit.error");
		}
		return result;
	}

	protected ModelAndView createEditModelAndView(final Section section) {
		return this.createEditModelAndView(section, null);
	}

	protected ModelAndView createEditModelAndView(final Section section, final String message) {
		final ModelAndView result = new ModelAndView("section/edit");

		result.addObject("section", section);
		result.addObject("message", message);

		this.configValues(result);

		return result;
	}

}
