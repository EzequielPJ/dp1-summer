
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
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
		ModelAndView result = null;

		final Message message = this.messageService.findOne(idMessage);
		final Actor actorLogged = this.actorService.findByUserAccount(LoginService.getPrincipal());
		if (!message.getActors().contains(actorLogged))
			result = new ModelAndView("redirect:list.do");
		else
			result.addObject("message", message);
		return null;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		return null;
	}

	@RequestMapping(value = "/send", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@ModelAttribute("Message") final MessageForm messageForm, final BindingResult bindingResult) {
		ModelAndView result = null;
		try {
			this.messageService.save(messageForm, bindingResult);
			result = new ModelAndView("redirect:list.do");
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(messageForm, "cannot.send.message");
		}
		return result;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int idMessage) {
		ModelAndView result = null;
		try {
			this.messageService.delete(idMessage);
			result = new ModelAndView("redirect:list.do");
		} catch (final Throwable oops) {
			result = this.listModelAndView(this.messageService.getMessagesOfActorLogged(), "cannot.delete.message");
		}
		return result;

	}

	protected ModelAndView createEditModelAndView(final MessageForm messageForm) {
		return this.createEditModelAndView(messageForm, null);
	}

	protected ModelAndView createEditModelAndView(final MessageForm messageForm, final String message) {
		final ModelAndView result = new ModelAndView();
		result.addObject("messageForm", messageForm);
		result.addObject("topics", this.topicService.findAll());
		result.addObject("actors", this.actorService.findAllExceptLogged());
		result.addObject("message", message);

		return null;
	}

	protected ModelAndView listModelAndView(final Collection<Message> messages) {
		return this.listModelAndView(messages, null);
	}

	//La variable message es una variable reservada, por eso se llama messageList
	protected ModelAndView listModelAndView(final Collection<Message> messagesList, final String message) {
		final ModelAndView result = new ModelAndView();

		result.addObject("messagesList", messagesList);
		result.addObject("message", message);

		return result;
	}
}
