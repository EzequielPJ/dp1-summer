
package services;

import java.util.Collection;

import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.AdminConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class AdminConfigServiceTest extends AbstractTest {

	@Autowired
	private AdminConfigService	adminConfigService;


	//SAVE ADMINCONFIG -------------------------
	@Test
	public void saveAdminConfigTestDriver() {
		final Object testingData[][] = {
			{
				"admin", "http://url.com", "+34", "Acme-Rookie", "Hi, you are welcome", "Hola, bienvenido", null
			}, {
				"sponsor0", "http://url.com", "+34", "Acme-Rookie", "Hi, you are welcome", "Hola, bienvenido", IllegalArgumentException.class
			}, {
				"author0", "http://url.com", "+34", "Acme-Rookie", "Hi, you are welcome", "Hola, bienvenido", IllegalArgumentException.class
			}, {
				"author0", "http://url.com", "+34", "Acme-Rookie", "Hi, you are welcome", "Hola, bienvenido", IllegalArgumentException.class
			}, {
				"reviewer", "http://url.com", "+34", "Acme-Rookie", "Hi, you are welcome", "Hola, bienvenido", IllegalArgumentException.class
			}, {
				"admin", "es no es una url", "+34", "Acme-Rookie", "Hi, you are welcome", "Hola, bienvenido", ConstraintViolationException.class
			}, {

				"admin", "http://url.com", "+3400", "Acme-Rookie", "Hi, you are welcome", "Hola, bienvenido", ConstraintViolationException.class
			}, {

				"admin", "http://url.com", "+34", "", "Hi, you are welcome", "Hola, bienvenido", ConstraintViolationException.class
			}, {

				"admin", "http://url.com", "+34", "Acme-Rookie", "", "Hola, bienvenido", ConstraintViolationException.class
			}, {

				"admin", "http://url.com", "+34", "Acme-Rookie", "Hi, you are welcome", "", ConstraintViolationException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.saveAdminConfigTestTemplate((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], (Class<?>) testingData[i][6]);
	}
	protected void saveAdminConfigTestTemplate(final String beanName, final String bannerURL, final String countryCode, final String systemName, final String welcomeMsgEN, final String welcomeMsgES, final Class<?> expected) {
		Class<?> caught;
		caught = null;

		try {
			super.authenticate(beanName);
			final AdminConfig adminConfig = this.adminConfigService.getAdminConfig();

			adminConfig.setSystemName(systemName);
			adminConfig.setBannerURL(bannerURL);
			adminConfig.setWelcomeMsgEN(welcomeMsgEN);
			adminConfig.setWelcomeMsgES(welcomeMsgES);
			adminConfig.setCountryCode(countryCode);

			this.adminConfigService.save(adminConfig);
			this.adminConfigService.flush();
			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}
	//-----------------------------

	//ADD VOID WORD -----------------------------
	@Test
	public void addVoidWordTest() {
		super.authenticate("admin");

		final String voidWordName = "Prueba   ";
		//Emulate reconstruct
		final AdminConfig adminConfig = this.adminConfigService.getAdminConfig();
		final Collection<String> voidWords = adminConfig.getVoidWords();
		voidWordName.trim().toLowerCase().replaceAll(" +", " ");

		if (!(voidWordName.isEmpty()))
			Assert.isTrue(!voidWords.contains(voidWordName));

		voidWords.add(voidWordName);
		adminConfig.setVoidWords(voidWords);
		//

		this.adminConfigService.save(adminConfig);
		this.adminConfigService.flush();

		super.unauthenticate();

	}

	@Test(expected = IllegalArgumentException.class)
	public void addVoidWordFailTest() {
		super.authenticate("author0");

		final String voidWordName = "prueba";
		//Emulate reconstruct
		final AdminConfig adminConfig = this.adminConfigService.getAdminConfig();
		final Collection<String> voidWords = adminConfig.getVoidWords();
		voidWordName.trim().toLowerCase().replaceAll(" +", " ");

		if (!(voidWordName.isEmpty()))
			Assert.isTrue(!voidWords.contains(voidWordName));

		voidWords.add(voidWordName);
		adminConfig.setVoidWords(voidWords);
		//

		this.adminConfigService.save(adminConfig);
		this.adminConfigService.flush();

		super.unauthenticate();

	}
	//-----------------------------

	//ADDMAKE -------------------------
	@Test
	public void addMakeWordTest() {
		super.authenticate("admin");

		final String make = "Prueba   ";
		//Emulate reconstruct
		final AdminConfig adminConfig = this.adminConfigService.getAdminConfig();
		final Collection<String> makes = adminConfig.getCreditCardMakes();
		make.trim().toLowerCase().replaceAll(" +", " ");

		if (!(make.isEmpty()))
			Assert.isTrue(!makes.contains(make));

		makes.add(make);
		adminConfig.setCreditCardMakes(makes);

		this.adminConfigService.save(adminConfig);
		this.adminConfigService.flush();

		super.unauthenticate();

	}
	@Test(expected = IllegalArgumentException.class)
	public void addMakeFailTest() {
		super.authenticate("admin");

		final String make = "VISA";
		//Emulate reconstruct
		final AdminConfig adminConfig = this.adminConfigService.getAdminConfig();
		final Collection<String> makes = adminConfig.getCreditCardMakes();
		make.trim().toLowerCase().replaceAll(" +", " ");

		if (!(make.isEmpty()))
			Assert.isTrue(!makes.contains(make));

		makes.add(make);
		adminConfig.setVoidWords(makes);
		//

		this.adminConfigService.save(adminConfig);
		this.adminConfigService.flush();

		super.unauthenticate();

	}
	//-----------------------------

	//DELETE VOID WORD -------------------------
	@Test
	public void deleteVoidWordTest() {
		super.authenticate("admin");

		this.adminConfigService.deleteVoidWord("a");

		this.adminConfigService.flush();

		super.unauthenticate();

	}

	@Test(expected = IllegalArgumentException.class)
	public void deleteVoidWordFailTest() {
		super.authenticate("author0");

		this.adminConfigService.deleteVoidWord("a");

		this.adminConfigService.flush();

		super.unauthenticate();
	}

	//DELETE MAKE -------------------------
	@Test
	public void deleteMakeTest() {
		super.authenticate("admin");

		this.adminConfigService.deleteCreditCardMake("VISA");

		this.adminConfigService.flush();

		super.unauthenticate();

	}

	@Test(expected = IllegalArgumentException.class)
	public void deleteMakeFailTest() {
		super.authenticate("admin");

		this.adminConfigService.deleteVoidWord("VISA");
		this.adminConfigService.deleteVoidWord("MASTER");
		this.adminConfigService.deleteVoidWord("AMEX");
		this.adminConfigService.deleteVoidWord("DINNERS");

		this.adminConfigService.flush();

		super.unauthenticate();

	}
	//-----------------------------

}
