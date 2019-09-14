
package controllers.administrator;

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

import services.GomelaService;
import controllers.AbstractController;
import domain.Gomela;

@Controller
@RequestMapping("/gomela/administrator")
public class GomelaAdministratorController extends AbstractController {

	@Autowired
	private GomelaService	gomelaService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		result = this.listModelAndView();
		return result;
	}

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int idGomela) {
		ModelAndView result;

		try {
			final Gomela gomela = this.gomelaService.findOne(idGomela);
			result = new ModelAndView("gomela/display");
			result.addObject("gomela", gomela);
			result.addObject("backURL", "/");
			result.addObject("bot", true);
		} catch (final Throwable oops) {
			result = this.listModelAndView("security.error.accessDenied");
		}

		this.configValues(result);
		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int idConference) {
		ModelAndView result;

		try {
			result = this.createEditModelAndView(this.gomelaService.create(idConference));
		} catch (final Throwable oops) {
			result = this.listModelAndView("security.error.accessDenied");
		}

		return result;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int idGomela) {
		ModelAndView result;

		try {
			this.gomelaService.delete(idGomela);
			result = new ModelAndView("redirect:list.do");
		} catch (final Throwable oops) {
			result = this.listModelAndView("gomela.commit.error");
			oops.printStackTrace();
		}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int idGomela) {
		ModelAndView result;

		try {
			final Gomela gomela = this.gomelaService.findOne(idGomela);
			Assert.isTrue(!gomela.getFinalMode());
			result = this.createEditModelAndView(gomela);
		} catch (final Throwable oops) {
			result = this.listModelAndView("security.error.accessDenied");
		}

		return result;
	}
	@RequestMapping(value = "/save", method = RequestMethod.POST, params = "save")
	public ModelAndView save(final Gomela gomela, final BindingResult binding) {
		ModelAndView result;

		try {
			this.gomelaService.reconstruct(gomela, binding);
			result = new ModelAndView("redirect:list.do");
		} catch (final ValidationException oops) {
			result = this.createEditModelAndView(gomela);
			oops.printStackTrace();
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(gomela, "gomela.commit.error");
			oops.printStackTrace();
		}

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

	protected ModelAndView createEditModelAndView(final Gomela gomela) {
		return this.createEditModelAndView(gomela, null);
	}

	protected ModelAndView createEditModelAndView(final Gomela gomela, final String messageCode) {
		final ModelAndView result;

		result = new ModelAndView("gomela/edit");

		result.addObject("gomela", gomela);
		result.addObject("message", messageCode);

		this.configValues(result);
		return result;
	}

}
