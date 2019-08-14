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

<form:form modelAttribute="section" action="section/administrator/edit.do">
		<acme:hidden path="id"/>
		<acme:hidden path="activity"/>
		
		<p>
			<acme:textarea code="section.list.title" path="title"/>
		</p>
		<p>
			<acme:textarea code="section.list.summary" path="summary"/>
		</p>
		<p>
			<acme:textarea code="section.list.pictures" path="pictures"/>
		</p>
		
		<p>
		<acme:submit name="save" code="section.edit.save"/>
		
		<acme:cancel url="activity/administrator/display.do?idActivity=${idActivity}&url=${url}" code="section.display.back"/>

	</form:form>
	
