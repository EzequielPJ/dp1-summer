
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import repositories.CategoryRepository;
import domain.Category;

@Service
@Transactional
public class CategoryService {

	@Autowired
	private CategoryRepository	categoryRepository;


	public Collection<Category> findAll() {
		return this.categoryRepository.findAll();
	}

	public Category findOne(final int id) {
		return this.categoryRepository.findOne(id);
	}

}
