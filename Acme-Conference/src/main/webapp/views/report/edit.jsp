<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<section>

	<form:form action="report/reviewer/save.do" modelAttribute="report">
		
		<acme:hidden path="id"/>
		<acme:hidden path="submission"/>
		

		<acme:inputNumber code="report.edit.originalityScore" path="originalityScore"/>
		<acme:inputNumber code="report.edit.qualityScore" path="qualityScore" />
		<acme:inputNumber code="report.edit.readibilityScore" path="readibilityScore" />
		
		<spring:message code="report.edit.decision" /><acme:text value="decision"/>
		<br/>
		<spring:message code="report.edit.comments" />
		<jstl:forEach var="comment" items="${report.comments}">
    	   	 <form:input class="textbox" path="comments" type="text" value="${comment}"/>       	
    	</jstl:forEach>
    	<form:errors path="comments" cssClass="error" />
		
		<acme:submit name="save" code="report.edit.save"/>
		<acme:cancel url="report/reviewer/list.do" code="report.edit.cancel"/>
		
	</form:form>
	<button class="addComment" onclick="addComment('comments','comments', 'textbox')"><spring:message code="report.edit.addComment" /></button>

</section>