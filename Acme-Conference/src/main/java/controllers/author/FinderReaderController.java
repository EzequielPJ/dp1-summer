
package controllers.author;

import java.text.ParseException;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import security.UserAccount;
import services.AuthorService;
import services.CategoryService;
import services.ConferenceService;
import services.FinderService;
import controllers.AbstractController;
import domain.Author;
import domain.Category;
import domain.Conference;
import domain.Finder;

@Controller
@RequestMapping("/finder/author")
public class FinderReaderController extends AbstractController {

	@Autowired
	private AuthorService		authorService;

	@Autowired
	private FinderService		finderService;

	@Autowired
	private ConferenceService	conferenceService;

	@Autowired
	private CategoryService		categoryService;


	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit() throws ParseException {
		final ModelAndView result;

		final UserAccount principal = LoginService.getPrincipal();
		final Author author = this.authorService.findByPrincipal(principal);

		final Finder finder = author.getFinder();

		result = this.createEditModelAndView(finder);
		result.addObject("requestURI", "finder/author/edit.do");
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(Finder finder, final BindingResult binding) throws ParseException {
		ModelAndView result;
		finder = this.finderService.reconstruct(finder, binding);

		if (binding.hasErrors())
			result = this.createEditModelAndView(finder);
		else
			try {
				this.finderService.save(finder);

				result = new ModelAndView("redirect:edit.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(finder, "finder.commit.error");
			}
		this.configValues(result);
		return result;
	}

	protected ModelAndView createEditModelAndView(final Finder finder) {

		final ModelAndView result;

		result = this.createEditModelAndView(finder, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(final Finder finder, final String messageCode) {
		final ModelAndView result;

		result = new ModelAndView("finder/edit");

		final Collection<Conference> conferences = this.conferenceService.getConferencesByFinder(finder.getId());
		final Collection<Category> categories = this.categoryService.findAll();
		result.addObject("finder", finder);
		result.addObject("conferences", conferences);
		result.addObject("categories", categories);

		result.addObject("message", messageCode);

		this.configValues(result);
		return result;
	}
}
