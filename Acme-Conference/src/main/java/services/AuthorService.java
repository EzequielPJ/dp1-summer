
package services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import repositories.AuthorRepository;
import security.UserAccount;
import domain.Author;

@Service
@Transactional
public class AuthorService {

	@Autowired
	private AuthorRepository	authorRepository;


	public Author findByPrincipal(final UserAccount principal) {
		return this.authorRepository.findByPrincipal(principal.getId());
	}

}
