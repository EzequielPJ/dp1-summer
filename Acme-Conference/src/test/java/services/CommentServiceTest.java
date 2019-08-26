
package services;

import java.util.Date;

import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import utilities.AbstractTest;
import domain.Comment;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class CommentServiceTest extends AbstractTest {

	@Autowired
	CommentService	commentService;


	@Test
	public void CreateCommentDriver() {

		final Object testingData[][] = {
			{

				"admin", "conference0", "text", null
			}, {

				"admin", "conference0", "", ConstraintViolationException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.createCommentTemplate((String) testingData[i][0], super.getEntityId((String) testingData[i][1]), (String) testingData[i][2], (Class<?>) testingData[i][3]);
	}
	protected void createCommentTemplate(final String beanName, final Integer conferenceId, final String text, final Class<?> expected) {
		Class<?> caught;
		caught = null;

		try {
			super.authenticate(beanName);
			final Comment c = this.commentService.create(conferenceId, 0);
			c.setText(text);
			c.setMoment(new Date());
			c.setTitle(text);
			this.commentService.save(c);
			this.commentService.flush();
			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

}
