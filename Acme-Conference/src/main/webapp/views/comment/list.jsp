<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<display:table pagesize="5" name="comments" id="comment" requestURI="${requestURI}">

			<display:column titleKey="comment.list.title"><jstl:out value="${comment.title}"/></display:column>
			<display:column titleKey="comment.list.moment"><jstl:out value="${comment.moment}"/></display:column>
			<display:column titleKey="comment.list.text"><jstl:out value="${comment.text}"/></display:column>
			<jstl:if test="${comment.actor.name == null}">
			<display:column titleKey="comment.list.actor"><jstl:out value="${anonim}"/></display:column>
			</jstl:if>
			<jstl:if test="${comment.actor.name != null}">
			<display:column titleKey="comment.list.actor"><jstl:out value="${comment.actor.name}"/></display:column>
			</jstl:if>
			

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
				
			
				
</display:table>