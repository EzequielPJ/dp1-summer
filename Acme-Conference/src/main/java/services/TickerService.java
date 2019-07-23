
package services;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import repositories.TickerRepository;
import domain.Author;
import domain.Ticker;

@Service
@Transactional(value = TxType.REQUIRES_NEW)
public class TickerService {

	@Autowired
	private TickerRepository	tickerRepository;


	public Ticker generateTicker(final Author author) throws NoSuchAlgorithmException {
		final Ticker ticker = new Ticker();
		Ticker result;
		String identifier;

		//Codigo con las iniciales
		final char initialName = author.getName().toUpperCase().charAt(0);
		final char initialMiddlename = author.getMiddlename() == null ? 'X' : author.getMiddlename().toUpperCase().charAt(0);
		final char initialSurname = author.getSurname().toUpperCase().charAt(0);
		identifier = initialName + initialMiddlename + initialSurname + "-";

		//Cadena alfanumerica
		final SecureRandom randomGenerator = SecureRandom.getInstance("SHA1PRNG");
		final String alfanumerica = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		for (int i = 0; i < 4; i++) {
			final Integer randomNumber = randomGenerator.nextInt(alfanumerica.length());
			identifier = identifier + alfanumerica.charAt(randomNumber);
		}

		ticker.setIdentifier(identifier);
		result = this.tickerRepository.saveAndFlush(ticker);

		return result;
	}

	public void delete(final Ticker ticker) {
		this.tickerRepository.delete(ticker);

	}

}
