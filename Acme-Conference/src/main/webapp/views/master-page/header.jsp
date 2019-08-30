<%--
 * header.jsp
 *
 * Copyright (C) 2019 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>

<div>
	<a href="#"><img src="${banner}" alt="${systemName} Co., Inc." height="200px" width="500px"/></a>
</div>
<div>
	<ul id="jMenu">
	
		<!-- Conference -->
		
		<security:authorize access="hasRole('ADMINISTRATOR')">
			<li>
				<a class="fNiv"><spring:message	code="master.page.registers" /></a>
				<ul>
					<li class="arrow"></li>
					<li>
						<a href="administrator/administrator/register.do"><spring:message code="master.page.register.administrator" /></a>
					</li>
				</ul>
			</li>
		</security:authorize>
	
		
		<security:authorize access="isAnonymous()">
		<li>
				<a class="fNiv"><spring:message	code="master.page.registers" /></a>
				<ul>
					<li class="arrow"></li>
					<li>
						<a href="author/register.do"><spring:message code="master.page.register.author" /></a>
					</li>
					<li>
						<a href="conferenceSponsor/register.do"><spring:message code="master.page.register.conferenceSponsor" /></a>
					</li>
					
					<li>
						<a href="reviewer/register.do"><spring:message code="master.page.register.reviewer" /></a>
					</li>
				</ul>
		</li>
	
		</security:authorize>
		
		<li>
				<a class="fNiv"><spring:message	code="master.page.conference" /></a>
				<ul>
					<li class="arrow"></li>
					<li>
						<a href="conference/listConferencePast.do"><spring:message code="master.page.listConferencePast" /></a>
					</li>
					<li>
						<a href="conference/listConferenceNow.do"><spring:message code="master.page.listConferenceNow" /></a>
					</li>
					<li>
						<a href="conference/listConferenceFuture.do"><spring:message code="master.page.listConferenceFuture" /></a>
					</li>
					
					<security:authorize access="hasRole('ADMINISTRATOR')">
					<li>
						<a href="conference/administrator/list.do"><spring:message code="master.page.administrator.list" /></a>
					</li>
					<li>
						<a href="conference/administrator/listSubmissionDeadline.do"><spring:message code="master.page.administrator.listSubmissionDeadline" /></a>
					</li>
					<li>
						<a href="conference/administrator/listNotificationDeadline.do"><spring:message code="master.page.administrator.listNotificationDeadline" /></a>
					</li>
					<li>
						<a href="conference/administrator/listCameraReadyDeadline.do"><spring:message code="master.page.administrator.listCameraReadyDeadline" /></a>
					</li>
					<li>
						<a href="conference/administrator/listNextConference.do"><spring:message code="master.page.administrator.listNextConference" /></a>
					</li>
					</security:authorize>
				</ul>
			</li>
	
		
		<li>
			<a class="fNiv" href="search/display.do" ><spring:message code="master.page.search.display" /></a>
		</li>
		
		<security:authorize access="hasRole('REVIEWER')">
			<li>
				<a class="fNiv" href="submission/reviewer/list.do"><spring:message code="master.page.submission.list" /></a>
			</li> 
			<li>
				<a class="fNiv" href="report/reviewer/list.do"><spring:message code="master.page.report.list" /></a>
			</li> 
		</security:authorize>
		
		<!-- List of contests -->
		<security:authorize access="hasRole('AUTHOR')">
		<li>
			<a class="fNiv" href="registration/author/list.do"><spring:message code="master.page.registration.list" /></a>
		</li> 
		<li>
			<a class="fNiv" href="submission/author/list.do"><spring:message code="master.page.submission.list" /></a>
		</li> 
		</security:authorize>
		
		<security:authorize access="hasRole('ADMINISTRATOR')">
		<li>
			<a class="fNiv" href="submission/administrator/list.do"><spring:message code="master.page.submission.list" /></a>
		</li> 
		</security:authorize>
		
		<!-- ADMINISTRATOR -->
		<security:authorize access="hasRole('ADMINISTRATOR')">
			<li>
				<a class="fNiv"><spring:message	code="master.page.administrator" /></a>
				<ul>
					<li class="arrow"></li>
					<li>
						<a href="adminConfig/administrator/display.do"><spring:message code="master.page.administrator.configuration" /></a>
					</li>
					<li>
						<a href="category/administrator/list.do"><spring:message code="master.page.administrator.categories" /></a>
					</li>
					<li>
						<a href="topic/administrator/list.do"><spring:message code="master.page.administrator.topics" /></a>
					</li>
					<li>
					    <a href="dashboard/administrator/display.do"><spring:message code="master.page.header.dashboard" /></a>
					</li>
					<li>
					   <a href="administrator/process.do"><spring:message code="master.page.process.launch" /></a>
					</li>
				</ul>
			</li>
		</security:authorize>
		
		<!-- Finder -->
		<security:authorize access="hasRole('AUTHOR')">
			<li>
				<a class="fNiv" href="finder/author/edit.do"><spring:message code="master.page.finder.edit" /></a>
			</li>
		</security:authorize>	
				
		<security:authorize access="hasRole('CONFERENCESPONSOR')">
			<li>
				<a class="fNiv" href="sponsorship/sponsor/list.do"><spring:message code="master.page.mySponsorships" /></a>
			</li>
		</security:authorize>
		
		
		<security:authorize access="isAuthenticated() and not(hasRole('BAN'))">
			<li>
				<a class="fNiv" href="message/list.do"><spring:message code="master.page.messages" /></a>
			</li>
			<li><a class="fNiv" href="actor/display.do"><spring:message code="master.page.profile" />(<security:authentication property="principal.username" />)</a></li>
			
		</security:authorize>
		
		<!-- My profile and logout -->
		<security:authorize access="isAuthenticated() ">
			<li>
				<a class="fNiv" href="j_spring_security_logout"><spring:message code="master.page.logout" /> </a>
			</li>
		</security:authorize>
		
		
		<!-- Login -->
		<security:authorize access="isAnonymous()">
		<li>
			<a class="fNiv" href="security/login.do"><spring:message code="master.page.login" /></a>
		</li>
		</security:authorize>
		
	
		
		
	</ul>
</div>

<div>
	<a href="?language=en">en</a> | <a href="?language=es">es</a>
</div>

