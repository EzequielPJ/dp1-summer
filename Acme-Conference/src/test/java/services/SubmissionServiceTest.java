
package services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Conference;
import domain.Submission;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class SubmissionServiceTest extends AbstractTest {

	@Autowired
	SubmissionService	submissionService;

	@Autowired
	ConferenceService	conferenceService;


	@Test
	public void assignReviewersDriver() {

		final Object testingData[][] = {
			{

				"admin", this.submissionService.findOne(this.getEntityId("submission1")), this.conferenceService.findOne(this.getEntityId("conference0")), null
			}, {

				null, this.submissionService.findOne(this.getEntityId("submission1")), this.conferenceService.findOne(this.getEntityId("conference0")), IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.assignReviewersTemplate((String) testingData[i][0], (Submission) testingData[i][1], (Conference) testingData[i][2], (Class<?>) testingData[i][3]);
	}
	protected void assignReviewersTemplate(final String beanName, final Submission submission, final Conference conference, final Class<?> expected) {
		Class<?> caught;
		caught = null;

		try {
			super.authenticate(beanName);
			this.submissionService.assignReviewers(conference.getId());
			this.submissionService.flush();
			Assert.isTrue(submission.getReviewers().size() != 0);
			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

	@Test
	public void decisionMakingDriver() {

		final Object testingData[][] = {
			{

				"admin", this.submissionService.findOne(this.getEntityId("submission1")), this.conferenceService.findOne(this.getEntityId("conference0")), null
			}, {

				null, this.submissionService.findOne(this.getEntityId("submission1")), this.conferenceService.findOne(this.getEntityId("conference0")), IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.decisionMakingTemplate((String) testingData[i][0], (Submission) testingData[i][1], (Conference) testingData[i][2], (Class<?>) testingData[i][3]);
	}
	protected void decisionMakingTemplate(final String beanName, final Submission submission, final Conference conference, final Class<?> expected) {
		Class<?> caught;
		caught = null;

		try {
			super.authenticate(beanName);
			this.submissionService.conferenceDecisionMaking(conference.getId());
			this.submissionService.flush();
			Assert.isTrue(submission.getStatus() != "UNDER-REVIEW");
			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

}
