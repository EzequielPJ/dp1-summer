
package services;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.text.ParseException;

import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import security.Authority;
import security.LoginService;
import security.UserAccount;
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

	@Test
	public void AuthorRegisterDriver() throws ParseException {
		final Object testingData[][] = {
			{
				null, true, null
			}, {
				"author0", true, IllegalArgumentException.class
			}, {
				"author0", false, IllegalArgumentException.class
			}, {
				"reviewer0", true, IllegalArgumentException.class
			}, {
				null, false, ConstraintViolationException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.AuthorRegisterTemplate((String) testingData[i][0], (boolean) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	// Ancillary methods ------------------------------------------------------

	protected void AuthorRegisterTemplate(final String username, final boolean validData, final Class<?> expected) throws ParseException {
		Class<?> caught;

		caught = null;
		Author author = this.authorService.create();
		try {
			this.authenticate(username);

			if (!validData) {
				author.setEmail("null");
				author.setPhotoURL("thisisanonvalidurl");
			} else {
				String uniqueId = "";
				if (username == null)
					uniqueId = "NonAuth";
				else
					uniqueId = "" + (LoginService.getPrincipal().hashCode() * 31);
				author.setEmail("author" + uniqueId + "@mailto.com");
				author.setPhotoURL("https://tiny.url/dummyPhoto");
			}
			author = AuthorServiceTest.fillData(author);
			this.authorService.save(author);
			this.authorService.flush();
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

	private static Author fillData(final Author author) throws NoSuchAlgorithmException {
		final Author res = author;

		res.setAddress("Direcci�n de prueba");

		res.setName("Dummy Author");
		res.setPhoneNumber("+34 555-2342");

		final String dummySurname = "Dummy Wane Dan";
		res.setSurname(dummySurname);

		final UserAccount a = new UserAccount();
		final Authority auth = new Authority();
		auth.setAuthority(Authority.AUTHOR);
		a.addAuthority(auth);
		final SecureRandom randomGenerator = SecureRandom.getInstance("SHA1PRNG");
		a.setUsername("DummyTest" + randomGenerator.nextInt(res.getEmail().length()) + randomGenerator.nextInt(res.getEmail().length()));
		a.setPassword("DummyPass");

		res.setUserAccount(a);

		return res;
	}

}
