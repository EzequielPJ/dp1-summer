<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

	<section>

		<form:form id="search" action="search/display.do" modelAttribute="searchForm" method="post">	
			<acme:textbox code="search.keyword" path="keyword" />
			<acme:submit name="save" code="search.save" />
		</form:form>

	</section>
	
	<section>
	
		<display:table pagesize="10" name="conferences" id="conference" requestURI="${requestURI}">
			<display:column titleKey="search.conference.title"><jstl:out value="${conference.title}"/>
			</display:column>
			<display:column titleKey="search.conference.venue"><jstl:out value="${conference.venue}"/></display:column>			
			<display:column titleKey="search.conference.summary"><jstl:out value="${conference.summary}"/></display:column>
			<display:column titleKey="search.conference.startDate"><jstl:out value="${conference.startDate}"/></display:column>
			<display:column titleKey="search.conference.endDate"><jstl:out value="${conference.endDate}"/></display:column>
			<display:column titleKey="search.conference.display">
				<acme:button url="conference/display.do?idConference=${conference.id}&url=${requestURI}" type="button" code="search.conference.display"/>
			</display:column>
			
		</display:table>
	
	</section>

