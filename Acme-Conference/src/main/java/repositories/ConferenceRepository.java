
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Conference;

@Repository
public interface ConferenceRepository extends JpaRepository<Conference, Integer> {

	@Query("select distinct(c) from Conference c where (c.finalMode = true) AND (c.title LIKE %?1% OR c.venue LIKE %?1% OR c.summary LIKE %?1%)")
	Collection<Conference> getFilterConferencesByKeyword(String keyword);
}
