
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import services.AuthorService;
import domain.Author;

@Component
@Transactional
public class StringToAuthorConverter implements Converter<String, Author> {

	@Autowired
	private AuthorService	authorService;


	@Override
	public Author convert(final String text) {
		Author result;
		final int id;

		try {
			if (StringUtils.isEmpty(text))
				result = null;
			else {
				id = Integer.valueOf(text);
				result = this.authorService.getAuthor(id);

			}
		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}
		return result;
	}
}
