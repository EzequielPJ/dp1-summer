
package controllers.author;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.AuthorService;
import services.PaperService;
import domain.Paper;

@Controller
@RequestMapping("/paper/author")
public class PaperAuthorController {

	@Autowired
	private AuthorService	authorService;

	@Autowired
	private PaperService	paperService;

	@Autowired
	private Validator		validator;


	//Create 
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int idSubmission) {
		return this.createModelAndView(this.paperService.createPaper(idSubmission));
	}

	//Save
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public ModelAndView save(final Paper paper, final BindingResult bindingResult) {
		ModelAndView result;

		paper.setCameraReadyPaper(true);
		this.validator.validate(paper, bindingResult);

		if (bindingResult.hasErrors())
			result = this.createModelAndView(paper);
		else
			try {
				this.paperService.save(paper);
				result = new ModelAndView("redirect:/author/submission/list.do");
			} catch (final Throwable oops) {
				result = this.createModelAndView(paper, "cannot.save.paper");
			}
		return result;
	}

	protected ModelAndView createModelAndView(final Paper paper) {
		return this.createModelAndView(paper, null);
	}

	protected ModelAndView createModelAndView(final Paper paper, final String message) {
		final ModelAndView result = new ModelAndView("paper/create");
		result.addObject("paper", paper);

		result.addObject("authors", this.authorService.getAllAuthors());
		result.addObject("message", message);

		return result;
	}
}
