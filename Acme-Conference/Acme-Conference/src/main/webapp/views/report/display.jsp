<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<acme:cancel url="${backUri }" code="report.display.back" />

<h3><spring:message code="report.list.decision" /></h3>
<b><jstl:out value="${report.decision}" /></b>
<hr/>
<br/>
<h3><spring:message code="report.list.scores" /></h3>
<b><spring:message code="report.list.originalityScore" />:</b><jstl:out value="${report.originalityScore}" />
<br/>
<b><spring:message code="report.list.qualityScore" />:</b><jstl:out value="${report.qualityScore}" />
<br/>
<b><spring:message code="report.list.readibilityScore" />:</b><jstl:out value="${report.readibilityScore}" />
<br/>
<hr/>
<br/>
<h3><spring:message code="report.list.comments" /></h3>
<display:table requestURI="${requestUri }" pagesize="10" name="${report.comments }" id="comment" >
	<display:column titleKey="report.list.comments" ><jstl:out value="${comment}" /></display:column>
</display:table>