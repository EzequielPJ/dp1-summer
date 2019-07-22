
package services;

import java.text.ParseException;
import java.util.Collection;
import java.util.Date;

import javax.transaction.Transactional;
import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.ConferenceRepository;
import security.LoginService;
import security.UserAccount;
import utiles.AuthorityMethods;
import domain.Administrator;
import domain.Category;
import domain.Conference;

@Service
@Transactional
public class ConferenceService {

	@Autowired
	private ConferenceRepository	conferenceRepository;

	@Autowired
	Validator						validator;


	public Conference findOne(final int id) {
		return this.conferenceRepository.findOne(id);
	}

	public Administrator findByPrincipal(final UserAccount principal) {
		return this.conferenceRepository.findByPrincipal(principal.getId());
	}

	public Collection<Conference> getYoursConference(final int id) {
		return this.conferenceRepository.getYoursConference(id);
	}

	public Collection<Conference> getConferencesBetweenSubmissionDeadline(final Date date1, final Date date2) {
		return this.conferenceRepository.getConferencesBetweenSubmissionDeadline(date1, date2);
	}

	public Collection<Conference> getConferencesBetweenNotificationDeadline(final Date date1, final Date date2) {
		return this.conferenceRepository.getConferencesBetweenNotificationDeadline(date1, date2);
	}

	public Collection<Conference> getConferencesBetweenCameraReadyDeadline(final Date date1, final Date date2) {
		return this.conferenceRepository.getConferencesBetweenCameraReadyDeadline(date1, date2);
	}

	public Collection<Conference> getConferencesBetweenStartDate(final Date date1, final Date date2) {
		return this.conferenceRepository.getConferencesBetweenStartDate(date1, date2);
	}

	public Conference create() {
		Assert.isTrue(AuthorityMethods.chechAuthorityLogged("ADMINISTRATOR"));
		final Conference conference = new Conference();
		conference.setAcronym("");
		conference.setAdministrator(this.findByPrincipal(LoginService.getPrincipal()));
		conference.setCategory(new Category());
		conference.setFee(0.0);
		conference.setFinalMode(false);
		conference.setSummary("");
		conference.setTitle("");
		conference.setVenue("");
		return conference;
	}

	public Conference save(final Conference conference) {
		Assert.isTrue(AuthorityMethods.chechAuthorityLogged("ADMINISTRATOR"));
		Assert.isTrue(conference.getAdministrator().equals(this.conferenceRepository.findByPrincipal(LoginService.getPrincipal().getId())));
		return this.conferenceRepository.save(conference);
	}

	public Conference reconstruct(final Conference conference, final BindingResult binding) throws ParseException {
		Conference conferenceRec;
		if (conference.getId() == 0) {
			conferenceRec = conference;
			conferenceRec.setAdministrator(this.findByPrincipal(LoginService.getPrincipal()));
		} else
			conferenceRec = this.conferenceRepository.findOne(conference.getId());
		this.validator.validate(conferenceRec, binding);
		if (binding.hasErrors())
			throw new ValidationException();

		return conferenceRec;
	}
}
