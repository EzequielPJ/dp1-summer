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

		


