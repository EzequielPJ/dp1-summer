
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Gomela;

@Repository
public interface GomelaRepository extends JpaRepository<Gomela, Integer> {

	@Query("select q from Gomela q where q.conference.administrator.id = ?1")
	Collection<Gomela> getGomelasOfAdmin(int idActor);

	@Query("select q from Gomela q where q.conference.id = ?1 and q.finalMode = true")
	Collection<Gomela> getGomelasOfConferenceFinalMode(int idConference);

	@Query("select q from Gomela q where q.conference.id = ?1")
	Collection<Gomela> getGomelasOfConferenceAll(int idConference);

}
