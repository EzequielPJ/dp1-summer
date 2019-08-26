
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import domain.Report;

public interface ReportRepository extends JpaRepository<Report, Integer> {

	@Query("select r from Report r join r.submission.reviewers reviewers where reviewers.id = ?1")
	public Collection<Report> findReportsFromReviewer(final int reviewerId);

	@Query("select r from Report r where r.decision = ?1 and r.submission.id = ?2")
	public Collection<Report> findReportsBySubmissionAndDecision(final String decision, final int submissionId);

}
