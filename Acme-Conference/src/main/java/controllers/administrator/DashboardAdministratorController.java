// //
// package controllers.administrator;
//
// import java.util.Collection;
// import java.util.List;
//
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.stereotype.Controller;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RequestMethod;
// import org.springframework.web.servlet.ModelAndView;
//
// import services.AdministratorService;
// import services.ContestService;
// import services.SponsorService;
// import services.WriterService;
// import controllers.AbstractController;
// import domain.Contest;
// import domain.Sponsor;
// import domain.Writer;
//
// @Controller
// @RequestMapping("/dashboard")
// public class DashboardAdministratorController extends AbstractController {
//
// @Autowired
// private AdministratorService administratorService;
//
// @Autowired
// private WriterService writerService;
//
// @Autowired
// private ContestService contestService;
//
// @Autowired
// private SponsorService sponsorService;
//
//
// @RequestMapping(value = "/administrator/display", method = RequestMethod.GET)
// public ModelAndView display() {
// final ModelAndView result = new ModelAndView("administrator/dashboard");
//
// //----------------------------------------------------------------
//
// final Double avgOfBooksPerWriter = this.administratorService.getAvgOfBooksPerWriter();
// if (avgOfBooksPerWriter != null)
// result.addObject("avgOfBooksPerWriter", avgOfBooksPerWriter);
// else
// result.addObject("avgOfBooksPerWriter", 0.0);
//
// final Integer minimumOfBooksPerWriter = this.administratorService.getMinimumOfBooksPerWriter();
// if (minimumOfBooksPerWriter != null)
// result.addObject("minimumOfBooksPerWriter", minimumOfBooksPerWriter);
// else
// result.addObject("minimumOfBooksPerWriter", 0);
//
// final Integer maximumOfBooksPerWriter = this.administratorService.getMaximumOfBooksPerWriter();
// if (maximumOfBooksPerWriter != null)
// result.addObject("maximumOfBooksPerWriter", maximumOfBooksPerWriter);
// else
// result.addObject("maximumOfBooksPerWriter", 0);
//
// final Double sDOfBooksPerWriter = this.administratorService.getSDOfBooksPerWriter();
// if (sDOfBooksPerWriter != null)
// result.addObject("sDOfBooksPerWriter", sDOfBooksPerWriter);
// else
// result.addObject("sDOfBooksPerWriter", 0.0);
//
// //----------------------------------------------------------------
//
// final Double avgOfContestPerPublisher = this.administratorService.getAvgOfContestPerPublisher();
// if (avgOfContestPerPublisher != null)
// result.addObject("avgOfContestPerPublisher", avgOfContestPerPublisher);
// else
// result.addObject("avgOfContestPerPublisher", 0.0);
//
// final Integer minimumOfContestPerPublisher = this.administratorService.getMinimumOfContestPerPublisher();
// if (minimumOfContestPerPublisher != null)
// result.addObject("minimumOfContestPerPublisher", minimumOfContestPerPublisher);
// else
// result.addObject("minimumOfContestPerPublisher", 0);
//
// final Integer maximumOfContestPerPublisher = this.administratorService.getMaximumOfContestPerPublisher();
// if (maximumOfContestPerPublisher != null)
// result.addObject("maximumOfContestPerPublisher", maximumOfContestPerPublisher);
// else
// result.addObject("maximumOfContestPerPublisher", 0);
//
// final Double sDOfContestPerPublisher = this.administratorService.getSDOfContestPerPublisher();
// if (sDOfContestPerPublisher != null)
// result.addObject("sDOfContestPerPublisher", sDOfContestPerPublisher);
// else
// result.addObject("sDOfContestPerPublisher", 0.0);
//
// //----------------------------------------------------------------
//
// final Double ratioOfBooksWithPublisherVsBooksIndependients = this.administratorService.getRatioOfBooksWithPublisherVsBooksIndependients();
// if (ratioOfBooksWithPublisherVsBooksIndependients != null)
// result.addObject("ratioOfBooksWithPublisherVsBooksIndependients", ratioOfBooksWithPublisherVsBooksIndependients);
// else
// result.addObject("ratioOfBooksWithPublisherVsBooksIndependients", 0.0);
//
// //-----------------------------------------------------------------x
// final Double ratioOfBooksAcceptedVsBooksRejected = this.administratorService.getRatioOfBooksAcceptedVsBooksRejected();
// if (ratioOfBooksAcceptedVsBooksRejected != null)
// result.addObject("ratioOfBooksAcceptedVsBooksRejected", ratioOfBooksAcceptedVsBooksRejected);
// else
// result.addObject("ratioOfBooksAcceptedVsBooksRejected", 0.0);
//
// //-----------------------------------------------------------------
//
// final Double avgOfChaptersPerBook = this.administratorService.getAvgOfChaptersPerBook();
// if (avgOfChaptersPerBook != null)
// result.addObject("avgOfChaptersPerBook", avgOfChaptersPerBook);
// else
// result.addObject("avgOfChaptersPerBook", 0.0);
//
// final Integer minimumOfChaptersPerBook = this.administratorService.getMinimumOfChaptersPerBook();
// if (minimumOfChaptersPerBook != null)
// result.addObject("minimumOfChaptersPerBook", minimumOfChaptersPerBook);
// else
// result.addObject("minimumOfChaptersPerBook", 0);
//
// final Integer maximumOfChaptersPerBook = this.administratorService.getMaximumOfChaptersPerBook();
// if (maximumOfChaptersPerBook != null)
// result.addObject("maximumOfChaptersPerBook", maximumOfChaptersPerBook);
// else
// result.addObject("maximumOfChaptersPerBook", 0);
//
// final Double sDOfChaptersPerBook = this.administratorService.getSDOfChaptersPerBook();
// if (sDOfChaptersPerBook != null)
// result.addObject("sDOfChaptersPerBook", sDOfChaptersPerBook);
// else
// result.addObject("sDOfChaptersPerBook", 0.0);
//
// //-------------------------------------------------------------------------
// final List<Object[]> histogramData = this.administratorService.getHistogramData();
// result.addObject("histogramData", histogramData);
//
// //-----------------------------------------------------------------
//
// final Double avgOfSponsorshipsPerSponsor = this.administratorService.getAvgOfSponsorshipsPerSponsor();
// if (avgOfSponsorshipsPerSponsor != null)
// result.addObject("avgOfSponsorshipsPerSponsor", avgOfSponsorshipsPerSponsor);
// else
// result.addObject("avgOfSponsorshipsPerSponsor", 0.0);
//
// final Integer minimumOfSponsorshipsPerSponsor = this.administratorService.getMinimumOfSponsorshipsPerSponsor();
// if (minimumOfSponsorshipsPerSponsor != null)
// result.addObject("minimumOfSponsorshipsPerSponsor", minimumOfSponsorshipsPerSponsor);
// else
// result.addObject("minimumOfSponsorshipsPerSponsor", 0);
//
// final Integer maximumOfSponsorshipsPerSponsor = this.administratorService.getMaximumOfSponsorshipsPerSponsor();
// if (maximumOfSponsorshipsPerSponsor != null)
// result.addObject("maximumOfSponsorshipsPerSponsor", maximumOfSponsorshipsPerSponsor);
// else
// result.addObject("maximumOfSponsorshipsPerSponsor", 0);
//
// final Double sDOfSponsorshipsPerSponsor = this.administratorService.getSDOfSponsorshipsPerSponsor();
// if (sDOfSponsorshipsPerSponsor != null)
// result.addObject("sDOfSponsorshipsPerSponsor", sDOfSponsorshipsPerSponsor);
// else
// result.addObject("sDOfSponsorshipsPerSponsor", 0.0);
//
// //-----------------------------------------------------------------
//
// final Double ratioOfSponsorshipsCancelledVsSponsorshipsNotCancelled = this.administratorService.getRatioOfSponsorshipsCancelledVsSponsorshipsNotCancelled();
// if (ratioOfSponsorshipsCancelledVsSponsorshipsNotCancelled != null)
// result.addObject("ratioOfSponsorshipsCancelledVsSponsorshipsNotCancelled", ratioOfSponsorshipsCancelledVsSponsorshipsNotCancelled);
// else
// result.addObject("ratioOfSponsorshipsCancelledVsSponsorshipsNotCancelled", 0.0);
//
// //-----------------------------------------------------------------
//
// final Double avgOfViewsPerSponsorship = this.administratorService.getAvgOfViewsPerSponsorship();
// if (avgOfViewsPerSponsorship != null)
// result.addObject("avgOfViewsPerSponsorship", avgOfViewsPerSponsorship);
// else
// result.addObject("avgOfViewsPerSponsorship", 0.0);
//
// final Integer minimumOfViewsPerSponsorship = this.administratorService.getMinimumOfViewsPerSponsorship();
// if (minimumOfViewsPerSponsorship != null)
// result.addObject("minimumOfViewsPerSponsorship", minimumOfViewsPerSponsorship);
// else
// result.addObject("minimumOfViewsPerSponsorship", 0);
//
// final Integer maximumOfViewsPerSponsorship = this.administratorService.getMaximumOfViewsPerSponsorship();
// if (maximumOfViewsPerSponsorship != null)
// result.addObject("maximumOfViewsPerSponsorship", maximumOfViewsPerSponsorship);
// else
// result.addObject("maximumOfViewsPerSponsorship", 0);
//
// final Double sDOfViewsPerSponsorship = this.administratorService.getSDOfViewsPerSponsorship();
// if (sDOfViewsPerSponsorship != null)
// result.addObject("sDOfViewsPerSponsorship", sDOfViewsPerSponsorship);
// else
// result.addObject("sDOfViewsPerSponsorship", 0.0);
//
// //----------------------------------------------------------------------
//
// final Collection<Writer> writersWithMoreBooks = this.writerService.getWritersWithMoreBooks();
// result.addObject("writersWithMoreBooks", writersWithMoreBooks);
//
// //----------------------------------------------------------------------
//
// final Collection<Writer> writersWithLessBooks = this.writerService.getWritersWithLessBooks();
// result.addObject("writersWithLessBooks", writersWithLessBooks);
//
// //----------------------------------------------------------------------
//
// final Collection<Contest> contestsWithMoreParticipations = this.contestService.getContestsWithMoreParticipations();
// result.addObject("contestsWithMoreParticipations", contestsWithMoreParticipations);
//
// final Integer maximumOfParticipationsContest = this.administratorService.getMaximumOfParticipationsContest();
// if (maximumOfParticipationsContest != null)
// result.addObject("maximumOfParticipationsContest", maximumOfParticipationsContest);
// else
// result.addObject("maximumOfParticipationsContest", 0);
// //----------------------------------------------------------------------
//
// final Collection<Contest> contestsWithMoreSponsorships = this.contestService.getContestsWithMoreSponsorships();
// result.addObject("contestsWithMoreSponsorships", contestsWithMoreSponsorships);
//
// final Integer maximumOfSponsorshipsContest = this.administratorService.getMaximumOfSponsorshipsContest();
// if (maximumOfSponsorshipsContest != null)
// result.addObject("maximumOfSponsorshipsContest", maximumOfSponsorshipsContest);
// else
// result.addObject("maximumOfSponsorshipsContest", 0);
// //----------------------------------------------------------------------
//
// final Collection<Sponsor> sponsorsWithMoreSponsorships = this.sponsorService.getSponsorsWithMoreSponsorships();
// result.addObject("sponsorsWithMoreSponsorships", sponsorsWithMoreSponsorships);
//
// result.addObject("requestURI", "dashboard/administrator/display.do");
//
// this.configValues(result);
// return result;
// }
//}

