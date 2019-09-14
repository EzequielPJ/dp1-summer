<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<jstl:if test="${bot}">
<acme:cancel url="${backURL}" code="gomela.display.back" />
</jstl:if>

<section id="main">

	<section id="displayGomelas">
	
		<hr>	
		
		
		
		<div>
		
			<p><strong><spring:message code="gomela.display.ticker"/>: </strong><jstl:out value="${gomela.ticker.identifier}"></jstl:out></p>
		 	<p><strong><spring:message code="gomela.display.picture"/>: </strong></p>
		 	<img style="width: 200px; height: 200px;"  src="${gomela.picture}"/>
		 	
		 	<p><strong><spring:message code="gomela.display.publicationMoment"/>: </strong>
		 		<jstl:choose>
					<jstl:when test="${gomela.finalMode == true}">
						<spring:message code="gomela.date.format" var="format" />
						<fmt:formatDate value="${gomela.publicationMoment}" pattern="${format}" />
					</jstl:when>
					<jstl:otherwise>
						<jstl:out value="N/A"/>
					</jstl:otherwise>
				</jstl:choose>
		 	</p>
		 	<p><strong><spring:message code="gomela.display.body"/>: </strong><jstl:out value="${gomela.body}"></jstl:out></p>
		 	<p><strong><spring:message code="gomela.display.titleConference"/>: </strong><jstl:out value="${gomela.conference.title}"></jstl:out></p>
			
			
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

#displayGomela{
	float: left;
	width: 45%;
	padding: 1% 2.5%;
}
</style>

