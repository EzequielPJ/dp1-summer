<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<acme:cancel url="conference/administrator/list.do" code="conference.display.back"/> 

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
		


