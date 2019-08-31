
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

import security.Authority;
import security.LoginService;
import security.UserAccount;
import utilities.AbstractTest;
import domain.Reviewer;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class ReviewerServiceTest extends AbstractTest {

	@Autowired
	private ReviewerService	reviewerService;


	@Test
	public void ReviewerRegisterDriver() throws ParseException {
		final Object testingData[][] = {
			{
				null, true, null
			}, {
				"reviewer0", true, IllegalArgumentException.class
			}, {
				"reviewer0", false, IllegalArgumentException.class
			}, {
				"admin", true, IllegalArgumentException.class
			}, {
				null, false, ConstraintViolationException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.ReviewerRegisterTemplate((String) testingData[i][0], (boolean) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	// Ancillary methods ------------------------------------------------------

	protected void ReviewerRegisterTemplate(final String username, final boolean validData, final Class<?> expected) throws ParseException {
		Class<?> caught;

		caught = null;
		Reviewer reviewer = this.reviewerService.create();
		try {
			this.authenticate(username);

			if (!validData) {
				reviewer.setEmail("null");
				reviewer.setPhotoURL("thisisanonvalidurl");
			} else {
				String uniqueId = "";
				if (username == null)
					uniqueId = "NonAuth";
				else
					uniqueId = "" + (LoginService.getPrincipal().hashCode() * 31);
				reviewer.setEmail("reviewer" + uniqueId + "@mailto.com");
				reviewer.setPhotoURL("https://tiny.url/dummyPhoto");
			}
			reviewer = ReviewerServiceTest.fillData(reviewer);
			this.reviewerService.save(reviewer);
			this.reviewerService.flush();
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
			oops.printStackTrace();
		}

		this.checkExceptions(expected, caught);
	}

	private static Reviewer fillData(final Reviewer reviewer) throws NoSuchAlgorithmException {
		final Reviewer res = reviewer;

		res.setAddress("Direcci�n de prueba");

		res.setName("Dummy Reviewer");
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
