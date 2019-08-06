
package services;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.AuthorRepository;
import security.UserAccount;
import utiles.AuthorityMethods;
import domain.Author;

@Service
@Transactional
public class AuthorService {

	@Autowired
	private AuthorRepository	authorRepository;

	@Autowired
	private ConferenceService	conferenceService;

	@Autowired
	private PaperService		paperService;


	public Author findByPrincipal(final UserAccount principal) {
		return this.authorRepository.findByPrincipal(principal.getId());
	}

	public void computeScore() throws ParseException {
		Assert.isTrue(AuthorityMethods.chechAuthorityLogged("ADMINISTRATOR"));
		Double score;
		final Collection<Author> authors = this.authorRepository.findAll();
		final List<String> conferenceWords = this.conferenceService.getConferenceBuzzWords();
		final Map<Integer, Integer> authorScores = new HashMap<Integer, Integer>();
		for (final Author author : authors) {
			authorScores.put(author.getId(), 0);
			final List<String> paperWords = this.paperService.getPaperWords(author.getId());
			for (final String string : paperWords)
				if (conferenceWords.contains(string))
					authorScores.put(author.getId(), authorScores.get(author.getId()) + 1);
		}
		final List<Integer> scores = new ArrayList<>();
		scores.addAll(authorScores.values());
		Collections.sort(scores);
		final Integer maxScore = scores.get(scores.size() - 1);
		for (final Author author : authors) {
			if (maxScore != 0)
				score = 1.0 * authorScores.get(author.getId()) / maxScore;
			else
				score = 0.0;
			author.setScore(new BigDecimal(score).setScale(2, RoundingMode.HALF_EVEN).doubleValue());
			this.authorRepository.save(author);
		}
	}

	public Collection<Author> getAllAuthors() {
		return this.authorRepository.findAll();
	}

	public Author getAuthor(final int idAuthor) {
		return this.authorRepository.findOne(idAuthor);
	}

}
