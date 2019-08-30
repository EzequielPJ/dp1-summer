
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Quolet;

@Repository
public interface QuoletRepository extends JpaRepository<Quolet, Integer> {

	@Query("select q from Quolet q where q.conference.administrator.id = ?1")
	Collection<Quolet> getQuoletsOfAdmin(int idActor);

	@Query("select q from Quolet q where q.conference.id = ?1 and q.finalMode = true")
	Collection<Quolet> getQuoletsOfConferenceFinalMode(int idConference);

	@Query("select q from Quolet q where q.conference.id = ?1")
	Collection<Quolet> getQuoletsOfConferenceAll(int idConference);

}
