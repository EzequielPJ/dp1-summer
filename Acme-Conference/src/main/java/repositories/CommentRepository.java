
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {

	@Query("select o from Comment o where o.conference.id = ?1")
	Collection<Comment> getCommentsByConference(final int id);

	@Query("select o from Comment o where o.activity.id = ?1")
	Collection<Comment> getCommentsByActivity(final int id);

}
