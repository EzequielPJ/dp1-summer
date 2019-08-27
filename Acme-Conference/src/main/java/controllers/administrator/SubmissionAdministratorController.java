
package controllers.administrator;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import services.AdministratorService;
import services.AuthorService;
import services.MessageService;
import services.PaperService;
import services.ReviewerService;
import services.SubmissionService;
import controllers.AbstractController;
import domain.Administrator;
import domain.Paper;
import domain.Reviewer;
import domain.Submission;

@Controller
@RequestMapping("/submission/administrator")
public class SubmissionAdministratorController extends AbstractController {

	@Autowired
	private SubmissionService		submissionService;

	@Autowired
	private PaperService			paperService;

	@Autowired
	private AuthorService			authorService;

	@Autowired
	private MessageService			messageService;

	@Autowired
	private AdministratorService	adminService;

	@Autowired
	private ReviewerService			reviewerService;


	//Display
	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int idSubmission) {
		final ModelAndView result;

		final Administrator admin = this.adminService.findByPrincipal(LoginService.getPrincipal());
		final Submission submission = this.submissionService.findOne(idSubmission);

		final Collection<Reviewer> reviewers = this.reviewerService.findAll();

		if (submission.getConference().getAdministrator().equals(admin)) {
			result = this.displayModelAndView(submission);
			if (submission.getConference().getSubmissionDeadline().before(new Date()) && submission.getConference().getNotificationDeadline().after(new Date())) {
				result.addObject("reviewers", reviewers);
				result.addObject("validDate", true);
			} else
				result.addObject("validDate", false);

		} else
			result = new ModelAndView("redirect:list.do");

		this.configValues(result);
		return result;
	}
	//list
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final Administrator admin = this.adminService.findByPrincipal(LoginService.getPrincipal());
		final Collection<Submission> submissions = this.submissionService.getSubmissionsOfAdmin(admin.getId());
		return this.listModelAndView("submission.list.all", submissions, "list.do");
	}

	@RequestMapping(value = "/listAccepted", method = RequestMethod.GET)
	public ModelAndView listAccepted() {
		final Administrator admin = this.adminService.findByPrincipal(LoginService.getPrincipal());
		final Collection<Submission> submissions = this.submissionService.getSubmissionsOfAdminAccepted(admin.getId());
		return this.listModelAndView("submission.list.accepted", submissions, "listAccepted.do");
	}

	@RequestMapping(value = "/listRejected", method = RequestMethod.GET)
	public ModelAndView listRejected() {
		final Administrator admin = this.adminService.findByPrincipal(LoginService.getPrincipal());
		final Collection<Submission> submissions = this.submissionService.getSubmissionsOfAdminRejected(admin.getId());
		return this.listModelAndView("submission.list.rejected", submissions, "listRejected.do");
	}

	@RequestMapping(value = "/listUnderReview", method = RequestMethod.GET)
	public ModelAndView listUnderReview() {
		final Administrator admin = this.adminService.findByPrincipal(LoginService.getPrincipal());
		final Collection<Submission> submissions = this.submissionService.getSubmissionsOfAdminUnderReview(admin.getId());
		return this.listModelAndView("submission.list.underReview", submissions, "listUnderReview.do");
	}

	@RequestMapping(value = "/assignTo", method = RequestMethod.GET)
	public ModelAndView assingTo(@RequestParam final int idSubmission, @RequestParam final int idReviewer) {
		ModelAndView res;

		this.submissionService.assignReviewer(idSubmission, idReviewer);
		res = new ModelAndView("redirect:display.do?idSubmission=" + idSubmission);

		return res;
	}

	//Change Status
	@RequestMapping(value = "/changeStatus", method = RequestMethod.GET)
	public ModelAndView changeStatus(@RequestParam final int idSubmission, @RequestParam final String status) {
		ModelAndView result;

		try {
			final Submission submission = this.submissionService.findOne(idSubmission);
			final Submission newSubmission = this.submissionService.changeStatus(submission, status);
			result = new ModelAndView("redirect:list.do");
		} catch (final Throwable oops) {
			final Submission submission = this.submissionService.findOne(idSubmission);

			oops.printStackTrace();
			result = this.displayModelAndView(submission, "cannot.change.status");
		}

		this.configValues(result);
		return result;
	}

	protected ModelAndView displayModelAndView(final Submission submission) {
		return this.displayModelAndView(submission, null);
	}

	//TODO: ADD CONFERENCE
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

	public ModelAndView listModelAndView(final String title, final Collection<Submission> submissions, final String uri) {
		final ModelAndView result = new ModelAndView("submission/list");
		result.addObject("submissions", submissions);
		result.addObject("title", title);
		result.addObject("requestURI", "submission/administrator/" + uri);

		this.configValues(result);
		return result;
	}

}
