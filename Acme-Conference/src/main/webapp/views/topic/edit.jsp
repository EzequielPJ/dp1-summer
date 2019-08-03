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
<%@taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<form:form modelAttribute="topic" action="topic/administrator/save.do" method="Post">
	
		<acme:hidden path="id"/>
		<acme:hidden path="version"/>
	
		<acme:textbox code="topic.edit.topicEN" path="topicEN"/>
		<acme:textbox code="topic.edit.topicES" path="topicES"/>
		
		<acme:submit name="save" code="topic.edit.save"/>
		<acme:button url="topic/administrator/list.do" type="button" code="topic.edit.back"/>

</form:form>




