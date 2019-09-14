
package controllers.author;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.AuthorService;
import controllers.AbstractController;
import domain.Author;
import forms.ActorForm;
import forms.AuthorForm;

@Controller
@RequestMapping("/author")
public class AuthorAuthorController extends AbstractController {

	@Autowired
	private AuthorService	authorService;


	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public ModelAndView register() {
		ModelAndView res;

		final AuthorForm authorForm = new AuthorForm();

		res = this.createEditModelAndView(authorForm);

		return res;
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public ModelAndView save(final AuthorForm authorForm, final BindingResult binding) {

		ModelAndView res;

		try {
			final Author authorRect = this.authorService.reconstruct(authorForm, binding);
			this.authorService.save(authorRect);
			res = new ModelAndView("redirect:/welcome/index.do");
		} catch (final ValidationException oops) {
			res = this.createEditModelAndView(authorForm);
		} catch (final Throwable oops) {
			res = this.createEditModelAndView(authorForm, "author.edit.commit.error");

		}

		return res;

	}

	@RequestMapping(value = "/author/save", method = RequestMethod.POST)
	public ModelAndView save(final Author author, final BindingResult binding) {

		ModelAndView res;

		try {
			final Author authorRect = this.authorService.reconstruct(author, binding);
			this.authorService.save(authorRect);
			res = new ModelAndView("redirect:/actor/display.do");
		} catch (final ValidationException oops) {
			res = this.createEditModelAndView(author);
		} catch (final Throwable oops) {
			res = this.createEditModelAndView(author, "author.edit.commit.error");

		}

		return res;

	}

	protected ModelAndView createEditModelAndView(final ActorForm authorForm, final String... messages) {

		final ModelAndView result;

		result = new ModelAndView("author/edit");
		result.addObject("authorForm", authorForm);
		result.addObject("edit", false);

		if (messages.length > 0)
			result.addObject("message", messages[0]);

		this.configValues(result);

		return result;

	}

	protected ModelAndView createEditModelAndView(final Author author, final String... messages) {

		final ModelAndView result;

		result = new ModelAndView("author/edit");
		result.addObject("author", author);
		result.addObject("edit", true);
		final List<String> messageCodes = new ArrayList<>();
		for (final String s : messages)
			messageCodes.add(s);
		result.addObject("messages", messageCodes);

		this.setCreditCardMakes(result);
		this.configValues(result);

		return result;

	}
}
