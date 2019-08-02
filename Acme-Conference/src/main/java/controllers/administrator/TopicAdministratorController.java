
package controllers.administrator;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.TopicService;
import controllers.AbstractController;
import domain.Topic;

@Controller
@RequestMapping("/topic/administrator")
public class TopicAdministratorController extends AbstractController {

	@Autowired
	private TopicService	topicService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		result = this.listModelAndView();
		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		result = this.createModelAndView(this.topicService.create());
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(final int idTopic) {
		ModelAndView result;
		final Topic topic = this.topicService.findOne(idTopic);
		result = this.createModelAndView(topic);
		return result;
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Topic topic, final BindingResult binding) {
		ModelAndView result;
		result = new ModelAndView("topic/edit");

		this.topicService.validateTopicName(topic, binding);

		if (binding.hasErrors())
			result = this.createModelAndView(topic);
		else
			try {
				this.topicService.save(topic);
				result = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {
				result = this.createModelAndView(topic, "topic.save.error");
			}

		return result;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int idTopic) {
		ModelAndView result;
		final Topic topic = this.topicService.findOne(idTopic);

		try {
			this.topicService.delete(topic);
			result = new ModelAndView("redirect:list.do");
		} catch (final Throwable oops) {
			result = this.listModelAndView("topic.save.error");
			oops.printStackTrace();
		}

		return result;
	}

	protected ModelAndView listModelAndView() {
		return this.listModelAndView(null);
	}

	protected ModelAndView listModelAndView(final String message) {
		final ModelAndView result = new ModelAndView("topic/list");

		final Collection<Topic> topics = this.topicService.findAll();
		result.addObject("topics", topics);
		result.addObject("requestURI", "/topic/administrator/list.do");
		result.addObject("message", message);

		this.configValues(result);
		return result;
	}

	protected ModelAndView createModelAndView(final Topic topic) {
		return this.createModelAndView(topic, null);
	}

	protected ModelAndView createModelAndView(final Topic topic, final String message) {
		final ModelAndView result = new ModelAndView("topic/edit");

		result.addObject("topic", topic);
		result.addObject("message", message);

		this.configValues(result);
		return result;
	}

}
