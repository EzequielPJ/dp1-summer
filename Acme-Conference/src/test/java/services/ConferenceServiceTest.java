
package services;

import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import utilities.AbstractTest;
import domain.Conference;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class ConferenceServiceTest extends AbstractTest {

	@Autowired
	ConferenceService	conferenceService;

	@Autowired
	CategoryService		categoryService;


	@Test
	public void CreateConferenceDriver() {

		final Object testingData[][] = {
			{

				"admin", "title", 13.5, null
			}, {

				"author0", "title", 13.5, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.createConferenceTemplate((String) testingData[i][0], (String) testingData[i][1], (Double) testingData[i][2], (Class<?>) testingData[i][3]);
	}
	protected void createConferenceTemplate(final String beanName, final String title, final Double fee, final Class<?> expected) {
		Class<?> caught;
		caught = null;

		try {
			super.authenticate(beanName);
			this.conferenceService.create();
			this.conferenceService.flush();
			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

	@Test
	public void EditConferenceDriver() {

		final Object testingData[][] = {
			{

				"admin", "conference0", "title", 13.5, null
			}, {

				"admin", "conference0", "", 13.5, ConstraintViolationException.class
			}, {

				"admin", "conference0", "title", -13.5, ConstraintViolationException.class
			}, {

				"author0", "conference0", "title", 13.5, IllegalArgumentException.class
			}, {

				"admin1", "conference0", "title", 13.5, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.editConferenceTemplate((String) testingData[i][0], super.getEntityId((String) testingData[i][1]), (String) testingData[i][2], (Double) testingData[i][3], (Class<?>) testingData[i][4]);
	}
	protected void editConferenceTemplate(final String beanName, final Integer conferenceId, final String title, final Double fee, final Class<?> expected) {
		Class<?> caught;
		caught = null;

		try {
			super.authenticate(beanName);
			final Conference c = this.conferenceService.findOne(conferenceId);
			c.setTitle(title);
			c.setFee(fee);
			this.conferenceService.save(c);
			this.conferenceService.flush();
			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

	@Test
	public void DeleteConferenceDriver() {

		final Object testingData[][] = {
			{

				"admin1", "conference0", IllegalArgumentException.class
			}, {

				"author0", "conference0", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.deleteConferenceTemplate((String) testingData[i][0], super.getEntityId((String) testingData[i][1]), (Class<?>) testingData[i][2]);
	}
	protected void deleteConferenceTemplate(final String beanName, final Integer conferenceId, final Class<?> expected) {
		Class<?> caught;
		caught = null;

		try {
			super.authenticate(beanName);
			this.conferenceService.delete(conferenceId);
			this.conferenceService.flush();
			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

}
