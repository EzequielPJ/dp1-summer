
package services;

import java.util.ArrayList;
import java.util.Collection;

import javax.transaction.Transactional;
import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;

import repositories.CategoryRepository;
import utiles.AuthorityMethods;
import domain.Category;

@Service
@Transactional
public class CategoryService {

	@Autowired
	private CategoryRepository	categoryRepository;


	public Category save(final Category category) {
		//ASSERTS
		Assert.isTrue(AuthorityMethods.chechAuthorityLogged("ADMINISTRATOR"));
		Assert.isTrue(!category.getCategoryES().equals("CONGRESO") && !category.getCategoryEN().equals("CONFERENCE"));
		Assert.isTrue(category.getId() != this.categoryRepository.getGeneralCategory().getId());

		final Category parent = category.getParent();
		Assert.isTrue(category.getId() != parent.getId());

		final Collection<Category> children = this.getChildrenOfACategory(category);
		Assert.isTrue(!children.contains(parent));

		final Category categoryDB = this.findOne(category.getId());

		System.out.println(categoryDB.equals(category));

		final Category categorySaved = this.categoryRepository.saveAndFlush(category);

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

	public Collection<Category> getChildren(final int id) {
		return this.categoryRepository.getChildren(id);
	}

	public Collection<Category> getChildrenOfACategory(final Category category) {
		final Collection<Category> acum = new ArrayList<>();
		return this.getChildrenOfACategory(category, acum);
	}

	private Collection<Category> getChildrenOfACategory(final Category category, final Collection<Category> acum) {
		final Collection<Category> children = this.getChildren(category.getId());
		if (children.size() == 0)
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
		else
			result = this.findOne(category.getId());

		result.setCategoryEN(category.getCategoryEN());
		result.setCategoryES(category.getCategoryES());
		result.setChildren(category.getChildren());

		this.updateChildrenOfParentOf(result, category);

		final Category generalCategory = this.categoryRepository.getGeneralCategory();
		if (category.getParent() == null)
			result.setParent(generalCategory);
		else
			result.setParent(category.getParent());

		this.validateCategoryName(category, binding);

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

		if (category.getCategoryEN().toUpperCase().trim().equals("GENRE") || category.getCategoryEN().toUpperCase().trim().equals("CONFERENCE") || category.getCategoryEN().toUpperCase().trim().equals("CONGRESO"))
			binding.rejectValue("categoryEN", "category.error.namelikeConference");

		if (category.getCategoryES().toUpperCase().trim().equals("GNERO") || category.getCategoryES().toUpperCase().trim().equals("CONGRESO") || category.getCategoryES().toUpperCase().trim().equals("CONFERENCE"))
			binding.rejectValue("categoryES", "category.error.namelikeConference");

		if (namesEN.contains(category.getCategoryEN().trim().toLowerCase()))
			binding.rejectValue("categoryEN", "category.error.namelikeOther");

		if (namesES.contains(category.getCategoryES().trim().toLowerCase()))
			binding.rejectValue("categoryES", "category.error.namelikeOther");
	}

	private void updateChildrenOfParentOf(final Category categoryDB, final Category newCategory) {

		if (newCategory.getId() == 0) {
			final Collection<Category> children1 = newCategory.getParent().getChildren();
			children1.add(newCategory);
		}

		if (newCategory.getId() != 0 && categoryDB.getParent().equals(newCategory.getParent())) {
			//NADA
		}

		if (newCategory.getId() != 0 && !categoryDB.getParent().equals(newCategory.getParent())) {
			final Collection<Category> childrenDB = categoryDB.getParent().getChildren();
			childrenDB.remove(categoryDB);
			categoryDB.getParent().setChildren(childrenDB);
		}

		this.categoryRepository.save(categoryDB.getParent());

		//SAVE

		//		final Category oldParent = this.findOne(category.getId()).getParent();
		//		final Collection<Category> childrenOldParent = oldParent.getChildren();
		//		final Category newParent = category.getParent();
		//		final Collection<Category> childrenNewParent = newParent.getChildren();
		//		
		//		childrenOldParent.remove(category);
		//		childrenNewParent.add(category);
		//		
		//		this.categoryRepository.save(oldParent);
		//		this.categoryRepository.save()
	}

}
