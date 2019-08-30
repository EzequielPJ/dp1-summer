<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags" %> 
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>



<section id="main">

	<section>

		<display:table pagesize="5" name="quolets" id="quolet" requestURI="${requestURI}">
		
			<display:column titleKey="quolet.list.ticker" ><jstl:out value="${quolet.ticker.identifier}"/></display:column>
			<display:column titleKey="quolet.list.title" ><jstl:out value="${quolet.title}"/></display:column>
			
			<display:column titleKey="quolet.list.publicationMoment" >
				<jstl:choose>
					<jstl:when test="${quolet.finalMode == true}">
						<spring:message code="quolet.date.format" var="format" />
						<fmt:formatDate value="${quolet.publicationMoment}" pattern="${format}" />
					</jstl:when>
					<jstl:otherwise>
						<jstl:out value="N/A"/>
					</jstl:otherwise>
				</jstl:choose>
			</display:column>
			
			<display:column titleKey="quolet.list.titleConference" ><jstl:out value="${quolet.conference.title}"/></display:column>
			
			<display:column titleKey="quolet.list.display">
				<acme:button url="quolet/administrator/display.do?idQuolet=${quolet.id}" type="button" code="quolet.list.display"/>
			</display:column>
				
			<display:column titleKey="quolet.list.update">
				<jstl:choose>
					<jstl:when test="${quolet.finalMode == true}">
						<spring:message code="quolet.list.finalMode"/>
					</jstl:when>
					<jstl:otherwise>
						<acme:button url="quolet/administrator/edit.do?idQuolet=${quolet.id}" type="button" code="quolet.list.update"/>
					</jstl:otherwise>
				</jstl:choose>
			</display:column>
			
			<display:column titleKey="quolet.list.delete">
				<jstl:choose>
					<jstl:when test="${quolet.finalMode == true}">
						<spring:message code="quolet.list.finalMode"/>
					</jstl:when>
					<jstl:otherwise>
						<acme:button url="quolet/administrator/delete.do?idQuolet=${quolet.id}" type="button" code="quolet.list.delete"/>
					</jstl:otherwise>
				</jstl:choose>	
			</display:column>
		</display:table>

	</section>

<hr>
</section>


<style>
#main {
	float: left;
	width: 100%;
}

#main>hr{
width: 100%;
float: left;
margin-top: 50px;
}
</style>
