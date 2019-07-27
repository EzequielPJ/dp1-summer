
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActivityService;
import services.CommentService;
import services.ConferenceService;
import domain.Comment;

@Controller
@RequestMapping("/comment")
public class CommentController extends AbstractController {

	@Autowired
	private CommentService		commentService;

	@Autowired
	private ConferenceService	conferenceService;

	@Autowired
	private ActivityService		activityService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView listConferencePast(@RequestParam final int idEntity) {
		final ModelAndView result = this.listModelAndView();

		final Collection<Integer> colConf = this.conferenceService.findAllId();
		final Collection<Integer> colAct = this.activityService.findAllId();

		Collection<Comment> comments = null;
		if (colConf.contains(idEntity))
			comments = this.commentService.getCommentsByConference(idEntity);
		else if (colAct.contains(idEntity))
			comments = this.commentService.getCommentsByActivity(idEntity);

		result.addObject("comments", comments);
		result.addObject("requestURI", "comments/list.do");
		result.addObject("general", true);

		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam(defaultValue = "0") final int idConference, @RequestParam(defaultValue = "0") final int idActivity) {
		ModelAndView result;
		final Comment comment = this.commentService.create(idConference, idActivity);
		result = this.createEditModelAndView(comment);
		return result;
	}

	protected ModelAndView createEditModelAndView(final Comment comment) {
		return this.createEditModelAndView(comment, null);
	}

	protected ModelAndView createEditModelAndView(final Comment comment, final String message) {
		final ModelAndView result = new ModelAndView("conference/edit");

		result.addObject("comment", comment);
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
