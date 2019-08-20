
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import domain.Registration;

public interface RegistrationRepository extends JpaRepository<Registration, Integer> {

	@Query("select r from Registration r where r.conference.id = ?1")
	public Collection<Registration> findRegistrationFromConference(final int conferenceId);

	@Query("select r from Registration r where r.author.id = ?1")
	public Collection<Registration> findRegistrationFromAuthor(final int authorId);

}
