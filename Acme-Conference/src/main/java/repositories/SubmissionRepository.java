
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Submission;

@Repository
public interface SubmissionRepository extends JpaRepository<Submission, Integer> {

	@Query("select s from Submission s where s.reviewers.size < 3 AND s.status = 'UNDER-REVIEW' AND s.conference.id = ?1")
	Collection<Submission> getAllSubmissionsUnassigned(int idConference);

	@Query("select s from Submission s where s.conference.id = ?1")
	Collection<Submission> getSubmissionsByConference(int idConference);

	@Query("select count(r) from Report r where r.submission.id = ?1 and r.decision = ?2")
	Integer getNumberOfReportsByStatusAndSubmission(int idSubmission, String status);

}
