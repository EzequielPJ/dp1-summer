<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<form:form modelAttribute="paper" action="paper/author/save.do">
	  	 
	<acme:hidden path="submission"/>
	
   	<p>
   		 <acme:textbox code="paper.create.title" path="title"/>
   	</p>
   	<p>
   		 <acme:textbox code="paper.create.documentUrl" path="documentUrl"/>
   	</p>
   	<p>
   		 <acme:textarea code="paper.create.summary" path="summary"/>
   	</p>
   	
	<p><spring:message code="paper.create.authors"/>:
	<select name="authors" onchange="javascript: return true;" multiple>
		<jstl:forEach var="author" items="${authors}">
			<option value="${author.id}" <jstl:if test="${paper.authors.contains(author)}">selected</jstl:if>><jstl:out value="${author.name} ${author.middlename} ${author.surname}"/></option>	
		</jstl:forEach>
	</select>
	<form:errors cssClass="error" path="authors" />
	</p>
	
	<div id="aliases"> 
	<spring:message code="paper.create.aliases"/>
   	<jstl:if test="${empty paper.aliases}">
   		<form:input class="textbox" path="aliases" type="text"/>    
   	</jstl:if>
   	
   	<jstl:forEach var="alias" items="${paper.aliases}">
   	   	 <form:input class="textbox" path="aliases" type="text" value="${alias}"/>       	
   	</jstl:forEach>
   	<form:errors cssClass="error" path="aliases" />
	</div>
   	 

   	<acme:submit name="save" code="paper.create.save"/>

</form:form>

<button class="addTag" onclick="addComment('aliases','aliases', 'textbox')"><spring:message code="paper.create.addAlias" /></button>

<%-- <acme:button code="book.edit.cancel" type="button" url="/book/writer/list.do"/> --%>



