
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Submission;

@Repository
public interface SubmissionRepository extends JpaRepository<Submission, Integer> {

	@Query("select count(s) from Submission s where s.author.id = ?1")
	Integer getNumberOfSubmissionsOfAuthor(int idAuthor);

	@Query("select s from Submission s where s.author.id = ?1")
	Collection<Submission> getSubmissionsOfAuthor(int idAuthor);

	@Query("select s from Submission s where s.conference.id = ?1")
	Collection<Submission> getSubmissionsOfConference(int idConference);

	@Query("select count(s) from Submission s where s.author.id = ?1 and s.conference.id = ?2")
	Integer getNumberOfSubmissionsOfAuthorByConference(int idAuthor, int idConference);

	@Query("select s from Submission s where s.conference.administrator.id =  ?1")
	Collection<Submission> getSubmissionsOfAdmin(int idAdmin);

}
