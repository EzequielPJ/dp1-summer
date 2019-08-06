
package services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

import javax.validation.ValidationException;

import org.joda.time.DateTime;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.SubmissionRepository;
import security.LoginService;
import utiles.AuthorityMethods;
import utiles.IntermediaryBetweenTransactions;
import domain.Author;
import domain.Conference;
import domain.Paper;
import domain.Reviewer;
import domain.Submission;
import forms.SubmissionPaperForm;

@Service
@Transactional
public class SubmissionService {

	@Autowired
	private SubmissionRepository			submissionRepository;

	@Autowired
	private ReviewerService					reviewerService;

	@Autowired
	private ConferenceService				conferenceService;

	@Autowired
	private AdministratorService			administratorService;

	@Autowired
	private AuthorService					authorService;

	@Autowired
	private PaperService					paperService;

	@Autowired
	private IntermediaryBetweenTransactions	intermediaryBetweenTransactions;

	@Autowired
	private Validator						validator;

	private final SimpleDateFormat			FORMAT	= new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");


	public void assignReviewers(final int idConference) throws ParseException {
		Assert.isTrue(AuthorityMethods.chechAuthorityLogged("ADMINISTRATOR"));
		final Conference conference = this.conferenceService.findOne(idConference);
		Assert.isTrue(conference.getAdministrator().equals(this.administratorService.findByPrincipal(LoginService.getPrincipal())));
		final LocalDateTime DATETIMENOW = LocalDateTime.now();
		final Date actual = this.FORMAT.parse(DATETIMENOW.getYear() + "/" + DATETIMENOW.getMonthOfYear() + "/" + DATETIMENOW.getDayOfMonth() + " " + DATETIMENOW.getHourOfDay() + ":" + DATETIMENOW.getMinuteOfHour() + ":" + DATETIMENOW.getSecondOfMinute());
		Assert.isTrue(conference.getFinalMode());
		Assert.isTrue(conference.getSubmissionDeadline().before(actual) && conference.getNotificationDeadline().after(actual));
		final Collection<Submission> allSubmissionsUnassigned = this.submissionRepository.getAllSubmissionsUnassigned(idConference);
		Collection<Reviewer> reviewers;
		for (final Submission submission : allSubmissionsUnassigned) {
			reviewers = this.reviewerService.getReviewersToAssign(3 - submission.getReviewers().size(), submission);
			reviewers.addAll(submission.getReviewers());
			submission.setReviewers(reviewers);
		}
	}

	public Collection<Submission> getSubmissionsByConference(final int idConference) {
		return this.submissionRepository.getSubmissionsByConference(idConference);
	}

	public void conferenceDecisionMaking(final int idConference) throws ParseException {
		Assert.isTrue(AuthorityMethods.chechAuthorityLogged("ADMINISTRATOR"));
		final Conference conference = this.conferenceService.findOne(idConference);
		Assert.isTrue(conference.getAdministrator().equals(this.administratorService.findByPrincipal(LoginService.getPrincipal())));
		final LocalDateTime DATETIMENOW = LocalDateTime.now();
		final Date actual = this.FORMAT.parse(DATETIMENOW.getYear() + "/" + DATETIMENOW.getMonthOfYear() + "/" + DATETIMENOW.getDayOfMonth() + " " + DATETIMENOW.getHourOfDay() + ":" + DATETIMENOW.getMinuteOfHour() + ":" + DATETIMENOW.getSecondOfMinute());
		Assert.isTrue(conference.getFinalMode());
		Assert.isTrue(conference.getSubmissionDeadline().before(actual) && conference.getNotificationDeadline().after(actual));
		Integer acceptedReports;
		Integer rejectedReports;
		final Collection<Submission> conferenceSubmissions = this.getSubmissionsByConference(idConference);
		for (final Submission submission : conferenceSubmissions) {
			rejectedReports = this.submissionRepository.getNumberOfReportsByStatusAndSubmission(submission.getId(), "REJECT");
			acceptedReports = this.submissionRepository.getNumberOfReportsByStatusAndSubmission(submission.getId(), "ACCEPT");
			if (rejectedReports > acceptedReports)
				submission.setStatus("REJECTED");
			else
				submission.setStatus("ACCEPTED");
			this.submissionRepository.saveAndFlush(submission);
		}

	}

