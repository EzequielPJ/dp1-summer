
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.ActivityRepository;
import utiles.AuthorityMethods;
import domain.Activity;
import domain.Paper;

@Service
@Transactional
public class ActivityService {

	@Autowired
	private ActivityRepository	activityRepository;

	@Autowired
	private ConferenceService	conferenceService;


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
		return activity;
	}

}
