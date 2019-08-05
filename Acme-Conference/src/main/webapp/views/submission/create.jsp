<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<form:form modelAttribute="submissionPaperForm" action="submission/author/save.do">
	  	 
   	<p>
   		 <acme:textarea code="submission.create.paper.title" path="title"/>
   	</p>
   	<p>
   		 <acme:textarea code="submission.create.paper.summary" path="summary"/>
   	</p>
   	<p>
   		 <acme:textarea code="submission.create.paper.documentUrl" path="documentUrl"/>
   	</p>
   	
	<acme:select items="${authors}" itemLabel="authors" code="submission.create.paper.authors" path="authors" optional="false"/>
	
	<div id="aliases"> 
	<spring:message code="submission.create.paper.aliases"/>
   	<jstl:if test="${empty submissionPaperForm.aliases}">
   		<form:input class="textbox" path="aliases" type="text"/>    
   	</jstl:if>
   	
   	<jstl:forEach var="alias" items="${submissionPaperForm.aliases}">
   	   	 <form:input class="textbox" path="aliases" type="text" value="${alias}"/>       	
   	</jstl:forEach>
	</div>
   	 

   	<acme:submit name="save" code="submission.create.save"/>

</form:form>

<button class="addTag" onclick="addComment('aliases','aliases', 'textbox')"><spring:message code="submission.create.paper.addAlias" /></button>

<%-- <acme:button code="book.edit.cancel" type="button" url="/book/writer/list.do"/> --%>



