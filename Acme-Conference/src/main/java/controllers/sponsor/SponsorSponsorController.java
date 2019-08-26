
package controllers.sponsor;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ConferenceSponsorService;
import controllers.AbstractController;
import domain.ConferenceSponsor;
import forms.ConferenceSponsorForm;

@Controller
@RequestMapping("/conferenceSponsor")
public class SponsorSponsorController extends AbstractController {

	@Autowired
	private ConferenceSponsorService	conferenceSponsorService;


	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public ModelAndView register() {
		ModelAndView res;

		final ConferenceSponsorForm sponsorForm = new ConferenceSponsorForm();

		res = this.createEditModelAndView(sponsorForm);

		return res;
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public ModelAndView save(final ConferenceSponsorForm sponsorForm, final BindingResult binding) {

		ModelAndView res;

		try {
			final ConferenceSponsor sponsorRect = this.conferenceSponsorService.reconstruct(sponsorForm, binding);
			this.conferenceSponsorService.save(sponsorRect);
			res = new ModelAndView("redirect:/welcome/index.do");
		} catch (final ValidationException oops) {
			res = this.createEditModelAndView(sponsorForm);
		} catch (final Throwable oops) {
			res = this.createEditModelAndView(sponsorForm, "sponsor.edit.commit.error");

		}

		return res;

	}

	@RequestMapping(value = "/conferenceSponsor/save", method = RequestMethod.POST)
	public ModelAndView save(final ConferenceSponsor sponsor, final BindingResult binding) {

		ModelAndView res;

		try {
			final ConferenceSponsor sponsorRect = this.conferenceSponsorService.reconstruct(sponsor, binding);
			this.conferenceSponsorService.save(sponsorRect);
			res = new ModelAndView("redirect:/actor/display.do");
		} catch (final ValidationException oops) {
			res = this.createEditModelAndView(sponsor);
		} catch (final Throwable oops) {
			res = this.createEditModelAndView(sponsor, "sponsor.edit.commit.error");

		}

		return res;

	}

	protected ModelAndView createEditModelAndView(final ConferenceSponsorForm sponsorForm, final String... messages) {

		final ModelAndView result;

		result = new ModelAndView("conferenceSponsor/edit");
		result.addObject("conferenceSponsorForm", sponsorForm);
		result.addObject("edit", false);

		final List<String> messageCodes = new ArrayList<>();
		for (final String s : messages)
			messageCodes.add(s);
		result.addObject("messages", messageCodes);

		this.setCreditCardMakes(result);
		this.configValues(result);

		return result;

	}

	protected ModelAndView createEditModelAndView(final ConferenceSponsor sponsor, final String... messages) {

		final ModelAndView result;

		result = new ModelAndView("conferenceSponsor/edit");
		result.addObject("sponsor", sponsor);
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
