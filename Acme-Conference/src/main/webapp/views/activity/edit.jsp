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

<script>
	function change(o) {
		if (o.value == 'PANEL' || o.value == 'TUTORIAL') {
			document.getElementById("speakers").style.display = 'block';
			document.getElementById("ready").style.display = 'none';
		} else if (o.value == 'PRESENTATION') {
			document.getElementById("speakers").style.display = 'none';
			document.getElementById("ready").style.display = 'block';
		} else {
			document.getElementById("speakers").style.display = 'none';
			document.getElementById("ready").style.display = 'none';
		}
	}
	
	function load() {
		document.getElementById("speakers").style.display = 'block';
		document.getElementById("ready").style.display = 'none';
	}
	function load2() {
		document.getElementById("speakers").style.display = 'none';
		document.getElementById("ready").style.display = 'block';
	}
</script>

<form:form modelAttribute="activity" action="activity/administrator/edit.do">
		<acme:hidden path="id"/>
		<acme:hidden path="conference"/>
		
		<jstl:if test="${!typ.equals('PRESENTATION')}">
			<body onload="load();"></body>
		</jstl:if>
		<jstl:if test="${typ.equals('PRESENTATION')}">
			<body onload="load2();"></body>
		</jstl:if>
		
		<p><spring:message code="activity.list.type"/>:
		<select name="type" onchange="change(this);">
		<jstl:forEach var="type" items="${typeList}">
			<option value="${type}"<jstl:if test="${typ.equals(type)}">selected</jstl:if>><jstl:out value="${type}"/></option>
		</jstl:forEach>
		</select>
		<form:errors cssClass="error" path="type" />
		</p>
		
		
		<div id="speakers"><p><spring:message code="activity.list.authors"/>:<p>
		<select name="authors" onchange="javascript: return true;" multiple>
		<jstl:forEach var="author" items="${authors}">
			 <option value="${author.id}" <jstl:if test="${paper.authors.contains(author)}">selected</jstl:if>><jstl:out value="${author.name} ${author.middlename} ${author.surname}"/></option>	
		</jstl:forEach>
		</select>
		<form:errors cssClass="error" path="authors" />
		</p>
		</div>
		
		<div id="ready"><p><spring:message code="activity.list.paper"/>:<p>
		<select name="paper" onchange="javascript: return true;">
		<jstl:forEach var="paper" items="${papers}">
			 <option value="${paper.id}" <jstl:if test="${paper eq paper}">selected</jstl:if>><jstl:out value="${paper.title}"/></option>	
		</jstl:forEach>
		</select>
		<form:errors cssClass="error" path="paper" />
		</p>
		<jstl:if test="${empty papers}"><strong><spring:message code="activity.paper.no"/></strong></jstl:if>
		</div>
		
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
		
		<jstl:if test="${activity.id == 0}">
		<acme:cancel url="conference/administrator/list.do"  code="conference.display.back"/>
		</jstl:if>
		<jstl:if test="${activity.id != 0}">
		<acme:cancel url="activity/administrator/list.do?idConference=${idConference}"  code="conference.display.back"/>
		</jstl:if>
	</form:form>
	
