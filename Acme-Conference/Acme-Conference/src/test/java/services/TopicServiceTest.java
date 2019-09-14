
package services;

import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import utilities.AbstractTest;
import domain.Topic;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class TopicServiceTest extends AbstractTest {

	@Autowired
	private TopicService	topicService;


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

			Topic topic = this.topicService.create();

			topic = this.fillData(topic, nameEN, nameES);

			this.topicService.save(topic);
			this.topicService.flush();

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

			Topic topic = this.topicService.findOne(this.getEntityId("topic2"));

			topic = this.fillData(topic, nameEN, nameES);

			this.topicService.save(topic);
			this.topicService.flush();

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
				"admin", "topic1", null
			}, {
				"admin", "topic4", IllegalArgumentException.class
			//Topic 4 es general del sistema
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

			final Topic topic = this.topicService.findOne(this.getEntityId(idSponsorship));

			this.topicService.delete(topic);

			super.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
			oops.printStackTrace();
		}

		this.checkExceptions(expected, caught);
	}

	private Topic fillData(final Topic topic, final String nameEN, final String nameES) {

		topic.setTopicEN(nameEN);
		topic.setTopicES(nameES);

		return topic;
	}
}
