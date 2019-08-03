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

<acme:button url="topic/administrator/create.do" type="button" code="topic.list.create"/>

<section>

		<display:table name="topics" id="topic" pagesize="5" requestURI="${requestURI}">
		
			<display:column titleKey="topic.list.name" ><jstl:out value="${topic.topicEN}"/> / <jstl:out value="${topic.topicES}"/></display:column>
		
			<display:column titleKey="topic.list.edit" ><acme:button url="topic/administrator/edit.do?idTopic=${topic.id}" type="button" code="topic.list.edit"/></display:column>
			<display:column titleKey="topic.list.delete" ><acme:button url="topic/administrator/delete.do?idTopic=${topic.id}" type="button" code="topic.list.delete"/></display:column>
			
		</display:table>

</section>




