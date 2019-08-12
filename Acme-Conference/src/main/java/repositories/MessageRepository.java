
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer> {

	@Query("select m from Message m join m.topics t where t.id = ?1")
	Collection<Message> findByTopic(int idTopic);

	@Query("select m from Message m inner join m.actors a where a.id = ?1")
	Collection<Message> findByUser(int idActor);
}
