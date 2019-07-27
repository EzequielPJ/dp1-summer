
package services;

import java.text.ParseException;
import java.util.Collection;
import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import repositories.CommentRepository;
import security.LoginService;
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


	public Collection<Comment> getCommentsByConference(final int id) {
		return this.commentRepository.getCommentsByConference(id);
	}

	public Collection<Comment> getCommentsByActivity(final int id) {
		return this.commentRepository.getCommentsByActivity(id);
	}

	public Comment create(final int idConference, final int idActivity) {
		final Comment comment = new Comment();
		comment.setActor(this.conferenceService.findByPrincipal(LoginService.getPrincipal()));
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
		commentRec.setActor(this.conferenceService.findByPrincipal(LoginService.getPrincipal()));

		return commentRec;
	}

}
