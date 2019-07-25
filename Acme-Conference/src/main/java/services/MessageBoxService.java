
package services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import repositories.MessageBoxRepository;
import domain.MessageBox;

@Service
@Transactional
public class MessageBoxService {

	@Autowired
	private MessageBoxRepository	messageBoxRepository;


	//	@Autowired
	//	private ActorService			actorService;
	//
	//	@Autowired
	//	private ActorRepository			actorRepository;
	//
	//	@Autowired
	//	private MessageRepository		messageRepository;
	//
	//	@Autowired
	//	private Validator				validator;

	//	public MessageBox create() {
	//		return new MessageBox();
	//	}
	//
	//	public MessageBox save(final MessageBox messageBox) {
	//		Assert.isTrue(this.notLikeOriginalName(messageBox.getName()));
	//		final Actor actor = this.actorService.findByUserAccount(LoginService.getPrincipal());
	//		if (messageBox.getParent() != null) {
	//			Assert.isTrue(messageBox.getId() != messageBox.getParent().getId());
	//			final Collection<MessageBox> childrens = this.allChildren(messageBox);
	//			Assert.isTrue(!childrens.contains(messageBox.getParent()));
	//			Assert.isTrue(this.actorService.getByMessageBox(messageBox.getParent().getId()).getId() == actor.getId());
	//			Assert.isTrue(messageBox.getParent().getDeleteable());
	//		}
	//
	//		if (messageBox.getId() != 0) {
	//			final MessageBox boxBD = this.messageBoxRepository.findOne(messageBox.getId());
	//			Assert.isTrue(boxBD.getDeleteable() == messageBox.getDeleteable());
	//			Assert.isTrue(messageBox.getDeleteable());
	//		}
	//
	//		final MessageBox boxSave = this.messageBoxRepository.save(messageBox);
	//
	//		if (messageBox.getId() == 0) {
	//			final Collection<MessageBox> messageBoxes = actor.getMessageBoxes();
	//			messageBoxes.add(boxSave);
	//			actor.setMessageBoxes(messageBoxes);
	//			this.actorService.save(actor);
	//		}
	//
	//		return boxSave;
	//	}
	//
	//	public void delete(final MessageBox messageBox) {
	//		Assert.isTrue(messageBox.getDeleteable());
	//		final Actor actor = this.actorService.findByUserAccount(LoginService.getPrincipal());
	//		Assert.isTrue(this.actorService.getByMessageBox(messageBox.getId()).equals(actor));
	//
	//		final Collection<MessageBox> childrens = this.allChildren(messageBox);
	//		final MessageBox trashBox = this.findOriginalBox(actor.getId(), "Trash Box");
	//		final Collection<Message> messagesToTrashBox = new ArrayList<>();
	//
	//		for (final MessageBox boxChild : childrens)
	//			if (boxChild.getMessages().size() > 0) {
	//				for (final Message messageInBoxChild : boxChild.getMessages()) {
	//					final Collection<MessageBox> boxes = this.messageBoxRepository.findAllMessageBoxByActorContainsAMessage(actor.getId(), messageInBoxChild.getId());
	//					boxes.remove(boxChild);
	//					if (boxes.size() == 0) {
	//						boxes.add(trashBox);
	//						messagesToTrashBox.add(messageInBoxChild);
	//					}
	//					messageInBoxChild.setMessageBoxes(boxes);
	//					this.messageRepository.save(messageInBoxChild);
	//				}
	//				boxChild.setMessages(null);
	//				this.messageBoxRepository.save(boxChild);
	//			}
	//
	//		final Collection<Message> trashBoxMessages = trashBox.getMessages();
	//		trashBoxMessages.addAll(messagesToTrashBox);
	//		trashBox.setMessages(trashBoxMessages);
	//		this.messageBoxRepository.save(trashBox);
	//
	//		final Collection<MessageBox> boxesActor = actor.getMessageBoxes();
	//		boxesActor.removeAll(childrens);
	//		actor.setMessageBoxes(boxesActor);
	//		this.actorRepository.save(actor);
	//
	//		this.messageBoxRepository.delete(childrens);
	//	}
	//	public Collection<MessageBox> findBoxToMove(final Message message) {
	//		final Actor actor = this.actorService.findByUserAccount(LoginService.getPrincipal());
	//		final Collection<MessageBox> messageBoxesToMove = this.findAllMessageBoxByActor(actor.getId());
	//
	//		final MessageBox trashBox = this.messageBoxRepository.findOriginalBox(actor.getId(), "Trash Box");
	//
	//		messageBoxesToMove.removeAll(message.getMessageBoxes());
	//		messageBoxesToMove.remove(trashBox);
	//
	//		return messageBoxesToMove;
	//	}
	//
	//	public Collection<MessageBox> initializeNewUserBoxes() {
	//		final List<MessageBox> initialBoxes = new ArrayList<>();
	//
	//		MessageBox inBox = this.create();
	//		inBox.setDeleteable(false);
	//		inBox.setName("In Box");
	//		inBox = this.messageBoxRepository.save(inBox);
	//
	//		MessageBox outBox = this.create();
	//		outBox.setDeleteable(false);
	//		outBox.setName("Out Box");
	//		outBox = this.messageBoxRepository.save(outBox);
	//
	//		MessageBox spamBox = this.create();
	//		spamBox.setDeleteable(false);
	//		spamBox.setName("Spam Box");
	//		spamBox = this.messageBoxRepository.save(spamBox);
	//
	//		MessageBox trashBox = this.create();
	//		trashBox.setDeleteable(false);
	//		trashBox.setName("Trash Box");
	//		trashBox = this.messageBoxRepository.save(trashBox);
	//
	//		MessageBox notificationBox = this.create();
	//		notificationBox.setName("Notification Box");
	//		notificationBox.setDeleteable(false);
	//		notificationBox = this.messageBoxRepository.save(notificationBox);
	//
	//		initialBoxes.add(inBox);
	//		initialBoxes.add(outBox);
	//		initialBoxes.add(trashBox);
	//		initialBoxes.add(spamBox);
	//		initialBoxes.add(notificationBox);
	//
	//		return initialBoxes;
	//	}
	//
	//	public MessageBox reconstruct(final MessageBox messageBox, final BindingResult binding) {
	//		MessageBox result;
	//
	//		if (messageBox.getId() == 0) {
	//			result = this.create();
	//			result.setMessages(null);
	//			result.setDeleteable(true);
	//			if (this.likeOriginalName(messageBox.getName()))
	//				binding.rejectValue("name", "messageBox.error.OriginalName");
	//			else if (this.likeOtherBox(messageBox.getName()))
	//				binding.rejectValue("name", "messageBox.error.likeOtherBox");
	//		} else {
	//			result = this.findOne(messageBox.getId());
	//			if (this.likeOriginalName(messageBox.getName()))
	//				binding.rejectValue("name", "messageBox.error.OriginalName");
	//			else if (!result.getName().equals(messageBox.getName()))
	//				if (this.likeOtherBox(messageBox.getName()))
	//					binding.rejectValue("name", "messageBox.error.likeOtherBox");
	//		}
	//
	//		result.setParent(messageBox.getParent());
	//		result.setName(messageBox.getName());
	//
	//		if (messageBox.getParent() != null) {
	//			Assert.isTrue(messageBox.getId() != messageBox.getParent().getId());
	//			final Collection<MessageBox> childrens = this.allChildren(messageBox);
	//			Assert.isTrue(!childrens.contains(messageBox.getParent()));
	//		}
	//
	//		this.validator.validate(result, binding);
	//
	//		if (binding.hasErrors())
	//			throw new ValidationException();
	//
	//		return result;
	//	}
	//
	public MessageBox findOne(final int id) {
		return this.messageBoxRepository.findOne(id);
	}
	//
	//	public MessageBox findOriginalBox(final int id, final String string) {
	//		return this.messageBoxRepository.findOriginalBox(id, string);
	//	}
	//
	//	public Collection<MessageBox> findAllMessageBoxByActor(final int id) {
	//		return this.messageBoxRepository.findAllMessageBoxByActor(id);
	//	}
	//
	//	public Collection<MessageBox> findPosibleParents(final int id) {
	//		return this.messageBoxRepository.findPosibleParents(id);
	//	}
	//
	//	public Collection<MessageBox> findChildren(final int id) {
	//		return this.messageBoxRepository.findChildren(id);
	//	}
	//
	//	public Collection<MessageBox> findAllMessageBoxByActorContainsAMessage(final int idActor, final int idMessage) {
	//		return this.messageBoxRepository.findAllMessageBoxByActorContainsAMessage(idActor, idMessage);
	//	}
	//
	//	//Utilities
	//	private boolean likeOriginalName(final String name) {
	//		return !this.notLikeOriginalName(name);
	//	}
	//
	//	private boolean notLikeOriginalName(final String name) {
	//		boolean res = false;
	//
	//		final String nameTrim = name.toUpperCase().replace(" ", "");
	//
	//		if (!(nameTrim.equals("INBOX") || nameTrim.equals("TRASHBOX") || nameTrim.equals("OUTBOX") || nameTrim.equals("SPAMBOX") || nameTrim.equals("NOTIFICATIONBOX")))
	//			res = true;
	//
	//		return res;
	//	}
	//
	//	private boolean likeOtherBox(final String nameBox) {
	//		Boolean res = true;
	//		final Actor actor = this.actorService.findByUserAccount(LoginService.getPrincipal());
	//
	//		if (this.messageBoxRepository.findAllNameMessageBoxOfActor(actor.getId(), nameBox.trim()) == null)
	//			res = false;
	//
	//		return res;
	//	}
	//
	//	public Collection<MessageBox> allChildren(final MessageBox box) {
	//		final Collection<MessageBox> acum = new ArrayList<>();
	//		return this.allChildren(box, acum);
	//	}
	//
	//	private Collection<MessageBox> allChildren(final MessageBox box, final Collection<MessageBox> acum) {
	//		final Collection<MessageBox> childrens = this.findChildren(box.getId());
	//		if (childrens.size() == 0)
	//			acum.add(box);
	//		else {
	//			for (final MessageBox child : childrens)
	//				this.allChildren(child, acum);
	//			acum.add(box);
	//		}
	//		return acum;
	//	}
	//
	//	public void flush() {
	//		this.messageBoxRepository.flush();
	//	}

}
