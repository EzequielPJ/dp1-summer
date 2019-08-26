
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


	@Test
	public void creatingDriver() {
		final Object testingData[][] = {
			{
				"admin", "nombreEN", "nombreES", null
			}, {
				"admin", "", "nombreES", ConstraintViolationException.class
			}, {
				"admin", "nombreEN", "", ConstraintViolationException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.creatingTemplate((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (Class<?>) testingData[i][3]);
	}
	// Ancillary methods ------------------------------------------------------
	protected void creatingTemplate(final String user, final String nameEN, final String nameES, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			super.authenticate(user);

			Category category = this.categoryService.create();

			category = this.fillData(category, nameEN, nameES, "category0");

			this.categoryService.save(category);
			this.categoryService.flush();

			super.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
			oops.printStackTrace();
		}

		this.checkExceptions(expected, caught);
	}

	@Test
	public void updatingDriver() {
		final Object testingData[][] = {
			{
				"admin", "nombreEN", "nombreES", null
			}, {
				"admin", "", "nombreES", ConstraintViolationException.class
			}, {

				"admin", "nombreEN", "", ConstraintViolationException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.updatingTemplate((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (Class<?>) testingData[i][3]);
	}
	// Ancillary methods ------------------------------------------------------
	protected void updatingTemplate(final String user, final String nameEN, final String nameES, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			super.authenticate(user);

			Category category = this.categoryService.findOne(this.getEntityId("category2"));

			category = this.fillData(category, nameEN, nameES, "category0");

			this.categoryService.save(category);
			this.categoryService.flush();

			super.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
			oops.printStackTrace();
		}

		this.checkExceptions(expected, caught);
	}

	@Test
	public void deletingDriver() {
		final Object testingData[][] = {
			{
				"admin", "category1", null
			}, {
				"admin", "category0", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.deletingTemplate((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);
	}
	// Ancillary methods ------------------------------------------------------
	protected void deletingTemplate(final String user, final String idSponsorship, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			super.authenticate(user);

			final Category category = this.categoryService.findOne(this.getEntityId(idSponsorship));

			this.categoryService.delete(category);

			super.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
			oops.printStackTrace();
		}

		this.checkExceptions(expected, caught);
	}

	private Category fillData(final Category category, final String nameEN, final String nameES, final String parentName) {

		category.setCategoryEN(nameEN);
		category.setCategoryES(nameES);
		category.setParent(this.categoryService.findOne(this.getEntityId(parentName)));

		return category;
	}
}
