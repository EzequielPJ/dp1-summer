
package services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import repositories.PaperRepository;

@Service
@Transactional
public class PaperService {

	@Autowired
	private PaperRepository		paperRepository;

	@Autowired
	private AdminConfigService	adminConfigService;


	public List<String> getPaperWords(final int authorId) {
		final Collection<String> aux = this.paperRepository.getTextsOfPapers(authorId);
		String texts = "";
		for (final String string : aux)
			texts = texts + " " + string;
		final List<String> textWords = new LinkedList<String>(Arrays.asList(texts.toLowerCase().trim().split("\"|\\s|,|\\.|;|:|!|\'|\\?|¿|¡|\\(|\\)|\\[|\\]|\\{|\\}|\\=|\\<|\\>")));
		textWords.removeAll(Collections.singleton(""));
		final List<String> res = new ArrayList<>();
		for (final String string : textWords)
			if (!(this.adminConfigService.getAdminConfig().getVoidWordsEN().contains(string) || this.adminConfigService.getAdminConfig().getVoidWordsES().contains(string)))
				res.add(string.trim());
		return res;
	}
}
