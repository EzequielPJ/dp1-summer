<%--
 * action-1.jsp
 *
 * Copyright (C) 2019 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<hr>

<section id="main">





<section id="configuration">
	<div>
	<h3><spring:message code="adminConfig.edit.titleManageConfig"/></h3>
	
	<form:form modelAttribute="adminConfigForm" action="adminConfig/administrator/save.do" method="Post">
	
		<acme:textbox code="adminConfig.edit.systemName" path="systemName"/>
		<acme:textbox code="adminConfig.edit.welcomeMessageEN" path="welcomeMsgEN"/>
		<acme:textbox code="adminConfig.edit.welcomeMessageES" path="welcomeMsgES"/>
		<acme:textbox code="adminConfig.edit.countryCode" path="countryCode"/>
		<acme:textbox code="adminConfig.edit.bannerURL" path="bannerURL"/>
		
		<acme:submit name="save" code="adminConfig.edit.save"/>
		<acme:button url="adminConfig/administrator/display.do" type="button" code="adminConfig.edit.back"/>

	</form:form>
	</div>

</section>

<%-- <section id="spamWords">

	<h3><spring:message code="adminConfig.edit.titleManageSpamWord"/></h3>

	<form:form modelAttribute="spamWordForm" action="adminConfig/administrator/addSpamWord.do" method="Post">
	
		<acme:textbox code="adminConfig.edit.spamWord" path="spamWord"/>
	
		<acme:submit name="save" code="adminConfig.edit.saveSpamWord"/>
	</form:form>

	<display:table name="spamWords" id="spamWord" requestURI="${requestURI}" pagesize="5" >
		<display:column titleKey="adminConfig.display.spamWords" ><acme:text value="${spamWord}"/></display:column>
		<display:column titleKey="adminConfig.edit.delete" ><acme:deleteWithForm  url="adminConfig/administrator/deleteSpamWord.do" name="spamWord" id="${spamWord}" code="adminConfig.edit.delete"/></display:column>
	</display:table>

</section>
<br>
 --%>
 
 <div id="lineavertical"></div>
 
 <section id="voids">
	<div>
	<h3><spring:message code="adminConfig.edit.titleManageMake"/></h3>
	
	<form:form modelAttribute="creditCardMakeForm" action="adminConfig/administrator/addCreditCardMake.do" method="Post">
	
		<acme:textbox code="adminConfig.edit.creditCardMake" path="creditCardMake"/>
		
		<acme:submit name="save" code="adminConfig.edit.saveMake"/>
		
	</form:form>

	<display:table name="creditCardMakes" id="creditCardMake" requestURI="${requestURI}" pagesize="5" >
		<display:column titleKey="adminConfig.edit.creditCardMakes" ><acme:text value="${creditCardMake}"/></display:column>
		<display:column titleKey="adminConfig.edit.delete" >
			<jstl:choose>
				<jstl:when test="${lastMake}">
					<spring:message code="adminConfig.edit.lastMake" />
				</jstl:when>
				<jstl:otherwise>
					<acme:deleteWithForm  url="adminConfig/administrator/deleteCreditCardMake.do" name="creditCardMake" id="${creditCardMake}" code="adminConfig.edit.delete"/>
				</jstl:otherwise>
			</jstl:choose>
		</display:column>
	</display:table>
	
		</div>

</section>

<div id="lineavertical"></div>

<section id="makes">

	<div>
	<h3><spring:message code="adminConfig.edit.titleManageMake"/></h3>
	
	<form:form modelAttribute="creditCardMakeForm" action="adminConfig/administrator/addCreditCardMake.do" method="Post">
	
		<acme:textbox code="adminConfig.edit.creditCardMake" path="creditCardMake"/>
		
		<acme:submit name="save" code="adminConfig.edit.saveMake"/>
		
	</form:form>

	<display:table name="creditCardMakes" id="creditCardMake" requestURI="${requestURI}" pagesize="5" >
		<display:column titleKey="adminConfig.edit.creditCardMakes" ><acme:text value="${creditCardMake}"/></display:column>
		<display:column titleKey="adminConfig.edit.delete" >
			<jstl:choose>
				<jstl:when test="${lastMake}">
					<spring:message code="adminConfig.edit.lastMake" />
				</jstl:when>
				<jstl:otherwise>
					<acme:deleteWithForm  url="adminConfig/administrator/deleteCreditCardMake.do" name="creditCardMake" id="${creditCardMake}" code="adminConfig.edit.delete"/>
				</jstl:otherwise>
			</jstl:choose>
		</display:column>
	</display:table>
	</div>

</section>

<br>

<hr>
</section>

<style>

#main {
	float: left;
	width: 100%;
}

 #main > hr{
	float: left;
	width: 100%;
}

#lineavertical{
	float: left;
	width: 0.1%;
	background-color: grey;
	height: 590px;
	margin: 0 1.5%;

}

#configuration{
	float: left;
	width: 20%;
	padding: 0 5%;
	margin-left: 1.5%;
}

#configuration > div{
	width: 90%;
	padding: 0 5%;
}

.textboxLabel{
	float: left;
	width: 100%;
	margin-bottom: 2px;
}

.textbox{
	float: left;
	width: 100%;
	margin-bottom: 20px;
}

#voids{
	float: left;
	width: 20%;
	padding: 0 5%;
}

#voids > div{
	width: 90%;
	padding: 0 5%;
}

#makes{
	float: left;
	width: 20%;
	padding: 0 5%;
	margin-right: 1.5%;
}

#makes > div{
	width: 90%;
	padding: 0 5%;
}



</style>




