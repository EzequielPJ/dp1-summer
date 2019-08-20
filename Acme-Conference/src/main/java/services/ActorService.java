//

package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import repositories.ActorRepository;
import security.LoginService;
import security.UserAccount;
import security.UserAccountRepository;
import domain.Actor;

@Service
@Transactional
public class ActorService {

	@Autowired
	private ActorRepository			actorRepository;

	@Autowired
	private UserAccountRepository	userAccountRepository;


	public Actor save(final Actor actor) {
		return this.actorRepository.save(actor);
	}

	public Actor findByUserAccount(final UserAccount userAccount) {
		return this.actorRepository.getByUserAccount(userAccount.getId());
	}
	//
	//	public Actor getByMessageBox(final int idBox) {
	//		return this.actorRepository.getByMessageBox(idBox);
	//	}
	//
		public Collection<Actor> findAll() {
			return this.actorRepository.findAll();
		}

	public Actor findOne(final int idActor) {
		return this.actorRepository.findOne(idActor);
	}

	public Collection<Actor> findAllExceptLogged() {
		final Actor actor = this.findByUserAccount(LoginService.getPrincipal());
		return this.actorRepository.findAllExceptLogged(actor.getId());
	}

	// Workaround for the problem of hibernate with inheritances
	public Actor getActor(final int idActor) {
		return this.actorRepository.getActor(idActor);
	}

	public void flush() {
		this.actorRepository.flush();
	}

}
