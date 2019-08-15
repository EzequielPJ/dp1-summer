
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

	@Query("select distinct(c) from Conference c where (c.finalMode = true) AND (c.title LIKE %?1% OR c.venue LIKE %?1% OR c.summary LIKE %?1%)")
	Collection<Conference> getFilterConferencesByKeyword(String keyword);

	@Query("select distinct(c) from Conference c LEFT JOIN c.category ca where (c.finalMode = true) AND (c.title LIKE %?1% OR c.acronym LIKE %?1% OR c.venue LIKE %?1% OR c.summary LIKE %?1%) AND (c.fee < ?4) AND (c.startDate BETWEEN ?2 and ?3) AND (c.endDate BETWEEN ?2 and ?3) AND (ca.id between ?5 and ?6)")
	Collection<Conference> getFilterConferencesByFinder(String keyWord, Date minimumDate, Date maximumDate, Double maximumFee, int minCategory, int maxCategory);

	@Query("select f.conferences from Finder f where f.id = ?1")
	Collection<Conference> getConferencesByFinder(int idFinder);

	@Query("select concat(c.title,' ', c.summary) from Conference c where c.startDate > ?1")
	Collection<String> getTextsOfConferences(Date date);

	@Query("select a from Administrator a where a.userAccount.id = ?1")
	Administrator findByPrincipal(int principalId);

	@Query("select c from Conference c where c.administrator.id = ?1")
	Collection<Conference> getYoursConference(final int id);

	@Query("select c from Conference c where c.administrator.id = ?3 and c.submissionDeadline between ?2 AND ?1")
	Collection<Conference> getConferencesBetweenSubmissionDeadline(Date date1, Date date2, int id);

	@Query("select c from Conference c where c.administrator.id = ?3 and c.notificationDeadline between ?2 AND ?1")
	Collection<Conference> getConferencesBetweenNotificationDeadline(Date date1, Date date2, int id);

	@Query("select c from Conference c where c.administrator.id = ?3 and c.cameraReadyDeadline between ?2 AND ?1")
	Collection<Conference> getConferencesBetweenCameraReadyDeadline(Date date1, Date date2, int id);

	@Query("select c from Conference c where c.administrator.id = ?3 and c.startDate between ?1 AND ?2")
	Collection<Conference> getConferencesBetweenStartDate(Date date1, Date date2, int id);

	@Query("select c from Conference c where c.finalMode = true and c.startDate < ?1")
	Collection<Conference> getConferencesPast(Date date1);

	@Query("select c from Conference c where c.finalMode = true and c.endDate > ?1")
	Collection<Conference> getConferencesFuture(Date date1);

	@Query("select c from Conference c where c.finalMode = true")
	Collection<Conference> getConferencesFinalMode();

	@Query("select count(*) from Conference c where c.id = ?2 and (c.title LIKE %?1% OR c.summary LIKE %?1%)")
	Integer getContainsExpertiseKeywords(String string, int conferenceId);

	@Query("select c from Conference c where c.finalMode = true and c.administrator.id = ?1 and (c.submissionDeadline < CURRENT_DATE AND CURRENT_DATE < c.notificationDeadline) and (select count(*) from Submission s where s.status = 'UNDER-REVIEW' and s.conference.id = c.id) != 0")
	Collection<Conference> getConferencesToDecisionMaking(int idAdministrator);

	@Query("select c from Conference c where c.finalMode = true and c.administrator.id = ?1 and (c.submissionDeadline < CURRENT_DATE AND CURRENT_DATE < c.notificationDeadline) and (select count(*) from Submission s where s.status = 'UNDER-REVIEW' and s.conference.id = c.id and s.reviewers.size < 3) != 0")
	Collection<Conference> getConferencesToAssingReviewers(int id);

	@Query("select c.id from Conference c")
	Collection<Integer> findAllId();

	@Query("select c from Conference c where c.category.id = ?1")
	Collection<Conference> getConferenceByCategory(int idCategory);

	@Query("select c from Conference c where c not in (select distinct (s.conference) from Submission s where s.author.id = ?1) and c.finalMode = true and submissionDeadline > CURRENT_TIMESTAMP")
	Collection<Conference> getConferenceCanBeSubmitted(int idAuthor);

}
