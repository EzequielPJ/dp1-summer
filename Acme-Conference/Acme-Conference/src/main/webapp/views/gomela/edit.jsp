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

	<form:form action="gomela/administrator/save.do" modelAttribute="gomela">
		
		<acme:hidden path="id"/>
		
		<jstl:if test="${gomela.id == 0}">
			<acme:hidden path="conference"/>
		</jstl:if>	
		
		<acme:textarea code="gomela.edit.body" path="body"/>
		<acme:textbox code="gomela.edit.picture" path="picture"/>
		<acme:checkbox code="gomela.edit.finalMode" path="finalMode"/>
		
		<acme:submit name="save" code="gomela.edit.save"/>
		<acme:cancel url="conference/administrator/list.do" code="gomela.edit.cancel"/>
		
	</form:form>

</section>