
package services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import utilities.AbstractTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class ConferenceServiceTest extends AbstractTest {

	@Autowired
	ConferenceService	conferenceService;


	@Test
	public void CreateConferenceDriver() {

		final Object testingData[][] = {
			{

				"admin", null
			}, {

				"author0", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.createConferenceTemplate((String) testingData[i][0], (Class<?>) testingData[i][1]);
	}
	protected void createConferenceTemplate(final String beanName, final Class<?> expected) {
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

}
