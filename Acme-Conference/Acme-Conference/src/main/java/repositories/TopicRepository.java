
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Topic;

@Repository
public interface TopicRepository extends JpaRepository<Topic, Integer> {

	@Query("select t.topicES from Topic t")
	Collection<String> getAllNameES();

	@Query("select t.topicEN from Topic t")
	Collection<String> getAllNameEN();

	@Query("select t from Topic t where t.topicES = 'OTROS'")
	Topic findOtherTopic();

}
