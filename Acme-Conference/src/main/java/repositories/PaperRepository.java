
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Paper;

@Repository
public interface PaperRepository extends JpaRepository<Paper, Integer> {

	@Query("select concat(p.title,' ', p.summary) from Paper p join p.authors a where p.cameraReadyPaper = true and a.id = ?1")
	Collection<String> getTextsOfPapers(int autorId);

	@Query("select count(p) from Paper p where p.submission.id = ?1 and cameraReadyPaper = false")
	Integer getNumOfNonCameraVersionPapersOfSubmission(int idSubmission);

	@Query("select count(p) from Paper p where p.submission.id = ?1 and cameraReadyPaper = true")
	Integer getNumOfCameraVersionPapersOfSubmission(int idSubmission);

	@Query("select p from Paper p where p.submission.id = ?1 and cameraReadyPaper = false")
	Paper getPaperNonCamerReadyVersionOfSubmission(int idSubmission);

	@Query("select p from Paper p where p.submission.id = ?1 and cameraReadyPaper = true")
	Paper getPaperCamerReadyVersionOfSubmission(int idSubmission);

}