
package services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;
import javax.validation.ValidationException;

import org.joda.time.LocalDateTime;
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
import domain.Conference;

@Service
@Transactional
public class ConferenceService {

	@Autowired
	private ConferenceRepository	conferenceRepository;

	@Autowired
	private AdminConfigService		adminConfigService;

	@Autowired
	private AdministratorService	administratorService;

	@Autowired
	Validator						validator;

	private final SimpleDateFormat	FORMAT	= new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");


	public Conference findOne(final int id) {
		return this.conferenceRepository.findOne(id);
	}

	public Collection<Conference> findAll() {
		return this.conferenceRepository.findAll();
	}

	public Collection<Integer> findAllId() {
		return this.conferenceRepository.findAllId();
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

	public Collection<Conference> getConferencesPast(final Date date1) {
		return this.conferenceRepository.getConferencesPast(date1);
	}

	public Collection<Conference> getConferencesFuture(final Date date1) {
		return this.conferenceRepository.getConferencesFuture(date1);
	}

	public Collection<Conference> getConferencesFinalMode() {
		return this.conferenceRepository.getConferencesFinalMode();
	}

	public Conference create() {
		Assert.isTrue(AuthorityMethods.chechAuthorityLogged("ADMINISTRATOR"));
		final Conference conference = new Conference();
		conference.setAcronym("");
		conference.setAdministrator(this.findByPrincipal(LoginService.getPrincipal()));
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

	public void delete(final int id) {
		Assert.isTrue(AuthorityMethods.chechAuthorityLogged("ADMINISTRATOR"));
		Assert.isTrue(this.conferenceRepository.findOne(id).getAdministrator().equals(this.conferenceRepository.findByPrincipal(LoginService.getPrincipal().getId())));
		Assert.isTrue(this.conferenceRepository.findOne(id).getFinalMode() == false);
		this.conferenceRepository.delete(this.conferenceRepository.findOne(id));
	}

	public Conference reconstruct(final Conference conference, final BindingResult binding) throws ParseException {
		Conference conferenceRec;
		if (conference.getId() == 0) {
			conferenceRec = conference;
			conferenceRec.setAdministrator(this.findByPrincipal(LoginService.getPrincipal()));
		} else {
			conferenceRec = this.conferenceRepository.findOne(conference.getId());
			Assert.isTrue(conferenceRec.getFinalMode() == false, "You can not edit a conference in finalMode");
			conferenceRec.setTitle(conference.getTitle());
			conferenceRec.setAcronym(conference.getAcronym());
			conferenceRec.setVenue(conference.getVenue());
			conferenceRec.setSubmissionDeadline(conference.getSubmissionDeadline());
			conferenceRec.setNotificationDeadline(conference.getNotificationDeadline());
			conferenceRec.setCameraReadyDeadline(conference.getCameraReadyDeadline());
			conferenceRec.setStartDate(conference.getStartDate());
			conferenceRec.setEndDate(conference.getEndDate());
			conferenceRec.setSummary(conference.getSummary());
			conferenceRec.setFee(conference.getFee());
			conferenceRec.setFinalMode(conference.getFinalMode());
			conferenceRec.setCategory(conference.getCategory());
		}

		if (conference.getSubmissionDeadline() == null)
			binding.rejectValue("submissionDeadline", "deadline.badDate");
		if (conference.getNotificationDeadline() == null)
			binding.rejectValue("notificationDeadline", "deadline.badDate");
		if (conference.getCameraReadyDeadline() == null)
			binding.rejectValue("cameraReadyDeadline", "deadline.badDate");
		if (conference.getStartDate() == null)
			binding.rejectValue("startDate", "deadline.badDate");
		if (conference.getEndDate() == null)
			binding.rejectValue("endDate", "deadline.badDate");

		if (conference.getCategory() == null)
			binding.rejectValue("category", "category.blank");

		if (conference.getSubmissionDeadline().after(conference.getNotificationDeadline()) || conference.getSubmissionDeadline().equals(conference.getNotificationDeadline()))
			binding.rejectValue("submissionDeadline", "submission.notification");
		else if (conference.getNotificationDeadline().after(conference.getCameraReadyDeadline()) || conference.getNotificationDeadline().equals(conference.getCameraReadyDeadline()))
			binding.rejectValue("notificationDeadline", "notification.cameraready");
		else if (conference.getCameraReadyDeadline().after(conference.getStartDate()) || conference.getCameraReadyDeadline().equals(conference.getStartDate()))
			binding.rejectValue("cameraReadyDeadline", "cameraready.startdate");
		else if (conference.getStartDate().after(conference.getEndDate()) || conference.getStartDate().equals(conference.getEndDate()))
			binding.rejectValue("startDate", "startdate.enddate");

		this.validator.validate(conferenceRec, binding);
		if (binding.hasErrors())
			throw new ValidationException();

		return conferenceRec;
	}

	public Collection<Conference> getFilterConferencesByKeyword(final String keyword) {
		return this.conferenceRepository.getFilterConferencesByKeyword(keyword);
	}

	public Collection<Conference> getFilterConferencesByFinder(final String keyWord, final Date minimumDate, final Date maximumDate, final Double maximumFee, final int minCategory, final int maxCategory) {
		Assert.isTrue(AuthorityMethods.chechAuthorityLogged("AUTHOR"));
		return this.conferenceRepository.getFilterConferencesByFinder(keyWord, minimumDate, maximumDate, maximumFee, minCategory, maxCategory);
	}

	public Collection<Conference> getConferencesByFinder(final int idFinder) {
		Assert.isTrue(AuthorityMethods.chechAuthorityLogged("AUTHOR"));
		return this.conferenceRepository.getConferencesByFinder(idFinder);
	}

	public List<String> getConferenceBuzzWords() throws ParseException {
		Assert.isTrue(AuthorityMethods.chechAuthorityLogged("ADMINISTRATOR"));
		final LocalDateTime DATETIMENOW = LocalDateTime.now();
		final Date date = this.FORMAT.parse((DATETIMENOW.getYear() - 1) + "/" + DATETIMENOW.getMonthOfYear() + "/" + DATETIMENOW.getDayOfMonth() + " " + DATETIMENOW.getHourOfDay() + ":" + LocalDateTime.now().getMinuteOfHour() + ":"
			+ DATETIMENOW.getSecondOfMinute());
		final Collection<String> aux = this.conferenceRepository.getTextsOfConferences(date);
		String texts = "";
		for (final String string : aux)
			texts = texts + " " + string;
		final List<String> textWords = new LinkedList<String>(Arrays.asList(texts.toLowerCase().trim().split("\"|\\s|,|\\.|;|:|!|\'|\\?|¿|¡|\\(|\\)|\\[|\\]|\\{|\\}|\\=|\\<|\\>")));
		textWords.removeAll(Collections.singleton(""));
		final Map<String, Integer> wordScores = new HashMap<>();
		for (final String string : textWords)
			if (!(this.adminConfigService.getAdminConfig().getVoidWords().contains(string)))
				if (wordScores.containsKey(string.trim()))
					wordScores.put(string.trim(), wordScores.get(string.trim()) + 1);
				else
					wordScores.put(string.trim(), 1);
		final List<Integer> scores = new ArrayList<>();
		scores.addAll(wordScores.values());
		Collections.sort(scores);
		final Double buzzScore = scores.get(scores.size() - 1) * 0.8;
		final List<String> res = new ArrayList<>();
		for (final String string : wordScores.keySet())
			if (wordScores.get(string) >= buzzScore)
				res.add(string);
		return res;
	}

	public boolean getContainsExpertiseKeywords(final String string, final int conferenceId) {
		Assert.isTrue(AuthorityMethods.chechAuthorityLogged("ADMINISTRATOR"));
		boolean res = false;
		if (this.conferenceRepository.getContainsExpertiseKeywords(string, conferenceId) == 1)
			res = true;
		return res;
	}

	public Collection<Conference> getConferencesToDecisionMaking() {
		Assert.isTrue(AuthorityMethods.chechAuthorityLogged("ADMINISTRATOR"));
		return this.conferenceRepository.getConferencesToDecisionMaking(this.administratorService.findByPrincipal(LoginService.getPrincipal()).getId());
	}

	public Collection<Conference> getConferencesToAssingReviewers() {
		Assert.isTrue(AuthorityMethods.chechAuthorityLogged("ADMINISTRATOR"));
		return this.conferenceRepository.getConferencesToAssingReviewers(this.administratorService.findByPrincipal(LoginService.getPrincipal()).getId());
	}

}
