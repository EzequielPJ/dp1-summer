
package controllers.author;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import services.AuthorService;
import services.ConferenceService;
import services.PaperService;
import services.SubmissionService;
import controllers.AbstractController;
import domain.Author;
import domain.Paper;
import domain.Submission;
import forms.SubmissionPaperForm;

@Controller
@RequestMapping("/submission/author")
public class SubmissionAuthorController extends AbstractController {

	@Autowired
	private SubmissionService	submissionService;

	@Autowired
	private ConferenceService	conferenceService;

	@Autowired
	private AuthorService		authorService;

	@Autowired
	private PaperService		paperService;


	//Create 
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		final SubmissionPaperForm submissionPaperForm = new SubmissionPaperForm();
		return this.createModelAndView(submissionPaperForm);
	}

	//Save
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public ModelAndView save(@Valid final SubmissionPaperForm submissionPaperForm, final BindingResult bindingResult) {
		ModelAndView result;

		if (bindingResult.hasErrors())
			result = this.createModelAndView(submissionPaperForm);
		else
			try {
				this.submissionService.save(submissionPaperForm, bindingResult);
				result = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {
				result = this.createModelAndView(submissionPaperForm, "cannot.save.submission");
			}

		this.configValues(result);
		return result;
	}

	//Listar las del autor
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final Author author = this.authorService.findByPrincipal(LoginService.getPrincipal());
		final Collection<Submission> submissions = this.submissionService.getSubmissionsOfAuthor(author.getId());

		return this.listModelAndView("submission.list.all", submissions);
	}

	@RequestMapping(value = "/listAccepted", method = RequestMethod.GET)
	public ModelAndView listAccepted() {
		final Author author = this.authorService.findByPrincipal(LoginService.getPrincipal());
		final Collection<Submission> submissions = this.submissionService.getSubmissionsOfAuthorAccepted(author.getId());

		return this.listModelAndView("submission.list.accepted", submissions);
	}

	@RequestMapping(value = "/listRejected", method = RequestMethod.GET)
	public ModelAndView listRejected() {
		final Author author = this.authorService.findByPrincipal(LoginService.getPrincipal());
		final Collection<Submission> submissions = this.submissionService.getSubmissionsOfAuthorRejected(author.getId());

		return this.listModelAndView("submission.list.rejected", submissions);
	}

	@RequestMapping(value = "/listUnderReview", method = RequestMethod.GET)
	public ModelAndView listUnderReview() {
		final Author author = this.authorService.findByPrincipal(LoginService.getPrincipal());
		final Collection<Submission> submissions = this.submissionService.getSubmissionsOfAuthorUnderReview(author.getId());

		return this.listModelAndView("submission.list.underReview", submissions);
	}

	//Display
	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int idSubmission) {
		final ModelAndView result;

		final Author author = this.authorService.findByPrincipal(LoginService.getPrincipal());

		final Submission submission = this.submissionService.findOne(idSubmission);
		if (submission.getAuthor().equals(author)) {
			result = new ModelAndView("submission/display");

			final Paper nonCameraReadyVersion = this.paperService.getPaperNonCamerReadyVersionOfSubmission(idSubmission);
			final Paper cameraReadyVersion = this.paperService.getPaperCamerReadyVersionOfSubmission(idSubmission);

			result.addObject("submission", submission);
			result.addObject("nonCameraReadyVersion", nonCameraReadyVersion);
			result.addObject("cameraReadyVersion", cameraReadyVersion);

		} else
			result = new ModelAndView("redirect:list.do");

		this.configValues(result);
		return result;
	}

	protected ModelAndView createModelAndView(final SubmissionPaperForm submissionPaperForm) {
		return this.createModelAndView(submissionPaperForm, null);
	}

	protected ModelAndView createModelAndView(final SubmissionPaperForm submissionPaperForm, final String message) {
		final ModelAndView result = new ModelAndView("submission/create");
		result.addObject("submissionPaperForm", submissionPaperForm);

		result.addObject("authors", this.authorService.getAllAuthors());
		result.addObject("conferences", this.conferenceService.getConferenceCanBeSubmitted());
		result.addObject("message", message);

		this.configValues(result);
		return result;
	}

	public ModelAndView listModelAndView(final String title, final Collection<Submission> submissions) {
		final ModelAndView result = new ModelAndView("submission/list");
		final Author author = this.authorService.findByPrincipal(LoginService.getPrincipal());
		result.addObject("submissions", submissions);
		result.addObject("title", title);
		result.addObject("submissionsCanAddCameraVersion", this.submissionService.getSubmissionsCanAddCameraReadyPaper(author.getId()));

		this.configValues(result);
		return result;
	}
}
