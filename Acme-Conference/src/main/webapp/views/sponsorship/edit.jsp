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

	<form:form action="sponsorship/sponsor/edit.do" modelAttribute="sponsorship">
		
		<acme:hidden path="id"/>
		
		<acme:textbox code="sponsorship.edit.targetPageURL" path="targetURL"/>
		<acme:textbox code="sponsorship.edit.bannerURL" path="bannerURL"/>
	
		<acme:select items="${conferences}" itemLabel="title" code="sponsorship.edit.conferences" path="conferences"/>
		
		<acme:textbox code="sponsorship.edit.holder" path="creditCard.holder"/>	
		<form:label path="creditCard.brandName"><spring:message code="sponsorship.edit.brandName"/></form:label> 
     	<form:select path="creditCard.brandName" multiple="false" > 
	     		<jstl:forEach items="${makers }" var="make"> 
	     			<form:option value="${make}" label="${make}" /> 
	     		</jstl:forEach> 
   		</form:select> 	
		<acme:inputNumber code="sponsorship.edit.number" path="creditCard.number"/>
		<acme:inputNumber code="sponsorship.edit.expirationMonth" path="creditCard.expirationMonth" />
		<acme:inputNumber code="sponsorship.edit.expirationYear" path="creditCard.expirationYear" />
		<acme:inputNumber code="sponsorship.edit.cvv" path="creditCard.cvv"/>
		
		<acme:submit name="save" code="sponsorship.edit.save"/>
		<acme:cancel url="sponsorship/sponsor/list.do" code="sponsorship.edit.cancel"/>
		
	</form:form>

</section>