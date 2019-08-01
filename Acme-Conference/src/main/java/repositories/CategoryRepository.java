
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

	@Query("select c from Category c where c.categoryEN != 'CONFERENCE'")
	Collection<Category> findAllMinusCONFERENCE();

	@Query("select c.categoryES from Category c where c.categoryEN != 'CONFERENCE'")
	Collection<String> getAllNameES();

	@Query("select c.categoryEN from Category c where c.categoryEN != 'CONFERENCE'")
	Collection<String> getAllNameEN();

	@Query("select c from Category c where c.parent.id = ?1")
	Collection<Category> getChildren(int id);

	@Query("select c from Category c where c.parent.categoryEN = 'CONFERENCE'")
	Collection<Category> findAllSuchACONFERENCEParent();

	@Query("select c from Category c where c.categoryEN = 'CONFERENCE'")
	Category getGeneralCategory();

}
