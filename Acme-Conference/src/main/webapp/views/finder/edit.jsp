<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>


<security:authorize access="hasRole('AUTHOR')">

	<section>

		<form:form id="editFinder" action="finder/author/edit.do"
			modelAttribute="finder" method="post">

			<acme:textbox code="finder.edit.keyword" path="keyWord" />		
			<acme:inputDate code="finder.edit.minimumDate" path="minimumDate" />
			<acme:inputDate code="finder.edit.maximumDate" path="maximumDate" />
			<acme:inputDouble code="finder.edit.maximumFee" path="maximumFee" val="${finder.maximumFee}"/>
			
			<jstl:choose>
				<jstl:when test="${cookie.language.value == 'es'}">
					<acme:select items="${categories}" itemLabel="categoryES" code="finder.edit.category" path="category"/>
				</jstl:when>
				
				<jstl:otherwise>
					<acme:select items="${categories}" itemLabel="categoryEN" code="finder.edit.category" path="category"/>
				</jstl:otherwise>
			</jstl:choose>
			
			<acme:cancel url="/" code="finder.edit.cancel" />
			<acme:submit name="save" code="finder.edit.save" />
			

		</form:form>

	</section>
	
	<section>
	
		<display:table pagesize="10" name="conferences" id="conference" requestURI="${requestURI}">
			<display:column titleKey="finder.edit.conference.title"><jstl:out value="${conference.title}"/></display:column>
			<display:column titleKey="finder.edit.conference.acronym"><jstl:out value="${conference.acronym}"/></display:column>
			<display:column titleKey="finder.edit.conference.venue"><jstl:out value="${conference.venue}"/></display:column>			
			<display:column titleKey="finder.edit.conference.summary"><jstl:out value="${conference.summary}"/></display:column>
			<display:column titleKey="finder.edit.conference.startDate"><jstl:out value="${conference.startDate}"/></display:column>
			<display:column titleKey="finder.edit.conference.endDate"><jstl:out value="${conference.endDate}"/></display:column>
			<display:column titleKey="finder.edit.conference.fee"><jstl:out value="${conference.fee}"/></display:column>
			<display:column titleKey="finder.edit.conference.category">
				<jstl:choose>
					<jstl:when test="${cookie.language.value == 'es'}">
						<jstl:out value="${conference.category.categoryES}"/>
					</jstl:when>
					
					<jstl:otherwise>
						<jstl:out value="${conference.category.categoryEN}"/>
					</jstl:otherwise>
				</jstl:choose>
			</display:column>
			<display:column titleKey="finder.edit.conference.display">
				<acme:button url="conference/display.do?idConference=${conference.id}&url=${requestURI}" type="button" code="finder.edit.conference.display"/>
			</display:column>
			
		</display:table>
	
	</section>




</security:authorize>


