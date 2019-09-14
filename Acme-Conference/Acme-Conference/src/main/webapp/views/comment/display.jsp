<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<acme:cancel url="comment/list.do?idEntity=${idEntity}" code="comment.display.back"/> 

<acme:text label="comment.list.title" value="${comment.title}"/>
<acme:text label="comment.list.moment" value="${comment.moment}"/>
<acme:text label="comment.list.text" value="${comment.text}"/>
<jstl:if test="${comment.actor.name == null}">
<acme:text label="comment.list.actor" value="${anonim}"/>
</jstl:if>
<jstl:if test="${comment.actor.name != null}">
<acme:text label="comment.list.actor" value="${comment.actor.name}"/>
</jstl:if>


		


