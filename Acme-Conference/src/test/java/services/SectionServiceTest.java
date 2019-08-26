
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
public class SectionServiceTest extends AbstractTest {

	@Autowired
	SectionService	sectionservice;


	@Test
	public void CreateSectionDriver() {

		final Object testingData[][] = {
			{

				"admin", "activity1", null
			}, {

				"admin", "activity0", IllegalArgumentException.class
			}, {

				"admin1", "activity1", IllegalArgumentException.class
			}, {

				"", "activity1", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.createSectionTemplate((String) testingData[i][0], super.getEntityId((String) testingData[i][1]), (Class<?>) testingData[i][2]);
	}
	protected void createSectionTemplate(final String beanName, final Integer activityId, final Class<?> expected) {
		Class<?> caught;
		caught = null;

		try {
			super.authenticate(beanName);
			this.sectionservice.create(activityId);
			this.sectionservice.flush();
			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

}
