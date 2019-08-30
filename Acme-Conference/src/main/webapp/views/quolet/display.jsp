<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<acme:cancel url="quolet/administrator/list.do" code="quolet.display.back" />

<section id="main">

	<section id="displayQuolets">
	
		<hr>	
		
		
		
		<div>
		
			<p><strong><spring:message code="quolet.display.ticker"/>: </strong><jstl:out value="${quolet.ticker.identifier}"></jstl:out></p>
		 	<p><strong><spring:message code="quolet.display.title"/>: </strong><jstl:out value="${quolet.title}"></jstl:out></p>
		 	<p><strong><spring:message code="quolet.display.publicationMoment"/>: </strong>
		 		<jstl:choose>
					<jstl:when test="${quolet.finalMode == true}">
						<spring:message code="quolet.date.format" var="format" />
						<fmt:formatDate value="${quolet.publicationMoment}" pattern="${format}" />
					</jstl:when>
					<jstl:otherwise>
						<jstl:out value="N/A"/>
					</jstl:otherwise>
				</jstl:choose>
		 	</p>
		 	<p><strong><spring:message code="quolet.display.body"/>: </strong><jstl:out value="${quolet.body}"></jstl:out></p>
		 	<p><strong><spring:message code="quolet.display.atributoDos"/>: </strong><jstl:out value="${quolet.atributoDos}"></jstl:out></p>
		 	<p><strong><spring:message code="quolet.display.titleConference"/>: </strong><jstl:out value="${quolet.conference.title}"></jstl:out></p>
			
			
		</div>

		
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

#displayQuolet{
	float: left;
	width: 45%;
	padding: 1% 2.5%;
}
</style>

