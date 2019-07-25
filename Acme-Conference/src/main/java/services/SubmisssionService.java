
package services;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.SubmissionRepository;
import security.LoginService;
import utiles.AuthorityMethods;
import utiles.IntermediaryBetweenTransactions;
import domain.Author;
import domain.Submission;
import forms.SubmissionPaperForm;

@Service
@Transactional
public class SubmisssionService {

	@Autowired
	private AuthorService					authorService;

	@Autowired
	private AdministratorService			administratorService;

	@Autowired
	private SubmissionRepository			submissionRepository;

	@Autowired
	private IntermediaryBetweenTransactions	intermediaryBetweenTransactions;


	public Submission save(final SubmissionPaperForm submissionPaperForm) {

		Assert.isTrue(AuthorityMethods.chechAuthorityLogged("AUTHOR"), "Debe ser un autor para realizar esta acción");

		final Author author = this.authorService.findByPrincipal(LoginService.getPrincipal());
		final Integer numOfSubmissions = this.getNumberOfSubmissionsOfAuthorByConference(author.getId(), submissionPaperForm.getConference().getId());
		Assert.isTrue(numOfSubmissions == 0, "Ya se ha hecho una solicitud para la conferencia seleccionada");

		Assert.isTrue(submissionPaperForm.getConference().getFinalMode(), "La conferencia seleccionada no esta disponible");
		Assert.isTrue(submissionPaperForm.getConference().getSubmissionDeadline().after(DateTime.now().toDate()));

		final Submission submission = new Submission();

		submission.setAuthor(author);
		submission.setConference(submissionPaperForm.getConference());
		submission.setMoment(DateTime.now().toDate());
		submission.setStatus("UNDER-REVIEW");
		submission.setTicker(this.intermediaryBetweenTransactions.generateTicker());

		return this.submissionRepository.save(submission);
	}

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

	public Integer getSubmissionsOfAuthor(final int idAuthor) {
		return this.submissionRepository.getSubmissionsOfAuthor(idAuthor);
	}

	public Integer getSubmissionsOfConference(final int idConference) {
		return this.submissionRepository.getSubmissionsOfConference(idConference);
	}

	public Integer getNumberOfSubmissionsOfAuthorByConference(final int idAuthor, final int idConference) {
		return this.submissionRepository.getNumberOfSubmissionsOfAuthorByConference(idAuthor, idConference);
	}

}
