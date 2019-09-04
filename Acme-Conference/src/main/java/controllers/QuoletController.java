
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.QuoletService;
import domain.Quolet;

@Controller
@RequestMapping("/quolet")
public class QuoletController extends AbstractController {

	@Autowired
	private QuoletService	quoletService;


	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int idQuolet) {
		ModelAndView result;

		try {
			final Quolet quolet = this.quoletService.findOne(idQuolet);
			result = new ModelAndView("quolet/display");
			result.addObject("quolet", quolet);
			result.addObject("bot", false);
		} catch (final Throwable oops) {
			result = this.listModelAndView("security.error.accessDenied");
		}

		this.configValues(result);
		return result;
	}

	protected ModelAndView listModelAndView() {
		return this.listModelAndView(null);
	}

	protected ModelAndView listModelAndView(final String messageCode) {
		final ModelAndView result = new ModelAndView("quolet/list");

		final Collection<Quolet> quolets = this.quoletService.getQuoletsOfAdminLogged();

		result.addObject("quolets", quolets);
		result.addObject("requestURI", "/quolet/administrator/list.do");
		result.addObject("message", messageCode);

		this.configValues(result);
		return result;
	}
}
