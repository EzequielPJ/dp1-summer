
package services;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Conference;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class SearchServiceTest extends AbstractTest {

	@Autowired
	private ConferenceService	conferenceService;


	@SuppressWarnings("unchecked")
	@Test
	public void searchDriver() {

		final Collection<Conference> conferences = new ArrayList<Conference>();

		conferences.add(this.conferenceService.findOne(this.getEntityId("conference1")));

		final Object testingData[][] = {
			{

				null, "videojuegos", conferences, null
			}, {

				null, "", this.conferenceService.findAll(), null
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.searchTemplate((String) testingData[i][0], (String) testingData[i][1], (Collection<Conference>) testingData[i][2], (Class<?>) testingData[i][3]);
	}
	protected void searchTemplate(final String beanName, final String keyword, final Collection<Conference> resultsExpected, final Class<?> expected) {
		Class<?> caught;
		caught = null;

		try {
			super.authenticate(beanName);
			final Collection<Conference> results = this.conferenceService.getFilterConferencesByKeyword(keyword);
			Assert.isTrue(resultsExpected.containsAll(results) && resultsExpected.size() == results.size());
			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

}
