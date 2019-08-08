
package controllers.administrator;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActivityService;
import controllers.AbstractController;
import domain.Activity;

@Controller
@RequestMapping("/activity/administrator")
public class ActivityAdministratorController extends AbstractController {

	@Autowired
	private ActivityService	activityService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final int idConference) {
		final ModelAndView result = this.listModelAndView();
		Collection<Activity> colAct;
		colAct = this.activityService.getActivityByConference(idConference);
		result.addObject("activities", colAct);
		result.addObject("requestURI", "activity/administrator/list.do");

		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int idConference) {
		ModelAndView result;
		final Activity activity = this.activityService.create(idConference);
		result = this.createEditModelAndView(activity);
		final Collection<String> colType = new ArrayList<>();
		colType.add("TUTORIAL");
		colType.add("PANEL");
		colType.add("PRESENTATION");
		result.addObject("typeList", colType);
		result.addObject("idConference", idConference);
		return result;
	}

	protected ModelAndView createEditModelAndView(final Activity activity) {
		return this.createEditModelAndView(activity, null);
	}

	protected ModelAndView createEditModelAndView(final Activity activity, final String message) {
		final ModelAndView result = new ModelAndView("activity/edit");

		result.addObject("activity", activity);
		result.addObject("message", message);

		this.configValues(result);

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
