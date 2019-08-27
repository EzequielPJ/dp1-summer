<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<button onclick="window.location.href=document.referrer"><spring:message code="submission.display.back"/></button>


 <security:authorize access="hasRole('ADMINISTRATOR')">
 	<jstl:if test="${submission.status == 'UNDER-REVIEW'}">
	 <acme:button url="/submission/administrator/changeStatus.do?idSubmission=${submission.id}&status=ACCEPTED" type="button" code="submission.display.changeStatus.accepted"/>
	 <acme:button url="/submission/administrator/changeStatus.do?idSubmission=${submission.id}&status=REJECTED" type="button" code="submission.display.changeStatus.rejected"/>
 	</jstl:if>
 </security:authorize>
 
 	<security:authorize access="hasRole('REVIEWER')">
 		<jstl:if test="${submission.status == 'UNDER-REVIEW'}">
 		<br />
 		<b><spring:message code="report.create.report" /></b>
			<acme:button url="report/reviewer/create.do?submissionId=${submission.id }" type="button" code="report.list.create"/>
		</jstl:if>
	</security:authorize>

<!-- Details of submission  -->	
<acme:text label="submission.display.moment" value="${submission.moment}"/>
<acme:text label="submission.display.status" value="${submission.status}"/>
<acme:text label="submission.display.conference" value="${submission.conference.title}"/>

<!-- Iterar sobre los reviewers  -->
<p><strong><spring:message code="submission.display.reviewers"/>: </strong>
<ul>
	<jstl:forEach items="${submission.reviewers}" var="reviewer">
		<li><jstl:out value="${reviewer.name} ${reviewer.middlename} ${reviewer.surname}"/></li>
	</jstl:forEach>
</ul>

 <security:authorize access="hasRole('ADMINISTRATOR')">
 	<jstl:if test="${submission.status == 'UNDER-REVIEW'}">
		<p><strong><spring:message code="submission.display.reviewers.assign"/>: </strong>
		<ul>
			<jstl:forEach items="${reviewers}" var="reviewer">
				<jstl:if test="${!submission.reviewers.contains(reviewer)}" >
					<li><jstl:out value="${reviewer.name} ${reviewer.middlename} ${reviewer.surname}"/><acme:button url="/submission/administrator/assignTo.do?idSubmission=${submission.id}&idReviewer=${reviewer.id }" type="button" code="submission.display.changeStatus.accepted"/></li>
				</jstl:if>
			</jstl:forEach>
		</ul>
	</jstl:if>
</security:authorize>

<acme:text label="submission.display.ticker" value="${submission.ticker.identifier}"/>
<acme:text label="submission.display.author" value="${submission.author.name} ${submission.author.middlename} ${submission.author.surname}"/>


<!-- Details of paper  -->
<hr/>

<h3><spring:message code="submission.display.paper"/></h3>

<acme:text label="submission.display.paper.title" value="${nonCameraReadyVersion.title}"/>
<acme:text label="submission.display.paper.summary" value="${nonCameraReadyVersion.summary}"/>
<a target="_blank" href="<jstl:out value="${nonCameraReadyVersion.documentUrl}"/>"><spring:message code="submission.display.paper.url"/></a><br/>

<p><strong><spring:message code="submission.display.paper.authors"/>: </strong>
<ul>
	<jstl:forEach items="${nonCameraReadyVersion.authors}" var="author">
		<li><jstl:out value="${author.name} ${author.middlename} ${author.surname}"/></li>
	</jstl:forEach>
	
	<jstl:forEach items="${nonCameraReadyVersion.aliases}" var="alias">
		<li><jstl:out value="${alias}"/></li>
	</jstl:forEach>
</ul>

<security:authorize access="hasRole('AUTHOR')">

	<jstl:if test="${submission.notified }">
		<display:table pagesize="5" name="reports" id="report" requestURI="${requestURI}">
			<display:column titleKey="submission.report.decision"><jstl:out value="${report.decision }" /></display:column>
			<display:column titleKey="submission.report.seeMore"><acme:button url="report/author/display.do?reportId=${report.id }&backUri='submission/author/display.do?submissionId=${submission.id }'" type="button" code="submission.report.seeMore"/></display:column>
		</display:table>
	</jstl:if>

</security:authorize>


<!-- Details of camera ready paper  -->
<hr/>
<h3><spring:message code="submission.display.cameraReadyPaper"/></h3>

<jstl:choose>
	<jstl:when test="${cameraReadyVersion eq null}">
		<spring:message code="submission.display.cameraReadyPaper.null"/>
	</jstl:when>
	
	<jstl:otherwise>
		<acme:text label="submission.display.paper.title" value="${cameraReadyVersion.title}"/>
		<acme:text label="submission.display.paper.summary" value="${cameraReadyVersion.summary}"/>
		<a target="_blank" href="<jstl:out value="${cameraReadyVersion.documentUrl}"/>"><spring:message code="submission.display.paper.url"/></a><br/>
		
		<p><strong><spring:message code="submission.display.paper.authors"/>: </strong>
		<ul>
			<jstl:forEach items="${cameraReadyVersion.authors}" var="author">
				<li><jstl:out value="${author.name} ${author.middlename} ${author.surname}"/></li>
			</jstl:forEach>
			
			<jstl:forEach items="${cameraReadyVersion.aliases}" var="alias">
				<li><jstl:out value="${alias}"/></li>
			</jstl:forEach>
		</ul>


	</jstl:otherwise>
</jstl:choose>


