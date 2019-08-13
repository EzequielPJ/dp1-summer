
package services;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.transaction.Transactional;
import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.ActivityRepository;
import utiles.AuthorityMethods;
import domain.Activity;
import domain.Author;
import domain.Paper;
import domain.Section;

@Service
@Transactional
public class ActivityService {

	@Autowired
	private ActivityRepository	activityRepository;

	@Autowired
	private ConferenceService	conferenceService;

	@Autowired
	Validator					validator;


	public Collection<Activity> findAll() {
		return this.activityRepository.findAll();
	}

	public Collection<Integer> findAllId() {
		return this.activityRepository.findAllId();
	}

	public Activity findOne(final int id) {
		return this.activityRepository.findOne(id);
	}

	public Collection<Activity> getActivityByConference(final int id) {
		return this.activityRepository.getActivityByConference(id);
	}

	public Collection<Author> getAuthorsWithSubmissionAcceptedInConference(final int id) {
		return this.activityRepository.getAuthorsWithSubmissionAcceptedInConference(id);
	}

	public Collection<Paper> getPapersInCameraReadyFromConference(final int id) {
		return this.activityRepository.getPapersInCameraReadyFromConference(id);
	}

	public Collection<Author> getAuthorByPaperId(final int id) {
		return this.activityRepository.getAuthorByPaperId(id);
	}

	public Activity create(final int idConference) {
		Assert.isTrue(AuthorityMethods.chechAuthorityLogged("ADMINISTRATOR"));
		final Activity activity = new Activity();
		activity.setType("");
		activity.setTitle("");
		activity.setStartMoment(new Date());
		activity.setDuration(0);
		activity.setSummary("");
		Collection<String> col;
		col = new ArrayList<>();
		activity.setAttachments(col);

		activity.setConference(this.conferenceService.findOne(idConference));
		activity.setPaper(new Paper());
		Collection<Author> colA;
		colA = new ArrayList<>();
		activity.setAuthors(colA);

		return activity;
	}

	public Activity save(final Activity activity) {
		Assert.isTrue(AuthorityMethods.chechAuthorityLogged("ADMINISTRATOR"));
		return this.activityRepository.save(activity);
	}

	public void delete(final int id) {
		Assert.isTrue(AuthorityMethods.chechAuthorityLogged("ADMINISTRATOR"));
		final Activity ac = this.activityRepository.findOne(id);
		if (ac.getType().equals("TUTORIAL")) {
			final Collection<Section> col = this.activityRepository.getSectionsByActivity(id);
			for (final Section s : col) {
				//borrar las sections asociadass
			}
		}
		this.activityRepository.delete(ac);
	}

	public Activity reconstruct(final Activity activity, final BindingResult binding) throws ParseException {
		Activity activityRec = null;
		if (activity.getId() == 0) {
			activityRec = activity;
			activityRec.setStartMoment(new Date());
			if (activity.getType().equals("PRESENTATION"))
				activityRec.setAuthors(this.activityRepository.getAuthorByPaperId(activity.getPaper().getId()));
		} else {
			activityRec = this.activityRepository.findOne(activity.getId());
			activityRec.setType(activity.getType());
			activityRec.setTitle(activity.getTitle());
			activityRec.setDuration(activity.getDuration());
			activityRec.setRoom(activity.getRoom());
			activityRec.setSummary(activity.getSummary());
			activityRec.setAttachments(activity.getAttachments());
			if (activity.getType().equals("PRESENTATION")) {
				activityRec.setPaper(activity.getPaper());
				activityRec.setAuthors(this.activityRepository.getAuthorByPaperId(activity.getPaper().getId()));
			}
			if (!activity.getType().equals("PRESENTATION")) {
				activityRec.setAuthors(activity.getAuthors());
				activityRec.setPaper(null);
			}
		}

		if (activityRec.getDuration() == 0)
			binding.rejectValue("duration", "activity.duration.bad");
		if (!activity.getType().equals("PRESENTATION"))
			if (activityRec.getAuthors() == null)
				binding.rejectValue("authors", "activity.authors.bad");
		if (activityRec.getAttachments().contains("<script>"))
			binding.rejectValue("attachments", "activity.attachments.bad");
		if (activityRec.getType().equals("---"))
			binding.rejectValue("type", "activity.type.bad");
		if (activity.getType().equals("PRESENTATION"))
			if (activityRec.getPaper() == null)
				binding.rejectValue("paper", "activity.paper.bad");

		this.validator.validate(activityRec, binding);
		if (binding.hasErrors())
			throw new ValidationException();

		return activityRec;
	}

}
