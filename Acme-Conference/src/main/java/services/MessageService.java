
package services;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.transaction.Transactional;
import javax.validation.ValidationException;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.MessageRepository;
import security.LoginService;
import utiles.AuthorityMethods;
import domain.Actor;
import domain.Author;
import domain.Message;
import domain.Topic;
import forms.MessageForm;

@Service
@Transactional
public class MessageService {

	@Autowired
	private MessageRepository	messageRepository;

	@Autowired
	private TopicService		topicService;

	@Autowired
	private ActorService		actorService;

	@Autowired
	private AuthorService		authorService;

	@Autowired
	private Validator			validator;


	public Message findOne(final int id) {
		Assert.isTrue(AuthorityMethods.checkIsSomeoneLogged());
		return this.messageRepository.findOne(id);
	}

	public Message reconstruct(final MessageForm messageForm, final BindingResult binding) {
		final Message message = new Message();

		message.setBody(messageForm.getBody());
		message.setTopics(messageForm.getTopics());
		message.setSubject(messageForm.getSubject());

		final Actor sender = this.actorService.findByUserAccount(LoginService.getPrincipal());
		message.setSender(sender);

		message.setMoment(DateTime.now().toDate());

		Collection<Author> recipients;
		if (!(utiles.AuthorityMethods.chechAuthorityLogged("ADMINISTRATOR") && !messageForm.getBroadcastType().equals(null))) {

			final Collection<Actor> recipientsp = messageForm.getRecipients();
			message.setRecipients(recipientsp);
		} else
			switch (messageForm.getBroadcastType()) {
			case "ALL-ACTORS":
				message.setRecipients(this.actorService.findAllExceptLogged());
				break;
			case "ALL-AUTHORS":
				recipients = this.authorService.getAllAuthors();
				message.setRecipients(this.authorService.castToActors(recipients));
				break;
			case "AUTHORS-WITH-REGISTRATIONS":
				recipients = this.authorService.getAuhtorsWithRegistration();
				message.setRecipients(this.authorService.castToActors(recipients));
				break;
			case "AUTHORS-WITH-SUBMISSIONS":
				recipients = this.authorService.getAuhtorsWithSubmission();
				message.setRecipients(this.authorService.castToActors(recipients));
				break;
			}

		final Set<Actor> actors = new HashSet<>();
		actors.addAll(message.getRecipients());
		actors.add(sender);

		final Collection<Actor> noRepetidos = actors;
		message.setActors(noRepetidos);

		this.validator.validate(message, binding);

		if (binding.hasErrors())
			throw new ValidationException();

		return message;
	}

	public Message save(final Message message) {
		final Actor actorLogged = this.actorService.findByUserAccount(LoginService.getPrincipal());
		Assert.isTrue(message.getSender().equals(actorLogged), "Debe estar logueado para realizar esta accion");

		Assert.isTrue(!message.getRecipients().contains(actorLogged), "No se puede mandar un mensaje a uno mismo");
		return this.messageRepository.save(message);
	}

	public Message save(final MessageForm messageForm, final BindingResult bindingResult) {
		final Message message = this.reconstruct(messageForm, bindingResult);
		return this.save(message);
	}

	public void delete(final int idMessage) {
		final Message message = this.findOne(idMessage);
		final Actor actorLogged = this.actorService.findByUserAccount(LoginService.getPrincipal());
		Assert.isTrue(message.getActors().contains(actorLogged), "No puede borrar este mensaje porque ya no existe");

		final Collection<Actor> actors = message.getActors();
		actors.remove(actorLogged);

		if (actors.size() == 0)
			this.messageRepository.delete(message);
		else {
			message.setActors(actors);
			this.messageRepository.save(message);
		}
	}

	public Collection<Message> findByTopic(final int idTopic) {
		return this.messageRepository.findByTopic(idTopic);
	}

	public void updateMessageWithThisTopic(final Topic topic) {
		final Collection<Message> messageWithTopic = this.findByTopic(topic.getId());
		for (final Message message : messageWithTopic) {
			final Collection<Topic> topics = message.getTopics();
			topics.remove(topic);
			if (topics.size() == 0)
				topics.add(this.topicService.findOtherTopic());
		}
		this.messageRepository.save(messageWithTopic);
	}

	public Collection<Message> getMessagesOfActorLogged() {
		final Actor actorLogged = this.actorService.findByUserAccount(LoginService.getPrincipal());
		return this.messageRepository.getMessagesOfActor(actorLogged.getId());
	}
}
