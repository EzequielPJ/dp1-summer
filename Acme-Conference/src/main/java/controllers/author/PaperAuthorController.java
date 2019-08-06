
package controllers.author;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.PaperService;
import services.SubmisssionService;
import domain.Paper;

@Controller
@RequestMapping("/submission/author")
public class PaperAuthorController {

	@Autowired
	private SubmisssionService	submissionService;

	@Autowired
	private AuthorService		authorService;

	@Autowired
	private PaperService		paperService;


	//Create 
	public ModelAndView create(@RequestParam final int idSubmission) {
		final ModelAndView result;

		result.addObject("paper", this.paperService.createPaper(idSubmission));

		return result;
	}

	//Save
	public ModelAndView save(@Valid final Paper paper, final BindingResult bindingResult) {
		ModelAndView result;

		if (bindingResult.hasErrors())
			result = this.createModelAndView(paper);
		else
			try {
				this.paperService.save(paper);
				result = new ModelAndView("redirect:/submission/list.do");
			} catch (final Throwable oops) {
				result = this.createModelAndView(paper, "cannot.save.paper");
			}
		return result;
	}

	protected ModelAndView createModelAndView(final Paper paper) {
		return this.createModelAndView(paper, null);
	}

	protected ModelAndView createModelAndView(final Paper paper, final String message) {
		final ModelAndView result = new ModelAndView();
		result.addObject("paper", paper);

		//TODO: Añadit autores para seleccionar

		return result;
	}
}
