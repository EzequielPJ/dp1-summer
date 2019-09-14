
package controllers;

import java.util.Collection;

import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import services.ActorService;
import services.MessageService;
import services.TopicService;
import domain.Actor;
import domain.Message;
import forms.MessageForm;

@Controller
@RequestMapping("/message")
public class MessageController extends AbstractController {

	@Autowired
	private ActorService	actorService;

	@Autowired
	private MessageService	messageService;

	@Autowired
	private TopicService	topicService;


	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int idMessage) {
		ModelAndView result = new ModelAndView("message/display");

		final Message message = this.messageService.findOne(idMessage);
		final Actor actorLogged = this.actorService.findByUserAccount(LoginService.getPrincipal());
		final Collection<Actor> actors = message.getActors();
		//No borrar los syso
		System.out.println("Actores: " + actors.size());
		System.out.println("Destinatarios: " + message.getRecipients().size());
		if (!actors.contains(actorLogged))
			result = new ModelAndView("redirect:list.do");
		else {
			result.addObject("recipients", this.messageService.getRecipientsOfMessage(idMessage));
			result.addObject("messageObject", message);
			result.addObject("actorLogged", actorLogged);
		}

		super.configValues(result);
		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		return this.createEditModelAndView(new MessageForm());
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final Collection<Message> messagesList = this.messageService.getMessagesOfActorLogged();
		return this.listModelAndView(messagesList, "message/list.do");
	}

	@RequestMapping(value = "/listByTopic", method = RequestMethod.GET)
	public ModelAndView listByTopic(@RequestParam final int idTopic) {
		final Collection<Message> messagesList = this.messageService.getMessagesOfActorLoggedByTopic(idTopic);
		return this.listModelAndView(messagesList, "message/listByTopic.do?idTopic=" + idTopic);
	}

	@RequestMapping(value = "/listBySender", method = RequestMethod.GET)
	public ModelAndView listBySender(@RequestParam final int idSender) {
		final Collection<Message> messagesList = this.messageService.getMessagesOfActorLoggedBySender(idSender);
		return this.listModelAndView(messagesList, "message/listBySender.do?idSender=" + idSender);
	}

	@RequestMapping(value = "/listByRecipient", method = RequestMethod.GET)
	public ModelAndView listByRecipient(@RequestParam final int idRecipient) {
		final Collection<Message> messagesList = this.messageService.getMessagesOfActorLoggedByRecipient(idRecipient);
		return this.listModelAndView(messagesList, "message/listByRecipient.do?idRecipient=" + idRecipient);
	}

	@RequestMapping(value = "/send", method = RequestMethod.POST, params = "save")
	public ModelAndView save(final MessageForm messageForm, final BindingResult bindingResult) {
		ModelAndView result;
		if (bindingResult.hasErrors())
			result = this.createEditModelAndView(messageForm);
		else
			try {
				this.messageService.save(messageForm, bindingResult);
				result = new ModelAndView("redirect:list.do");
			} catch (final ValidationException ve) {
				result = this.createEditModelAndView(messageForm);
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(messageForm, "cannot.send.message");
				oops.printStackTrace();
			}
		return result;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int idMessage) {
		ModelAndView result;
		try {
			final Message messageDelete = this.messageService.prepareMessageToDelete(idMessage);
			//final Message message = this.messageService.getMessage(idMessage);
			this.messageService.delete(messageDelete);
			result = new ModelAndView("redirect:list.do");
		} catch (final Throwable oops) {
			result = this.listModelAndView(this.messageService.getMessagesOfActorLogged(), "message/list.do", "cannot.delete.message");
			oops.printStackTrace();
		}
		return result;

	}

	protected ModelAndView createEditModelAndView(final MessageForm messageForm) {
		return this.createEditModelAndView(messageForm, null);
	}

	protected ModelAndView createEditModelAndView(final MessageForm messageForm, final String message) {
		final ModelAndView result = new ModelAndView("message/create");
		result.addObject("messageForm", messageForm);
		result.addObject("topics", this.topicService.findAll());
		result.addObject("actors", this.actorService.findAllExceptLogged());
		result.addObject("message", message);

		super.configValues(result);
		return result;
	}

	protected ModelAndView listModelAndView(final Collection<Message> messages, final String uri) {
		return this.listModelAndView(messages, uri, null);
	}

	//La variable message es una variable reservada, por eso se llama messageList
	protected ModelAndView listModelAndView(final Collection<Message> messagesList, final String uri, final String message) {
		final ModelAndView result = new ModelAndView("message/list");

		result.addObject("messagesList", messagesList);
		for (final Message message2 : messagesList) {

			System.out.println("Asunto del mensaje con id= " + message2.getId() + ": " + message2.getSubject());
			System.out.println("Numero de destinatarios del mensaje con id= " + message2.getId() + ": " + message2.getRecipients().size());
		}
		result.addObject("message", message);
		result.addObject("requestURI", uri);

		final Actor actorLogged = this.actorService.findByUserAccount(LoginService.getPrincipal());
		result.addObject("actorLogged", actorLogged);

		result.addObject("recipients", this.messageService.getRecipientsWhoHaveSentMessagesAnActorLogged());
		result.addObject("senders", this.messageService.getSendersWhoHaveSentMessagesToAnActor());
		result.addObject("topics", this.messageService.getAllTopicsOfMessagesOfActor());

		super.configValues(result);
		return result;
	}
}
