

package services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.FinderRepository;
import security.LoginService;
import utiles.AuthorityMethods;
import domain.Category;
import domain.Conference;
import domain.Finder;

@Service
@Transactional
public class FinderService {

	@Autowired
	private FinderRepository		finderRepository;

	@Autowired
	private ConferenceService		conferenceService;

	@Autowired
	private AuthorService			authorService;

	@Autowired
	private Validator				validator;

	private final SimpleDateFormat	FORMAT	= new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");


	public Finder save(final Finder finder) throws ParseException {
		Assert.isTrue(AuthorityMethods.chechAuthorityLogged("AUTHOR"));
		Assert.notNull(finder);

		Assert.isTrue(this.authorService.findByPrincipal(LoginService.getPrincipal()).getFinder().getId() == finder.getId());

		String keyWord = finder.getKeyWord();
		Date minimumDate = finder.getMinimumDate();
		Date maximumDate = finder.getMaximumDate();
		Double maximumFee = finder.getMaximumFee();
		int minCategory;
		int maxCategory;
		final Category category = finder.getCategory();

		if (keyWord == null)
			keyWord = "";
		if (minimumDate == null)
			minimumDate = this.FORMAT.parse("0001/01/01 01:00:00");
		if (maximumDate == null)
			maximumDate = this.FORMAT.parse("9999/01/01 01:00:00");
		;
		if (maximumFee == null)
			maximumFee = 2147483647.0;
		if (category == null) {
			minCategory = 0;
			maxCategory = 2147483647;
		} else {
			minCategory = category.getId();
			maxCategory = category.getId();
		}

		final Collection<Conference> results = this.conferenceService.getFilterConferencesByFinder(keyWord, minimumDate, maximumDate, maximumFee, minCategory, maxCategory);

		finder.setConferences(results);
		return this.finderRepository.save(finder);
	}

	public Finder findOne(final int finderId) {
		Assert.notNull(finderId);
		return this.finderRepository.findOne(finderId);
	}

	public Finder create() throws ParseException {
		final Finder finder = new Finder();
		return finder;
	}
	public Finder generateNewFinder() throws ParseException {

		final Finder finder = this.create();
		Finder res;
		res = this.finderRepository.save(finder);
		return res;
	}

	public void flush() {
		this.finderRepository.flush();
	}

	public Finder reconstruct(final Finder finder, final BindingResult binding) {

		final Finder result = this.finderRepository.findOne(this.authorService.findByPrincipal(LoginService.getPrincipal()).getFinder().getId());

		finder.setId(result.getId());
		finder.setVersion(result.getVersion());
		finder.setConferences(result.getConferences());
		this.validator.validate(finder, binding);

		return finder;
	}

}
