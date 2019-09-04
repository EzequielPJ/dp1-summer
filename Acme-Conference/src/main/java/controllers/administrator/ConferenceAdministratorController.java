
package controllers.administrator;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import javax.servlet.http.HttpServletResponse;
import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import services.ActorService;
import services.AdministratorService;
import services.CategoryService;
import services.ConferenceService;
import services.PaperService;
import services.QuoletService;
import services.SubmissionService;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.List;
import com.itextpdf.text.ListItem;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.ZapfDingbatsList;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;

import controllers.AbstractController;
import domain.Actor;
import domain.Administrator;
import domain.Author;
import domain.Conference;
import domain.Paper;
import domain.Quolet;

@Controller
@RequestMapping("/conference/administrator")
public class ConferenceAdministratorController extends AbstractController {

	@Autowired
	private ActorService			actorService;

	@Autowired
	private QuoletService			quoletService;

	@Autowired
	private ConferenceService		conferenceService;

	@Autowired
	private CategoryService			categoryService;

	@Autowired
	private SubmissionService		submissionService;

	@Autowired
	private AdministratorService	adminService;

	@Autowired
	private PaperService			paperService;

	DateFormat						dateFormat	= new SimpleDateFormat("yyyy/MM/dd hh:mm");


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@CookieValue(value = "language", required = false) final String lang) {
		final ModelAndView result = this.listModelAndView();
		final Collection<Conference> myConferences = this.conferenceService.getYoursConference(this.conferenceService.findByPrincipal(LoginService.getPrincipal()).getId());
		result.addObject("conferences", myConferences);
		result.addObject("requestURI", "conference/administrator/list.do");
		result.addObject("categories", this.categoryService.findAll());
		result.addObject("sel", true);
		if (lang == null)
			result.addObject("lang", "en");
		else
			result.addObject("lang", lang);

		return result;
	}

	@RequestMapping(value = "/listByCategory", method = RequestMethod.GET)
	public ModelAndView listByCatgeory(@RequestParam final int idCategory, @CookieValue(value = "language", required = false) final String lang) {
		final ModelAndView result = this.listModelAndView();
		final Collection<Conference> conferenceList = this.conferenceService.getYoursConferenceByCategory(this.conferenceService.findByPrincipal(LoginService.getPrincipal()).getId(), idCategory);
		result.addObject("conferences", conferenceList);
		result.addObject("requestURI", "conference/administrator/listByCategory.do?idCategory=" + idCategory);
		result.addObject("categories", this.categoryService.findAll());
		result.addObject("sel", true);
		if (lang == null)
			result.addObject("lang", "en");
		else
			result.addObject("lang", lang);
		return result;
	}

	@RequestMapping(value = "/listSubmissionDeadline", method = RequestMethod.GET)
	public ModelAndView listSubmissionDeadline(@CookieValue(value = "language", required = false) final String lang) {
		final ModelAndView result = this.listModelAndView();

		final Administrator adm = this.conferenceService.findByPrincipal(LoginService.getPrincipal());

		final Date date1 = new Date();
		final Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.DAY_OF_YEAR, -6);
		final Date date2 = calendar.getTime();

		final ArrayList<Conference> myConferencesF = new ArrayList<Conference>();
		final Collection<Conference> myConferences = this.conferenceService.getConferencesBetweenSubmissionDeadline(date1, date2, adm.getId());
		for (final Conference conf : myConferences)
			if (conf.getAdministrator().equals(this.conferenceService.findByPrincipal(LoginService.getPrincipal())))
				myConferencesF.add(conf);
		result.addObject("conferences", myConferencesF);
		result.addObject("requestURI", "conference/administrator/listSubmissionDeadline.do");
		if (lang == null) {
			result.addObject("lang", "en");
			result.addObject("tit", "Conference whose submission deadline elapsed in the last five days");
		} else {
			result.addObject("lang", lang);
			result.addObject("tit", "Conferencias cuya fecha de presentación haya vencido en los últimos 5 días");
		}
		return result;
	}
	@RequestMapping(value = "/listNotificationDeadline", method = RequestMethod.GET)
	public ModelAndView listNotificationDeadline(@CookieValue(value = "language", required = false) final String lang) {
		final ModelAndView result = this.listModelAndView();

		final Administrator adm = this.conferenceService.findByPrincipal(LoginService.getPrincipal());

		final Date date1 = new Date();
		final Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.DAY_OF_YEAR, -5);
		final Date date2 = calendar.getTime();

		final ArrayList<Conference> myConferencesF = new ArrayList<Conference>();
		final Collection<Conference> myConferences = this.conferenceService.getConferencesBetweenNotificationDeadline(date1, date2, adm.getId());
		for (final Conference conf : myConferences)
			if (conf.getAdministrator().equals(this.conferenceService.findByPrincipal(LoginService.getPrincipal())))
				myConferencesF.add(conf);
		result.addObject("conferences", myConferencesF);
		result.addObject("requestURI", "conference/administrator/listNotificationDeadline.do");
		if (lang == null) {
			result.addObject("lang", "en");
			result.addObject("tit", "Conference whose notification deadline elapsed in less than five days");
		} else {
			result.addObject("lang", lang);
			result.addObject("tit", "Conferencias cuya fecha de notificaión expire en menos de 5 días");
		}
		return result;
	}

	@RequestMapping(value = "/listCameraReadyDeadline", method = RequestMethod.GET)
	public ModelAndView listCameraReadyDeadline(@CookieValue(value = "language", required = false) final String lang) {
		final ModelAndView result = this.listModelAndView();

		final Administrator adm = this.conferenceService.findByPrincipal(LoginService.getPrincipal());

		final Date date1 = new Date();
		final Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.DAY_OF_YEAR, -5);
		final Date date2 = calendar.getTime();

		final ArrayList<Conference> myConferencesF = new ArrayList<Conference>();
		final Collection<Conference> myConferences = this.conferenceService.getConferencesBetweenCameraReadyDeadline(date1, date2, adm.getId());
		for (final Conference conf : myConferences)
			if (conf.getAdministrator().equals(this.conferenceService.findByPrincipal(LoginService.getPrincipal())))
				myConferencesF.add(conf);
		result.addObject("conferences", myConferencesF);
		result.addObject("requestURI", "conference/administrator/listCameraReadyDeadline.do");
		if (lang == null) {
			result.addObject("lang", "en");
			result.addObject("tit", "Conference whose camera-ready deadline elapsed in less than five days");
		} else {
			result.addObject("lang", lang);
			result.addObject("tit", "Conferencias cuya fecha de presentación final expire en menos de 5 días");
		}
		return result;
	}

	@RequestMapping(value = "/listNextConference", method = RequestMethod.GET)
	public ModelAndView listNextConference(@CookieValue(value = "language", required = false) final String lang) {
		final ModelAndView result = this.listModelAndView();

		final Administrator adm = this.conferenceService.findByPrincipal(LoginService.getPrincipal());

		final Date date1 = new Date();
		final Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.DAY_OF_YEAR, 5);
		final Date date2 = calendar.getTime();

		final ArrayList<Conference> myConferencesF = new ArrayList<Conference>();
		final Collection<Conference> myConferences = this.conferenceService.getConferencesBetweenStartDate(date1, date2, adm.getId());
		for (final Conference conf : myConferences)
			if (conf.getAdministrator().equals(this.conferenceService.findByPrincipal(LoginService.getPrincipal())))
				myConferencesF.add(conf);
		result.addObject("conferences", myConferencesF);
		result.addObject("requestURI", "conference/administrator/listNextConference.do");
		if (lang == null) {
			result.addObject("lang", "en");
			result.addObject("tit", "Conference that are going to be organised in less than five days");
		} else {
			result.addObject("lang", lang);
			result.addObject("tit", "Conferencias que se organizarán en menos de 5 días");
		}
		return result;
	}

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int idConference, @CookieValue(value = "language", required = false) final String lang, @RequestParam final String url) {
		ModelAndView result = null;

		final Conference conference = this.conferenceService.findOne(idConference);

		if (!conference.getAdministrator().equals(this.conferenceService.findByPrincipal(LoginService.getPrincipal())))
			result = new ModelAndView("redirect:list.do");
		else
			result = new ModelAndView("conference/display");

		result.addObject("conference", conference);
		result.addObject("requestURI", "conference/administrator/display.do?idConference=" + idConference);
		result.addObject("url", url);

		if (conference.getCameraReadyDeadline().after(new Date()))
			result.addObject("showButton", true);

		if (lang == null)
			result.addObject("lang", "en");
		else
			result.addObject("lang", lang);

		//CONTROL CHECK QUOLET/////////////////////////

		Collection<Quolet> quolets = new ArrayList<>();
		final Actor actorLogged = this.actorService.findByUserAccount(LoginService.getPrincipal());
		if (conference.getAdministrator().getId() == actorLogged.getId())
			quolets = this.quoletService.getQuoletsOfConferenceAll(idConference);
		else
			quolets = this.quoletService.getQuoletsOfConferenceFinalMode(idConference);

		result.addObject("quolets", quolets);

		result.addObject("bot", true);

		final Date currentDate = new Date();
		final Date aMonthAgo = new Date(currentDate.getTime() - 2629746000l);
		result.addObject("aMonthAgo", aMonthAgo);
		final Date twoMonthAgo = new Date(currentDate.getTime() - 5259492000l);
		result.addObject("twoMonthAgo", twoMonthAgo);

		////////////////////////////////

		this.configValues(result);
		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@CookieValue(value = "language", required = false) final String lang) {
		ModelAndView result;
		final Conference conference = this.conferenceService.create();
		result = this.createEditModelAndView(conference);
		if (lang == null)
			result.addObject("lang", "en");
		else
			result.addObject("lang", lang);
		result.addObject("categoriesList", this.categoryService.findAll());
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam(required = true) final int idConference, @CookieValue(value = "language", required = false) final String lang) {
		ModelAndView result;

		final Conference conference = this.conferenceService.findOne(idConference);

		if (!conference.getAdministrator().equals(this.conferenceService.findByPrincipal(LoginService.getPrincipal())))
			result = this.listModelAndView("security.error.accessDenied");
		else if (conference.getFinalMode() == true)
			result = this.listModelAndView("security.error.accessDenied");
		else
			result = this.createEditModelAndView(conference);

		if (lang == null)
			result.addObject("lang", "en");
		else
			result.addObject("lang", lang);
		result.addObject("categoriesList", this.categoryService.findAll());
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(final Conference conference, final BindingResult binding, @CookieValue(value = "language", required = false) final String lang) {
		ModelAndView result;
		try {
			final Conference conferenceRect = this.conferenceService.reconstruct(conference, binding);
			this.conferenceService.save(conferenceRect);
			result = new ModelAndView("redirect:list.do");
		} catch (final ValidationException oops) {
			result = this.createEditModelAndView(conference);
			if (lang == null)
				result.addObject("lang", "en");
			else
				result.addObject("lang", lang);
			result.addObject("categoriesList", this.categoryService.findAll());
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(conference, "conference.edit.commit.error");
			if (lang == null)
				result.addObject("lang", "en");
			else
				result.addObject("lang", lang);
			result.addObject("categoriesList", this.categoryService.findAll());
		}
		return result;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam(required = true) final int idConference) {
		ModelAndView result;
		try {
			if (this.conferenceService.findOne(idConference).getFinalMode() == true)
				result = this.listModelAndView("security.error.accessDenied");
			else if (!this.conferenceService.findOne(idConference).getAdministrator().equals(this.conferenceService.findByPrincipal(LoginService.getPrincipal())))
				result = this.listModelAndView("security.error.accessDenied");
			else {
				this.conferenceService.delete(idConference);
				result = new ModelAndView("redirect:list.do");
			}
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:list.do");
		}
		return result;
	}

	@RequestMapping(value = "/decisionMaking", method = RequestMethod.GET)
	public ModelAndView conferenceDecisionMaking(@RequestParam final int idConference) {
		ModelAndView result;

		try {
			if (!this.conferenceService.getConferencesToDecisionMaking().contains(this.conferenceService.findOne(idConference)))
				result = this.listModelAndView("decisionMaking.commit.error");
			else {
				this.submissionService.conferenceDecisionMaking(idConference);
				result = new ModelAndView("redirect:list.do");
			}
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:list.do");
		}
		return result;

	}

	@RequestMapping(value = "/assignReviewers", method = RequestMethod.GET)
	public ModelAndView assignReviewers(@RequestParam final int idConference) {
		ModelAndView result;
		try {
			if (!this.conferenceService.getConferencesToAssingReviewers().contains(this.conferenceService.findOne(idConference)))
				result = this.listModelAndView("decisionMaking.commit.error");
			else {
				this.submissionService.assignReviewers(idConference);
				result = new ModelAndView("redirect:list.do");
			}
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:list.do");
		}
		return result;

	}

	protected ModelAndView createEditModelAndView(final Conference conference) {
		return this.createEditModelAndView(conference, null);
	}

	protected ModelAndView createEditModelAndView(final Conference conference, final String message) {
		final ModelAndView result = new ModelAndView("conference/edit");

		result.addObject("conference", conference);
		result.addObject("message", message);

		this.configValues(result);

		return result;
	}

	protected ModelAndView listModelAndView() {
		return this.listModelAndView(null);
	}

	protected ModelAndView listModelAndView(final String message) {
		final ModelAndView result = new ModelAndView("conference/list");
		final Collection<Conference> conferencesToDecisionMaking = this.conferenceService.getConferencesToDecisionMaking();
		final Collection<Conference> conferencesToAssingReviewers = this.conferenceService.getConferencesToAssingReviewers();
		final Collection<Conference> myConferencesF = this.conferenceService.getYoursConference(this.conferenceService.findByPrincipal(LoginService.getPrincipal()).getId());

		result.addObject("message", message);
		result.addObject("conferences", myConferencesF);
		result.addObject("conferencesToDecisionMaking", conferencesToDecisionMaking);
		result.addObject("conferencesToAssingReviewers", conferencesToAssingReviewers);

		this.configValues(result);

		return result;
	}

	/**
	 * @param idConference
	 * @param response
	 * @return
	 * @throws DocumentException
	 * @throws IOException
	 */
	@RequestMapping(value = "/downloadConference", method = RequestMethod.GET)
	public void downloadBook(@RequestParam final int idConference, final HttpServletResponse response) throws DocumentException, IOException {
		final Conference conference = this.conferenceService.findOne(idConference);

		final Administrator administratorLogged = this.adminService.findByPrincipal(LoginService.getPrincipal());
		if (conference.getAdministrator().equals(administratorLogged) && conference.getCameraReadyDeadline().after(new Date())) {

			response.setContentType("application/pdf");

			final Document doc = new Document();

			final PdfWriter pdfWriter = PdfWriter.getInstance(doc, response.getOutputStream());
			doc.open();
			final BaseFont helvetica = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.EMBEDDED);

			//Portada del documento: Datos de la conferencia
			doc.add(Chunk.NEWLINE);
			final Font fontTitle = new Font(helvetica, 24, Font.BOLD);
			final Paragraph paragraphTitle = new Paragraph(conference.getTitle(), fontTitle);
			paragraphTitle.setAlignment(Element.ALIGN_CENTER);
			doc.add(paragraphTitle);
			doc.add(Chunk.NEWLINE);
			doc.add(Chunk.NEWLINE);

			final Image image = Image.getInstance("https://i.imgur.com/Ew8PhPu.png");
			image.setAlignment(Element.ALIGN_BOTTOM | Element.ALIGN_CENTER | Image.TEXTWRAP);
			image.scaleAbsolute(200, 200);
			//Revisar el tamanio de la portada
			doc.add(image);
			doc.add(Chunk.NEWLINE);

			final Font fontDate = new Font(helvetica, 20, Font.BOLD);
			final String date = this.dateFormat.format(conference.getStartDate()) + " - " + this.dateFormat.format(conference.getEndDate());
			final Paragraph paragraphDate = new Paragraph(date, fontDate);
			paragraphDate.setAlignment(Element.ALIGN_CENTER);
			doc.add(paragraphDate);
			doc.add(Chunk.NEWLINE);

			final Font fontPlace = new Font(helvetica, 14, Font.NORMAL);
			final String place = conference.getVenue() + " (" + conference.getFee() + "$)";
			final Paragraph paragraphPlace = new Paragraph(place, fontPlace);
			paragraphPlace.setAlignment(Element.ALIGN_CENTER);
			doc.add(paragraphPlace);

			doc.newPage();

			//Papers
			final Collection<Paper> papers = this.paperService.getPaperCamerReadyVersionOfConference(conference.getId());

			//Indice
			final Font fontTitleIndex = new Font(helvetica, 22, (Font.BOLD | Font.UNDERLINE));
			final Paragraph paragraphTitleIndex = new Paragraph("Index:", fontTitleIndex);
			paragraphTitleIndex.setAlignment(Element.ALIGN_LEFT);
			doc.add(paragraphTitleIndex);
			doc.add(Chunk.NEWLINE);
			final List indice = new List(true);
			for (final Paper paper : papers) {
				final ListItem indicePaper = new ListItem(paper.getTitle());
				indice.add(indicePaper);
			}

			doc.add(indice);
			doc.newPage();

			final Font fontTitlePaper = new Font(helvetica, 20, Font.UNDERLINE);
			final Font fontPaper = new Font(helvetica, 12);
			final Font fontPaperURL = new Font(helvetica, 12, Font.UNDERLINE, new BaseColor(0, 191, 255));
			final Font fontSubTitle = new Font(helvetica, 14, Font.BOLD);
			for (final Paper paper : papers) {
				doc.add(Chunk.NEWLINE);

				final Paragraph paragraphPaperTitle = new Paragraph(paper.getTitle(), fontTitlePaper);
				paragraphPaperTitle.setAlignment(Element.ALIGN_CENTER);
				doc.add(paragraphPaperTitle);
				doc.add(Chunk.NEWLINE);

				final Paragraph paragraphAuthorsTitle = new Paragraph("Authors:", fontSubTitle);
				paragraphAuthorsTitle.setAlignment(Element.ALIGN_JUSTIFIED);
				doc.add(paragraphAuthorsTitle);

				final ZapfDingbatsList authors = new ZapfDingbatsList(108);
				for (final Author author : paper.getAuthors()) {
					final ListItem authorIndex = new ListItem(author.getName() + " " + author.getMiddlename() + " " + author.getSurname() + " (" + author.getEmail() + ")", fontPaper);
					authors.add(authorIndex);
				}

				for (final String alias : paper.getAliases()) {
					final ListItem authorIndex = new ListItem(alias, fontPaper);
					authors.add(authorIndex);
				}

				authors.setIndentationLeft(45f);
				doc.add(authors);
				doc.add(Chunk.NEWLINE);

				final Paragraph paragraphSummaryTitle = new Paragraph("Summary:", fontSubTitle);
				paragraphSummaryTitle.setAlignment(Element.ALIGN_JUSTIFIED);
				doc.add(paragraphSummaryTitle);

				final Paragraph summary = new Paragraph(paper.getSummary(), fontPaper);
				summary.setIndentationLeft(45f);
				summary.setAlignment(Element.ALIGN_JUSTIFIED);
				doc.add(summary);

				doc.add(Chunk.NEWLINE);

				final Chunk urlDoc = new Chunk("Go to the document.", fontPaperURL);
				urlDoc.setAnchor(paper.getDocumentUrl());
				doc.add(urlDoc);

				doc.newPage();
			}

			doc.close();
			pdfWriter.close();
		}

	}
}
