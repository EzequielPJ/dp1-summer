<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="fn"	uri="http://java.sun.com/jsp/jstl/functions"%>

<h2><spring:message code="${title}"/></h2>



<display:table pagesize="5" name="messagesList" id="messageObject" requestURI="${requestURI}">
	
	<display:column titleKey="message.list.sender"><jstl:out value="${messageObject.sender.email}" /></display:column>
	
	<display:column titleKey="message.list.recipients">
	
		<jstl:choose>
			<jstl:when test="${message.sender eq actorLogged}">
				<jstl:choose>
					<jstl:when test="${fn:length(message.recipients) le 5)}">
						<jstl:forEach var="recipient" items="${message.recipients}">
							<jstl:out value="${recipient.email}"/>
						</jstl:forEach>
					</jstl:when>
					
					<jstl:otherwise>
						<jstl:forEach var="recipient" items="${message.recipients}">
							<jstl:out value="${recipient.email}"/>
						</jstl:forEach>
						+ <jstl:out value="${fn:length(message.recipients) - 5}"/>
					</jstl:otherwise>
				</jstl:choose>
					
			</jstl:when>
			
			<jstl:otherwise>
				<jstl:out value="${actorLogged.email}"/> 
			</jstl:otherwise>
		</jstl:choose>
	
	
	</display:column>
	
	<display:column titleKey="message.list.subject"><jstl:out value="${messageObject.subject}" /></display:column>
	
	<display:column titleKey="message.list.moment"><jstl:out value="${messageObject.moment}" /></display:column>
	
	<display:column titleKey="message.list.display">
		<acme:button url="message/display.do?idMessage=${message.id}" type="button" code="message.list.display"/>
	</display:column>
	
	<display:column titleKey="message.list.delete">
		<acme:button url="message/delete.do?idMessage=${message.id}" type="button" code="message.list.delete"/>
	
	</display:column>
	
	
</display:table>



