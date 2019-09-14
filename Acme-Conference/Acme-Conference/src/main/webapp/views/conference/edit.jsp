<%--
 * action-1.jsp
 *
 * Copyright (C) 2019 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<form:form modelAttribute="conference" action="conference/administrator/edit.do">
		<acme:hidden path="id"/>
		
		<p>
			<acme:textarea code="conference.list.title" path="title"/>
		</p>
		<p>
			<acme:textarea code="conference.list.acronym" path="acronym"/>
		</p>
		<p>
			<acme:textarea code="conference.list.venue" path="venue"/>
		</p>
  		<p>
			<acme:inputDate code="conference.list.submissionDeadline" path="submissionDeadline"/>
		</p>
		<p>
			<acme:inputDate code="conference.list.notificationDeadline" path="notificationDeadline"/>
		</p>
		<p>
			<acme:inputDate code="conference.list.cameraReadyDeadline" path="cameraReadyDeadline"/>
		</p>
		<p>
			<acme:inputDate code="conference.list.startDate" path="startDate"/>
		</p>
		<p>
			<acme:inputDate code="conference.list.endDate" path="endDate"/>
		</p>
		<p>
			<acme:textarea code="conference.list.summary" path="summary"/>
		</p>
		<p>
			<acme:inputDouble code="conference.list.fee" path="fee" val="${conference.fee}" />
		</p>
		<acme:checkbox code="conference.list.finalMode" path="finalMode"/>
		<p>
		</p>
		
		<jstl:if test="${lang eq 'en' }">
		<acme:select items="${categoriesList}" itemLabel="categoryEN" code="conference.list.category" path="category"/>
		</jstl:if>
		<jstl:if test="${lang eq 'es' }">
		<acme:select items="${categoriesList}" itemLabel="categoryES" code="conference.list.category" path="category"/>
		</jstl:if>
		
		<p>
		<acme:submit name="save" code="conference.edit.save"/>
		<acme:cancel url="conference/administrator/list.do" code="conference.display.back"/>
		
	</form:form>
	
