
package controllers;

import java.util.Collection;

import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CookieValue;
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
	public ModelAndView listComment(@RequestParam final int idEntity, @CookieValue(value = "language", required = false) final String lang) {
		final ModelAndView result = this.listModelAndView();

		final Collection<Integer> colConf = this.conferenceService.findAllId();
		final Collection<Integer> colAct = this.activityService.findAllId();

		Collection<Comment> comments = null;
		if (colConf.contains(idEntity))
			comments = this.commentService.getCommentsByConference(idEntity);
		else if (colAct.contains(idEntity))
			comments = this.commentService.getCommentsByActivity(idEntity);

		result.addObject("comments", comments);
		result.addObject("requestURI", "comment/list.do");

		if (lang == null)
			result.addObject("anonim", "anonymous");
		else
			result.addObject("anonim", "anónimo");

		result.addObject("general", true);

		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam(defaultValue = "0") final int idConference, @RequestParam(defaultValue = "0") final int idActivity) {
		ModelAndView result = null;
		Boolean fail = false;
		if (idConference != 0) {
			if (this.conferenceService.findOne(idConference).getFinalMode() == false) {
				result = this.listModelAndView("security.error.accessDenied");
				fail = true;
			}
		} else if (idActivity != 0)
			if (this.activityService.findOne(idActivity).getConference().getFinalMode() == false) {
				result = this.listModelAndView("security.error.accessDenied");
				fail = true;
			}

		if (!fail) {
			final Comment comment = this.commentService.create(idConference, idActivity);
			result = this.createEditModelAndView(comment);
		}
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(final Comment comment, final BindingResult binding) {
		ModelAndView result = null;
		try {
			final Comment commentRect = this.commentService.reconstruct(comment, binding);
			this.commentService.save(commentRect);

			Integer i;
			if (commentRect.getActivity() != null)
				i = commentRect.getActivity().getId();
			else
				i = commentRect.getConference().getId();
			//				result = new ModelAndView("redirect:../conference/display.do?idConference" + i + "&url=conference/list.do");
			result = new ModelAndView("redirect:list.do?idEntity=" + i);
		} catch (final ValidationException oops) {
			result = this.createEditModelAndView(comment);
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(comment, "comment.edit.commit.error");
		}
		return result;
	}

	protected ModelAndView createEditModelAndView(final Comment comment) {
		return this.createEditModelAndView(comment, null);
	}

	protected ModelAndView createEditModelAndView(final Comment comment, final String message) {
		final ModelAndView result = new ModelAndView("comment/edit");

		result.addObject("comment", comment);
		result.addObject("message", message);

		this.configValues(result);

		return result;
	}

	protected ModelAndView listModelAndView() {
		return this.listModelAndView(null);
	}

	protected ModelAndView listModelAndView(final String message) {
		final ModelAndView result = new ModelAndView("comment/list");

		result.addObject("message", message);

		this.configValues(result);

		return result;
	}
}
