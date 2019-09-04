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

	<form:form action="quolet/administrator/save.do" modelAttribute="quolet">
		
		<acme:hidden path="id"/>
		
		<jstl:if test="${quolet.id == 0}">
			<acme:hidden path="conference"/>
		</jstl:if>	
		
		<acme:textbox code="quolet.edit.title" path="title"/>
		<acme:textarea code="quolet.edit.body" path="body"/>
		<acme:textbox code="quolet.edit.atributoDos" path="atributoDos"/>
		<acme:checkbox code="quolet.edit.finalMode" path="finalMode"/>
		
		<acme:submit name="save" code="quolet.edit.save"/>
		<acme:cancel url="conference/administrator/list.do" code="quolet.edit.cancel"/>
		
	</form:form>

</section>