
package services;

import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import utilities.AbstractTest;
import domain.Category;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class CategoryServiceTest extends AbstractTest {

	@Autowired
	private CategoryService	categoryService;


	//CREATE
	public void createDriver() {
		final Object testingData[][] = {
			{
				"admin", "Nueva Categoría", "New Category", super.getEntityId("category6"), null
			}, {
				"sponsor0", "Nueva Categoría", "New Category", super.getEntityId("category6"), IllegalArgumentException.class
			}, {
				"admin", "", "New Category", super.getEntityId("category6"), ConstraintViolationException.class
			}, {
				"admin", "Nueva Categoría", "", super.getEntityId("category6"), ConstraintViolationException.class
			}, {
				"admin", "Nueva Categoría", "CONFERENCE", super.getEntityId("category6"), IllegalArgumentException.class
			}, {
				"admin", "CONGRESO", "Nueva Categoría", super.getEntityId("category6"), IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.createTemplate((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (int) testingData[i][3], (Class<?>) testingData[i][4]);
	}

	// Ancillary methods ------------------------------------------------------

	protected void createTemplate(final String user, final String categoryES, final String categoryEN, final int parentID, final Class<?> expected) {
		Class<?> caught = null;

		try {
			super.authenticate(user);

			final Category category = this.categoryService.create();

			category.setCategoryEN(categoryEN);
			category.setCategoryES(categoryES);
			category.setParent(this.categoryService.findOne(parentID));

			this.categoryService.save(category);

			this.categoryService.flush();

			super.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

	//EDIT
	@Test
	public void editDriver() {
		final Object testingData[][] = {
			//			{
			//				"admin", "Categoría", "Category", super.getEntityId("category4"), super.getEntityId("category6"), null
			//			}, {
			//				"admin", "", "Category", super.getEntityId("category4"), super.getEntityId("category6"), ConstraintViolationException.class
			//			}, {
			//				"admin", "Categoría", "", super.getEntityId("category4"), super.getEntityId("category6"), ConstraintViolationException.class
			//			}, {
			//				"admin", "Categoria", "CONFERENCE", super.getEntityId("category4"), super.getEntityId("category6"), IllegalArgumentException.class
			//			}, {
			//				"admin", "congreso", "Category", super.getEntityId("category4"), super.getEntityId("category6"), IllegalArgumentException.class
			//			}, 
			{
				"admin", "Categoría", "Category", super.getEntityId("category0"), super.getEntityId("category6"), IllegalArgumentException.class
			}
		//, {
		//				"admin", "Categoría", "Category", super.getEntityId("category6"), super.getEntityId("category6"), IllegalArgumentException.class
		//			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.editTemplate((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (int) testingData[i][3], (int) testingData[i][4], (Class<?>) testingData[i][5]);
	}
	// Ancillary methods ------------------------------------------------------

	protected void editTemplate(final String user, final String categoryES, final String categoryEN, final int categoryID, final int parentID, final Class<?> expected) {
		Class<?> caught = null;

		try {
			super.authenticate(user);

			final Category category = this.categoryService.findOne(categoryID);
			System.out.println("-------------");

			category.setCategoryEN(categoryEN);
			category.setCategoryES(categoryES);
			category.setParent(this.categoryService.findOne(parentID));
			System.out.println("-------------");

			this.categoryService.save(category);

			this.categoryService.flush();

			super.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

	//DELETE

}
