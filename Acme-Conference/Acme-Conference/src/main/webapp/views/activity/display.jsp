<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<acme:cancel url="${url}" code="conference.display.back"/> 

<acme:text label="activity.list.type" value="${activity.type}"/>
<acme:text label="activity.list.title" value="${activity.title}"/>
<acme:text label="activity.list.startMoment" value="${activity.startMoment}"/>
<acme:text label="activity.list.duration" value="${activity.duration}"/>
<acme:text label="activity.list.room" value="${activity.room}"/>
<acme:text label="activity.list.summary" value="${activity.summary}"/>
<acme:text label="activity.list.attachments" value="${activity.attachments}"/>
<jstl:if test="${activity.type eq 'PRESENTATION'}">
<acme:text label="activity.list.paper" value="${activity.paper.title}"/>
</jstl:if>

<p><strong><spring:message code="activity.list.authors"/>: </strong>
<ul>
	<jstl:forEach items="${activity.authors}" var="authors">
		<li><jstl:out value="${authors.name} ${authors.middlename} ${authors.surname}"/></li>
	</jstl:forEach>
</ul>
</p>

<hr>
<jstl:if test="${activity.type eq 'TUTORIAL'}">
<display:table pagesize="5" name="sections" id="section" requestURI="${requestURI}">

			<display:column titleKey="section.list.title"><jstl:out value="${section.title}"/></display:column>
			<display:column titleKey="section.list.summary"><jstl:out value="${section.summary}"/></display:column>
			<display:column titleKey="section.list.pictures"><jstl:out value="${section.pictures}"/></display:column>
				
</display:table>
<jstl:if test="${botton}">
<acme:button url="section/administrator/create.do?idActivity=${activity.id}" type="button" code="section.list.create"/>
</jstl:if>
</jstl:if>

<div id="sponsor" style="width: 50px;">
	<a target="_blank" href="${sponsorshipRandom.targetURL}" ><img style="width: 200px;" src="${sponsorshipRandom.bannerURL}" alt="${sponsorshipRandom.targetURL}"/></a>
</div>

		


