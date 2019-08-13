<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<form:form modelAttribute="messageForm" action="message/save.do">
	  	 
   	<p>
   		 <acme:textbox code="message.create.subject" path="subject"/>
   	</p>
   	<p>
   		 <acme:textarea code="message.create.body" path="body"/>
   	</p>
   	
   	<acme:select items="${actors}" itemLabel="email" code="message.create.recipients" path="recipients"/>
   	
   	<jstl:choose>
		<jstl:when test="${cookie.language.value == 'es'}">       
   			<acme:select items="${topics}" itemLabel="topicEN" code="message.create.topics" path="topics"/>
		</jstl:when>
		
		<jstl:otherwise>
			<acme:select items="${topics}" itemLabel="topicES" code="message.create.topics" path="topics"/>
		</jstl:otherwise>
	</jstl:choose>
   	
	<p>
	<spring:message code="book.edit.lang"/>: <select name="broadcastType">
	  <option value="ALL-ACTORS" <jstl:if test="${messageForm.broadcastType == 'ALL-ACTORS'}"><jstl:out value="selected"/></jstl:if>><spring:message code="message.create.broadcastType.allActors"/></option>
	  <option value="ALL-AUTHORS" <jstl:if test="${messageForm.broadcastType == 'ALL-AUTHORS'}"><jstl:out value="selected"/></jstl:if>><spring:message code="message.create.broadcastType.allAuthors"/></option>
	  <option value="AUTHORS-WITH-REGISTRATIONS" <jstl:if test="${messageForm.broadcastType == 'AUTHORS-WITH-REGISTRATIONS'}"><jstl:out value="selected"/></jstl:if>><spring:message code="message.create.broadcastType.authorsRegistration"/></option>
	  <option value="AUTHORS-WITH-SUBMISSIONS" <jstl:if test="${messageForm.broadcastType == 'AUTHORS-WITH-SUBMISSIONS'}"><jstl:out value="selected"/></jstl:if>><spring:message code="message.create.broadcastType.authorsSubmissions"/></option>
	</select>
   	</p>
	
	
   	 

   	<acme:submit name="save" code="message.create.save"/>

</form:form>

<acme:button code="message.create.cancel" type="button" url="/message/list.do"/>

