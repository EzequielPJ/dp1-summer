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

		<display:table pagesize="5" name="gomelas" id="gomela" requestURI="${requestURI}">
		
			<display:column titleKey="gomela.list.ticker" ><jstl:out value="${gomela.ticker.identifier}"/></display:column>
			<display:column titleKey="gomela.list.body" ><jstl:out value="${gomela.body}"/></display:column>
			
			<display:column titleKey="gomela.list.publicationMoment" >
				<jstl:choose>
					<jstl:when test="${gomela.finalMode == true}">
						<spring:message code="gomela.date.format" var="format" />
						<fmt:formatDate value="${gomela.publicationMoment}" pattern="${format}" />
					</jstl:when>
					<jstl:otherwise>
						<jstl:out value="N/A"/>
					</jstl:otherwise>
				</jstl:choose>
			</display:column>
			
			<display:column titleKey="gomela.list.titleConference" ><jstl:out value="${gomela.conference.title}"/></display:column>
			
			<display:column titleKey="gomela.list.display">
				<acme:button url="gomela/administrator/display.do?idGomela=${gomela.id}" type="button" code="gomela.list.display"/>
			</display:column>
				
			<display:column titleKey="gomela.list.update">
				<jstl:choose>
					<jstl:when test="${gomela.finalMode == true}">
						<spring:message code="gomela.list.finalMode"/>
					</jstl:when>
					<jstl:otherwise>
						<acme:button url="gomela/administrator/edit.do?idGomela=${gomela.id}" type="button" code="gomela.list.update"/>
					</jstl:otherwise>
				</jstl:choose>
			</display:column>
			
			<display:column titleKey="gomela.list.delete">
				<jstl:choose>
					<jstl:when test="${gomela.finalMode == true}">
						<spring:message code="gomela.list.finalMode"/>
					</jstl:when>
					<jstl:otherwise>
						<acme:button url="gomela/administrator/delete.do?idGomela=${gomela.id}" type="button" code="gomela.list.delete"/>
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
