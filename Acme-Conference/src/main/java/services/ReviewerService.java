
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ReviewerRepository;
import utiles.AuthorityMethods;
import domain.Reviewer;
import domain.Submission;

@Service
@Transactional
public class ReviewerService {

	@Autowired
	private ReviewerRepository	reviewerRepository;

	@Autowired
	private ConferenceService	conferenceService;


	public Collection<Reviewer> getReviewersToAssign(final int size, final Submission submission) {
		Assert.isTrue(AuthorityMethods.chechAuthorityLogged("ADMINISTRATOR"));
		List<Reviewer> res;
		final List<Reviewer> availableReviewers = new ArrayList<>();
		final Collection<Reviewer> allReviewers = this.reviewerRepository.findAll();
		for (final Reviewer reviewer : allReviewers)
			for (final String string : reviewer.getExpertiseKeywordsList())
				if (this.conferenceService.getContainsExpertiseKeywords(string, submission.getConference().getId())) {
					availableReviewers.add(reviewer);
					break;
				}
		availableReviewers.removeAll(submission.getReviewers());
		if (availableReviewers.size() > size)
			res = availableReviewers.subList(0, size);
		else
			res = availableReviewers;
		return res;
	}
}
