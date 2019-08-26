
package controllers.author;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ReportService;
import controllers.AbstractController;
import domain.Report;

@Controller
@RequestMapping("/report/author")
public class ReportAuthorController extends AbstractController {

	@Autowired
	private ReportService	reportService;


	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView displayReport(@RequestParam final int reportId) {
		return this.displayModelAndView(reportId);
	}

	protected ModelAndView displayModelAndView(final int reportId) {

		final ModelAndView res = new ModelAndView("report/display");

		final Report report = this.reportService.findOne(reportId);

		res.addObject("report", report);
		res.addObject("requestURI", "report/author/display.do");

		this.configValues(res);
		return res;

	}

}
