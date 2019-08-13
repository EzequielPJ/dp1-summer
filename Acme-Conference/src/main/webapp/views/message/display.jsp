<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<button onclick="window.location.href=document.referrer"><spring:message code="message.display.back"/></button>

<acme:text label="message.display.subject" value="${message.subject}"/>
<acme:text label="message.display.moment" value="${message.moment}"/>
<acme:text label="message.display.sender" value="${message.sender.email}"/>

<jstl:choose>
<spring:message code="message.display.recipients"/>
	<jstl:when test="${message.sender eq actorLogged}">
		<jstl:forEach var="recipient" items="${message.recipients}">
		<jstl:out value="${recipient.email}"/>        
		</jstl:forEach>
			
	</jstl:when>
	
	<jstl:otherwise>
		<jstl:out value="${actorLogged.email}"/> 
	</jstl:otherwise>
</jstl:choose>

<spring:message code="message.display.topics"/>
<jstl:forEach var="topic" items="${message.topics}">
	<jstl:choose>
		<jstl:when test="${cookie.language.value == 'es'}">
			<jstl:out value="${topic.topicES}"/>        
		</jstl:when>
		
		<jstl:otherwise>
			<jstl:out value="${topic.topicEN}"/>        
		</jstl:otherwise>
	</jstl:choose>
	
</jstl:forEach>



