
package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import security.Authority;
import security.LoginService;
import security.UserAccount;
import services.ActorService;
import services.AdministratorService;
import services.AuthorService;
import services.MessageService;
import services.ReviewerService;
import services.SponsorService;
import services.SponsorshipService;
import utiles.AuthorityMethods;
import domain.Actor;
import domain.Administrator;
import domain.Author;
import domain.Reviewer;
import domain.Sponsor;

@Controller
@RequestMapping("/actor")
public class ActorController extends AbstractController {

	@Autowired
	private ActorService			actorService;

	@Autowired
	private AdministratorService	administratorService;

	@Autowired
	private AuthorService			authorService;

	@Autowired
	private ReviewerService			reviewerService;

	@Autowired
	private SponsorService			sponsorService;

	@Autowired
	private MessageService			messageService;

	@Autowired
	private SponsorshipService		sponsorshipService;


	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(final String targetURL) {
		ModelAndView result;
		result = this.createModelAndViewDisplay(targetURL);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit() {

		ModelAndView res;

		res = this.createModelAndViewEditActor();

		return res;

	}

	protected ModelAndView createModelAndViewDisplay(final String targetURL) {
		final ModelAndView result = new ModelAndView("actor/display");

		final UserAccount principal = LoginService.getPrincipal();

		final Actor actor = this.actorService.findByUserAccount(principal);

		result.addObject("actor", actor);
		result.addObject("userLogged", principal);

		final Authority authority = AuthorityMethods.getLoggedAuthority();

		result.addObject("authority", authority.getAuthority());

		switch (authority.getAuthority()) {
		case "ADMINISTRATOR":
			final Administrator administrator = this.administratorService.findOne(actor.getId());
			result.addObject("administrator", administrator);
			break;

		case "AUTHOR":
			final Author author = this.authorService.findOne(actor.getId());
			result.addObject("author", author);
			break;

		case "REVIEWER":
			final Reviewer reviewer = this.reviewerService.findOne(actor.getId());
			result.addObject("reviewer", reviewer);

			break;

		case "SPONSOR":
			final Sponsor sponsor = this.sponsorService.findOne(actor.getId());
			result.addObject("sponsor", sponsor);
			break;
		}

		result.addObject("back", false);
		result.addObject("requestURI", "actor/display.do");
		result.addObject("targetURL", targetURL);
		this.configValues(result);
		return result;

	}

	protected ModelAndView createModelAndViewEditActor() {
		ModelAndView result;

		final Authority authority = AuthorityMethods.getLoggedAuthority();

		switch (authority.getAuthority()) {
		case "ADMINISTRATOR":
			final Administrator admin = this.administratorService.findByPrincipal(LoginService.getPrincipal());
			result = new ModelAndView("administrator/edit");
			result.addObject("administrator", admin);
			result.addObject("edit", true);
			break;

		case "AUTHOR":
			final Author author = this.authorService.findByPrincipal(LoginService.getPrincipal());
			result = new ModelAndView("author/edit");
			result.addObject("author", author);
			result.addObject("edit", true);
			this.setCreditCardMakes(result);
			break;

		case "REVIEWER":
			final Reviewer reviewer = this.reviewerService.findByPrincipal(LoginService.getPrincipal());
			result = new ModelAndView("reviewer/edit");
			result.addObject("reviewer", reviewer);
			result.addObject("edit", true);
			break;

		case "SPONSOR":
			final Sponsor sponsor = this.sponsorService.findByPrincipal(LoginService.getPrincipal());
			result = new ModelAndView("sponsor/edit");
			result.addObject("sponsor", sponsor);
			result.addObject("edit", true);
			this.setCreditCardMakes(result);
			break;
		default:

			result = new ModelAndView("/");
			break;
		}

		this.configValues(result);
		return result;
	}

}
