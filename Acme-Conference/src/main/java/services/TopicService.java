
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;

import repositories.TopicRepository;
import utiles.AuthorityMethods;
import domain.Topic;

@Service
@Transactional
public class TopicService {

	@Autowired
	private TopicRepository	topicRepository;

	@Autowired
	private MessageService	messageService;


	// CRUD methods
	//---------------------------------------------------------------------------------------
	public Topic create() {
		return new Topic();
	}

	public Topic save(final Topic topic) {
		Assert.isTrue(AuthorityMethods.chechAuthorityLogged("ADMINISTRATOR"));
		Assert.isTrue(topic.getTopicEN().trim().toUpperCase() != "OTHER" && topic.getTopicES().trim().toUpperCase() != "OTROS");
		Assert.isTrue(!topic.equals(this.findOtherTopic()));

		final Collection<String> namesEN = this.getAllNameEN();
		final Collection<String> namesES = this.getAllNameES();

		if (topic.getId() != 0) {
			namesEN.remove(this.findOne(topic.getId()).getTopicEN());
			namesES.remove(this.findOne(topic.getId()).getTopicES());
		}

		Assert.isTrue(!namesEN.contains(topic.getTopicEN().trim().toUpperCase().replaceAll(" +", " ")));
		Assert.isTrue(!namesES.contains(topic.getTopicES().trim().toUpperCase().replaceAll(" +", " ")));

		return this.topicRepository.save(topic);
	}

	public void delete(final Topic topic) {
		Assert.isTrue(AuthorityMethods.chechAuthorityLogged("ADMINISTRATOR"));
		Assert.isTrue(!topic.equals(this.findOtherTopic()));

		this.messageService.updateMessageWithThisTopic(topic);

		this.topicRepository.delete(topic);
	}
	//---------------------------------------------------------------------------------------

	// Auxiliar methods
	//---------------------------------------------------------------------------------------
	public Topic findOne(final int idTopic) {
		return this.topicRepository.findOne(idTopic);
	}

	public Collection<Topic> findAll() {
		return this.topicRepository.findAll();
	}

	public Topic findOtherTopic() {
		return this.topicRepository.findOtherTopic();
	}

	public void validateTopicName(final Topic topic, final BindingResult binding) {
		final Collection<String> namesEN = this.getAllNameEN();
		final Collection<String> namesES = this.getAllNameES();

		if (topic.getId() != 0) {
			namesEN.remove(this.findOne(topic.getId()).getTopicEN());
			namesES.remove(this.findOne(topic.getId()).getTopicES());
		}

		if (namesEN.contains(topic.getTopicEN().trim().toUpperCase().replaceAll(" +", " ")))
			binding.rejectValue("topicEN", "category.error.namelikeOther");

		if (namesES.contains(topic.getTopicES().trim().toUpperCase().replaceAll(" +", " ")))
			binding.rejectValue("topicES", "category.error.namelikeOther");

	}

	private Collection<String> getAllNameES() {
		return this.topicRepository.getAllNameES();
	}

	private Collection<String> getAllNameEN() {
		return this.topicRepository.getAllNameEN();
	}

public void flush() {
		this.topicRepository.flush();
	}

	//---------------------------------------------------------------------------------------

}
