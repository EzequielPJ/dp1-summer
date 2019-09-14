
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.GomelaService;
import domain.Gomela;

@Controller
@RequestMapping("/gomela")
public class GomelaController extends AbstractController {

	@Autowired
	private GomelaService	gomelaService;


	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int idGomela) {
		ModelAndView result;

		try {
			final Gomela gomela = this.gomelaService.findOne(idGomela);
			result = new ModelAndView("gomela/display");
			result.addObject("gomela", gomela);
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
		final ModelAndView result = new ModelAndView("gomela/list");

		final Collection<Gomela> gomelas = this.gomelaService.getGomelasOfAdminLogged();

		result.addObject("gomelas", gomelas);
		result.addObject("requestURI", "/gomela/administrator/list.do");
		result.addObject("message", messageCode);

		this.configValues(result);
		return result;
	}
}
