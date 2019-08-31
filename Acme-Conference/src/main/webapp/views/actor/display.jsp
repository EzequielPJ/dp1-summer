<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>


<jstl:if test="${actor.userAccount.id == userLogged.id}">

	<acme:button url="actor/edit.do" type="button" code="actor.edit" />

</jstl:if>
<jstl:if test = "${targetURL != null }"><acme:button code="actor.back" url="${targetURL }" type="button" /></jstl:if>

<jstl:if test = "${targetURL == null }"><acme:button code="actor.back" url="/" type="button" /></jstl:if>
<br/>

<jstl:choose>
	<jstl:when test="${authority == 'REVIEWER'}">
	
		<hr />

			<img style="width: 200px;" src="${reviewer.photoURL }" alt="${sponsor.photoURL }"/>
		<br />
		<b><spring:message code="actor.name" /></b>:
		<jstl:out value="${reviewer.name }" />
		<br />
		<jstl:if test="${reviewer.middlename ne null }">
			<b><spring:message code="actor.middlename" /></b>:
			<jstl:out value="${reviewer.middlename }" />
			<br />
		</jstl:if>
		<b><spring:message code="actor.surname" /></b>:
		<jstl:out value="${reviewer.surname }" />
		<br />
		<b><spring:message code="actor.email" /></b>:
		<jstl:out value="${reviewer.email }" />
		<br />
		<b><spring:message code="actor.address" /></b>:
		<jstl:out value="${reviewer.address }" />
		<br />
		<b><spring:message code="actor.phoneNumber" /></b>:
		<jstl:out value="${reviewer.phoneNumber }" />
		<br />
		<b><spring:message code="actor.keyWords" /></b>:
			<jstl:if test="${empty reviewer.expertiseKeywordsList}">
   				N/A    
   			</jstl:if>
   			<jstl:if test="${not(empty reviewer.expertiseKeywordsList)}">
   				<jstl:forEach var="keyWord" items="${reviewer.expertiseKeywordsList}">
   					<li><jstl:out value="${keyWord}"/></li>
    			</jstl:forEach>
   			</jstl:if>
		<br />

	</jstl:when>
	
	<jstl:when test="${authority == 'ADMINISTRATOR'}">
		<img style="width: 200px;" src="${administrator.photoURL }" alt="${sponsor.photoURL }"/>
		<br />
		<b><spring:message code="actor.name" /></b>:
		<jstl:out value="${administrator.name }" />
		<br />
		<jstl:if test="${administrator.middlename ne null }">
			<b><spring:message code="actor.middlename" /></b>:
			<jstl:out value="${administrator.middlename }" />
			<br />
		</jstl:if>
		<b><spring:message code="actor.surname" /></b>:
			<jstl:out value="${administrator.surname }" />
		<br />
		<b><spring:message code="actor.email" /></b>:
		<jstl:out value="${administrator.email }" />
		<br />
		<b><spring:message code="actor.address" /></b>:
		<jstl:out value="${administrator.address }" />
		<br />
		<b><spring:message code="actor.phoneNumber" /></b>:
		<jstl:out value="${administrator.phoneNumber }" />
		<br />

		<br />

	</jstl:when>

	<jstl:when test="${authority == 'AUTHOR'}">
		<img style="width: 200px;" src="${author.photoURL }" alt="${author.photoURL }"/>
		<br />
		<b><spring:message code="actor.name" /></b>:
		<jstl:out value="${author.name }" />
		<br />
		<jstl:if test="${author.middlename ne null }">
			<b><spring:message code="actor.middlename" /></b>:
			<jstl:out value="${author.middlename }" />
			<br />
		</jstl:if>
		<b><spring:message code="actor.surname" /></b>:
			<jstl:out value="${author.surname }" />
		<br />
		<b><spring:message code="actor.email" /></b>:
		<jstl:out value="${author.email }" />
		<br />
		<b><spring:message code="actor.address" /></b>:
		<jstl:out value="${author.address }" />
		<br />
		<b><spring:message code="actor.phoneNumber" /></b>:
		<jstl:out value="${author.phoneNumber }" />
		
		<jstl:if test="${actor.score ne null }">
			<br />
			<b><spring:message code="actor.score" /></b>:
			<jstl:out value="${author.score }" />
		</jstl:if>

		<br />

	</jstl:when>
	
	<jstl:when test="${authority == 'CONFERENCESPONSOR'}">
		<img style="width: 200px;" src="${sponsor.photoURL }" alt="${sponsor.photoURL }"/>
		<br />
		<b><spring:message code="actor.name" /></b>:
		<jstl:out value="${sponsor.name }" />
		<br />
		<jstl:if test="${sponsor.middlename ne null }">
			<b><spring:message code="actor.middlename" /></b>:
			<jstl:out value="${sponsor.middlename }" />
			<br />
		</jstl:if>
		<b><spring:message code="actor.surname" /></b>:
			<jstl:out value="${sponsor.surname }" />
		<br />
		<b><spring:message code="actor.email" /></b>:
		<jstl:out value="${sponsor.email }" />
		<br />
		<b><spring:message code="actor.address" /></b>:
		<jstl:out value="${sponsor.address }" />
		<br />
		<b><spring:message code="actor.phoneNumber" /></b>:
		<jstl:out value="${sponsor.phoneNumber }" />

		<br />

	</jstl:when>
	
</jstl:choose>



