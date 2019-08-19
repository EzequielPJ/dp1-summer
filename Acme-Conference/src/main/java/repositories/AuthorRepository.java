
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Author;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Integer> {

	@Query("select a from Author a where a.userAccount.id = ?1")
	Author findByPrincipal(int principalId);

	@Query("select distinct(r.author)  from Registration r")
	Collection<Author> getAuhtorsWithRegistration();

	@Query("select distinct(s.author)  from Submission s")
	Collection<Author> getAuhtorsWithSubmission();

}
