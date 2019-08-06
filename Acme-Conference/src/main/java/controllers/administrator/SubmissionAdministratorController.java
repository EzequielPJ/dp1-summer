
package controllers.administrator;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import services.AdministratorService;
import services.PaperService;
import services.SubmisssionService;
import domain.Administrator;
import domain.Paper;
import domain.Submission;

@Controller
@RequestMapping("/submission/administrator")
public class SubmissionAdministratorController {

	@Autowired
	private SubmisssionService		submissionService;

	@Autowired
	private PaperService			paperService;

	@Autowired
	private AuthorService			authorService;

	@Autowired
	private AdministratorService	adminService;


	//Display
	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int idSubmission) {
		final ModelAndView result;

		final Administrator admin = this.adminService.findByPrincipal(LoginService.getPrincipal());

		final Submission submission = this.submissionService.findOne(idSubmission);
		if (submission.getConference().getAdministrator().equals(admin)) {
			result = new ModelAndView("");

			final Paper nonCameraReadyVersion = this.paperService.getPaperNonCamerReadyVersionOfSubmission(idSubmission);
			final Paper cameraReadyVersion = this.paperService.getPaperCamerReadyVersionOfSubmission(idSubmission);

			result.addObject("submission", submission);
			result.addObject("nonCameraReadyVersion", nonCameraReadyVersion);
			result.addObject("cameraReadyVersion", cameraReadyVersion);

		} else
			result = new ModelAndView("redirect:list.do");

		return result;
	}

	//list
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result = new ModelAndView();
		final Administrator admin = this.adminService.findByPrincipal(LoginService.getPrincipal());
		final Collection<Submission> submissionOfAdmin = this.submissionService.getSubmissionsOfAdmin(admin.getId());
		result.addObject("submissions", submissionOfAdmin);
		return result;
	}

	//Change Status
	@RequestMapping(value = "/changeStatus", method = RequestMethod.GET)
	public ModelAndView changeStatus(@RequestParam final int idSubmission, @RequestParam final String status) {
		final ModelAndView result;
		final Submission submission = this.submissionService.findOne(idSubmission);
		try {
			this.submissionService.changeStatus(submission, status);
			result = new ModelAndView("redirect:list.do");
		} catch (final Throwable oops) {
			oops.printStackTrace();
			result = this.displayModelAndView(submission, "cannot.change.status");

		}
		return result;
	}

	protected ModelAndView displayModelAndView(final Submission submission) {
		return this.displayModelAndView(submission, null);
	}

	//TODO: ADD CONFERENCE
	protected ModelAndView displayModelAndView(final Submission submission, final String message) {
		final ModelAndView result = new ModelAndView();
		result.addObject("submission", submission);
		final Paper nonCameraReadyVersion = this.paperService.getPaperNonCamerReadyVersionOfSubmission(submission.getId());
		final Paper cameraReadyVersion = this.paperService.getPaperCamerReadyVersionOfSubmission(submission.getId());
		result.addObject("nonCameraReadyVersion", nonCameraReadyVersion);
		result.addObject("cameraReadyVersion", cameraReadyVersion);
		result.addObject("message", message);
		return result;
	}

}
