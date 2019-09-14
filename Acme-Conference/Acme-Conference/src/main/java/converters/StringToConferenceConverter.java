
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import services.ConferenceService;
import domain.Conference;

@Component
@Transactional
public class StringToConferenceConverter implements Converter<String, Conference> {

	@Autowired
	private ConferenceService	conferenceService;


	@Override
	public Conference convert(final String text) {
		final Conference result;
		final int id;

		try {
			if (StringUtils.isEmpty(text))
				result = null;
			else {
				id = Integer.valueOf(text);
				result = this.conferenceService.findOne(id);
			}
		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}
		return result;
	}

}
