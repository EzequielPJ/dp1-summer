
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import domain.Activity;

@Service
@Transactional
public class ActivityService {

	@Autowired
	private ActivityService	activityService;


	public Collection<Activity> findAll() {
		return this.activityService.findAll();
	}

	public Collection<Integer> findAllId() {
		return this.activityService.findAllId();
	}

	public Activity findOne(final int id) {
		return this.activityService.findOne(id);
	}

}
