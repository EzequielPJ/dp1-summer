
package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import services.AuthorService;
import services.SubmissionService;

@Controller
@RequestMapping("/administrator")
public class AdministratorController extends AbstractController {

	@Autowired
	private AuthorService		authorService;

	@Autowired
	private SubmissionService	submissionService;


	// @Autowired
	// private SponsorshipService sponsorshipService;
	//
	//
	@RequestMapping(value = "/process", method = RequestMethod.GET)
	public ModelAndView process() {
		return this.processModelAndView();
	}
	//
	// @RequestMapping(value = "/updateSpam", method = RequestMethod.GET)
	// public ModelAndView updateSpam() {
	// ModelAndView result;
	// try {
	// this.actorService.updateSpam();
	// result = this.processModelAndView();
	// } catch (final Throwable oops) {
	// result = this.processModelAndView("administrator.commit.error");
	// }
	// return result;
	// }
	//
	// @RequestMapping(value = "/ban", method = RequestMethod.GET)
	// public ModelAndView ban(@RequestParam final Integer idActor) {
	// ModelAndView result;
	// try {
	// final Actor actor = this.actorService.getActor(idActor);
	// this.actorService.ban(actor);
	// result = this.processModelAndView();
	// } catch (final Throwable oops) {
	// result = this.processModelAndView("administrator.commit.error");
	// }
	//
	// return result;
	// }
	//
	// @RequestMapping(value = "/unban", method = RequestMethod.GET)
	// public ModelAndView unban(@RequestParam final Integer idActor) {
	// ModelAndView result;
	// try {
	// final Actor actor = this.actorService.getActor(idActor);
	// this.actorService.unban(actor);
	// result = this.processModelAndView();
	// } catch (final Throwable oops) {
	// result = this.processModelAndView("administrator.commit.error");
	// }
	// return result;
	// }
	//
	@RequestMapping(value = "/computeScore", method = RequestMethod.GET)
	public ModelAndView computeScore() {
		ModelAndView result;
		try {
			this.authorService.computeScore();
			result = this.processModelAndView();
		} catch (final Throwable e) {
			result = this.processModelAndView("administrator.commit.error");
			e.printStackTrace();
		}
		return result;

	}

	@RequestMapping(value = "/assignReviewers", method = RequestMethod.GET)
	public ModelAndView assignReviewers() {
		ModelAndView result;
		try {
			this.submissionService.assignReviewers();
			result = this.processModelAndView();
		} catch (final Exception e) {
			result = this.processModelAndView("administrator.commit.error");
			e.printStackTrace();

		}
		return result;

	}

	protected ModelAndView processModelAndView() {
		return this.processModelAndView(null);
	}

	protected ModelAndView processModelAndView(final String messageCode) {
		final ModelAndView result = new ModelAndView("administrator/process");
		result.addObject("actorLogged", LoginService.getPrincipal());
		result.addObject("message", messageCode);

		this.configValues(result);
		return result;
	}
}
