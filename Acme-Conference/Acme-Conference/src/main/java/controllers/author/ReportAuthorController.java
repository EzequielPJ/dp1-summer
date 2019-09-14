
package controllers.author;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import services.AuthorService;
import services.ReportService;
import controllers.AbstractController;
import domain.Author;
import domain.Report;

@Controller
@RequestMapping("/report/author")
public class ReportAuthorController extends AbstractController {

	@Autowired
	private ReportService	reportService;

	@Autowired
	private AuthorService	authorService;


	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView displayReport(@RequestParam final int reportId, @RequestParam final String backUri) {
		return this.displayModelAndView(reportId, backUri);
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView listReports() {
		final Author a = this.authorService.findByPrincipal(LoginService.getPrincipal());

		return this.listModelAndView(a);
	}

	protected ModelAndView displayModelAndView(final int reportId, final String backUri) {

		final ModelAndView res = new ModelAndView("report/display");

		final Report report = this.reportService.findOne(reportId);

		res.addObject("report", report);
		res.addObject("requestURI", "report/author/display.do");
		res.addObject("backUri", backUri);

		this.configValues(res);
		return res;

	}

	protected ModelAndView listModelAndView(final Author author) {

		final ModelAndView res = new ModelAndView("report/list");

		final Collection<Report> reports = this.reportService.findReportsByAuthor(author.getId());

		res.addObject("reports", reports);
		this.configValues(res);

		return res;

	}

}
