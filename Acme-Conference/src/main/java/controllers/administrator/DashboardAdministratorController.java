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
