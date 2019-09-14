<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<acme:cancel url="sponsorship/sponsor/list.do" code="sponsorship.display.back" />

<section id="main">

	<section id="displaySponsorship">
	
		<h3><spring:message code="sponsorship.display.info"/></h3>
		<hr>	
		
		
		<div>
		
		
	 	<strong><spring:message code="sponsorship.display.targetPageURL"/></strong> <a target="_blank" href="${sponsorship.targetURL}"> <jstl:out value="${sponsorship.targetURL}"></jstl:out></a>
		<p><strong><spring:message code="sponsorship.display.banner"/></strong></p>
		<img style="width: 100%;"  src="${sponsorship.bannerURL}"/>
		
		</div>

		
		<hr>
		
	
 
	</section>
	
	<section id="displayConferences">
		
	
		<h3><spring:message code="sponsorship.display.conferences"/></h3>
		<hr>
		
		<display:table pagesize="5" name="sponsorship.conferences" id="conference" requestURI="${requestURI}">
 				<display:column titleKey="sponsorship.display.conferenceTitle"><jstl:out value="${conference.title}"/></display:column>
	   		 	<display:column titleKey="sponsorship.display.conferenceDisplay"><acme:button url="conference/display.do?idConference=${conference.id}&urlBack=${requestURI}" type="button" code="sponsorship.display.conferenceDisplay"/></display:column>
		</display:table>
		
		<hr>
	
	</section>
	
	<section id="displayCreditCard">
		
	
		<h3><spring:message code="sponsorship.display.creditCard"/></h3>
		<hr>
		
		<p><strong><spring:message code="sponsorship.display.brandName"/></strong> <jstl:out value="${sponsorship.creditCard.brandName}"/>
		<jstl:out value="  |  "/> <strong><spring:message code="sponsorship.display.holder"/></strong> <jstl:out value="${sponsorship.creditCard.holder}"/>
		
		<p><strong><spring:message code="sponsorship.display.number"/></strong> <jstl:out value="${anonymizedNumber}"/>
		<jstl:out value="  |  "/> <strong><spring:message code="sponsorship.display.expirationMonth"/></strong> <jstl:out value="${sponsorship.creditCard.expirationMonth}"/>
		<jstl:out value="  |  "/> <strong><spring:message code="sponsorship.display.expirationYear"/></strong> <jstl:out value="${sponsorship.creditCard.expirationYear}"/> </p>
		
		<hr>
	
	</section>

<hr>
</section>



<style>
 #main {
	float: left;
	width: 100%
}
 #main > hr{
	float: left;
	margin-top: 50px;
	width: 100%;
}

#displaySponsorship {
	float: left;
	width: 45%;
	padding: 1% 2.5%;
}

#displayConferences {
	float: left;
	width: 45%;
	padding: 1% 2.5%;
}

#displayCreditCard {
	float: left;
	width: 45%;
	padding: 1% 2.5%;
}

 .botones{
  	margin-left: 70px;
  	float: right;
}

.botones > button{
	margin-left: 10px;
}
</style>

