<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<h2><spring:message code="${title}"/></h2>

<security:authorize access="hasRole('AUTHOR')"> 
		<acme:button url="submission/author/list.do" type="button" code="submission.list.all"/>
		<acme:button url="submission/author/listAccepted.do" type="button" code="submission.list.accepted"/>
		<acme:button url="submission/author/listRejected.do" type="button" code="submission.list.rejected"/>
		<acme:button url="submission/author/listUnderReview.do" type="button" code="submission.list.underReview"/>
		
		<br/><acme:button url="submission/author/create.do" type="button" code="submission.list.create"/>
</security:authorize>

<security:authorize access="hasRole('ADMINISTRATOR')"> 
		<acme:button url="submission/administrator/list.do" type="button" code="submission.list.all"/>
		<acme:button url="submission/administrator/listAccepted.do" type="button" code="submission.list.accepted"/>
		<acme:button url="submission/administrator/listRejected.do" type="button" code="submission.list.rejected"/>
		<acme:button url="submission/administrator/listUnderReview.do" type="button" code="submission.list.underReview"/>
	
</security:authorize>

<display:table pagesize="5" name="submissions" id="submission" requestURI="${requestURI}">
	
	<display:column titleKey="submission.list.ticker"><jstl:out value="${submission.ticker.identifier}" /></display:column>
	
	<display:column titleKey="submission.list.conference"><jstl:out value="${submission.conference.title}" /></display:column>
	
	<display:column titleKey="submission.list.status"><jstl:out value="${submission.status}" /></display:column>
	
	<display:column titleKey="submission.list.display">
	<security:authorize access="hasRole('AUTHOR')">  
		 <acme:button url="submission/author/display.do?idSubmission=${submission.id}" type="button" code="submission.list.display"/>
	</security:authorize>
	<security:authorize access="hasRole('ADMINISTRATOR')">  
		 <acme:button url="submission/administrator/display.do?idSubmission=${submission.id}" type="button" code="submission.list.display"/>
	</security:authorize>
	<security:authorize access="hasRole('REVIEWER')">  
		 <acme:button url="submission/reviewer/display.do?idSubmission=${submission.id}" type="button" code="submission.list.display"/>
	</security:authorize>
	</display:column>
	 
	 <security:authorize access="hasRole('AUTHOR')"> 
		 <display:column titleKey="submission.list.addCameraReadyVersion">
			 <jstl:if test="${submissionsCanAddCameraVersion.contains(submission)}">
				 <acme:button url="paper/author/create.do?idSubmission=${submission.id}" type="button" code="submission.list.addCameraReadyVersion"/>
			 </jstl:if>
		 </display:column>
	 </security:authorize>
	
</display:table>



