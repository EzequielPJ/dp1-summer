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

<form:form modelAttribute="comment" action="comment/edit.do">
		<acme:hidden path="id"/>
		<acme:hidden path="conference"/>
		<acme:hidden path="activity"/>
		
		<p>
			<acme:textarea code="comment.list.title" path="title"/>
		</p>
		<p>
			<acme:textarea code="comment.list.text" path="text"/>
		</p>
		
		<p>
		<acme:submit name="save" code="comment.edit.save"/>
		<acme:cancel url="welcome/index.do" code="comment.display.back"/>
		
	</form:form>
	
