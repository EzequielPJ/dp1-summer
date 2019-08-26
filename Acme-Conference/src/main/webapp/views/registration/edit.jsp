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

	<form:form action="registration/author/save.do" modelAttribute="registration">
		
		<acme:hidden path="id"/>
		<acme:hidden path="conference"/>
		<acme:hidden path="moment"/>
		<acme:hidden path="author"/>
		
		<acme:textbox code="registration.edit.holder" path="creditCard.holder"/>	
		<form:label path="creditCard.brandName"><spring:message code="registration.edit.brandName"/></form:label> 
     	<form:select path="creditCard.brandName" multiple="false" > 
	     		<jstl:forEach items="${makers }" var="make"> 
	     			<form:option value="${make}" label="${make}" /> 
	     		</jstl:forEach> 
   		</form:select> 	
   		<form:errors path="creditCard.brandName" class="error" />
		<acme:inputNumber code="registration.edit.number" path="creditCard.number"/>
		<acme:inputNumber code="registration.edit.expirationMonth" path="creditCard.expirationMonth" />
		<acme:inputNumber code="registration.edit.expirationYear" path="creditCard.expirationYear" />
		<acme:inputNumber code="registration.edit.cvv" path="creditCard.cvv"/>
		
		<acme:submit name="save" code="registration.edit.save"/>
		<acme:cancel url="registration/author/list.do" code="registration.edit.cancel"/>
		
	</form:form>

</section>