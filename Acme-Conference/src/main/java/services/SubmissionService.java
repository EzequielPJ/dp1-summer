
package services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.SubmissionRepository;
import security.LoginService;
import utiles.AuthorityMethods;
import domain.Conference;
import domain.Reviewer;
import domain.Submission;

@Service
@Transactional
public class SubmissionService {

	@Autowired
	private SubmissionRepository	submissionRepository;

	@Autowired
	private ReviewerService			reviewerService;

	@Autowired
	private ConferenceService		conferenceService;

	@Autowired
	private AdministratorService	administratorService;

	private final SimpleDateFormat	FORMAT	= new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");


	public void assignReviewers(final int idConference) throws ParseException {
		Assert.isTrue(AuthorityMethods.chechAuthorityLogged("ADMINISTRATOR"));
		final Conference conference = this.conferenceService.findOne(idConference);
		Assert.isTrue(conference.getAdministrator().equals(this.administratorService.findByPrincipal(LoginService.getPrincipal())));
		final LocalDateTime DATETIMENOW = LocalDateTime.now();
		final Date actual = this.FORMAT.parse(DATETIMENOW.getYear() + "/" + DATETIMENOW.getMonthOfYear() + "/" + DATETIMENOW.getDayOfMonth() + " " + DATETIMENOW.getHourOfDay() + ":" + DATETIMENOW.getMinuteOfHour() + ":" + DATETIMENOW.getSecondOfMinute());
		Assert.isTrue(conference.getFinalMode());
		Assert.isTrue(conference.getSubmissionDeadline().before(actual) && conference.getNotificationDeadline().after(actual));
		final Collection<Submission> allSubmissionsUnassigned = this.submissionRepository.getAllSubmissionsUnassigned(idConference);
		Collection<Reviewer> reviewers;
		for (final Submission submission : allSubmissionsUnassigned) {
			reviewers = this.reviewerService.getReviewersToAssign(3 - submission.getReviewers().size(), submission);
			reviewers.addAll(submission.getReviewers());
			submission.setReviewers(reviewers);
		}
	}

	public Collection<Submission> getSubmissionsByConference(final int idConference) {
		return this.submissionRepository.getSubmissionsByConference(idConference);
	}

	public void conferenceDecisionMaking(final int idConference) throws ParseException {
		Assert.isTrue(AuthorityMethods.chechAuthorityLogged("ADMINISTRATOR"));
		final Conference conference = this.conferenceService.findOne(idConference);
		Assert.isTrue(conference.getAdministrator().equals(this.administratorService.findByPrincipal(LoginService.getPrincipal())));
		final LocalDateTime DATETIMENOW = LocalDateTime.now();
		final Date actual = this.FORMAT.parse(DATETIMENOW.getYear() + "/" + DATETIMENOW.getMonthOfYear() + "/" + DATETIMENOW.getDayOfMonth() + " " + DATETIMENOW.getHourOfDay() + ":" + DATETIMENOW.getMinuteOfHour() + ":" + DATETIMENOW.getSecondOfMinute());
		Assert.isTrue(conference.getFinalMode());
		Assert.isTrue(conference.getSubmissionDeadline().before(actual) && conference.getNotificationDeadline().after(actual));
		Integer acceptedReports;
		Integer rejectedReports;
		final Collection<Submission> conferenceSubmissions = this.getSubmissionsByConference(idConference);
		for (final Submission submission : conferenceSubmissions) {
			rejectedReports = this.submissionRepository.getNumberOfReportsByStatusAndSubmission(submission.getId(), "REJECT");
			acceptedReports = this.submissionRepository.getNumberOfReportsByStatusAndSubmission(submission.getId(), "ACCEPT");
			if (rejectedReports > acceptedReports)
				submission.setStatus("REJECTED");
			else
				submission.setStatus("ACCEPTED");
			this.submissionRepository.saveAndFlush(submission);
		}

	}
}
