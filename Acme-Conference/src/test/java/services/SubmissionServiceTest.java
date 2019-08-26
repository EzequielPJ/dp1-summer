
package services;

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

import security.LoginService;
import utiles.IntermediaryBetweenTransactions;
import utilities.AbstractTest;
import domain.Author;
import domain.Conference;
import domain.Reviewer;
import domain.Submission;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class SubmissionServiceTest extends AbstractTest {

	@Autowired
	SubmissionService				submissionService;

	@Autowired
	ConferenceService				conferenceService;

	@Autowired
	AuthorService					authorService;

	@Autowired
	ReviewerService					reviewerService;

	@Autowired
	IntermediaryBetweenTransactions	intermediaryBetweenTransactions;


	@SuppressWarnings("unchecked")
	@Test
	public void assignReviewersDriver() {

		final Collection<Reviewer> reviewers = new ArrayList<>();
		reviewers.add(this.reviewerService.findOne(this.getEntityId("reviewer0")));
		final Object testingData[][] = {
			{

				"admin", this.submissionService.findOne(this.getEntityId("submission4")), this.conferenceService.findOne(this.getEntityId("conference2")), reviewers, null
			}, {

				null, this.submissionService.findOne(this.getEntityId("submission4")), this.conferenceService.findOne(this.getEntityId("conference2")), reviewers, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.assignReviewersTemplate((String) testingData[i][0], (Submission) testingData[i][1], (Conference) testingData[i][2], (Collection<Reviewer>) testingData[i][3], (Class<?>) testingData[i][4]);
	}
	protected void assignReviewersTemplate(final String beanName, final Submission submission, final Conference conference, final Collection<Reviewer> reviewers, final Class<?> expected) {
		Class<?> caught;
		caught = null;

		try {
			super.authenticate(beanName);
			this.submissionService.assignReviewers(conference.getId());
			this.submissionService.flush();
			Assert.isTrue(submission.getReviewers().containsAll(reviewers) && submission.getReviewers().size() == reviewers.size());
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

				"admin", this.submissionService.findOne(this.getEntityId("submission4")), this.conferenceService.findOne(this.getEntityId("conference2")), null
			}, {

				null, this.submissionService.findOne(this.getEntityId("submission4")), this.conferenceService.findOne(this.getEntityId("conference2")), IllegalArgumentException.class
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

	@Test
	public void saveSubmissionDriver() {

		final Object testingData[][] = {
			{
				//El autor 0 hace una entrega a una conferencia. Positivo
				"author0", "conference1", null
			}, {
				//El autor 0 hace una entrega a una conferencia. Negativo porque ya hay una entrega de este autor a la conferencia especificada
				"author0", "conference0", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.saveSubmissionTemplate((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	protected void saveSubmissionTemplate(final String actor, final String conference, final Class<?> expected) {
		Class<?> caught;
		caught = null;

		try {
			super.authenticate(actor);
			final Author author = this.authorService.findByPrincipal(LoginService.getPrincipal());
			final Submission submission = new Submission();
			submission.setMoment(new Date());
			submission.setStatus("UNDER-REVIEW");
			submission.setAuthor(author);
			submission.setConference(this.conferenceService.findOne(this.getEntityId(conference)));
			submission.setTicker(this.intermediaryBetweenTransactions.generateTicker(author));

			this.submissionService.save(submission);
			this.submissionService.flush();
			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

	@Test
	public void changeStatusDriver() {

		final Object testingData[][] = {
			{
				"author0", "submission1", "ACCEPTED", IllegalArgumentException.class
			}, {

				"admin", "submission1", "ACCEPTED", null
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.changeStatusTemplate((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (Class<?>) testingData[i][3]);
	}

	protected void changeStatusTemplate(final String actor, final String submission, final String status, final Class<?> expected) {
		Class<?> caught;
		caught = null;

		try {
			super.authenticate(actor);
			final Submission submissionTest = this.submissionService.findOne(this.getEntityId(submission));
			this.submissionService.changeStatus(submissionTest, status);
			this.submissionService.flush();
			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

}
