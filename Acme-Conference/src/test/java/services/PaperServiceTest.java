
package services;

import java.util.Collection;
import java.util.HashSet;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import utilities.AbstractTest;
import domain.Author;
import domain.Paper;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class PaperServiceTest extends AbstractTest {

	@Autowired
	SubmissionService	submissionService;

	@Autowired
	PaperService		paperService;

	@Autowired
	AuthorService		authorService;


	//Test de crear un documento en version preaprado para cámara
	@Test
	public void savePaperDriver() {

		final Object testingData[][] = {
			{

				"author0", "submission0", null
			}, {
				"author0", "submission2", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.savePaperTemplate((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);
	}
	protected void savePaperTemplate(final String actor, final String submission, final Class<?> expected) {
		Class<?> caught;
		caught = null;

		try {
			super.authenticate(actor);
			final Paper paper = this.paperService.createPaper(this.getEntityId(submission));
			paper.setTitle("Documento de prueba");
			paper.setCameraReadyPaper(true);
			paper.setDocumentUrl("http://www.prueba.es");
			paper.setSummary("Prueba");

			final Collection<Author> authors = new HashSet<>();
			authors.add(this.authorService.findOne(this.getEntityId(actor)));
			paper.setAuthors(authors);
			this.paperService.save(paper);
			this.paperService.flush();
			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}
}
