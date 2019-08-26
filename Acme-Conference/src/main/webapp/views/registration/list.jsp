<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags" %> 
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>



<section id="main">

	<section id="displayRegistrations">

		<display:table pagesize="5" name="registrations" id="registration" requestURI="${requestURI}">
			<display:column titleKey="registration.list.conference.title" ><jstl:out value="${registration.conference.title}"/> </display:column>
			<display:column titleKey="registration.list.conference.startDate" ><jstl:out value="${registration.conference.startDate}"/></display:column>
			<display:column titleKey="registration.list.moment"><jstl:out value="${registration.moment}"/></display:column>	
			<display:column titleKey="registration.list.seeMore"><acme:button url="registration/author/display.do?registrationId=${registration.id}" type="button" code="registration.list.seeMore"/></display:column>
			<display:column titleKey="registration.list.delete"><acme:button url="registration/author/delete.do?registrationId=${registration.id}" type="button" code="registration.list.delete"/></display:column>
		</display:table>

	</section>

<hr>
</section>


<style>
#main {
	float: left;
	width: 100%;
}

#displayBoxes {
	float: left;
	margin: 20px;
	width: 15%;
	border: 1px solid black;
}

.boxSelect{
	background-color: black;
	width: 100%;
	float: left;
	border: 1px solid black;
}

.boxSelect > p{
	float:left;
	margin-left: 35px;
	color: white;
}

.boxSelect > a{
	float: right;
	color: white;
	display:block;
	width: 25%;
	margin-top: 15px;
	text-decoration: none;
	display: block;
}

.boxNoSelect{
	float: left;
	width: 100%;
	border: 1px solid black;
}

.boxNoSelect > a{
	display: block;
	width: 70%;
	padding: 10px 15%;
	text-decoration: none;
	color: black;
	background-color: white;
}

.boxNoSelect > a:hover{
	display: block;
	width: 70%;
	padding: 10px 15%;
	text-decoration: none;
	background-color: #006666;
	color: white;
}

#displayMessages {
	float: right;
	padding: 20px 50px;
	margin: 20px;
	width: 70%;
	border: 2px solid black;
}

.selectLabel{
	width: 100%;
	float: left;
	margin-bottom: 5px;
}

.select{
	width: 100%;
	float: left;
	margin-bottom: 20px;
}

.botones{
  	margin-left: 70px;
  	float: right;
}

.botones > button{
	margin-left: 10px;
}

#main>hr{
width: 100%;
float: left;
margin-top: 50px;
}
</style>
