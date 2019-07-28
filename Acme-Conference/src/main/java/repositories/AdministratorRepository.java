
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Administrator;

@Repository
public interface AdministratorRepository extends JpaRepository<Administrator, Integer> {

	//	@Query("select a from Administrator a where a.userAccount.id = ?1")
	//	Administrator findByPrincipal(int principalId);
	//
	//DASHBOARD

	@Query("select avg(1*(select count(s) from Submission s where s.conference.id = c.id)) from Conference c")
	Double getAvgOfSubmissionsPerConference();

	@Query("select min(1*(select count(s) from Submission s where s.conference.id = c.id)) from Conference c")
	Integer getMinimumOfSubmissionsPerConference();

	@Query("select max(1*(select count(s) from Submission s where s.conference.id = c.id)) from Conference c")
	Integer getMaximumOfSubmissionsPerConference();

	@Query("select stddev(1*(select count(s) from Submission s where s.conference.id = c.id)) from Conference c")
	Double getSDOfSubmissionsPerConference();

	//	//---------------------------------------------------------------------------------

	@Query("select avg(1*(select count(r) from Registration r where r.conference.id = c.id)) from Conference c")
	Double getAvgOfRegistrationsPerConference();

	@Query("select min(1*(select count(r) from Registration r where r.conference.id = c.id)) from Conference c")
	Integer getMinimumOfRegistrationsPerConference();

	@Query("select max(1*(select count(r) from Registration r where r.conference.id = c.id)) from Conference c")
	Integer getMaximumOfRegistrationsPerConference();

	@Query("select stddev(1*(select count(r) from Registration r where r.conference.id = c.id)) from Conference c")
	Double getSDOfRegistrationsPerConference();

	//	//---------------------------------------------------------------------------------

	@Query("select avg(c.fee) from Conference c")
	Double getAvgOfConferenceFees();

	@Query("select min(c.fee) from Conference c")
	Integer getMinimumOfConferenceFees();

	@Query("select max(c.fee) from Conference c")
	Integer getMaximumOfConferenceFees();

	@Query("select stddev(c.fee) from Conference c")
	Double getSDOfConferenceFees();

	//	//---------------------------------------------------------------------------------

	@Query("select avg(datediff(c.endDate,c.startDate)) from Conference c")
	Double getAvgOfDaysPerConference();

	@Query("select min(datediff(c.endDate,c.startDate)) from Conference c")
	Integer getMinimumOfDaysPerConference();

	@Query("select max(datediff(c.endDate,c.startDate)) from Conference c")
	Integer getMaximumOfDaysPerConference();

	@Query("select stddev(datediff(c.endDate,c.startDate)) from Conference c")
	Double getSDOfDaysPerConference();

	//	//---------------------------------------------------------------------------------

	@Query("select avg(1*(select count(c) from Conference c where c.category.id = ca.id)) from Category ca")
	Double getAvgOfConferencesPerCategory();

	@Query("select min(1*(select count(c) from Conference c where c.category.id = ca.id)) from Category ca")
	Integer getMinimumOfConferencesPerCategory();

	@Query("select max(1*(select count(c) from Conference c where c.category.id = ca.id)) from Category ca")
	Integer getMaximumOfConferencesPerCategory();

	@Query("select stddev(1*(select count(c) from Conference c where c.category.id = ca.id)) from Category ca")
	Double getSDOfConferencesPerCategory();

	//	//--------------------------------------------------------------------------------

	@Query("select avg(1*(select count(co) from Comment co where co.conference.id = c.id)) from Conference c")
	Double getAvgOfCommentsPerConference();

	@Query("select min(1*(select count(co) from Comment co where co.conference.id = c.id)) from Conference c")
	Integer getMinimumOfCommentsPerConference();

	@Query("select max(1*(select count(co) from Comment co where co.conference.id = c.id)) from Conference c")
	Integer getMaximumOfCommentsPerConference();

	@Query("select stddev(1*(select count(co) from Comment co where co.conference.id = c.id)) from Conference c")
	Double getSDOfCommentsPerConference();

	//	//--------------------------------------------------------------------------------

	@Query("select avg(1*(select count(co) from Comment co where co.activity.id = a.id)) from Activity a")
	Double getAvgOfCommentsPerActivity();

	@Query("select min(1*(select count(co) from Comment co where co.activity.id = a.id)) from Activity a")
	Integer getMinimumOfCommentsPerActivity();

	@Query("select max(1*(select count(co) from Comment co where co.activity.id = a.id)) from Activity a")
	Integer getMaximumOfCommentsPerActivity();

	@Query("select stddev(1*(select count(co) from Comment co where co.activity.id = a.id)) from Activity a")
	Double getSDOfCommentsPerActivity();

	@Query("select a from Administrator a where a.userAccount.id = ?1")
	Administrator findByPrincipal(int id);

}
