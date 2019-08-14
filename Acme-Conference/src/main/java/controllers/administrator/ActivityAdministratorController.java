
package controllers.administrator;

import java.util.ArrayList;
import java.util.Collection;

import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActivityService;
import services.SectionService;
import controllers.AbstractController;
import domain.Activity;

@Controller
@RequestMapping("/activity/administrator")
public class ActivityAdministratorController extends AbstractController {

	@Autowired
	private ActivityService	activityService;

	@Autowired
	private SectionService	sectionService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final int idConference) {
		final ModelAndView result = this.listModelAndView();
		Collection<Activity> colAct;
		colAct = this.activityService.getActivityByConference(idConference);
		result.addObject("activities", colAct);
		result.addObject("requestURI", "activity/administrator/list.do?idConference=" + idConference);

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
		result.addObject("authors", this.activityService.getAuthorsWithSubmissionAcceptedInConference(idConference));
		result.addObject("papers", this.activityService.getPapersInCameraReadyFromConference(idConference));
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(final Activity activity, final BindingResult binding) {
		ModelAndView result;
		try {
			final Activity activityRect = this.activityService.reconstruct(activity, binding);
			if (activityRect.getAuthors().contains(null) && activityRect.getAuthors().size() >= 2)
				activityRect.getAuthors().remove(null);
			this.activityService.save(activityRect);
			//			if (ty.equals("TUTORIAL") && !activityRect.getType().equals("TUTORIAL")) {
			//				final Activity a = this.activityService.findOne(activityRect.getId());
			//				final Collection<Section> colS = this.sectionService.getSectionByActivity(a.getId());
			//				for (final Section sect : colS)
			//					this.sectionService.delete(sect.getId());
			//			}
			result = new ModelAndView("redirect:list.do?idConference=" + activity.getConference().getId());
		} catch (final ValidationException oops) {
			final Collection<String> colType = new ArrayList<>();
			colType.add("TUTORIAL");
			colType.add("PANEL");
			colType.add("PRESENTATION");
			result = this.createEditModelAndView(activity);
			result.addObject("typ", activity.getType());
			result.addObject("typeList", colType);
			result.addObject("authors", this.activityService.getAuthorsWithSubmissionAcceptedInConference(activity.getConference().getId()));
			result.addObject("papers", this.activityService.getPapersInCameraReadyFromConference(activity.getConference().getId()));
			result.addObject("idConference", activity.getConference().getId());
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(activity, "activity.edit.commit.error");
		}
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam(required = true) final int idActivity) {
		ModelAndView result;

		final Activity act = this.activityService.findOne(idActivity);
		final Collection<String> colType = new ArrayList<>();
		colType.add("TUTORIAL");
		colType.add("PANEL");
		colType.add("PRESENTATION");
		result = this.createEditModelAndView(act);

		result.addObject("typ", act.getType());
		result.addObject("typeList", colType);
		result.addObject("authors", this.activityService.getAuthorsWithSubmissionAcceptedInConference(act.getConference().getId()));
		result.addObject("papers", this.activityService.getPapersInCameraReadyFromConference(act.getConference().getId()));
		result.addObject("idConference", act.getConference().getId());
		return result;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam(required = true) final int idActivity) {
		ModelAndView result;
		try {
			final Integer a = this.activityService.findOne(idActivity).getConference().getId();
			this.activityService.delete(idActivity);
			result = new ModelAndView("redirect:list.do?idConference=" + a);
		} catch (final Throwable oops) {
			final Integer a = this.activityService.findOne(idActivity).getConference().getId();
			result = new ModelAndView("redirect:list.do?idConference=" + a);
		}
		return result;
	}

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int idActivity, @RequestParam final String url) {
		ModelAndView result = null;

		final Activity act = this.activityService.findOne(idActivity);

		result = new ModelAndView("activity/display");

		result.addObject("activity", act);
		result.addObject("requestURI", "activity/administrator/display.do?idActivity=" + idActivity);
		result.addObject("url", url);
		result.addObject("sections", this.sectionService.getSectionByActivity(idActivity));

		this.configValues(result);
		return result;
	}

	protected ModelAndView createEditModelAndView(final Activity activity) {
		return this.createEditModelAndView(activity, null);
	}

	protected ModelAndView createEditModelAndView(final Activity activity, final String message) {
		final ModelAndView result = new ModelAndView("activity/edit");

		result.addObject("activity", activity);
		result.addObject("message", message);

		final Collection<String> colType = new ArrayList<>();
		colType.add("TUTORIAL");
		colType.add("PANEL");
		colType.add("PRESENTATION");
		result.addObject("typeList", colType);
		result.addObject("authors", this.activityService.getAuthorsWithSubmissionAcceptedInConference(activity.getConference().getId()));

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
