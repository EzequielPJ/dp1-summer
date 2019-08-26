<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<acme:cancel url="registration/author/list.do" code="registration.display.back" />

<section id="main">
	
		<h3><spring:message code="registration.display.conference"/></h3>
		<hr>
		
		<b><spring:message code="registration.display.conference" />: </b><jstl:out value="${registration.conference.title }" />
		<br/>
		<b><spring:message code="registration.display.conference.startDate" />: </b><jstl:out value="${registration.conference.startDate }" />
		<br/>
		<b><spring:message code="registration.display.conference.endDate" />: </b><jstl:out value="${registration.conference.endDate }" />
		<br/>
		<b><spring:message code="registration.display.date" />: </b><jstl:out value="${registration.moment }" />
		<br/>
		
		<hr>
		
	
		<h3><spring:message code="registration.display.creditCard"/></h3>
		<hr>
		
		<p><strong><spring:message code="registration.display.brandName"/></strong> <jstl:out value="${registration.creditCard.brandName}"/>
		<jstl:out value="  |  "/> <strong><spring:message code="registration.display.holder"/></strong> <jstl:out value="${registration.creditCard.holder}"/>
		
		<p><strong><spring:message code="registration.display.number"/></strong> <jstl:out value="${anonymizedNumber}"/>
		<jstl:out value="  |  "/> <strong><spring:message code="registration.display.expirationMonth"/></strong> <jstl:out value="${registration.creditCard.expirationMonth}"/>
		<jstl:out value="  |  "/> <strong><spring:message code="registration.display.expirationYear"/></strong> <jstl:out value="${registration.creditCard.expirationYear}"/> </p>
		
</section>
