<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags" %>

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
	</display:column>
	 
	 <security:authorize access="hasRole('AUTHOR')"> 
		 <display:column titleKey="submission.list.addCameraReadyVersion">
			 <jstl:if test="${submissionsCanAddCameraVersion.contains(submission)}">
				 <acme:button url="paper/author/create.do?idSubmission=${submission.id}" type="button" code="submission.list.addCameraReadyVersion"/>
			 </jstl:if>
		 </display:column>
	 </security:authorize>
	
</display:table>



