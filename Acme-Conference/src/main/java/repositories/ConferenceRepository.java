
package repositories;

import java.util.Collection;
import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Administrator;
import domain.Conference;

@Repository
public interface ConferenceRepository extends JpaRepository<Conference, Integer> {

	@Query("select a from Administrator a where a.userAccount.id = ?1")
	Administrator findByPrincipal(int principalId);

	@Query("select c from Conference c where c.administrator.id = ?1")
	Collection<Conference> getYoursConference(final int id);

	@Query("select c from Conference c where c.submissionDeadline between ?2 AND ?1")
	Collection<Conference> getConferencesBetweenSubmissionDeadline(Date date1, Date date2);

	@Query("select c from Conference c where c.notificationDeadline between ?2 AND ?1")
	Collection<Conference> getConferencesBetweenNotificationDeadline(Date date1, Date date2);

	@Query("select c from Conference c where c.cameraReadyDeadline between ?2 AND ?1")
	Collection<Conference> getConferencesBetweenCameraReadyDeadline(Date date1, Date date2);

	@Query("select c from Conference c where c.startDate between ?1 AND ?2")
	Collection<Conference> getConferencesBetweenStartDate(Date date1, Date date2);
}
