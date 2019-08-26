
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
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
import domain.Submission;
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
	private SubmissionService	submissionService;

	@Autowired
	private Validator			validator;


	public Message findOne(final int id) {
		Assert.isTrue(AuthorityMethods.checkIsSomeoneLogged());
		return this.messageRepository.findOne(id);
	}

	public Message getMessage(final int id) {
		Assert.isTrue(AuthorityMethods.checkIsSomeoneLogged());
		return this.messageRepository.getMessage(id);
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
		if (!(utiles.AuthorityMethods.chechAuthorityLogged("ADMINISTRATOR") && !messageForm.getBroadcastType().equals(""))) {
			if (!(messageForm.getRecipients() == null || messageForm.getRecipients().isEmpty() || messageForm.getRecipients().contains(null))) {
				final Collection<Actor> recipientsp = messageForm.getRecipients();
				message.setRecipients(recipientsp);

				final Set<Actor> actors = new HashSet<>();
				actors.addAll(message.getRecipients());
				actors.add(sender);

				final Collection<Actor> noRepetidos = actors;
				message.setActors(noRepetidos);
			}
		} else {

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
		}

		this.validator.validate(message, binding);

		if (binding.hasErrors())
			throw new ValidationException();
		return message;
	}

	public Message save(final Message message) {
		Assert.isTrue(AuthorityMethods.checkIsSomeoneLogged(), "Debe estar logueado para realizar esta accion");
		final Actor actorLogged = this.actorService.findByUserAccount(LoginService.getPrincipal());
		Assert.isTrue(message.getSender().equals(actorLogged), "Debe ser el emisor del mensaje");

		Assert.isTrue(!message.getRecipients().contains(actorLogged), "No se puede mandar un mensaje a uno mismo");
		return this.messageRepository.save(message);
	}

	public Message notifiqueStatusChanged(final Submission submission) throws ParseException {

		final Actor admin = this.actorService.findByUserAccount(LoginService.getPrincipal());

		final Message message = new Message();

		final Collection<Actor> actorToBeNotified = new ArrayList<>();
		actorToBeNotified.add(submission.getAuthor());
		message.setRecipients(actorToBeNotified);

		final Collection<Actor> actors = new ArrayList<>();
		actors.add(admin);
		actors.add(submission.getAuthor());
		message.setActors(actors);

		message.setMoment(new Date());
		message.setSender(admin);

		final Collection<Topic> topics = new ArrayList<>();
		topics.add(this.topicService.findOtherTopic());
		message.setTopics(topics);

		message.setSubject("SUBMISSION " + submission.getTicker().getIdentifier() + " STATUS NOTIFICATION ---- NOTIFICACI�N POR CAMBIO DE ESTADO ENTREGA " + submission.getTicker().getIdentifier());
		message.setBody("Your submmision " + submission.getTicker().getIdentifier() + " status has changed {" + submission.getStatus() + "}. Please verify it to submit the camera ready version of the paper if needed before "
			+ submission.getConference().getCameraReadyDeadline() + ".  ---------  Tu entrega " + submission.getTicker().getIdentifier() + " ha recibido una actualizaci�n del estado {" + submission.getStatus()
			+ "}. Por favor, compruebe el mismo para adjuntar la versi�n para la presentaci�n del documento si fuese necesario antes de " + submission.getConference().getCameraReadyDeadline() + ".");

		this.submissionService.setNotified(submission);
		return this.messageRepository.save(message);

	}
	public Message save(final MessageForm messageForm, final BindingResult bindingResult) {
		final Message message = this.reconstruct(messageForm, bindingResult);
		return this.save(message);
	}

	public void delete(final Message message) {
		if (message.getRecipients().isEmpty() && message.getActors().isEmpty())
			this.messageRepository.delete(message);

	}

	public Message prepareMessageToDelete(final int idMessage) {
		//Se setean los destinatarios porque spring trae los mensajes de la base de datos sin destinatarios
		final Collection<Actor> recipients = this.messageRepository.getRecipientsOfMessage(idMessage);

		final Message message = this.getMessage(idMessage);
		Message result;
		System.out.println("Numero de actores: " + message.getActors().size());
		final Actor actorLogged = this.actorService.findByUserAccount(LoginService.getPrincipal());
		Assert.isTrue(message.getActors().contains(actorLogged), "No puede borrar este mensaje porque ya no existe");

		final Collection<Actor> actors = message.getActors();
		actors.remove(actorLogged);

		if (actors.size() == 0) {
			message.setRecipients(new ArrayList<Actor>());
			message.setActors(new ArrayList<Actor>());
			result = this.messageRepository.save(message);
		} else {
			message.setRecipients(recipients);
			message.setActors(actors);
			result = this.messageRepository.save(message);
		}

		return result;
	}

	public Message deleteRecipients(final Message message) {
		message.setRecipients(null);
		return this.messageRepository.saveAndFlush(message);
	}

	public Collection<Message> findByTopic(final int idTopic) {
		return this.messageRepository.findByTopic(idTopic);
	}

	public Collection<Actor> getRecipientsOfMessage(final int idMessage) {
		return this.messageRepository.getRecipientsOfMessage(idMessage);
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
		final Collection<Message> messages = this.messageRepository.getMessagesOfActor(actorLogged.getId());
		return messages;
	}
	public Collection<Message> getMessagesOfActorLoggedByTopic(final int idTopic) {
		final Actor actorLogged = this.actorService.findByUserAccount(LoginService.getPrincipal());
		final Collection<Message> messages = this.messageRepository.getMessagesOfActorByTopic(actorLogged.getId(), idTopic);
		return messages;
	}

	public Collection<Message> getMessagesOfActorLoggedBySender(final int idSender) {
		final Actor actorLogged = this.actorService.findByUserAccount(LoginService.getPrincipal());
		final Collection<Message> messages = this.messageRepository.getMessagesOfActorBySender(actorLogged.getId(), idSender);
		return messages;
	}

	public Collection<Message> getMessagesOfActorLoggedByRecipient(final int idRecipient) {
		final Actor actorLogged = this.actorService.findByUserAccount(LoginService.getPrincipal());
		final Collection<Message> messages = this.messageRepository.getMessagesOfActorByRecipient(actorLogged.getId(), idRecipient);
		return messages;
	}

	public Collection<Actor> getRecipientsWhoHaveSentMessagesAnActorLogged() {
		final Actor actorLogged = this.actorService.findByUserAccount(LoginService.getPrincipal());
		final Collection<Actor> actors = this.messageRepository.getRecipientsWhoHaveSentMessagesAnActor(actorLogged.getId());
		return actors;
	}

	public Collection<Actor> getSendersWhoHaveSentMessagesToAnActor() {
		final Actor actorLogged = this.actorService.findByUserAccount(LoginService.getPrincipal());
		final Collection<Actor> actors = this.messageRepository.getSendersWhoHaveSentMessagesToAnActor(actorLogged.getId());
		return actors;
	}

	public Collection<Topic> getAllTopicsOfMessagesOfActor() {
		final Actor actorLogged = this.actorService.findByUserAccount(LoginService.getPrincipal());
		final Collection<Topic> topics = this.messageRepository.getAllTopicsOfMessagesOfActor(actorLogged.getId());
		return topics;
	}

	public void flush() {
		this.messageRepository.flush();
	}

}
