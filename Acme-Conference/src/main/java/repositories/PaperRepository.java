
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Paper;

@Repository
public interface PaperRepository extends JpaRepository<Paper, Integer> {

	@Query("select concat(p.title,' ', p.summary) from Paper p join p.authors a where p.cameraReadyPaper = true and a.id = ?1")
	Collection<String> getTextsOfPapers(int autorId);
}
