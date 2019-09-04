
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

import services.QuoletService;
import controllers.AbstractController;
import domain.Quolet;

@Controller
@RequestMapping("/quolet/administrator")
public class QuoletAdministratorController extends AbstractController {

	@Autowired
	private QuoletService	quoletService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		result = this.listModelAndView();
		return result;
	}

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int idQuolet) {
		ModelAndView result;

		try {
			final Quolet quolet = this.quoletService.findOne(idQuolet);
			result = new ModelAndView("quolet/display");
			result.addObject("quolet", quolet);
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
			result = this.createEditModelAndView(this.quoletService.create(idConference));
		} catch (final Throwable oops) {
			result = this.listModelAndView("security.error.accessDenied");
		}

		return result;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int idQuolet) {
		ModelAndView result;

		try {
			this.quoletService.delete(idQuolet);
			result = new ModelAndView("redirect:list.do");
		} catch (final Throwable oops) {
			result = this.listModelAndView("quolet.commit.error");
			oops.printStackTrace();
		}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int idQuolet) {
		ModelAndView result;

		try {
			final Quolet quolet = this.quoletService.findOne(idQuolet);
			Assert.isTrue(!quolet.getFinalMode());
			result = this.createEditModelAndView(quolet);
		} catch (final Throwable oops) {
			result = this.listModelAndView("security.error.accessDenied");
		}

		return result;
	}
	@RequestMapping(value = "/save", method = RequestMethod.POST, params = "save")
	public ModelAndView save(final Quolet quolet, final BindingResult binding) {
		ModelAndView result;

		try {
			this.quoletService.reconstruct(quolet, binding);
			result = new ModelAndView("redirect:list.do");
		} catch (final ValidationException oops) {
			result = this.createEditModelAndView(quolet);
			oops.printStackTrace();
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(quolet, "quolet.commit.error");
			oops.printStackTrace();
		}

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

	protected ModelAndView createEditModelAndView(final Quolet quolet) {
		return this.createEditModelAndView(quolet, null);
	}

	protected ModelAndView createEditModelAndView(final Quolet quolet, final String messageCode) {
		final ModelAndView result;

		result = new ModelAndView("quolet/edit");

		result.addObject("quolet", quolet);
		result.addObject("message", messageCode);

		this.configValues(result);
		return result;
	}

}