	public Submission reconstruct(final SubmissionPaperForm submissionPaperForm, final BindingResult bindingResult) {

		final Submission submission = new Submission();

		final Author author = this.authorService.findByPrincipal(LoginService.getPrincipal());
		submission.setAuthor(author);
		submission.setConference(submissionPaperForm.getConference());
		submission.setMoment(DateTime.now().toDate());
		submission.setStatus("UNDER-REVIEW");
		submission.setTicker(this.intermediaryBetweenTransactions.generateTicker(author));

		this.validator.validate(submission, bindingResult);

		if (bindingResult.hasErrors())
			throw new ValidationException();

		return submission;
	}

	public Submission save(final Submission submission) {

		Assert.isTrue(AuthorityMethods.chechAuthorityLogged("AUTHOR"), "Debe ser un autor para realizar esta acción");

		final Author author = this.authorService.findByPrincipal(LoginService.getPrincipal());
		final Integer numOfSubmissions = this.getNumberOfSubmissionsOfAuthorByConference(author.getId(), submission.getConference().getId());
		Assert.isTrue(submission.getAuthor().equals(author), "El autor no coincide con el logueado");
		Assert.isTrue(numOfSubmissions == 0, "Ya se ha hecho una solicitud para la conferencia seleccionada");

		Assert.isTrue(submission.getConference().getFinalMode(), "La conferencia seleccionada no esta disponible");
		Assert.isTrue(submission.getConference().getSubmissionDeadline().after(DateTime.now().toDate()), "La submission no se puede hacer porque ha pasado su fecha límite");

		return this.submissionRepository.save(submission);
	}

	//FIXME: HAY QUE TENER ALGUNA RESTRICCION DE FECHA?
	public Submission changeStatus(final Submission submission, final String status) {
		Assert.isTrue(AuthorityMethods.chechAuthorityLogged("ADMINISTRATOR"), "Debe ser un administrador para realizar esta acción");
		Assert.isTrue(submission.getConference().getAdministrator().equals(this.administratorService.findByPrincipal(LoginService.getPrincipal())), "Debe ser el propietario de la conferencia");
		Assert.isTrue(submission.getStatus().equals("UNDER-REVIEW"), "A la conferencia seleccionada ya se le ha cambiado el estado");
		Assert.isTrue(status.equals("ACCEPTED") || status.equals("REJECTED"), "El estado es incorrecto");
		submission.setStatus(status);
		return this.submissionRepository.save(submission);
	}

	public Integer getNumberOfSubmissionsOfAuthor(final int idAuthor) {
		return this.submissionRepository.getNumberOfSubmissionsOfAuthor(idAuthor);
	}

	public Collection<Submission> getSubmissionsOfAuthor(final int idAuthor) {
		return this.submissionRepository.getSubmissionsOfAuthor(idAuthor);
	}

	public Collection<Submission> getSubmissionsCanAddCameraReadyPaper(final int idAuthor) {
		return this.submissionRepository.getSubmissionsCanAddCameraReadyPaper(idAuthor);
	}

	public Collection<Submission> getSubmissionsOfAdmin(final int idAdmin) {
		return this.submissionRepository.getSubmissionsOfAdmin(idAdmin);
	}

	public Integer getNumberOfSubmissionsOfAuthorByConference(final int idAuthor, final int idConference) {
		return this.submissionRepository.getNumberOfSubmissionsOfAuthorByConference(idAuthor, idConference);
	}

	public Submission findOne(final int idSubmission) {
		return this.submissionRepository.findOne(idSubmission);
	}

	public Submission save(final SubmissionPaperForm submissionPaperForm, final BindingResult bindingResult) {
		final Submission submission = this.reconstruct(submissionPaperForm, bindingResult);
		final Submission submissionSaved = this.save(submission);
		final Paper paper = this.paperService.reconstruct(submissionPaperForm, submissionSaved, bindingResult);
		final Paper paperSaved = this.paperService.save(paper);
		return submissionSaved;
	}

}
