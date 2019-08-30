
package services;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import utilities.AbstractTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class DashboardServiceTest extends AbstractTest {

	@Autowired
	private AdministratorService	administratorService;


	@Override
	@Before
	public void setUp() {
		this.unauthenticate();
	}

	@Test
	public void dashboardSubmissionPerConferenceDriver() {

		final Object testingData[][] = {
			{

				"admin", 1.25, 0, 3, 1.299, null
			}, {

				"author0", 1.25, 0, 3, 1.299, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.dashboardSubmissionPerConferenceTemplate((String) testingData[i][0], (Double) testingData[i][1], (Integer) testingData[i][2], (Integer) testingData[i][3], (Double) testingData[i][4], (Class<?>) testingData[i][5]);
	}
	protected void dashboardSubmissionPerConferenceTemplate(final String beanName, final Double avgOfSubmissionsPerConferenceExpected, final Integer minOfSubmissionsPerConferenceExpected, final Integer maxOfSubmissionsPerConferenceExpected,
		final Double sDOfSubmissionsPerConferenceExpected, final Class<?> expected) {
		Class<?> caught;
		caught = null;

		try {
			super.authenticate(beanName);

			final Double avgOfSubmissionsPerConference = this.administratorService.getAvgOfSubmissionsPerConference();
			final Integer maxOfSubmissionsPerConference = this.administratorService.getMaximumOfSubmissionsPerConference();
			final Integer minOfSubmissionsPerConference = this.administratorService.getMinimumOfSubmissionsPerConference();
			final Double sDOfSubmissionsPerConference = this.administratorService.getSDOfSubmissionsPerConference();

			Assert.isTrue(avgOfSubmissionsPerConference.equals(avgOfSubmissionsPerConferenceExpected) && maxOfSubmissionsPerConference.equals(maxOfSubmissionsPerConferenceExpected)
				&& minOfSubmissionsPerConference.equals(minOfSubmissionsPerConferenceExpected) && sDOfSubmissionsPerConference.equals(sDOfSubmissionsPerConferenceExpected));

			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

	@Test
	public void dashboardRegistrationsPerConferenceDriver() {

		final Object testingData[][] = {
			{

				"admin", 0.25, 0, 1, 0.433, null
			}, {

				"author0", 0.25, 0, 1, 0.433, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.dashboardRegistrationsPerConferenceTemplate((String) testingData[i][0], (Double) testingData[i][1], (Integer) testingData[i][2], (Integer) testingData[i][3], (Double) testingData[i][4], (Class<?>) testingData[i][5]);
	}
	protected void dashboardRegistrationsPerConferenceTemplate(final String beanName, final Double avgOfRegistrationsPerConferenceExpected, final Integer minOfRegistrationsPerConferenceExpected, final Integer maxOfRegistrationsPerConferenceExpected,
		final Double sDOfRegistrationsPerConferenceExpected, final Class<?> expected) {
		Class<?> caught;
		caught = null;

		try {
			super.authenticate(beanName);

			final Double avgOfRegistrationsPerConference = this.administratorService.getAvgOfRegistrationsPerConference();
			final Integer maxOfRegistrationsPerConference = this.administratorService.getMaximumOfRegistrationsPerConference();
			final Integer minOfRegistrationsPerConference = this.administratorService.getMinimumOfRegistrationsPerConference();
			final Double sDOfRegistrationsPerConference = this.administratorService.getSDOfRegistrationsPerConference();

			Assert.isTrue(avgOfRegistrationsPerConference.equals(avgOfRegistrationsPerConferenceExpected) && maxOfRegistrationsPerConference.equals(maxOfRegistrationsPerConferenceExpected)
				&& minOfRegistrationsPerConference.equals(minOfRegistrationsPerConferenceExpected) && sDOfRegistrationsPerConference.equals(sDOfRegistrationsPerConferenceExpected));

			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

	@Test
	public void dashboardConferenceFeesDriver() {

		final Object testingData[][] = {
			{

				"admin", 24.0, 14, 32, 6.442049363362563, null
			}, {

				"author0", 24.0, 14, 32, 6.442049363362563, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.dashboardConferenceFeesTemplate((String) testingData[i][0], (Double) testingData[i][1], (Integer) testingData[i][2], (Integer) testingData[i][3], (Double) testingData[i][4], (Class<?>) testingData[i][5]);
	}
	protected void dashboardConferenceFeesTemplate(final String beanName, final Double avgOfConferenceFeesExpected, final Integer minOfConferenceFeesExpected, final Integer maxOfConferenceFeesExpected, final Double sDOfConferenceFeesExpected,
		final Class<?> expected) {
		Class<?> caught;
		caught = null;

		try {
			super.authenticate(beanName);

			final Double avgOfConferenceFees = this.administratorService.getAvgOfConferenceFees();
			final Integer maxOfConferenceFees = this.administratorService.getMaximumOfConferenceFees();
			final Integer minOfConferenceFees = this.administratorService.getMinimumOfConferenceFees();
			final Double sDOfConferenceFees = this.administratorService.getSDOfConferenceFees();

			Assert.isTrue(avgOfConferenceFees.equals(avgOfConferenceFeesExpected) && maxOfConferenceFees.equals(maxOfConferenceFeesExpected) && minOfConferenceFees.equals(minOfConferenceFeesExpected)
				&& sDOfConferenceFees.equals(sDOfConferenceFeesExpected));

			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

	@Test
	public void dashboardDaysPerConferenceDriver() {

		final Object testingData[][] = {
			{

				"admin", 10.0, 1, 31, 12.1861, null
			}, {

				"author0", 10.0, 1, 31, 12.1861, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.dashboardDaysPerConferenceTemplate((String) testingData[i][0], (Double) testingData[i][1], (Integer) testingData[i][2], (Integer) testingData[i][3], (Double) testingData[i][4], (Class<?>) testingData[i][5]);
	}
	protected void dashboardDaysPerConferenceTemplate(final String beanName, final Double avgOfDaysPerConferenceExpected, final Integer minOfDaysPerConferenceExpected, final Integer maxOfDaysPerConferenceExpected,
		final Double sDOfDaysPerConferenceExpected, final Class<?> expected) {
		Class<?> caught;
		caught = null;

		try {
			super.authenticate(beanName);

			final Double avgOfDaysPerConference = this.administratorService.getAvgOfDaysPerConference();
			final Integer maxOfDaysPerConference = this.administratorService.getMaximumOfDaysPerConference();
			final Integer minOfDaysPerConference = this.administratorService.getMinimumOfDaysPerConference();
			final Double sDOfDaysPerConference = this.administratorService.getSDOfDaysPerConference();

			Assert.isTrue(avgOfDaysPerConference.equals(avgOfDaysPerConferenceExpected) && maxOfDaysPerConference.equals(maxOfDaysPerConferenceExpected) && minOfDaysPerConference.equals(minOfDaysPerConferenceExpected)
				&& sDOfDaysPerConference.equals(sDOfDaysPerConferenceExpected));

			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

	@Test
	public void dashboardConferencesPerCategoryDriver() {

		final Object testingData[][] = {
			{

				"admin", 0.2105, 0, 4, 0.8932, null
			}, {

				"author0", 0.2105, 0, 4, 0.8932, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.dashboardConferencesPerCategoryTemplate((String) testingData[i][0], (Double) testingData[i][1], (Integer) testingData[i][2], (Integer) testingData[i][3], (Double) testingData[i][4], (Class<?>) testingData[i][5]);
	}
	protected void dashboardConferencesPerCategoryTemplate(final String beanName, final Double avgOfConferencesPerCategoryExpected, final Integer minOfConferencesPerCategoryExpected, final Integer maxOfConferencesPerCategoryExpected,
		final Double sDOfConferencesPerCategoryExpected, final Class<?> expected) {
		Class<?> caught;
		caught = null;

		try {
			super.authenticate(beanName);

			final Double avgOfConferencesPerCategory = this.administratorService.getAvgOfConferencesPerCategory();
			final Integer maxOfConferencesPerCategory = this.administratorService.getMaximumOfConferencesPerCategory();
			final Integer minOfConferencesPerCategory = this.administratorService.getMinimumOfConferencesPerCategory();
			final Double sDOfConferencesPerCategory = this.administratorService.getSDOfConferencesPerCategory();

			Assert.isTrue(avgOfConferencesPerCategory.equals(avgOfConferencesPerCategoryExpected) && maxOfConferencesPerCategory.equals(maxOfConferencesPerCategoryExpected) && minOfConferencesPerCategory.equals(minOfConferencesPerCategoryExpected)
				&& sDOfConferencesPerCategory.equals(sDOfConferencesPerCategoryExpected));

			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

	@Test
	public void dashboardCommentsPerConferenceDriver() {

		final Object testingData[][] = {
			{

				"admin", 0.25, 0, 1, 0.433, null
			}, {

				"author0", 0.25, 0, 1, 0.433, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.dashboardCommentsPerConferenceTemplate((String) testingData[i][0], (Double) testingData[i][1], (Integer) testingData[i][2], (Integer) testingData[i][3], (Double) testingData[i][4], (Class<?>) testingData[i][5]);
	}
	protected void dashboardCommentsPerConferenceTemplate(final String beanName, final Double avgOfCommentsPerConferenceExpected, final Integer minOfCommentsPerConferenceExpected, final Integer maxOfCommentsPerConferenceExpected,
		final Double sDOfCommentsPerConferenceExpected, final Class<?> expected) {
		Class<?> caught;
		caught = null;

		try {
			super.authenticate(beanName);

			final Double avgOfCommentsPerConference = this.administratorService.getAvgOfCommentsPerConference();
			final Integer maxOfCommentsPerConference = this.administratorService.getMaximumOfCommentsPerConference();
			final Integer minOfCommentsPerConference = this.administratorService.getMinimumOfCommentsPerConference();
			final Double sDOfCommentsPerConference = this.administratorService.getSDOfCommentsPerConference();

			Assert.isTrue(avgOfCommentsPerConference.equals(avgOfCommentsPerConferenceExpected) && maxOfCommentsPerConference.equals(maxOfCommentsPerConferenceExpected) && minOfCommentsPerConference.equals(minOfCommentsPerConferenceExpected)
				&& sDOfCommentsPerConference.equals(sDOfCommentsPerConferenceExpected));

			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

	@Test
	public void dashboardCommentsPerActivityDriver() {

		final Object testingData[][] = {
			{

				"admin", 0.0, 0, 0, 0.0, null
			}, {

				"author0", 0.0, 0, 0, 0.0, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.dashboardCommentsPerActivityTemplate((String) testingData[i][0], (Double) testingData[i][1], (Integer) testingData[i][2], (Integer) testingData[i][3], (Double) testingData[i][4], (Class<?>) testingData[i][5]);
	}
	protected void dashboardCommentsPerActivityTemplate(final String beanName, final Double avgOfCommentsPerActivityExpected, final Integer minOfCommentsPerActivityExpected, final Integer maxOfCommentsPerActivityExpected,
		final Double sDOfCommentsPerActivityExpected, final Class<?> expected) {
		Class<?> caught;
		caught = null;

		try {
			super.authenticate(beanName);

			final Double avgOfCommentsPerActivity = this.administratorService.getAvgOfCommentsPerActivity();
			final Integer maxOfCommentsPerActivity = this.administratorService.getMaximumOfCommentsPerActivity();
			final Integer minOfCommentsPerActivity = this.administratorService.getMinimumOfCommentsPerActivity();
			final Double sDOfCommentsPerActivity = this.administratorService.getSDOfCommentsPerActivity();

			Assert.isTrue(avgOfCommentsPerActivity.equals(avgOfCommentsPerActivityExpected) && maxOfCommentsPerActivity.equals(maxOfCommentsPerActivityExpected) && minOfCommentsPerActivity.equals(minOfCommentsPerActivityExpected)
				&& sDOfCommentsPerActivity.equals(sDOfCommentsPerActivityExpected));

			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

}
