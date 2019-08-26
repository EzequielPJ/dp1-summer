
package services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Author;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class AuthorServiceTest extends AbstractTest {

	@Autowired
	AuthorService	authorService;


	@Test
	public void computeScoreDriver() {

		final Object testingData[][] = {
			{

				"admin", this.authorService.getAuthor(this.getEntityId("author1")), null
			}, {

				null, this.authorService.getAuthor(this.getEntityId("author1")), IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.computeScoreTemplate((String) testingData[i][0], (Author) testingData[i][1], (Class<?>) testingData[i][2]);
	}
	protected void computeScoreTemplate(final String beanName, final Author author, final Class<?> expected) {
		Class<?> caught;
		caught = null;

		try {
			super.authenticate(beanName);
			this.authorService.computeScore();
			this.authorService.flush();
			Assert.isTrue(author.getScore() == 0.5);
			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}
}
