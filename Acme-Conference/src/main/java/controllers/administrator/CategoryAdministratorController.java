
package controllers.administrator;

import java.util.Collection;

import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.CategoryService;
import controllers.AbstractController;
import domain.Category;

@Controller
@RequestMapping("/category/administrator")
public class CategoryAdministratorController extends AbstractController {

	@Autowired
	private CategoryService	categoryService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		result = this.listModelAndView();
		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		result = this.createModelAndView(this.categoryService.create());
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(final int idCategory) {
		ModelAndView result;
		final Category category = this.categoryService.findOne(idCategory);
		result = this.createModelAndView(category);
		return result;
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST, params = "save")
	public ModelAndView save(Category category, final BindingResult binding) {
		ModelAndView result;
		result = new ModelAndView("category/edit");

		category = this.categoryService.reconstruct(category, binding);

		try {
			this.categoryService.save(category);
			result = new ModelAndView("redirect:list.do");
		} catch (final ValidationException e) {
			result = this.createModelAndView(category);
		} catch (final Throwable oops) {
			result = this.createModelAndView(category, "category.save.error");
			oops.printStackTrace();
		}

		return result;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int idCategory) {
		ModelAndView result;
		final Category category = this.categoryService.findOne(idCategory);

		try {
			this.categoryService.delete(category);
			result = new ModelAndView("redirect:list.do");
		} catch (final Throwable oops) {
			result = this.listModelAndView("category.save.error");
			oops.printStackTrace();
		}

		return result;
	}

	protected ModelAndView listModelAndView() {
		return this.listModelAndView(null);
	}

	protected ModelAndView listModelAndView(final String message) {
		final ModelAndView result = new ModelAndView("category/list");

		final Collection<Category> categories = this.categoryService.findAllMinusCONFERENCE();
		result.addObject("categories", categories);
		result.addObject("requestURI", "/category/administrator/list.do");
		result.addObject("message", message);

		this.configValues(result);
		return result;
	}

	protected ModelAndView createModelAndView(final Category category) {
		return this.createModelAndView(category, null);
	}

	//FIXME
	protected ModelAndView createModelAndView(final Category category, final String message) {
		final ModelAndView result = new ModelAndView("category/edit");

		//Todos menos la categoria en cuestion, sus subcategorias y la categoria general
		final Collection<Category> posibleParents = this.categoryService.findAllMinusCONFERENCE();
		posibleParents.remove(category);
		final Collection<Category> allSubcategories = this.categoryService.getChildrenOfACategory(category);
		posibleParents.removeAll(allSubcategories);

		result.addObject("category", category);
		result.addObject("posibleParents", posibleParents);
		result.addObject("message", message);

		this.configValues(result);
		return result;
	}

}
