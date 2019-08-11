<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>


<acme:cancel url="${url}" code="conference.display.back"/> 

<acme:text label="conference.list.title" value="${conference.title}"/>
<acme:text label="conference.list.acronym" value="${conference.acronym}"/>
<acme:text label="conference.list.venue" value="${conference.venue}"/>
<acme:text label="conference.list.submissionDeadline" value="${conference.submissionDeadline}"/>
<acme:text label="conference.list.notificationDeadline" value="${conference.notificationDeadline}"/>
<acme:text label="conference.list.cameraReadyDeadline" value="${conference.cameraReadyDeadline}"/>
<acme:text label="conference.list.startDate" value="${conference.startDate}"/>
<acme:text label="conference.list.endDate" value="${conference.endDate}"/>
<acme:text label="conference.list.summary" value="${conference.summary}"/>
<acme:text label="conference.list.fee" value="${conference.fee}"/>


<jstl:if test="${lang eq 'en' }">
<acme:text label="conference.list.category" value="${conference.category.categoryEN}"/>
</jstl:if>
<jstl:if test="${lang eq 'es' }">
<acme:text label="conference.list.category" value="${conference.category.categoryES}"/>
</jstl:if>

<hr>
<%-- <display:table pagesize="5" name="comments" id="comment" requestURI="${requestURI}">

			<display:column titleKey="comment.list.title"><jstl:out value="${comment.title}"/></display:column>
			<display:column titleKey="comment.list.moment"><jstl:out value="${comment.moment}"/></display:column>
			<display:column titleKey="comment.list.text"><jstl:out value="${comment.text}"/></display:column>
			<jstl:if test="${comment.actor.name == null}">
			<display:column titleKey="comment.list.actor"><jstl:out value="${anonim}"/></display:column>
			</jstl:if>
			<jstl:if test="${comment.actor.name != null}">
			<display:column titleKey="comment.list.actor"><jstl:out value="${comment.actor.name}"/></display:column>
			</jstl:if>	
				
</display:table> --%>


<div id="sponsor" style="width: 50px;">
	<a target="_blank" href="${sponsorshipRandom.targetURL}" ><img style="width: 200px;" src="${sponsorshipRandom.bannerURL}" alt="${sponsorshipRandom.targetURL}"/></a>
</div>

		


