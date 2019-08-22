
package controllers.reviewer;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ReviewerService;
import controllers.AbstractController;
import domain.Reviewer;
import forms.ReviewerForm;

@Controller
@RequestMapping("/reviewer")
public class ReviewerReviewerController extends AbstractController {

	@Autowired
	private ReviewerService	reviewerService;


	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public ModelAndView register() {
		ModelAndView res;

		final ReviewerForm reviewerForm = new ReviewerForm();

		res = this.createEditModelAndView(reviewerForm);

		return res;
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public ModelAndView save(final ReviewerForm sponsorForm, final BindingResult binding) {

		ModelAndView res;

		try {
			final Reviewer reviewerRect = this.reviewerService.reconstruct(sponsorForm, binding);
			this.reviewerService.save(reviewerRect);
			res = new ModelAndView("redirect:/welcome/index.do");
		} catch (final ValidationException oops) {
			res = this.createEditModelAndView(sponsorForm);
		} catch (final Throwable oops) {
			res = this.createEditModelAndView(sponsorForm, "reviewer.edit.commit.error");

		}

		return res;

	}

	@RequestMapping(value = "/reviewer/save", method = RequestMethod.POST)
	public ModelAndView save(final Reviewer reviewer, final BindingResult binding) {

		ModelAndView res;

		try {
			final Reviewer reviewerRect = this.reviewerService.reconstruct(reviewer, binding);
			this.reviewerService.save(reviewerRect);
			res = new ModelAndView("redirect:/actor/display.do");
		} catch (final ValidationException oops) {
			res = this.createEditModelAndView(reviewer);
		} catch (final Throwable oops) {
			res = this.createEditModelAndView(reviewer, "reviewer.edit.commit.error");

		}

		return res;

	}

	protected ModelAndView createEditModelAndView(final ReviewerForm sponsorForm, final String... messages) {

		final ModelAndView result;

		result = new ModelAndView("reviewer/edit");
		result.addObject("reviewerForm", sponsorForm);
		result.addObject("edit", false);

		final List<String> messageCodes = new ArrayList<>();
		for (final String s : messages)
			messageCodes.add(s);
		result.addObject("messages", messageCodes);

		this.configValues(result);

		return result;

	}

	protected ModelAndView createEditModelAndView(final Reviewer reviewer, final String... messages) {

		final ModelAndView result;

		result = new ModelAndView("reviewer/edit");
		result.addObject("reviewer", reviewer);
		result.addObject("edit", true);
		final List<String> messageCodes = new ArrayList<>();
		for (final String s : messages)
			messageCodes.add(s);
		result.addObject("messages", messageCodes);

		this.configValues(result);

		return result;

	}
}
