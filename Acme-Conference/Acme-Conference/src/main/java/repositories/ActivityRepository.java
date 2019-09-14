
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Activity;
import domain.Author;
import domain.Paper;
import domain.Section;

@Repository
public interface ActivityRepository extends JpaRepository<Activity, Integer> {

	@Query("select a.id from Activity a")
	Collection<Integer> findAllId();

	@Query("select a from Activity a where a.conference.id = ?1")
	Collection<Activity> getActivityByConference(final int id);

	@Query("select p.authors from Paper p join p.submission s join s.conference c where c.id = ?1 and s.status = 'ACCEPTED'")
	Collection<Author> getAuthorsWithSubmissionAcceptedInConference(final int id);

	@Query("select s from Section s where s.activity.id = ?1")
	Collection<Section> getSectionsByActivity(final int id);

	@Query("select p from Paper p join p.submission s join s.conference c where c.id = ?1 and s.status = 'ACCEPTED' and p.cameraReadyPaper = true")
	Collection<Paper> getPapersInCameraReadyFromConference(final int id);

	@Query("select p.authors from Paper p where p.id = ?1")
	Collection<Author> getAuthorByPaperId(final int id);

}
