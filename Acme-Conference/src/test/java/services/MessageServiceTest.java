
package services;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import security.LoginService;
import utilities.AbstractTest;
import domain.Actor;
import domain.Message;
import domain.Topic;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class MessageServiceTest extends AbstractTest {

	@Autowired
	private ActorService	actorService;

	@Autowired
	private MessageService	messageService;

	@Autowired
	private TopicService	topicService;


	@Test
	public void sendMessageDriver() {

		final Object testingData[][] = {
			{

				"author0", "subject", "body", "topic0", "author1", null
			}, {

				"author0", null, null, "topic0", "author1", ConstraintViolationException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.sendMessageTemplate((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (Class<?>) testingData[i][5]);
	}

	protected void sendMessageTemplate(final String actor, final String subject, final String body, final String topic, final String recipient, final Class<?> expected) {
		Class<?> caught;
		caught = null;

		try {
			super.authenticate(actor);
			final Message message = new Message();
			final Actor sender = this.actorService.findByUserAccount(LoginService.getPrincipal());

			message.setSender(sender);
			message.setMoment(new Date());
			message.setBody(body);
			message.setSubject(subject);

			final Collection<Actor> recipients = new HashSet<>();
			recipients.add(this.actorService.findOne(this.getEntityId(recipient)));
			message.setRecipients(recipients);

			final Collection<Topic> topics = new HashSet<>();
			topics.add(this.topicService.findOne(this.getEntityId(topic)));
			message.setTopics(topics);

			final Collection<Actor> actors = new HashSet<>();
			actors.addAll(recipients);
			actors.add(sender);
			message.setActors(actors);

			this.messageService.save(message);
			this.messageService.flush();
			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

	@Test
	public void deleteMessageDriver() {

		final Object testingData[][] = {
			{

				"author0", "message0", null
			}, {

				"author1", "message1", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.deleteMessageTemplate((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	protected void deleteMessageTemplate(final String actor, final String message, final Class<?> expected) {
		Class<?> caught;
		caught = null;

		try {
			super.authenticate(actor);

			this.messageService.prepareMessageToDelete(this.getEntityId(message));
			this.messageService.delete(this.getEntityId(message));

			this.messageService.flush();
			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

}
