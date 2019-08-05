<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags" %>


<!-- Details of submission  -->	
<acme:text label="submission.display.moment" value="${submission.moment}"/>
<acme:text label="submission.display.status" value="${submission.status}"/>
<acme:text label="submission.display.conference" value="${submission.conference.title}"/>

<!-- Iterar sobre los reviewers  -->
<spring:message code="submission.display.reviewers"/>
<ul>
	<jstl:forEach items="${submission.reviewers}" var="reviewer">
		<li><jstl:out value="${reviewer.author.name} ${reviewer.author.middlename} ${reviewer.author.surname}"/></li>
	</jstl:forEach>
</ul>

<acme:text label="submission.display.ticker" value="${submission.ticker.identifier}"/>
<acme:text label="submission.display.author" value="${submission.author.name} ${submission.author.middlename} ${submission.author.surname}"/>


<!-- Details of paper  -->
<h3><spring:message code="submission.display.paper"/></h3>

<acme:text label="submission.display.paper.title" value="${paper.title}"/>
<acme:text label="submission.display.paper.summary" value="${paper.summary}"/>
<a href="<jstl:out value="${paper.documentUrl}""><spring:message code="submission.display.paper.url"/></a>

<spring:message code="submission.display.paper.authors"/>
<ul>
	<jstl:forEach items="${paper.authors}" var="author">
		<li><jstl:out value="${author.author.name} ${author.author.middlename} ${author.author.surname}"/></li>
	</jstl:forEach>
	
	<jstl:forEach items="${paper.aliases}" var="alias">
		<li><jstl:out value="${alias}"/></li>
	</jstl:forEach>
</ul>


<!-- Details of camera ready paper  -->
<h3><spring:message code="submission.display.cameraReadyPaper"/></h3>

<jstl:choose>
	<jstl:when test="${cameraReadyPaper eq null}">
		<spring:message code="submission.display.cameraReadyPaper.null"/>
	</jstl:when>
	
	<jstl:otherwise>
		<acme:text label="submission.display.paper.title" value="${cameraReadyPaper.title}"/>
		<acme:text label="submission.display.paper.summary" value="${cameraReadyPaper.summary}"/>
		<a href="<jstl:out value="${cameraReadyPaper.documentUrl}""><spring:message code="submission.display.paper.url"/></a>
		
		<spring:message code="submission.display.paper.authors"/>
		<ul>
			<jstl:forEach items="${cameraReadyPaper.authors}" var="author">
				<li><jstl:out value="${author.author.name} ${author.author.middlename} ${author.author.surname}"/></li>
			</jstl:forEach>
			
			<jstl:forEach items="${cameraReadyPaper.aliases}" var="alias">
				<li><jstl:out value="${alias}"/></li>
			</jstl:forEach>
		</ul>

	</jstl:otherwise>
</jstl:choose>


