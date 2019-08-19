
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Actor;
import domain.Message;
import domain.Topic;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer> {

	@Query("select m from Message m join m.topics t where t.id = ?1")
	Collection<Message> findByTopic(int idTopic);

	@Query("select distinct(m) from Message m inner join m.actors a join fetch m.recipients r where a.id = ?1 order by m.moment desc")
	Collection<Message> getMessagesOfActor(int idActor);

	@Query("select distinct(m) from Message m inner join m.actors a inner join m.topics t join fetch m.recipients r where a.id = ?1 and t.id = ?2 order by m.moment desc")
	Collection<Message> getMessagesOfActorByTopic(int idActor, int idTopic);

	@Query("select distinct(m) from Message m inner join m.actors a join fetch m.recipients r where a.id = ?1 and m.sender.id = ?2 order by m.moment desc")
	Collection<Message> getMessagesOfActorBySender(int idActor, int idSender);

	@Query("select distinct(m) from Message m inner join m.actors a join fetch m.recipients r where a.id = ?1 and r.id = ?2 order by m.moment desc")
	Collection<Message> getMessagesOfActorByRecipient(int idActor, int idRecipient);

	@Query("select distinct(m) from Message m inner join fetch m.actors a inner join m.recipients r where m.id = ?1")
	Message getMessage(int idMessage);

	@Query("select m.recipients from Message m where m.id = ?1")
	Collection<Actor> getRecipientsOfMessage(int idMessage);

	@Query("select distinct(r) from Message m inner join m.actors a inner join m.recipients r where a.id = ?1 and (m.sender.id = ?1 or r.id = ?1)")
	Collection<Actor> getRecipientsWhoHaveSentMessagesAnActor(int idActor);

	@Query("select distinct(m.sender) from Message m inner join m.actors a where a.id = ?1")
	Collection<Actor> getSendersWhoHaveSentMessagesToAnActor(int idActor);

	@Query("select distinct(t) from Message m inner join m.actors a inner join m.topics t where a.id = ?1")
	Collection<Topic> getAllTopicsOfMessagesOfActor(int idActor);

}
