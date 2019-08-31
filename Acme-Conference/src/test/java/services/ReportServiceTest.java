
package services;

import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import security.LoginService;
import utilities.AbstractTest;
import domain.Report;
import domain.Submission;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class ReportServiceTest extends AbstractTest {

	@Autowired
	private ReportService		reportService;

	@Autowired
	private ReviewerService		reviewerService;

	@Autowired
	private SubmissionService	submissionService;


	@Test
	public void CreateReportDriver() {

		final Object testingData[][] = {
			{

				"reviewer0", true, null
			}, {

				"reviewer0", false, null
			}, {

				null, false, IllegalArgumentException.class
			}, {

				"admin", true, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.createReportTemplate((String) testingData[i][0], (boolean) testingData[i][1], (Class<?>) testingData[i][2]);
	}
	protected void createReportTemplate(final String beanName, final boolean validData, final Class<?> expected) {
		Class<?> caught;
		caught = null;

		try {
			this.authenticate(beanName);
			final Collection<Submission> submissionsAssigned = this.submissionService.getSubmissionsOfReviewer(this.reviewerService.findByPrincipal(LoginService.getPrincipal()).getId());

			for (final Submission s : submissionsAssigned) {
				this.submissionService.assignReviewer(s.getId(), this.reviewerService.findByPrincipal(LoginService.getPrincipal()).getId());
				this.submissionService.flush();
				final Report r = this.reportService.create(s);
				if (validData) {
					r.setOriginalityScore(5);
					r.setQualityScore(5);
					r.setReadibilityScore(5);
					r.setDecision("ACCEPT");
				} else {
					r.setOriginalityScore(5);
					r.setQualityScore(5);
					r.setReadibilityScore(5);
					r.setDecision("NONE");
				}
				this.reportService.save(r);
				this.reportService.flush();
				break;
			}

			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}
}
