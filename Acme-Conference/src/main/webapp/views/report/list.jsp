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

	<section id="displayReports">

		<display:table pagesize="5" name="reports" id="report" requestURI="${requestURI}">
			<display:column titleKey="report.list.submission" ><jstl:out value="${report.submission.ticker.identifier}"/> </display:column>
			<display:column titleKey="report.list.decision" ><jstl:out value="${report.decision}"/></display:column>
			<display:column titleKey="report.list.display"><acme:button url="report/reviewer/display.do?reportId=${report.id}" type="button" code="report.list.display"/></display:column>
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
