
package repositories;

import java.util.Collection;
import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Conference;

@Repository
public interface ConferenceRepository extends JpaRepository<Conference, Integer> {

	@Query("select distinct(c) from Conference c where (c.finalMode = true) AND (c.title LIKE %?1% OR c.venue LIKE %?1% OR c.summary LIKE %?1%)")
	Collection<Conference> getFilterConferencesByKeyword(String keyword);

	@Query("select distinct(c) from Conference c LEFT JOIN c.category ca where (c.finalMode = true) AND (c.title LIKE %?1% OR c.acronym LIKE %?1% OR c.venue LIKE %?1% OR c.summary LIKE %?1%) AND (c.fee < ?4) AND (c.startDate BETWEEN ?2 and ?3) AND (c.endDate BETWEEN ?2 and ?3) AND (ca.id between ?5 and ?6)")
	Collection<Conference> getFilterConferencesByFinder(String keyWord, Date minimumDate, Date maximumDate, Double maximumFee, int minCategory, int maxCategory);

	@Query("select f.conferences from Finder f where f.id = ?1")
	Collection<Conference> getConferencesByFinder(int idFinder);
}
