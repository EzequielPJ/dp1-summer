
package services;

import java.text.ParseException;
import java.util.Collection;
import java.util.Date;

import javax.transaction.Transactional;
import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.CommentRepository;
import security.LoginService;
import domain.Actor;
import domain.Comment;

@Service
@Transactional
public class CommentService {

	@Autowired
	private CommentRepository	commentRepository;

	@Autowired
	private ConferenceService	conferenceService;

	@Autowired
	private ActivityService		activityService;

	@Autowired
	Validator					validator;


	public Comment findOne(final int id) {
		return this.commentRepository.findOne(id);
	}

	public Collection<Comment> getCommentsByConference(final int id) {
		return this.commentRepository.getCommentsByConference(id);
	}

	public Collection<Comment> getCommentsByActivity(final int id) {
		return this.commentRepository.getCommentsByActivity(id);
	}

	public Actor findActorPrincipal(final int id) {
		return this.commentRepository.findActorPrincipal(id);
	}

	public Comment create(final int idConference, final int idActivity) {
		final Comment comment = new Comment();
		try {
			comment.setActor(this.commentRepository.findActorPrincipal(LoginService.getPrincipal().getId()));
		} catch (final Throwable oops) {
			comment.setActor(null);
		}
		comment.setMoment(new Date());
		comment.setText("");
		comment.setTitle("");
		if (idConference != 0)
			comment.setConference(this.conferenceService.findOne(idConference));
		if (idActivity != 0)
			comment.setActivity(this.activityService.findOne(idActivity));
		return comment;
	}
	public Comment save(final Comment comment) {
		return this.commentRepository.save(comment);
	}

	public Comment reconstruct(final Comment comment, final BindingResult binding) throws ParseException {
		Comment commentRec;
		commentRec = comment;
		try {
			commentRec.setActor(this.commentRepository.findActorPrincipal(LoginService.getPrincipal().getId()));
		} catch (final Throwable oops) {

		}
		comment.setMoment(new Date());
		this.validator.validate(commentRec, binding);
		if (binding.hasErrors())
			throw new ValidationException();

		return commentRec;
	}

	public void flush() {
		this.commentRepository.flush();
	}

}
