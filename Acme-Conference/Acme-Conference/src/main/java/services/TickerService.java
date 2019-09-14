
package services;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

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

	DateFormat					dateFormat		= new SimpleDateFormat("ddMMyy");

	DateFormat					dateFormatCC	= new SimpleDateFormat("MMddyy");


	public Ticker generateTicker(final Author author) throws NoSuchAlgorithmException {
		final Ticker ticker = new Ticker();
		Ticker result;
		String identifier;

		//Codigo con las iniciales
		final Character initialName = author.getName().toUpperCase().charAt(0);
		final Character initialMiddlename = author.getMiddlename() == null ? 'X' : author.getMiddlename().toUpperCase().charAt(0);
		final Character initialSurname = author.getSurname().toUpperCase().charAt(0);
		identifier = initialName.toString() + initialMiddlename.toString() + initialSurname.toString() + "-";

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

	public Ticker generateTickerGomela() throws NoSuchAlgorithmException {
		final Ticker ticker = new Ticker();
		Ticker result;
		final String identifier;

		//DOSLETRASDOSDIGITOS-MMddyy

		//Date
		final Date fechaActual = new Date();
		final String fecha = this.dateFormatCC.format(fechaActual);

		//Cadena alfanumerica
		final SecureRandom randomGenerator = SecureRandom.getInstance("SHA1PRNG");
		final String alfanumerica = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		String parteAlfanumerica = "";
		for (int i = 0; i < 2; i++) {
			final Integer randomNumber = randomGenerator.nextInt(alfanumerica.length());
			parteAlfanumerica = parteAlfanumerica + alfanumerica.charAt(randomNumber);
		}

		//Cadena numerica
		final SecureRandom randomGeneratorNum = SecureRandom.getInstance("SHA1PRNG");
		final String numerica = "0123456789";
		String parteNumerica = "";
		for (int i = 0; i < 2; i++) {
			final Integer randomNumber = randomGeneratorNum.nextInt(numerica.length());
			parteNumerica = parteNumerica + numerica.charAt(randomNumber);
		}

		identifier = parteAlfanumerica + parteNumerica + "-" + fecha;

		ticker.setIdentifier(identifier);
		result = this.tickerRepository.saveAndFlush(ticker);

		return result;
	}

	public void delete(final Ticker ticker) {
		this.tickerRepository.delete(ticker);

	}

}