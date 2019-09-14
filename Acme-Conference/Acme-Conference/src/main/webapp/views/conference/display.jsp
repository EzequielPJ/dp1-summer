<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags" %>


<acme:cancel url="${url}" code="conference.display.back"/> 

<security:authorize access="hasRole('AUTHOR')">
	<jstl:if test="${avaliable }">
		<acme:button url="registration/author/create.do?conferenceId=${conference.id}" type="button" code="conference.list.register"/>
	</jstl:if>
</security:authorize>

<jstl:if test= "${showButton}">
	<acme:button url="conference/administrator/downloadConference.do?idConference=${conference.id}" type="button" code="conference.display.download"/>
</jstl:if>

<acme:text label="conference.list.title" value="${conference.title}"/>
<acme:text label="conference.list.acronym" value="${conference.acronym}"/>
<acme:text label="conference.list.venue" value="${conference.venue}"/>
<acme:text label="conference.list.submissionDeadline" value="${conference.submissionDeadline}"/>
<acme:text label="conference.list.notificationDeadline" value="${conference.notificationDeadline}"/>
<acme:text label="conference.list.cameraReadyDeadline" value="${conference.cameraReadyDeadline}"/>
<acme:text label="conference.list.startDate" value="${conference.startDate}"/>
<acme:text label="conference.list.endDate" value="${conference.endDate}"/>
<acme:text label="conference.list.summary" value="${conference.summary}"/>
<acme:text label="conference.list.fee" value="${conference.fee}"/>


<jstl:if test="${lang eq 'en' }">
<acme:text label="conference.list.category" value="${conference.category.categoryEN}"/>
</jstl:if>
<jstl:if test="${lang eq 'es' }">
<acme:text label="conference.list.category" value="${conference.category.categoryES}"/>
</jstl:if>

<hr>

		
		<h3><spring:message code="gomela.list.gomelas" /></h3>

		<display:table pagesize="5" name="gomelas" id="gomela" requestURI="${requestURI}">
		
			<jstl:choose>
		
				<jstl:when test="${gomela.publicationMoment gt aMonthAgo}">
					
					<display:column class="lessAMonth" titleKey="gomela.list.ticker"><jstl:out value="${gomela.ticker.identifier}" /></display:column>
					<display:column class="lessAMonth" titleKey="gomela.list.body" ><jstl:out value="${gomela.body}"/></display:column>
					
					<display:column class="lessAMonth" titleKey="gomela.list.publicationMoment">
						<jstl:if test="${!gomela.finalMode}">
							<jstl:out value="N/A" />
						</jstl:if>
			
						<jstl:if test="${gomela.finalMode}">
							<spring:message code="gomela.date.format" var="format" />
							<fmt:formatDate value="${gomela.publicationMoment}" pattern="${format}" />
						</jstl:if>
					</display:column>
			
					<display:column class="lessAMonth" titleKey="gomela.list.display">
					<jstl:if test= "${bot}">
						<acme:button url="gomela/administrator/display.do?idGomela=${gomela.id}" type="button" code="gomela.list.display" />
					</jstl:if>
					<jstl:if test= "${!bot}">
						<acme:button url="gomela/display.do?idGomela=${gomela.id}" type="button" code="gomela.list.display" />
					</jstl:if>
					</display:column>
			
				</jstl:when>
				
				<jstl:when test="${(gomela.publicationMoment lt twoMonthAgo) or (gomela.publicationMoment eq null)}">
				
					<display:column class="moreThanTwoMonths" titleKey="gomela.list.ticker"><jstl:out value="${gomela.ticker.identifier}" /></display:column>
					<display:column class="moreThanTwoMonths" titleKey="gomela.list.body" ><jstl:out value="${gomela.body}"/></display:column>
					
					<display:column class="moreThanTwoMonths" titleKey="gomela.list.publicationMoment">
			
						<jstl:if test="${!gomela.finalMode}">
							<jstl:out value="N/A" />
						</jstl:if>
			
						<jstl:if test="${gomela.finalMode}">
							<spring:message code="gomela.date.format" var="format" />
							<fmt:formatDate value="${gomela.publicationMoment}" pattern="${format}" />
						</jstl:if>
			
					</display:column>
			
					<display:column class="moreThanTwoMonths" titleKey="gomela.list.display">
					<jstl:if test= "${bot}">
						<acme:button url="gomela/administrator/display.do?idGomela=${gomela.id}" type="button" code="gomela.list.display" />
					</jstl:if>
					<jstl:if test= "${!bot}">
						<acme:button url="gomela/display.do?idGomela=${gomela.id}" type="button" code="gomela.list.display" />
					</jstl:if>
					</display:column>
			
				</jstl:when>
			
				<jstl:otherwise>
				
					<display:column class="betweenMonths" titleKey="gomela.list.ticker"><jstl:out value="${gomela.ticker.identifier}" /></display:column>
					<display:column class="betweenMonths" titleKey="gomela.list.body" ><jstl:out value="${gomela.body}"/></display:column>
					
					<display:column class="betweenMonths" titleKey="gomela.list.publicationMoment">
			
						<jstl:if test="${!gomela.finalMode}">
							<jstl:out value="N/A" />
						</jstl:if>
			
						<jstl:if test="${gomela.finalMode}">
							<spring:message code="gomela.date.format" var="format" />
							<fmt:formatDate value="${gomela.publicationMoment}" pattern="${format}" />
						</jstl:if>
			
					</display:column>
			
					<display:column class="betweenMonths" titleKey="gomela.list.display">
					<jstl:if test= "${bot}">
						<acme:button url="gomela/administrator/display.do?idGomela=${gomela.id}" type="button" code="gomela.list.display" />
					</jstl:if>
					<jstl:if test= "${!bot}">
						<acme:button url="gomela/display.do?idGomela=${gomela.id}" type="button" code="gomela.list.display" />
					</jstl:if>
					</display:column>
			
				</jstl:otherwise>
			
			</jstl:choose>
 		
 		</display:table>


<div id="sponsor" style="width: 50px;">
	<a target="_blank" href="${sponsorshipRandom.targetURL}" ><img style="width: 200px;" src="${sponsorshipRandom.bannerURL}" alt="${sponsorshipRandom.targetURL}"/></a>
</div>

		


