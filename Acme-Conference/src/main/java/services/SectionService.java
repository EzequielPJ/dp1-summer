
package services;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;

import javax.transaction.Transactional;
import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.SectionRepository;
import security.LoginService;
import utiles.AuthorityMethods;
import domain.Section;

@Service
@Transactional
public class SectionService {

	@Autowired
	private ActivityService		activityService;

	@Autowired
	private SectionRepository	sectionRepository;

	@Autowired
	private ConferenceService	conferenceService;

	@Autowired
	Validator					validator;


	public Collection<Section> getSectionByActivity(final int id) {
		return this.sectionRepository.getSectionByActivity(id);
	}

	public Section findOne(final int id) {
		return this.sectionRepository.findOne(id);
	}

	public Section create(final int idActivity) {
		Assert.isTrue(AuthorityMethods.chechAuthorityLogged("ADMINISTRATOR"));
		Assert.isTrue(this.activityService.findOne(idActivity).getType().equals("TUTORIAL"));
		Assert.isTrue(this.activityService.findOne(idActivity).getConference().getAdministrator().equals(this.conferenceService.findByPrincipal(LoginService.getPrincipal())));
		final Section section = new Section();
		section.setActivity(this.activityService.findOne(idActivity));
		section.setTitle("");
		section.setSummary("");
		section.setPictures(new ArrayList<String>());
		return section;
	}

	public Section save(final Section section) {
		Assert.isTrue(AuthorityMethods.chechAuthorityLogged("ADMINISTRATOR"));
		return this.sectionRepository.save(section);
	}

	public void delete(final int id) {
		Assert.isTrue(AuthorityMethods.chechAuthorityLogged("ADMINISTRATOR"));
		this.sectionRepository.delete(this.sectionRepository.findOne(id));
	}

	public Section reconstruct(final Section section, final BindingResult binding) throws ParseException {
		Section sectionRec;
		sectionRec = section;

		if (!sectionRec.getPictures().isEmpty()) {
			final Collection<String> col = sectionRec.getPictures();
			for (final String s : col)
				if (s.startsWith("https:") || s.startsWith("http:")) {

				} else
					binding.rejectValue("pictures", "section.pictures.bad");
		}

		this.validator.validate(sectionRec, binding);
		if (binding.hasErrors())
			throw new ValidationException();

		return sectionRec;

	}

	public void flush() {
		this.sectionRepository.flush();
	}

}
