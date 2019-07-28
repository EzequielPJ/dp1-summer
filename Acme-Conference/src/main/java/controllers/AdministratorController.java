
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


	@RequestMapping(value = "/process", method = RequestMethod.GET)
	public ModelAndView process() {
		return this.processModelAndView();
	}

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
