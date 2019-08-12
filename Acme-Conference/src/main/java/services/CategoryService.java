
package services;

import java.util.ArrayList;
import java.util.Collection;

import javax.transaction.Transactional;
import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.CategoryRepository;
import utiles.AuthorityMethods;
import domain.Category;
import domain.Conference;
import domain.Finder;

@Service
@Transactional
public class CategoryService {

	@Autowired
	private CategoryRepository	categoryRepository;

	@Autowired
	private FinderService		finderService;

	@Autowired
	private ConferenceService	conferenceService;

	@Autowired
	private Validator			validator;


	public Category reconstructAndSave(Category category, final BindingResult binding) {
		category = this.reconstruct(category, binding);
		this.save(category);

		return category;
	}

	public Category save(final Category category) {
		Assert.isTrue(AuthorityMethods.chechAuthorityLogged("ADMINISTRATOR"));
		Assert.isTrue(!category.getCategoryES().equals("CONGRESO") && !category.getCategoryEN().equals("CONFERENCE"));
		Assert.isTrue(category.getId() != this.categoryRepository.getGeneralCategory().getId());

		final Category parent = category.getParent();
		Assert.isTrue(category.getId() != parent.getId());

		final Collection<Category> children = this.getChildrenOfACategory(category);
		Assert.isTrue(!children.contains(parent));

		final Category categorySaved = this.categoryRepository.saveAndFlush(category);

		if (category.getId() == 0) {
			final Category newParent = categorySaved.getParent();
			newParent.getChildren().add(categorySaved);
			this.categoryRepository.save(newParent);
		}

		return categorySaved;
	}
	public Collection<Category> findAll() {
		return this.categoryRepository.findAll();
	}

	public Category findOne(final int id) {
		return this.categoryRepository.findOne(id);
	}

	public Collection<Category> findAllMinusCONFERENCE() {
		return this.categoryRepository.findAllMinusCONFERENCE();
	}

	public Collection<Category> getChildrenOfACategory(final Category category) {
		final Collection<Category> acum = new ArrayList<>();
		return this.getChildrenOfACategory(category, acum);
	}

	private Collection<Category> getChildrenOfACategory(final Category category, final Collection<Category> acum) {
		final Collection<Category> children = category.getChildren();
		if (children == null || children.size() == 0)
			acum.add(category);
		else {
			for (final Category child : children)
				this.getChildrenOfACategory(child, acum);
			acum.add(category);
		}
		return acum;
	}

	public Collection<Category> findAllSuchACONFERENCEParent() {
		return this.categoryRepository.findAllSuchACONFERENCEParent();
	}

	public Category create() {
		return new Category();
	}

	public Collection<String> getAllNameEN() {
		return this.categoryRepository.getAllNameEN();
	}

	public Collection<String> getAllNameES() {
		return this.categoryRepository.getAllNameES();
	}

	public Category reconstruct(final Category category, final BindingResult binding) {
		final Category result;

		if (category.getId() == 0)
			result = this.create();
		else {
			result = this.findOne(category.getId());
			final Category oldParent = result.getParent();
			final Category newParent = category.getParent();
			if (!oldParent.equals(newParent))
				this.updateChildrenOfParentOf(result, category);
		}

		result.setCategoryEN(category.getCategoryEN().toUpperCase());
		result.setCategoryES(category.getCategoryES().toUpperCase());

		final Category generalCategory = this.categoryRepository.getGeneralCategory();
		if (category.getParent() == null)
			result.setParent(generalCategory);
		else
			result.setParent(category.getParent());

		this.validateCategoryName(category, binding);

		this.validator.validate(result, binding);

		if (binding.hasErrors())
			throw new ValidationException();

		return result;
	}
	private void validateCategoryName(final Category category, final BindingResult binding) {
		final Collection<String> namesEN = this.getAllNameEN();
		final Collection<String> namesES = this.getAllNameES();

		if (category.getId() != 0) {
			namesEN.remove(this.findOne(category.getId()).getCategoryEN());
			namesES.remove(this.findOne(category.getId()).getCategoryES());
		}

		if (category.getCategoryEN().toUpperCase().trim().equals("CONFERENCE") || category.getCategoryEN().toUpperCase().trim().equals("CONGRESO"))
			binding.rejectValue("categoryEN", "category.error.namelikeConference");

		if (category.getCategoryES().toUpperCase().trim().equals("CONGRESO") || category.getCategoryES().toUpperCase().trim().equals("CONFERENCE"))
			binding.rejectValue("categoryES", "category.error.namelikeConference");

		if (namesEN.contains(category.getCategoryEN().trim().toUpperCase()))
			binding.rejectValue("categoryEN", "category.error.namelikeOther");

		if (namesES.contains(category.getCategoryES().trim().toUpperCase()))
			binding.rejectValue("categoryES", "category.error.namelikeOther");
	}

	private void updateChildrenOfParentOf(final Category oldCategory, final Category newCategory) {

		final Category oldParent = oldCategory.getParent();
		oldParent.getChildren().remove(oldCategory);
		this.categoryRepository.saveAndFlush(oldParent);

		Category newParent = newCategory.getParent();
		if (newParent == null)
			newParent = this.categoryRepository.getGeneralCategory();
		newParent.getChildren().add(newCategory);
		this.categoryRepository.saveAndFlush(newParent);
	}

	public void delete(final Category category) {
		Assert.isTrue(AuthorityMethods.chechAuthorityLogged("ADMINISTRATOR"));
		final Category generalCategory = this.categoryRepository.getGeneralCategory();
		Assert.isTrue(category.getId() != generalCategory.getId());

		final Category categoryParent = category.getParent();

		final Collection<Conference> conferenceWithThisCategory = this.conferenceService.getConferenceByCategory(category.getId());

		for (final Conference conference : conferenceWithThisCategory) {
			conference.setCategory(categoryParent);
			this.conferenceService.updateCategory(conference);
		}

		final Collection<Finder> finderWithThisCategory = this.finderService.getFindersByCategory(category.getId());

		for (final Finder finder : finderWithThisCategory) {
			finder.setCategory(categoryParent);
			this.finderService.updateCategory(finder);
		}

		final Collection<Category> subcategories = category.getChildren();

		for (final Category subcategory : subcategories) {
			subcategory.setParent(categoryParent);
			this.categoryRepository.save(subcategory);
		}

		final Collection<Category> children = categoryParent.getChildren();
		children.remove(category);
		children.addAll(subcategories);
		categoryParent.setChildren(children);
		this.categoryRepository.save(categoryParent);

		this.categoryRepository.delete(category);

	}

}
