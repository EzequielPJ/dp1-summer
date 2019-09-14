
package services;

import java.util.ArrayList;
import java.util.Collection;

import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import utilities.AbstractTest;
import domain.Conference;
import domain.CreditCard;
import domain.Sponsorship;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class SponsorshipServiceTest extends AbstractTest {

	@Autowired
	private SponsorshipService			sponsorshipService;

	@Autowired
	private ConferenceSponsorService	sponsorService;

	@Autowired
	private ConferenceService			conferenceService;


	@Test
	public void listingDriver() {
		final Object testingData[][] = {
			{
				"sponsor0", "sponsor0", null
			}, {
				"sponsor0", "sponsor1", IllegalArgumentException.class
			}, {
				"author0", "sponsor1", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.listingTemplate((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	// Ancillary methods ------------------------------------------------------
	protected void listingTemplate(final String user, final String owner, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			super.authenticate(user);

			final int ownerID = super.getEntityId(owner);
			this.sponsorshipService.findAllBySponsor(ownerID);

			super.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

	@Test
	public void showingDriver() {
		final Object testingData[][] = {
			{
				"sponsor0", super.getEntityId("sponsorship0"), null
			}, {
				"sponsor0", super.getEntityId("sponsorship1"), IllegalArgumentException.class
			}, {
				"author0", super.getEntityId("sponsorship1"), IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.showingTemplate((String) testingData[i][0], (int) testingData[i][1], (Class<?>) testingData[i][2]);
	}
	// Ancillary methods ------------------------------------------------------
	protected void showingTemplate(final String user, final int idSponsorship, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			super.authenticate(user);

			this.sponsorshipService.findOne(idSponsorship);

			super.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

	@Test
	public void creatingDriver() {
		final Object testingData[][] = {
			{
				"sponsor0", "http://url.com", "http://url.com", "conference0", null
			}, {
				"sponsor0", "http://url.com", "http://url.com", "conference0", null
			}, {
				"sponsor0", "Non URL", "http://url.com", "conference0", ConstraintViolationException.class
			}, {
				"sponsor0", "", "http://url.com", "conference0", ConstraintViolationException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.creatingTemplate((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (Class<?>) testingData[i][4]);
	}
	// Ancillary methods ------------------------------------------------------
	protected void creatingTemplate(final String user, final String bannerURL, final String targetPageURL, final String conferenceName, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			super.authenticate(user);

			Sponsorship sponsorship = this.sponsorshipService.create();

			sponsorship.setConferenceSponsor(this.sponsorService.findOne(this.getEntityId(user)));

			sponsorship = this.fillData(sponsorship, bannerURL, targetPageURL, conferenceName);

			this.sponsorshipService.save(sponsorship);
			this.sponsorshipService.flush();

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
				"sponsor0", "http://url.com", "http://url.com", "conference0", null
			}, {
				"sponsor0", "http://url.com", "http://url.com", "conference0", null
			}, {
				"sponsor0", "Non URL", "http://url.com", "conference0", ConstraintViolationException.class
			}, {
				"sponsor0", "", "http://url.com", "conference0", ConstraintViolationException.class
			}, {
				"sponsor0", "http://url.com", "Non URL", "conference0", ConstraintViolationException.class
			}, {
				"sponsor0", "http://url.com", "", "conference0", ConstraintViolationException.class
			}, {
				"sponsor1", "http://url.com", "http://url.com", "conference0", IllegalArgumentException.class
			}, {
				"admin", "http://url.com", "http://url.com", "conference0", IllegalArgumentException.class
			}, {
				"author0", "http://url.com", "http://url.com", "conference0", IllegalArgumentException.class
			}, {
				"reviewer0", "http://url.com", "http://url.com", "conference0", IllegalArgumentException.class
			}, {
				null, "http://url.com", "http://url.com", "conference0", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.updatingTemplate((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (Class<?>) testingData[i][4]);
	}
	// Ancillary methods ------------------------------------------------------
	protected void updatingTemplate(final String user, final String bannerURL, final String targetPageURL, final String conferenceName, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			super.authenticate(user);

			Sponsorship sponsorship = this.sponsorshipService.findOne(this.getEntityId("sponsorship0"));

			sponsorship = this.fillData(sponsorship, bannerURL, targetPageURL, conferenceName);

			this.sponsorshipService.save(sponsorship);
			this.sponsorshipService.flush();

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
				"sponsor0", "sponsorship0", null
			}, {
				"sponsor0", "sponsorship1", IllegalArgumentException.class
			}, {
				"author0", "sponsorship1", IllegalArgumentException.class
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

			final Sponsorship sponsorship = this.sponsorshipService.findOne(this.getEntityId(idSponsorship));

			this.sponsorshipService.delete(sponsorship);

			super.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
			oops.printStackTrace();
		}

		this.checkExceptions(expected, caught);
	}

	private Sponsorship fillData(final Sponsorship sponsorship, final String bannerURL, final String targetPageURL, final String conferenceName) {
		sponsorship.setBannerURL(bannerURL);
		sponsorship.setTargetURL(targetPageURL);
		final CreditCard cc = new CreditCard();
		cc.setCvv("231");
		cc.setExpirationMonth(04);
		cc.setExpirationYear(2022);
		cc.setHolder("VISA");
		cc.setBrandName("VISA");
		cc.setNumber("1111222233334444");
		sponsorship.setCreditCard(cc);
		final Collection<Conference> conferences = new ArrayList<>();
		final Conference conference = this.conferenceService.findOne(this.getEntityId(conferenceName));
		conferences.add(conference);
		sponsorship.setConferences(conferences);
		return sponsorship;
	}
}
