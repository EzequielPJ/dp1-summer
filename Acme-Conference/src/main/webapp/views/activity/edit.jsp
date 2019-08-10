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

<form:form modelAttribute="activity" action="activity/administrator/edit.do">
		<acme:hidden path="id"/>
		<acme:hidden path="conference"/>
		<acme:hidden path="authors"/>
		<acme:hidden path="paper"/>
		
		<p><spring:message code="activity.list.type"/>:
		<select name="type" onchange="javascript: return true;">
		<jstl:forEach var="type" items="${typeList}">
			<option value="${type}"><jstl:out value="${type}"/></option>
		</jstl:forEach>
		</select>
		<form:errors cssClass="error" path="type" />
		</p>
		
		<p><spring:message code="activity.list.authors"/>:
		<select name="authors" onchange="javascript: return true;" multiple>
		<jstl:forEach var="author" items="${authors}">
		<%-- <option value="${author.id}"><jstl:out value="${author.name} ${author.middlename} ${author.surname}"/></option> --%>
			 <option value="${author.id}" <jstl:if test="${paper.authors.contains(author)}">selected</jstl:if>><jstl:out value="${author.name} ${author.middlename} ${author.surname}"/></option>	
		</jstl:forEach>
		</select>
		<form:errors cssClass="error" path="authors" />
		</p>
		
		<p>
			<acme:textarea code="activity.list.title" path="title"/>
		</p>
		<p>
			<acme:inputNumber code="activity.list.duration" path="duration"/>
		</p>
		<p>
			<acme:textarea code="activity.list.room" path="room"/>
		</p>
  		<p>
			<acme:textarea code="activity.list.summary" path="summary"/>
		</p>
		<p>
			<acme:textarea code="activity.list.attachments" path="attachments"/>
		</p>
		
		<p>
		<acme:submit name="save" code="activity.edit.save"/>
		<acme:cancel url="activity/administrator/list.do?idConference=${idConference}"  code="conference.display.back"/>
		
	</form:form>
	
