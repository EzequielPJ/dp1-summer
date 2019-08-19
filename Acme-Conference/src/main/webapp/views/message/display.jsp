<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<%@taglib prefix="fn"	uri="http://java.sun.com/jsp/jstl/functions"%>

<button onclick="window.location.href=document.referrer"><spring:message code="message.display.back"/></button>

<acme:text label="message.display.subject" value="${messageObject.subject}"/>
<acme:text label="message.display.moment" value="${messageObject.moment}"/>
<acme:text label="message.display.sender" value="${messageObject.sender.email}"/>

<strong><spring:message code="message.display.recipients"/>: </strong>
<jstl:choose>
	<jstl:when test="${messageObject.sender eq actorLogged}">
		<jstl:forEach var="counter" begin="0" end="${fn:length(recipients) - 1}">
			<jstl:out value="${recipients.get(counter).email}"/><jstl:if test="${counter != fn:length(recipients) - 1}">,</jstl:if>
		</jstl:forEach>
			
	</jstl:when>
	
	<jstl:otherwise>
		<jstl:out value="${actorLogged.email}"/> 
	</jstl:otherwise>
</jstl:choose>

<acme:text label="message.display.body" value="${messageObject.body}"/>

<spring:message code="message.display.topics"/>
<jstl:forEach var="topic" items="${messageObject.topics}">
	<jstl:choose>
		<jstl:when test="${cookie.language.value == 'es'}">
			<jstl:out value="${topic.topicES}"/>        
		</jstl:when>
		
		<jstl:otherwise>
			<jstl:out value="${topic.topicEN}"/>        
		</jstl:otherwise>
	</jstl:choose>
	
</jstl:forEach>



