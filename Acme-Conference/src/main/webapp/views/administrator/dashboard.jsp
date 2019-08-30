<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>
<script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.4.0/Chart.min.js"></script>
			
<table>
	<tr>
		<th> <spring:message code="administrator.dashboard.information"/> </th>
		<th> <spring:message code="administrator.dashboard.avg"/> </th>
		<th> <spring:message code='administrator.dashboard.minimum'/> </th>
		<th> <spring:message code='administrator.dashboard.maximum'/> </th>
		<th> <spring:message code='administrator.dashboard.sD'/> </th>
	</tr>
	<tr>
		<th> <spring:message code='administrator.dashboard.SubmissionsPerConference'/> </th>
		<td> <jstl:out value="${avgOfSubmissionsPerConference}"/>	</td>
		<td> <jstl:out value="${minimumOfSubmissionsPerConference}"/>	</td>
		<td> <jstl:out value="${maximumOfSubmissionsPerConference}"/>	</td>
		<td> <jstl:out value="${sDOfSubmissionsPerConference}"/>	</td>
	</tr>
	<tr>
		<th> <spring:message code='administrator.dashboard.RegistrationsPerConference'/> </th>	
		<td> <jstl:out value="${avgOfRegistrationsPerConference}"/>	</td>
		<td> <jstl:out value="${minimumOfRegistrationsPerConference}"/>	</td>
		<td> <jstl:out value="${maximumOfRegistrationsPerConference}"/>	</td>
		<td> <jstl:out value="${sDOfRegistrationsPerConference}"/>	</td>
	</tr>
	<tr>
		<th> <spring:message code='administrator.dashboard.ConferenceFees'/> </th>
		<td> <jstl:out value="${avgOfConferenceFees}"/>	</td>
		<td> <jstl:out value="${minimumOfConferenceFees}"/>	</td>
		<td> <jstl:out value="${maximumOfConferenceFees}"/>	</td>
		<td> <jstl:out value="${sDOfConferenceFees}"/>	</td>
	</tr>
	<tr>
		<th> <spring:message code='administrator.dashboard.DaysPerConference'/> </th>
		<td> <jstl:out value="${avgOfDaysPerConference}"/>	</td>
		<td> <jstl:out value="${minimumOfDaysPerConference}"/>	</td>
		<td> <jstl:out value="${maximumOfDaysPerConference}"/>	</td>
		<td> <jstl:out value="${sDOfDaysPerConference}"/>	</td>
	</tr>
	<tr>
		<th> <spring:message code='administrator.dashboard.ConferencesPerCategory'/> </th>
		<td> <jstl:out value="${avgOfConferencesPerCategory}"/>	</td>
		<td> <jstl:out value="${minimumOfConferencesPerCategory}"/>	</td>
		<td> <jstl:out value="${maximumOfConferencesPerCategory}"/>	</td>
		<td> <jstl:out value="${sDOfConferencesPerCategory}"/>	</td>
	</tr>
	<tr>
		<th> <spring:message code='administrator.dashboard.CommentsPerConference'/> </th>
		<td> <jstl:out value="${avgOfCommentsPerConference}"/>	</td>
		<td> <jstl:out value="${minimumOfCommentsPerConference}"/>	</td>
		<td> <jstl:out value="${maximumOfCommentsPerConference}"/>	</td>
		<td> <jstl:out value="${sDOfCommentsPerConference}"/>	</td>
	</tr>
	<tr>
		<th> <spring:message code='administrator.dashboard.CommentsPerActivity'/> </th>
		<td> <jstl:out value="${avgOfCommentsPerActivity}"/>	</td>
		<td> <jstl:out value="${minimumOfCommentsPerActivity}"/>	</td>
		<td> <jstl:out value="${maximumOfCommentsPerActivity}"/>	</td>
		<td> <jstl:out value="${sDOfCommentsPerActivity}"/>	</td>
	</tr>
</table>

<hr/>

<!-- DASHBOARD CONTROL CHECK -->
<table>
	<tr>
		<th> <spring:message code="administrator.dashboard.avgQuolets"/> </th>
		<th> <spring:message code="administrator.dashboard.sDQuolets"/> </th>
		<th> <spring:message code='administrator.dashboard.ratioQuolets'/> </th>
	</tr>
	<tr>
		<td> <jstl:out value="${avgOfQuolets}"/>	</td>
		<td> <jstl:out value="${sDOfQuolets}"/>	</td>
		<td> <jstl:out value="${ratioOfFinalModeVsDraftModeQuolets}"/>	</td>
		
	</tr>
</table>