<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<display:table pagesize="5" name="activities" id="activity" requestURI="${requestURI}">

			<display:column titleKey="activity.list.type"><jstl:out value="${activity.type}"/></display:column>
			<display:column titleKey="activity.list.title"><jstl:out value="${activity.title}"/></display:column>
			<display:column titleKey="activity.list.startMoment"><jstl:out value="${activity.startMoment}"/></display:column>
			<display:column titleKey="activity.list.duration"><jstl:out value="${activity.duration}"/></display:column>
			<display:column titleKey="activity.list.room"><jstl:out value="${activity.startMoment}"/></display:column>
			<display:column titleKey="activity.list.summary"><jstl:out value="${activity.startMoment}"/></display:column>
			<display:column titleKey="activity.list.attachments"><jstl:out value="${activity.startMoment}"/></display:column>

<%-- 				<display:column titleKey="comment.list.seeMore">
					<acme:button url="comment/display.do?idComment=${conference.id}" type="button" code="comment.list.seeMore"/>
				</display:column> --%>


<%-- 				
				<display:column titleKey="conference.list.edit">
					<jstl:if test="${conference.finalMode eq false}">
						<acme:button url="conference/administrator/edit.do?idConference=${conference.id}" type="button" code="conference.list.edit"/>
					</jstl:if>
				</display:column>
				<display:column titleKey="conference.list.delete">
					<jstl:if test="${conference.finalMode eq false}">
						<acme:button url="conference/administrator/delete.do?idConference=${conference.id}" type="button" code="conference.list.delete"/>
					</jstl:if>
				</display:column>  --%>
				
			<jstl:if test="${general}">
					<display:column titleKey="activity.list.comment">
						<acme:button url="comment/list.do?idEntity=${activity.id}" type="button" code="conference.list.comment"/>
					</display:column>
					<display:column titleKey="activity.list.comment.create">
						<acme:button url="comment/create.do?idActivity=${activity.id}" type="button" code="conference.list.comment.create"/>
					</display:column>
				</jstl:if>
				
			
				
</display:table>