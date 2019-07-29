
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import repositories.ActivityRepository;
import domain.Activity;

@Service
@Transactional
public class ActivityService {

	@Autowired
	private ActivityRepository	activityRepository;


	public Collection<Activity> findAll() {
		return this.activityRepository.findAll();
	}

	public Collection<Integer> findAllId() {
		return this.activityRepository.findAllId();
	}

	public Activity findOne(final int id) {
		return this.activityRepository.findOne(id);
	}

}
