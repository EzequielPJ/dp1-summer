
package services;

import java.util.Collection;
import java.util.HashSet;

import javax.transaction.Transactional;
import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.ReportRepository;
import security.LoginService;
import utiles.AuthorityMethods;
import domain.Report;
import domain.Reviewer;
import domain.Submission;

@Service
@Transactional
public class ReportService {

	@Autowired
	private ReviewerService		reviewerService;

	@Autowired
	private ReportRepository	reportRepository;

	@Autowired
	private Validator			validator;


	public Report create(final Submission submission) {
		final Report res = new Report();

		res.setComments(new HashSet<String>());
		res.setSubmission(submission);

		return res;
	}

	public Report save(final Report report) {
		Assert.isTrue(report != null);
		Assert.isTrue(AuthorityMethods.checkIsSomeoneLogged());
		final Reviewer reviewer = this.reviewerService.findByPrincipal(LoginService.getPrincipal());
		Assert.isTrue(this.isAssigned(report, reviewer));
		Assert.isTrue(report.getId() == 0);

		final Report res = this.reportRepository.saveAndFlush(report);
		this.reportRepository.flush();
		return res;

	}

	private boolean isAssigned(final Report report, final Reviewer reviewer) {
		boolean res = false;

		for (final Reviewer r : report.getSubmission().getReviewers())
			if (r.getId() == reviewer.getId()) {
				res = true;
				break;
			}

		return res;
	}

	public Report reconstruct(final Report report, final BindingResult binding) {
		final Report res = new Report();

		res.setSubmission(report.getSubmission());
		res.setComments(report.getComments());
		res.setDecision(report.getDecision());
		res.setOriginalityScore(report.getOriginalityScore());
		res.setQualityScore(report.getQualityScore());
		res.setReadibilityScore(report.getReadibilityScore());

		this.validator.validate(res, binding);

		if (binding.hasErrors())
			throw new ValidationException();

		return res;
	}
	public void flush() {
		this.reportRepository.flush();
	}

	public Report findOne(final int reportId) {
		return this.reportRepository.findOne(reportId);
	}

	public Collection<Report> findReportsByReviewer(final int reviewerId) {
		return this.reportRepository.findReportsFromReviewer(reviewerId);
	}

	public Collection<Report> findReportsByAuthor(final int authorId) {
		return this.reportRepository.findReportsFromAuthor(authorId);
	}

}
