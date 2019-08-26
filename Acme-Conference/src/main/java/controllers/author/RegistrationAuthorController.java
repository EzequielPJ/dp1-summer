
package controllers.author;

import java.util.Collection;
import java.util.Date;

import javax.validation.ValidationException;

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
import services.RegistrationService;
import controllers.AbstractController;
import domain.Author;
import domain.Conference;
import domain.Registration;

@Controller
@RequestMapping("/registration/author")
public class RegistrationAuthorController extends AbstractController {

	@Autowired
	private RegistrationService	registrationService;

	@Autowired
	private ConferenceService	conferenceService;

	@Autowired
	private AuthorService		authorService;


	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int registrationId) {
		return this.displayModelAndView(registrationId);
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		return this.listModelAndView();
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int conferenceId) {
		return this.createModelAndView(conferenceId);
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int registrationId) {
		this.registrationService.delete(this.registrationService.findOne(registrationId));
		return this.listModelAndView();
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST, params = "save")
	public ModelAndView save(final Registration registration, final BindingResult binding) {
		ModelAndView result;
		result = new ModelAndView("registration/edit");

		try {
			final Registration registrationFinal = this.registrationService.reconstruct(registration, binding);
			final Registration res = this.registrationService.save(registrationFinal);
			result = new ModelAndView("redirect:display.do?registrationId=" + res.getId());
		} catch (final ValidationException oops) {
			result = this.createModelAndView(registration);
		} catch (final Throwable oops) {
			result = this.createModelAndView(registration, "registration.save.error");
		}

		return result;
	}

	protected ModelAndView createModelAndView(final int conferenceId) {
		final ModelAndView res = new ModelAndView("registration/edit");

		final Conference conference = this.conferenceService.findOne(conferenceId);
		if (conference.getStartDate().before(new Date()))
			return new ModelAndView("redirect:../../conference/listConferenceFuture.do");
		final Registration registration = this.registrationService.create(conference);

		res.addObject("registration", registration);

		this.configValues(res);
		this.setCreditCardMakes(res);
		return res;
	}
	protected ModelAndView createModelAndView(final Registration registration, final String... strings) {
		final ModelAndView res = new ModelAndView("registration/edit");

		res.addObject("registration", registration);

		if (strings.length > 0)
			res.addObject("message", strings[0]);

		this.configValues(res);
		this.setCreditCardMakes(res);
		return res;
	}

	protected ModelAndView listModelAndView() {
		final ModelAndView res = new ModelAndView("registration/list");

		final Author author = this.authorService.findByPrincipal(LoginService.getPrincipal());

		final Collection<Registration> registrations = this.registrationService.findRegistrationFromAuthor(author.getId());

		res.addObject("requestURI", "registration/author/list.do");
		res.addObject("registrations", registrations);

		this.configValues(res);
		return res;
	}

	protected ModelAndView displayModelAndView(final int registrationId) {
		final ModelAndView res = new ModelAndView("registration/display");

		final Registration registration = this.registrationService.findOne(registrationId);
		res.addObject("registration", registration);
		res.addObject("anonymizedNumber", "*************" + registration.getCreditCard().getNumber().substring(13));

		this.configValues(res);
		return res;
	}

}
