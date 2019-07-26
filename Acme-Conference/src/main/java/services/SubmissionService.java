
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.SubmissionRepository;
import utiles.AuthorityMethods;
import domain.Reviewer;
import domain.Submission;

@Service
@Transactional
public class SubmissionService {

	@Autowired
	private SubmissionRepository	submissionRepository;

	@Autowired
	private ReviewerService			reviewerService;


	public void assignReviewers() {
		Assert.isTrue(AuthorityMethods.chechAuthorityLogged("ADMINISTRATOR"));
		final Collection<Submission> allSubmissionsUnassigned = this.submissionRepository.getAllSubmissionsUnassigned();
		Collection<Reviewer> reviewers;
		for (final Submission submission : allSubmissionsUnassigned) {
			reviewers = this.reviewerService.getReviewersToAssign(3 - submission.getReviewers().size(), submission);
			reviewers.addAll(submission.getReviewers());
			submission.setReviewers(reviewers);
		}
	}
}
