
package repositories;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Sponsorship;

@Repository
public interface SponsorshipRepository extends JpaRepository<Sponsorship, Integer> {

	@Query("select sp from Sponsorship sp where sp.conferenceSponsor.id = ?1")
	Collection<Sponsorship> findAllBySponsor(int idSponsor);

	@Query("select sp from Sponsorship sp join sp.conferences p where p.id = ?1")
	List<Sponsorship> findAllByConference(int idConference);

	@Query("select distinct(sp) from Sponsorship sp join sp.conferences c where c.id = ?1")
	Collection<Sponsorship> getSponsorshipsOfConference(int idConference);

}
