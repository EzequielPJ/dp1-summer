
package services;

import java.util.Collection;
import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.ConferenceRepository;
import utiles.AuthorityMethods;
import domain.Conference;

@Service
@Transactional
public class ConferenceService {

	@Autowired
	private ConferenceRepository	conferenceRepository;


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

}
