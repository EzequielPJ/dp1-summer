
package services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
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
	private Validator			validator;

	@Autowired
	private AuthorService		authorService;

	@Autowired
	private PaperRepository		paperRepository;

	@Autowired
	private SubmissionService	submissionService;

	@Autowired
	private AdminConfigService	adminConfigService;


	public Paper findOne(final int id) {
		return this.paperRepository.findOne(id);
	}

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
		Assert.isTrue(AuthorityMethods.chechAuthorityLogged("AUTHOR"), "Debe ser un autor para realizar esta acci�n");

		final Author author = this.authorService.findByPrincipal(LoginService.getPrincipal());
		Assert.isTrue(paper.getSubmission().getAuthor().equals(author), "Debe ser el autor de la submission para agregar un paper");

		if (!paper.getCameraReadyPaper()) {
			final Integer numOfPapers = this.getNumOfNonCameraVersionPapersOfSubmission(paper.getSubmission().getId());
			Assert.isTrue(numOfPapers == 0, "Ya existe un paper en esta submission");
		} else {
			final Integer numOfPapers = this.getNumOfCameraVersionPapersOfSubmission(paper.getSubmission().getId());
			Assert.isTrue(numOfPapers == 0, "Ya existe un paper definitivo en esta submission");
			Assert.isTrue(paper.getSubmission().getConference().getCameraReadyDeadline().after(DateTime.now().toDate()), "La fecha para subir un paper definitivo ha expirado");
			Assert.isTrue(paper.getSubmission().getStatus().equals("ACCEPTED"), "El estado de la submission debe ser ACCEPTED");
		}

		final Set<Author> nonRepeatedAuthors = (Set<Author>) paper.getAuthors();
		paper.setAuthors(nonRepeatedAuthors);

		return this.paperRepository.save(paper);

	}

	public Integer getNumOfNonCameraVersionPapersOfSubmission(final int idSubmission) {
		return this.paperRepository.getNumOfNonCameraVersionPapersOfSubmission(idSubmission);
	}

	public Integer getNumOfCameraVersionPapersOfSubmission(final int idSubmission) {
		return this.paperRepository.getNumOfCameraVersionPapersOfSubmission(idSubmission);
	}

	public Paper getPaperCamerReadyVersionOfSubmission(final int idSubmission) {
		return this.paperRepository.getPaperCamerReadyVersionOfSubmission(idSubmission);
	}

	public Paper getPaperNonCamerReadyVersionOfSubmission(final int idSubmission) {
		return this.paperRepository.getPaperNonCamerReadyVersionOfSubmission(idSubmission);
	}

	public Paper createPaper(final int idSubmission) {
		Assert.isTrue(AuthorityMethods.chechAuthorityLogged("AUTHOR"), "Debe ser un autor para realizar esta acci�n");
		final Submission submission = this.submissionService.findOne(idSubmission);

		final Author author = this.authorService.findByPrincipal(LoginService.getPrincipal());
		Assert.isTrue(submission.getAuthor().equals(author), "Debe ser el autor de la submission para agregar un paper");

		final Paper paper = new Paper();
		paper.setSubmission(submission);
		return paper;
	}

	public List<String> getPaperWords(final int authorId) {
		final Collection<String> aux = this.paperRepository.getTextsOfPapers(authorId);
		String texts = "";
		for (final String string : aux)
			texts = texts + " " + string;
		final List<String> textWords = new LinkedList<String>(Arrays.asList(texts.toLowerCase().trim().split("\"|\\s|,|\\.|;|:|!|\'|\\?|�|�|\\(|\\)|\\[|\\]|\\{|\\}|\\=|\\<|\\>")));
		textWords.removeAll(Collections.singleton(""));
		final List<String> res = new ArrayList<>();
		for (final String string : textWords)
			if (!(this.adminConfigService.getAdminConfig().getVoidWords().contains(string)))
				res.add(string.trim());
		return res;
	}
}
