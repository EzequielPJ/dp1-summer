
package controllers.reviewer;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import services.PaperService;
import services.ReviewerService;
import services.SubmissionService;
import controllers.AbstractController;
import domain.Paper;
import domain.Reviewer;
import domain.Submission;

@Controller
@RequestMapping("/submission/reviewer")
public class SubmissionReviewerController extends AbstractController {

	@Autowired
	private ReviewerService		reviewerService;

	@Autowired
	private SubmissionService	submissionService;

	@Autowired
	private PaperService		paperService;


	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int idSubmission) {
		final ModelAndView result;

		final Reviewer reviewer = this.reviewerService.findByPrincipal(LoginService.getPrincipal());
		final Submission submission = this.submissionService.findOne(idSubmission);

		final Collection<Reviewer> reviewers = this.reviewerService.findAll();

		if (submission.getReviewers().contains(reviewer)) {
			result = this.displayModelAndView(submission);
			result.addObject("reviewers", reviewers);
		} else
			result = new ModelAndView("redirect:list.do");

		this.configValues(result);
		return result;
	}

	//list
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final Reviewer reviewer = this.reviewerService.findByPrincipal(LoginService.getPrincipal());
		final Collection<Submission> submissions = this.submissionService.getSubmissionsOfReviewer(reviewer.getId());
		return this.listModelAndView("submission.list.all", submissions);
	}

	protected ModelAndView displayModelAndView(final Submission submission) {
		return this.displayModelAndView(submission, null);
	}

	protected ModelAndView displayModelAndView(final Submission submission, final String message) {
		final ModelAndView result = new ModelAndView("submission/display");
		result.addObject("submission", submission);
		final Paper nonCameraReadyVersion = this.paperService.getPaperNonCamerReadyVersionOfSubmission(submission.getId());
		final Paper cameraReadyVersion = this.paperService.getPaperCamerReadyVersionOfSubmission(submission.getId());
		result.addObject("nonCameraReadyVersion", nonCameraReadyVersion);
		result.addObject("cameraReadyVersion", cameraReadyVersion);
		result.addObject("message", message);

		this.configValues(result);
		return result;
	}

	public ModelAndView listModelAndView(final String title, final Collection<Submission> submissions) {
		final ModelAndView result = new ModelAndView("submission/list");
		result.addObject("submissions", submissions);
		result.addObject("title", title);

		this.configValues(result);
		return result;
	}

}
