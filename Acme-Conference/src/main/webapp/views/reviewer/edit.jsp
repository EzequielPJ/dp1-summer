<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>


<script type='text/javascript'>
	function checkPhone(str) {
		if (str != "") {
			var patt = new RegExp("^(\[+][1-9][0-9]{0,2}[ ]{1}\[(][1-9][0-9]{0,2}\[)][ ]{1}[0-9]{4,}|\[+][1-9][0-9]{0,2}[ ]{1}[0-9]{4,}|[0-9]{4,}|[ ]{1,})$");
			if (patt.test(str) == false) { return confirm("<spring:message code="reviewer.edit.phone.error"/>"); }
		}
	}
</script>

<jstl:if test="${edit }">
	<form:form action="reviewer/reviewer/save.do"
		modelAttribute="reviewer">
		<acme:textbox code="reviewer.edit.name" path="name" />
		<acme:textbox code="reviewer.edit.middlename" path="middlename" />
		<acme:textbox code="reviewer.edit.surname" path="surname" /> 
		<acme:textbox code="reviewer.edit.photoURL" path="photoURL" />
		<acme:textbox code="reviewer.edit.address" path="address" />
		<acme:textbox code="reviewer.edit.email" path="email" />
		<acme:textbox code="reviewer.edit.phoneNumber" path="phoneNumber" id="phone" />
		<br />
		<spring:message code="reviewer.edit.submit" var="submit"/>
		<input type="submit" name="submit" onclick="return checkPhone(this.form.phone.value)" value="${ submit}" />
		<acme:cancel url="/actor/display.do" code="reviewer.edit.cancel" />
	</form:form>
</jstl:if>
<jstl:if test="${not edit }">
	<form:form action="reviewer/save.do"
		modelAttribute="reviewerForm">
		<acme:textbox code="reviewer.edit.username"
			path="userAccount.username" />
		<acme:password code="reviewer.edit.password"
			path="userAccount.password" />
		<acme:password code="reviewer.edit.confirmPassword"
			path="confirmPassword" />

		<acme:textbox code="reviewer.edit.name" path="name" />
		<acme:textbox code="reviewer.edit.middlename" path="middlename" />
		<acme:textbox code="reviewer.edit.surname" path="surname" /> 
		<acme:textbox code="reviewer.edit.photoURL" path="photoURL" />
		<acme:textbox code="reviewer.edit.address" path="address" />
		<acme:textbox code="reviewer.edit.email" path="email" />
		<acme:textbox code="reviewer.edit.phoneNumber" path="phoneNumber" id="phone" />
		<br />
		<form:checkbox path="termsAndConditions" />
		<b><spring:message code="reviewer.edit.termsAndConditions" /></b>
		<form:errors path="termsAndConditions" cssClass="error" />
		<br />
		<spring:message code="reviewer.edit.submit" var="submit"/>
		<input type="submit" name="submit" onclick="return checkPhone(this.form.phone.value)" value="${ submit}"/>
		<acme:cancel url="/" code="reviewer.edit.cancel" />
	</form:form>
</jstl:if>