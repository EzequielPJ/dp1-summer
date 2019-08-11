
package services;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Category;
import domain.Conference;
import domain.Finder;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class FinderServiceTest extends AbstractTest {

	@Autowired
	private FinderService		finderService;

	@Autowired
	private ConferenceService	conferenceService;

	@Autowired
	private CategoryService		categoryService;


	@SuppressWarnings("unchecked")
	@Test
	public void saveFinderDriver() throws ParseException {

		final Collection<Conference> conferences = new ArrayList<Conference>();

		conferences.add(this.conferenceService.findOne(this.getEntityId("conference1")));

		final Object testingData[][] = {

			{
				"author0", "videojuegos", this.finderService.findOne(this.getEntityId("finder0")), 15.0, this.categoryService.findOne(this.getEntityId("category6")), null, null, conferences, null
			}, {
				"author1", "videojuegos", this.finderService.findOne(this.getEntityId("finder0")), 15.0, this.categoryService.findOne(this.getEntityId("category6")), null, null, conferences, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.saveFinderTemplate((String) testingData[i][0], (String) testingData[i][1], (Finder) testingData[i][2], (Double) testingData[i][3], (Category) testingData[i][4], (Date) testingData[i][5], (Date) testingData[i][6],
				(Collection<Conference>) testingData[i][7], (Class<?>) testingData[i][8]);
	}
	protected void saveFinderTemplate(final String beanName, final String keyword, Finder finder, final Double maximumFee, final Category category, final Date minimumDate, final Date maximumDate, final Collection<Conference> resultsExpected,
		final Class<?> expected) {
		Class<?> caught;
		caught = null;

		try {
			super.authenticate(beanName);
			finder.setKeyWord(keyword);
			finder.setMaximumFee(maximumFee);
			finder.setCategory(category);
			finder.setMinimumDate(minimumDate);
			finder.setMaximumDate(maximumDate);

			finder = this.finderService.save(finder);
			this.finderService.flush();

			Assert.isTrue(resultsExpected.containsAll(finder.getConferences()) && resultsExpected.size() == finder.getConferences().size());
			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

}
