<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<hr>

<h3>
	<spring:message code="administrator.process.computeScore" />
</h3>

<acme:button code="administrator.process.compute" type="button" url="administrator/computeScore.do" />

<hr>

<h3>
	<spring:message code="administrator.process.assignReviewers" />
</h3>

<acme:button code="administrator.process.assign" type="button" url="administrator/assignReviewers.do" />
