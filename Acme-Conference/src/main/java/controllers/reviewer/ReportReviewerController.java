
package controllers.reviewer;

import java.util.Collection;

import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import services.ReportService;
import services.ReviewerService;
import services.SubmissionService;
import controllers.AbstractController;
import domain.Report;
import domain.Reviewer;
import domain.Submission;

@Controller
@RequestMapping("/report/reviewer/")
public class ReportReviewerController extends AbstractController {

	@Autowired
	private ReportService		reportService;

	@Autowired
	private ReviewerService		reviewerService;

	@Autowired
	private SubmissionService	submissionService;


	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView displayReport(@RequestParam final int reportId, @RequestParam final String backUri) {
		return this.displayModelAndView(reportId, backUri);
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final Reviewer reviewer = this.reviewerService.findByPrincipal(LoginService.getPrincipal());
		return this.listReportsByReviewerModelAndView(reviewer.getId());
	}
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int submissionId) {
		return this.createModelAndView(this.submissionService.findOne(submissionId));
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST, params = "save")
	public ModelAndView save(final Report report, final BindingResult binding) {
		ModelAndView result;
		result = new ModelAndView("report/edit");

		try {
			final Report reportFinal = this.reportService.reconstruct(report, binding);
			final Report res = this.reportService.save(reportFinal);
			result = new ModelAndView("redirect:display.do?reportId=" + res.getId());
		} catch (final ValidationException oops) {
			result = this.createModelAndView(report);
		} catch (final Throwable oops) {
			oops.printStackTrace();
			result = this.createModelAndView(report, "report.save.error");
		}

		return result;
	}

	protected ModelAndView createModelAndView(final Submission submission) {
		final ModelAndView res = new ModelAndView("report/edit");

		final Report report = this.reportService.create(submission);

		res.addObject("report", report);

		this.configValues(res);
		return res;
	}

	protected ModelAndView createModelAndView(final Report report, final String... strings) {
		final ModelAndView res = new ModelAndView("report/edit");

		res.addObject("report", report);
		if (strings.length > 0)
			res.addObject("message", strings[0]);

		this.configValues(res);
		return res;
	}

	protected ModelAndView listReportsByReviewerModelAndView(final int reviewerId) {

		final ModelAndView res = new ModelAndView("report/list");

		final Collection<Report> reports = this.reportService.findReportsByReviewer(reviewerId);

		res.addObject("reports", reports);
		res.addObject("requestURI", "report/reviewer/list.do");

		this.configValues(res);
		return res;

	}

	protected ModelAndView displayModelAndView(final int reportId, final String backUri) {

		final ModelAndView res = new ModelAndView("report/display");

		final Report report = this.reportService.findOne(reportId);

		res.addObject("report", report);

		res.addObject("backUri", backUri);

		this.configValues(res);
		return res;

	}

}
