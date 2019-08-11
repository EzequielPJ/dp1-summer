
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

	//Author
	@Query("select count(s) from Submission s where s.author.id = ?1")
	Integer getNumberOfSubmissionsOfAuthor(int idAuthor);

	@Query("select s from Submission s where s.author.id = ?1")
	Collection<Submission> getSubmissionsOfAuthor(int idAuthor);

	@Query("select s from Submission s where s.author.id = ?1 and s.status = 'ACCEPTED'")
	Collection<Submission> getSubmissionsOfAuthorAccepted(int idAuthor);

	@Query("select s from Submission s where s.author.id = ?1 and s.status = 'REJECTED'")
	Collection<Submission> getSubmissionsOfAuthorRejected(int idAuthor);

	@Query("select s from Submission s where s.author.id = ?1 and s.status = 'UNDER-REVIEW'")
	Collection<Submission> getSubmissionsOfAuthorUnderReview(int idAuthor);

	@Query("select count(s) from Submission s where s.author.id = ?1 and s.conference.id = ?2")
	Integer getNumberOfSubmissionsOfAuthorByConference(int idAuthor, int idConference);

	//Administrator
	@Query("select s from Submission s where s.conference.administrator.id =  ?1")
	Collection<Submission> getSubmissionsOfAdmin(int idAdmin);

	@Query("select s from Submission s where s.conference.administrator.id =  ?1 and s.status = 'ACCEPTED'")
	Collection<Submission> getSubmissionsOfAdminAccepted(int idAdmin);

	@Query("select s from Submission s where s.conference.administrator.id =  ?1 and s.status = 'REJECTED'")
	Collection<Submission> getSubmissionsOfAdminRejected(int idAdmin);

	@Query("select s from Submission s where s.conference.administrator.id =  ?1 and s.status = 'UNDER-REVIEW'")
	Collection<Submission> getSubmissionsOfAdminUnderReview(int idAdmin);

	@Query("select s from Submission s where s.status = 'ACCEPTED' and s.author.id = ?1 and s.conference.cameraReadyDeadline > CURRENT_TIMESTAMP and s.conference.finalMode = true and s not in (select p.submission from Paper p where p.cameraReadyPaper = true and p.submission.author.id = ?1)")
	Collection<Submission> getSubmissionsCanAddCameraReadyPaper(int idAuthor);

}
