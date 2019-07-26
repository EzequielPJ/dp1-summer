
package services;

import java.util.Set;

import javax.validation.ValidationException;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.PaperRepository;
import security.LoginService;
import utiles.AuthorityMethods;
import domain.Author;
import domain.Paper;
import domain.Submission;
import forms.SubmissionPaperForm;

@Service
@Transactional
public class PaperService {

	@Autowired
	private Validator		validator;

	@Autowired
	private AuthorService	authorService;

	@Autowired
	PaperRepository			paperRepository;


	//RECONSTRUCT
	public Paper reconstruct(final SubmissionPaperForm submissionPaperForm, final Submission submission, final BindingResult bindingResult) {

		final Paper paper = new Paper();

		paper.setTitle(submissionPaperForm.getTitle());
		paper.setAliases(submissionPaperForm.getAliases());
		paper.setSummary(submissionPaperForm.getSummary());
		paper.setDocumentUrl(submissionPaperForm.getDocumentUrl());
		paper.setCameraReadyPaper(false);
		paper.setAuthors(submissionPaperForm.getAuthors());
		paper.setSubmission(submission);

		this.validator.validate(paper, bindingResult);

		if (bindingResult.hasErrors())
			throw new ValidationException();

		return paper;
	}

	//SAVE
	public Paper save(final Paper paper) {
		Assert.isTrue(AuthorityMethods.chechAuthorityLogged("AUTHOR"), "Debe ser un autor para realizar esta acción");

		final Author author = this.authorService.findByPrincipal(LoginService.getPrincipal());
		Assert.isTrue(paper.getSubmission().getAuthor().equals(author), "Debe ser el autor de la submission para agregar un paper");

		if (!paper.getCameraReadyPaper()) {
			final Integer numOfPapers = this.getNumOfNonCameraVersionPapersOfSubmission(paper.getSubmission().getId());
			Assert.isTrue(numOfPapers == 0, "Ya existe un paper en esta submission");
		} else {
			final Integer numOfPapers = this.getNumOfCameraVersionPapersOfSubmission(paper.getSubmission().getId());
			Assert.isTrue(numOfPapers == 0, "Ya existe un paper definitivo en esta submission");
			Assert.isTrue(paper.getSubmission().getConference().getCameraReadyDeadline().after(DateTime.now().toDate()), "La fecha para subir un paper definitivo ha expirado");
		}

		final Set<Author> nonRepeatedAuthors = (Set<Author>) paper.getAuthors();
		paper.setAuthors(nonRepeatedAuthors);

		return this.paperRepository.save(paper);

	}

	Integer getNumOfNonCameraVersionPapersOfSubmission(final int idSubmission) {
		return this.paperRepository.getNumOfNonCameraVersionPapersOfSubmission(idSubmission);
	}

	Integer getNumOfCameraVersionPapersOfSubmission(final int idSubmission) {
		return this.paperRepository.getNumOfCameraVersionPapersOfSubmission(idSubmission);
	}

}
