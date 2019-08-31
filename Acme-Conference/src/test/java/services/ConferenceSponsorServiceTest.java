
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
import domain.ConferenceSponsor;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class ConferenceSponsorServiceTest extends AbstractTest {

	@Autowired
	private ConferenceSponsorService	conferenceSponsorService;


	@Test
	public void ConferenceSponsorRegisterDriver() throws ParseException {
		final Object testingData[][] = {
			{
				null, true, null
			}, {
				"sponsor0", true, IllegalArgumentException.class
			}, {
				"sponsor0", false, IllegalArgumentException.class
			}, {
				"reviewer0", true, IllegalArgumentException.class
			}, {
				null, false, ConstraintViolationException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.ConferenceSponsorRegisterTemplate((String) testingData[i][0], (boolean) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	// Ancillary methods ------------------------------------------------------

	protected void ConferenceSponsorRegisterTemplate(final String username, final boolean validData, final Class<?> expected) throws ParseException {
		Class<?> caught;

		this.unauthenticate();
		caught = null;
		try {

			this.authenticate(username);

			ConferenceSponsor conferenceSponsor = this.conferenceSponsorService.create();

			if (!validData) {
				conferenceSponsor.setEmail("null");
				conferenceSponsor.setPhotoURL("thisisanonvalidurl");
			} else {
				String uniqueId = "";
				if (username == null)
					uniqueId = "NonAuth";
				else
					uniqueId = "" + (LoginService.getPrincipal().hashCode() * 31);
				conferenceSponsor.setEmail("confSponsor" + uniqueId + "@mailto.com");
				conferenceSponsor.setPhotoURL("https://tiny.url/dummyPhoto");
			}
			conferenceSponsor = ConferenceSponsorServiceTest.fillData(conferenceSponsor);
			this.conferenceSponsorService.save(conferenceSponsor);
			this.conferenceSponsorService.flush();
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

	private static ConferenceSponsor fillData(final ConferenceSponsor conferenceSponsor) throws NoSuchAlgorithmException {
		final ConferenceSponsor res = conferenceSponsor;

		res.setAddress("Direcci�n de prueba");

		res.setName("Dummy ConferSponsor");
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