package controllers.administrator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.AdministratorService;
import controllers.AbstractController;

@Controller
@RequestMapping("/dashboard")
public class DashboardAdministratorController extends AbstractController {

	@Autowired
	private AdministratorService	administratorService;


	@RequestMapping(value = "/administrator/display", method = RequestMethod.GET)
	public ModelAndView display() {
		final ModelAndView result = new ModelAndView("administrator/dashboard");

		//----------------------------------------------------------------

		final Double avgOfSubmissionsPerConference = this.administratorService.getAvgOfSubmissionsPerConference();
		if (avgOfSubmissionsPerConference != null)
			result.addObject("avgOfSubmissionsPerConference", avgOfSubmissionsPerConference);
		else
			result.addObject("avgOfSubmissionsPerConference", 0.0);

		final Integer minimumOfSubmissionsPerConference = this.administratorService.getMinimumOfSubmissionsPerConference();
		if (minimumOfSubmissionsPerConference != null)
			result.addObject("minimumOfSubmissionsPerConference", minimumOfSubmissionsPerConference);
		else
			result.addObject("minimumOfSubmissionsPerConference", 0);

		final Integer maximumOfSubmissionsPerConference = this.administratorService.getMaximumOfSubmissionsPerConference();
		if (maximumOfSubmissionsPerConference != null)
			result.addObject("maximumOfSubmissionsPerConference", maximumOfSubmissionsPerConference);
		else
			result.addObject("maximumOfSubmissionsPerConference", 0);

		final Double sDOfSubmissionsPerConference = this.administratorService.getSDOfSubmissionsPerConference();
		if (sDOfSubmissionsPerConference != null)
			result.addObject("sDOfSubmissionsPerConference", sDOfSubmissionsPerConference);
		else
			result.addObject("sDOfSubmissionsPerConference", 0.0);

		//----------------------------------------------------------------

		final Double avgOfRegistrationsPerConference = this.administratorService.getAvgOfRegistrationsPerConference();
		if (avgOfRegistrationsPerConference != null)
			result.addObject("avgOfRegistrationsPerConference", avgOfRegistrationsPerConference);
		else
			result.addObject("avgOfRegistrationsPerConference", 0.0);

		final Integer minimumOfRegistrationsPerConference = this.administratorService.getMinimumOfRegistrationsPerConference();
		if (minimumOfRegistrationsPerConference != null)
			result.addObject("minimumOfRegistrationsPerConference", minimumOfRegistrationsPerConference);
		else
			result.addObject("minimumOfRegistrationsPerConference", 0);

		final Integer maximumOfRegistrationsPerConference = this.administratorService.getMaximumOfRegistrationsPerConference();
		if (maximumOfRegistrationsPerConference != null)
			result.addObject("maximumOfRegistrationsPerConference", maximumOfRegistrationsPerConference);
		else
			result.addObject("maximumOfRegistrationsPerConference", 0);

		final Double sDOfRegistrationsPerConference = this.administratorService.getSDOfRegistrationsPerConference();
		if (sDOfRegistrationsPerConference != null)
			result.addObject("sDOfRegistrationsPerConference", sDOfRegistrationsPerConference);
		else
			result.addObject("sDOfRegistrationsPerConference", 0.0);

		//----------------------------------------------------------------

		final Double avgOfConferenceFees = this.administratorService.getAvgOfConferenceFees();
		if (avgOfConferenceFees != null)
			result.addObject("avgOfConferenceFees", avgOfConferenceFees);
		else
			result.addObject("avgOfConferenceFees", 0.0);

		final Integer minimumOfConferenceFees = this.administratorService.getMinimumOfConferenceFees();
		if (minimumOfConferenceFees != null)
			result.addObject("minimumOfConferenceFees", minimumOfConferenceFees);
		else
			result.addObject("minimumOfConferenceFees", 0);

		final Integer maximumOfConferenceFees = this.administratorService.getMaximumOfConferenceFees();
		if (maximumOfConferenceFees != null)
			result.addObject("maximumOfConferenceFees", maximumOfConferenceFees);
		else
			result.addObject("maximumOfConferenceFees", 0);

		final Double sDOfConferenceFees = this.administratorService.getSDOfConferenceFees();
		if (sDOfConferenceFees != null)
			result.addObject("sDOfConferenceFees", sDOfConferenceFees);
		else
			result.addObject("sDOfConferenceFees", 0.0);

		//----------------------------------------------------------------

		final Double avgOfDaysPerConference = this.administratorService.getAvgOfDaysPerConference();
		if (avgOfDaysPerConference != null)
			result.addObject("avgOfDaysPerConference", avgOfDaysPerConference);
		else
			result.addObject("avgOfDaysPerConference", 0.0);

		final Integer minimumOfDaysPerConference = this.administratorService.getMinimumOfDaysPerConference();
		if (minimumOfDaysPerConference != null)
			result.addObject("minimumOfDaysPerConference", minimumOfDaysPerConference);
		else
			result.addObject("minimumOfDaysPerConference", 0);

		final Integer maximumOfDaysPerConference = this.administratorService.getMaximumOfDaysPerConference();
		if (maximumOfDaysPerConference != null)
			result.addObject("maximumOfDaysPerConference", maximumOfDaysPerConference);
		else
			result.addObject("maximumOfDaysPerConference", 0);

		final Double sDOfDaysPerConference = this.administratorService.getSDOfDaysPerConference();
		if (sDOfDaysPerConference != null)
			result.addObject("sDOfDaysPerConference", sDOfDaysPerConference);
		else
			result.addObject("sDOfDaysPerConference", 0.0);

		//----------------------------------------------------------------

		final Double avgOfConferencesPerCategory = this.administratorService.getAvgOfConferencesPerCategory();
		if (avgOfConferencesPerCategory != null)
			result.addObject("avgOfConferencesPerCategory", avgOfConferencesPerCategory);
		else
			result.addObject("avgOfConferencesPerCategory", 0.0);

		final Integer minimumOfConferencesPerCategory = this.administratorService.getMinimumOfConferencesPerCategory();
		if (minimumOfConferencesPerCategory != null)
			result.addObject("minimumOfConferencesPerCategory", minimumOfConferencesPerCategory);
		else
			result.addObject("minimumOfConferencesPerCategory", 0);

		final Integer maximumOfConferencesPerCategory = this.administratorService.getMaximumOfConferencesPerCategory();
		if (maximumOfConferencesPerCategory != null)
			result.addObject("maximumOfConferencesPerCategory", maximumOfConferencesPerCategory);
		else
			result.addObject("maximumOfConferencesPerCategory", 0);

		final Double sDOfConferencesPerCategory = this.administratorService.getSDOfConferencesPerCategory();
		if (sDOfConferencesPerCategory != null)
			result.addObject("sDOfConferencesPerCategory", sDOfConferencesPerCategory);
		else
			result.addObject("sDOfConferencesPerCategory", 0.0);

		//----------------------------------------------------------------

		final Double avgOfCommentsPerConference = this.administratorService.getAvgOfCommentsPerConference();
		if (avgOfCommentsPerConference != null)
			result.addObject("avgOfCommentsPerConference", avgOfCommentsPerConference);
		else
			result.addObject("avgOfCommentsPerConference", 0.0);

		final Integer minimumOfCommentsPerConference = this.administratorService.getMinimumOfCommentsPerConference();
		if (minimumOfCommentsPerConference != null)
			result.addObject("minimumOfCommentsPerConference", minimumOfCommentsPerConference);
		else
			result.addObject("minimumOfCommentsPerConference", 0);

		final Integer maximumOfCommentsPerConference = this.administratorService.getMaximumOfCommentsPerConference();
		if (maximumOfCommentsPerConference != null)
			result.addObject("maximumOfCommentsPerConference", maximumOfCommentsPerConference);
		else
			result.addObject("maximumOfCommentsPerConference", 0);

		final Double sDOfCommentsPerConference = this.administratorService.getSDOfCommentsPerConference();
		if (sDOfCommentsPerConference != null)
			result.addObject("sDOfCommentsPerConference", sDOfCommentsPerConference);
		else
			result.addObject("sDOfCommentsPerConference", 0.0);

		//----------------------------------------------------------------

		final Double avgOfCommentsPerActivity = this.administratorService.getAvgOfCommentsPerActivity();
		if (avgOfCommentsPerActivity != null)
			result.addObject("avgOfCommentsPerActivity", avgOfCommentsPerActivity);
		else
			result.addObject("avgOfCommentsPerActivity", 0.0);

		final Integer minimumOfCommentsPerActivity = this.administratorService.getMinimumOfCommentsPerActivity();
		if (minimumOfCommentsPerActivity != null)
			result.addObject("minimumOfCommentsPerActivity", minimumOfCommentsPerActivity);
		else
			result.addObject("minimumOfCommentsPerActivity", 0);

		final Integer maximumOfCommentsPerActivity = this.administratorService.getMaximumOfCommentsPerActivity();
		if (maximumOfCommentsPerActivity != null)
			result.addObject("maximumOfCommentsPerActivity", maximumOfCommentsPerActivity);
		else
			result.addObject("maximumOfCommentsPerActivity", 0);

		final Double sDOfCommentsPerActivity = this.administratorService.getSDOfCommentsPerActivity();
		if (sDOfCommentsPerActivity != null)
			result.addObject("sDOfCommentsPerActivity", sDOfCommentsPerActivity);
		else
			result.addObject("sDOfCommentsPerActivity", 0.0);

		result.addObject("requestURI", "dashboard/administrator/display.do");

		this.configValues(result);
		return result;
	}
}
