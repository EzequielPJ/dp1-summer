
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActivityService;
import domain.Activity;

@Controller
@RequestMapping("/activity")
public class ActivityController extends AbstractController {

	@Autowired
	private ActivityService	activityService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final int idConference) {
		final ModelAndView result = this.listModelAndView();
		Collection<Activity> colAct;
		colAct = this.activityService.getActivityByConference(idConference);
		result.addObject("activities", colAct);
		result.addObject("requestURI", "activity/list.do?idConference=" + idConference);
		result.addObject("general", true);

		return result;
	}
	protected ModelAndView listModelAndView() {
		return this.listModelAndView(null);
	}

	protected ModelAndView listModelAndView(final String message) {
		final ModelAndView result = new ModelAndView("activity/list");

		result.addObject("message", message);

		this.configValues(result);

		return result;
	}

}
