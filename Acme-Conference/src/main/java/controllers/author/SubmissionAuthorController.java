
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
import services.PaperService;
import services.SubmisssionService;
import domain.Author;
import domain.Paper;
import domain.Submission;
import forms.SubmissionPaperForm;

@Controller
@RequestMapping("/submission/author")
public class SubmissionAuthorController {

	@Autowired
	private SubmisssionService	submissionService;

	@Autowired
	private AuthorService		authorService;

	@Autowired
	private PaperService		paperService;


	//Create 
	public ModelAndView create() {
		final SubmissionPaperForm submissionPaperForm = new SubmissionPaperForm();
		return this.createModelAndView(submissionPaperForm);
	}

	//Save
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
		return result;
	}

	//Listar las del autor
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result = new ModelAndView();
		final Author author = this.authorService.findByPrincipal(LoginService.getPrincipal());
		final Collection<Submission> submissionOfAuthor = this.submissionService.getSubmissionsOfAuthor(author.getId());
		result.addObject("submissions", submissionOfAuthor);
		return result;
	}

	//Display
	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int idSubmission) {
		final ModelAndView result;

		final Author author = this.authorService.findByPrincipal(LoginService.getPrincipal());

		final Submission submission = this.submissionService.findOne(idSubmission);
		if (submission.getAuthor().equals(author)) {
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

	protected ModelAndView createModelAndView(final SubmissionPaperForm submissionPaperForm) {
		return this.createModelAndView(submissionPaperForm, null);
	}

	protected ModelAndView createModelAndView(final SubmissionPaperForm submissionPaperForm, final String message) {
		final ModelAndView result = new ModelAndView();
		result.addObject("submissionPaperForm", submissionPaperForm);

		//TODO: Add conferences para seleccionar

		return result;
	}
}
