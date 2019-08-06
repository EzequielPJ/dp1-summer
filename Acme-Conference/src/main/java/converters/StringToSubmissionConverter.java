
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import services.SubmissionService;
import domain.Submission;

@Component
@Transactional
public class StringToSubmissionConverter implements Converter<String, Submission> {

	@Autowired
	private SubmissionService	submissionService;


	@Override
	public Submission convert(final String text) {
		final Submission result;
		final int id;

		try {
			if (StringUtils.isEmpty(text))
				result = null;
			else {
				id = Integer.valueOf(text);
				result = this.submissionService.findOne(id);
			}
		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}
		return result;
	}

}
