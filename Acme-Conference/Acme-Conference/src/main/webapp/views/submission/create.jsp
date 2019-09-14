<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<form:form modelAttribute="submissionPaperForm" action="submission/author/save.do">
	  	 
	<p><spring:message code="submission.create.paper.conference"/>:
	<form:select id="conference" path="conference">
		<form:options items="${conferences}" itemLabel="title" itemValue="id" />
	</form:select> 
	<form:errors cssClass="error" path="conference" />
	</p>
	
   	<p>
   		 <acme:textbox code="submission.create.paper.title" path="title"/>
   	</p>
   	<p>
   		 <acme:textbox code="submission.create.paper.documentUrl" path="documentUrl"/>
   	</p>
   	<p>
   		 <acme:textarea code="submission.create.paper.summary" path="summary"/>
   	</p>
   	
	<p><spring:message code="submission.create.paper.authors"/>:
	<select name="authors" onchange="javascript: return true;" multiple>
		<jstl:forEach var="author" items="${authors}">
			<option value="${author.id}" <jstl:if test="${submissionPaperForm.authors.contains(author)}">selected</jstl:if>><jstl:out value="${author.name} ${author.middlename} ${author.surname}"/></option>	
		</jstl:forEach>
	</select>
	<form:errors cssClass="error" path="authors" />
	</p>
	
	<div id="aliases"> 
	<spring:message code="submission.create.paper.aliases"/>
   	<jstl:if test="${empty submissionPaperForm.aliases}">
   		<form:input class="textbox" path="aliases" type="text"/>    
   	</jstl:if>
   	
   	<jstl:forEach var="alias" items="${submissionPaperForm.aliases}">
   	   	 <form:input class="textbox" path="aliases" type="text" value="${alias}"/>       	
   	</jstl:forEach>
   	<form:errors cssClass="error" path="aliases" />
	</div>
   	 

   	<acme:submit name="save" code="submission.create.save"/>

</form:form>

<button class="addTag" onclick="addComment('aliases','aliases', 'textbox')"><spring:message code="submission.create.paper.addAlias" /></button>

<acme:button code="submision.create.cancel" type="button" url="/submission/author/list.do"/>



