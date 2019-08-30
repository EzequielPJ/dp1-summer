<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<h3><jstl:out value="${tit}"></jstl:out></h3>

<jstl:if test="${sel}">
<div>
	<ul class="jMenu">
		<li>
			<a class="fNiv"><spring:message	code="conference.list.byCategory" /></a>
			<ul>
		r		<li class="arrow"></li>
				<jstl:forEach var="category" items="${categories}">				
					<li>
					<jstl:choose>
						<jstl:when test="${cookie.language.value == 'es'}">
							<a href="conference/administrator/listByCategory.do?idCategory=${category.id}"><jstl:out value="${category.categoryES}"/></a>
						</jstl:when>
						
						<jstl:otherwise>
							<a href="conference/administrator/listByCategory.do?idCategory=${category.id}"><jstl:out value="${category.categoryEN}"/></a>   
						</jstl:otherwise>
					</jstl:choose>
					</li>
				</jstl:forEach>
			</ul>
		</li>
	</ul>
</div>
</jstl:if>

<jstl:if test="${!general && sel}">
<acme:button url="conference/administrator/create.do" type="button" code="master.page.administrator.create"/>
</jstl:if>

<display:table pagesize="5" name="conferences" id="conference" requestURI="${requestURI}">

			<display:column titleKey="conference.list.title"><jstl:out value="${conference.title}"/></display:column>
			<display:column titleKey="conference.list.startDate"><jstl:out value="${conference.startDate}"/></display:column>
			<display:column titleKey="conference.list.endDate"><jstl:out value="${conference.endDate}"/></display:column>
			<display:column titleKey="conference.list.summary"><jstl:out value="${conference.summary}"/></display:column>
			<display:column titleKey="conference.list.fee"><jstl:out value="${conference.fee}"/></display:column>
			<jstl:if test="${!general}">
			<jstl:if test="${lang eq 'en' }">
			<display:column titleKey="conference.list.category"><jstl:out value="${conference.category.categoryEN}"/></display:column>
			</jstl:if>
			<jstl:if test="${lang eq 'es' }">
			<display:column titleKey="conference.list.category"><jstl:out value="${conference.category.categoryES}"/></display:column>
			</jstl:if>
			</jstl:if>
			
			
				<jstl:if test="${!general}">
				<display:column titleKey="conference.list.seeMore">
					<acme:button url="conference/administrator/display.do?idConference=${conference.id}&url=${requestURI}" type="button" code="conference.list.seeMore"/>
				</display:column>
				</jstl:if>
				<jstl:if test="${general}">
				<display:column titleKey="conference.list.seeMore">
					<acme:button url="conference/display.do?idConference=${conference.id}&url=${requestURI}" type="button" code="conference.list.seeMore"/>
				</display:column>
				</jstl:if>
				
				<jstl:if test="${!general}">
				
				<display:column titleKey="conference.list.edit">
					<jstl:if test="${conference.finalMode eq false}">
						<acme:button url="conference/administrator/edit.do?idConference=${conference.id}" type="button" code="conference.list.edit"/>
					</jstl:if>
				</display:column>
				<display:column titleKey="conference.list.delete">
					<jstl:if test="${conference.finalMode eq false}">
						<acme:button url="conference/administrator/delete.do?idConference=${conference.id}" type="button" code="conference.list.delete"/>
					</jstl:if>
				</display:column> 
				<display:column titleKey="conference.list.decisionMakingProcess">
					<jstl:if test="${conferencesToDecisionMaking.contains(conference)}">
						<acme:button url="conference/administrator/decisionMaking.do?idConference=${conference.id}" type="button" code="conference.list.decisionMaking"/>
					</jstl:if>
				</display:column>
				<display:column titleKey="administrator.process.assignReviewers" >
				<jstl:if test="${conferencesToAssingReviewers.contains(conference)}">
					<acme:button code="administrator.process.assign" type="button" url="conference/administrator/assignReviewers.do?idConference=${conference.id}" />
				</jstl:if>
				</display:column>
				</jstl:if>
				
			
				<jstl:if test="${!general}">
				<display:column titleKey="conference.list.activity">
				<jstl:if test="${conference.finalMode eq true}">
						<acme:button url="activity/administrator/list.do?idConference=${conference.id}" type="button" code="conference.list.activity"/>
				</jstl:if>
				</display:column>
				<display:column titleKey="conference.list.activity.create">
				<jstl:if test="${conference.finalMode eq true}">
						<acme:button url="activity/administrator/create.do?idConference=${conference.id}" type="button" code="conference.list.activity.create"/>
				</jstl:if>
				</display:column>
				</jstl:if>
				
				<jstl:if test="${general}">
				<display:column titleKey="conference.list.activity">
				<jstl:if test="${conference.finalMode eq true}">
						<acme:button url="activity/list.do?idConference=${conference.id}" type="button" code="conference.list.activity"/>
				</jstl:if>
				</display:column>
				</jstl:if>
				
				<jstl:if test="${general}">
					<display:column titleKey="conference.list.comment">
						<acme:button url="comment/list.do?idEntity=${conference.id}" type="button" code="conference.list.comment"/>
					</display:column>
					<display:column titleKey="conference.list.comment.create">
						<acme:button url="comment/create.do?idConference=${conference.id}" type="button" code="conference.list.comment.create"/>
					</display:column>
				</jstl:if>
				
</display:table>


