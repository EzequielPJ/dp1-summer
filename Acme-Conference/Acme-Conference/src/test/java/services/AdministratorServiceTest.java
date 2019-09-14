
package services;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import security.Authority;
import security.LoginService;
import security.UserAccount;
import utilities.AbstractTest;
import domain.Administrator;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class AdministratorServiceTest extends AbstractTest {

	@Autowired
	private AdministratorService	administratorService;


	@Test
	public void AdminRegisterDriver() {
		final Object testingData[][] = {
			{
				null, true, IllegalArgumentException.class
			}, {
				"admin", true, null
			}, {
				"author0", true, IllegalArgumentException.class
			}, {
				"reviewer0", true, IllegalArgumentException.class
			}, {
				"admin", false, ConstraintViolationException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.AdminRegisterTemplate((String) testingData[i][0], (boolean) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	// Ancillary methods ------------------------------------------------------

	protected void AdminRegisterTemplate(final String username, final boolean validData, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		Administrator admin = this.administratorService.create();
		try {
			this.authenticate(username);

			if (!validData) {
				admin.setEmail("");
				admin.setPhotoURL("thisisanonvalidurl");
			} else {
				String uniqueId = "";
				if (username == null)
					uniqueId = "NonAuth";
				else
					uniqueId = "" + (LoginService.getPrincipal().hashCode() * 31);
				admin.setEmail("<admin" + uniqueId + "@>");
				admin.setPhotoURL("https://tiny.url/dummyPhoto");
			}
			admin = AdministratorServiceTest.fillData(admin);
			this.administratorService.save(admin);
			this.administratorService.flush();
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}
	private static Administrator fillData(final Administrator admin) {
		final Administrator res = admin;

		res.setAddress("Direcci�n de prueba");

		res.setName("DummyBoss");
		res.setPhoneNumber("+34 555-2342");

		final String dummySurname = "Dummy Wane Dan";
		res.setSurname(dummySurname);

		final UserAccount a = new UserAccount();
		final Authority auth = new Authority();
		auth.setAuthority(Authority.ADMINISTRATOR);
		a.addAuthority(auth);
		a.setUsername("DummyTest" + res.getEmail());
		a.setPassword("DummyPass");

		res.setUserAccount(a);

		return res;
	}
}
