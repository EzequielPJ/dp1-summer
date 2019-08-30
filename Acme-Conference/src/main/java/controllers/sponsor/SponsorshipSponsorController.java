
package controllers.sponsor;

import java.util.Collection;

import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import services.AdminConfigService;
import services.ConferenceService;
import services.ConferenceSponsorService;
import services.SponsorshipService;
import controllers.AbstractController;
import domain.ConferenceSponsor;
import domain.Sponsorship;

@Controller
@RequestMapping("/sponsorship/sponsor")
public class SponsorshipSponsorController extends AbstractController {

	@Autowired
	private ConferenceSponsorService	sponsorService;

	@Autowired
	private SponsorshipService			sponsorshipService;

	@Autowired
	private AdminConfigService			adminConfigService;

	@Autowired
	private ConferenceService			conferenceService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		return this.listModelAndView();
	}

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int idSponsorship) {
		ModelAndView result;

		try {
			final Sponsorship sponsorship = this.sponsorshipService.findOne(idSponsorship);
			Assert.notNull(sponsorship);
			result = new ModelAndView("sponsorship/display");
			result.addObject("sponsorship", sponsorship);
			result.addObject("anonymizedNumber", "*************" + sponsorship.getCreditCard().getNumber().substring(13));
			result.addObject("requestURI", "/sponsorship/sponsor/display.do?idSponsorship=" + idSponsorship);
		} catch (final Exception e) {
			result = this.listModelAndView("security.error.accessDenied");
		}

		this.configValues(result);
		return result;
	}
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		final ModelAndView result;

		result = this.createEditModelAndView(this.sponsorshipService.create());

		return result;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int idSponsorship) {
		ModelAndView result;

		try {
			final Sponsorship sponsorship = this.sponsorshipService.findOne(idSponsorship);
			Assert.notNull(sponsorship);
			this.sponsorshipService.delete(sponsorship);
			result = new ModelAndView("redirect:list.do");
		} catch (final Exception e) {
			result = this.listModelAndView("sponsorship.commit.error");
		}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int idSponsorship) {
		ModelAndView result;

		try {
			final Sponsorship sponsorship = this.sponsorshipService.findOne(idSponsorship);
			Assert.notNull(sponsorship);
			result = this.createEditModelAndView(sponsorship);
		} catch (final Exception e) {
			result = this.listModelAndView("security.error.accessDenied");
		}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(Sponsorship sponsorship, final BindingResult binding) {
		ModelAndView result;

		try {
			sponsorship = this.sponsorshipService.reconstruct(sponsorship, binding);
			this.sponsorshipService.save(sponsorship);
			result = new ModelAndView("redirect:list.do");
		} catch (final ValidationException oops) {
			result = this.createEditModelAndView(sponsorship);
			oops.printStackTrace();
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(sponsorship, "sponsorship.commit.error");
		}

		return result;
	}

	protected ModelAndView listModelAndView() {
		return this.listModelAndView(null);
	}

	protected ModelAndView listModelAndView(final String messageCode) {
		final ModelAndView result = new ModelAndView("sponsorship/list");

		final ConferenceSponsor sponsor = this.sponsorService.findByPrincipal(LoginService.getPrincipal());
		final Collection<Sponsorship> sponsorships = this.sponsorshipService.findAllBySponsor(sponsor.getId());

		result.addObject("sponsorships", sponsorships);
		result.addObject("requestURI", "/sponsorship/sponsor/list.do");
		result.addObject("message", messageCode);

		this.configValues(result);
		return result;
	}

	protected ModelAndView createEditModelAndView(final Sponsorship sponsorship) {
		return this.createEditModelAndView(sponsorship, null);
	}

	protected ModelAndView createEditModelAndView(final Sponsorship sponsorship, final String messageCode) {
		final ModelAndView result;

		result = new ModelAndView("sponsorship/edit");

		result.addObject("sponsorship", sponsorship);
		result.addObject("conferences", this.conferenceService.getConferencesFinalMode());
		result.addObject("makers", this.adminConfigService.getAdminConfig().getCreditCardMakes());
		result.addObject("message", messageCode);

		this.configValues(result);
		return result;
	}

}
