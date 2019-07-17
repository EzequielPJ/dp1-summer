

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<jstl:choose>

<jstl:when test="${logged}">
	<acme:button url="book/writer/list.do" type="button" code="book.display.back"/>
</jstl:when>
<jstl:otherwise>
	<button onclick="window.location.href=document.referrer"><spring:message code="book.display.back"/></button>
</jstl:otherwise>
</jstl:choose>
<br/>

<jstl:if test="${publisher and book.status == 'PENDING'}">
 <acme:button url="book/publisher/changeStatus.do?idBook=${book.id}&status=ACCEPTED" type="button" code="book.list.accept"/>
 <acme:button url="book/publisher/changeStatus.do?idBook=${book.id}&status=REJECTED" type="button" code="book.list.reject"/>
</jstl:if><br/>

<jstl:if test="${book.cover != null}">
	<img src="<jstl:out value="${book.cover}"/>" alt="<jstl:out value="${book.cover}"/>" height="250" width="250">
</jstl:if>

<acme:text label="book.display.title" value="${book.title}"/>
<acme:text label="book.display.description" value="${book.description}"/>
<acme:text label="book.display.lang" value="${book.lang}"/>

<jstl:choose> 
	<jstl:when test="${book.score == null}">
		<acme:text label="book.display.score" value="N/A"/>
	</jstl:when>
	<jstl:otherwise>
		<acme:text label="book.display.score" value="${book.score}"/>
	</jstl:otherwise>
</jstl:choose>
<acme:text label="book.display.numWords" value="${book.numWords}"/>
<jstl:choose>
	<jstl:when test="${cookie.language.value == 'es'}">
		<acme:text label="book.display.genre" value="${book.genre.nameES}"/>
	</jstl:when>
	
	<jstl:otherwise>
		<acme:text label="book.display.genre" value="${book.genre.nameEN}"/>
	</jstl:otherwise>
</jstl:choose>
<jstl:if test="${book.publisher ne null}">
	<acme:text label="book.display.publisher" value="${book.publisher.commercialName}"/>

</jstl:if>
<p><strong><spring:message code="book.display.writer" />:</strong>  <jstl:out value="${book.writer.name}"/> <jstl:out value="${book.writer.surname}"/></p>
<h4><spring:message code="book.display.chapters"/></h4>
<jstl:if test="${logged and book.draft}">
	<acme:button url="chapter/writer/create.do?idBook=${book.id}" type="button" code="book.display.create.chapter"/>
</jstl:if>
<display:table pagesize="5" name="chapters" id="chapter" requestURI="${requestURIChapters}">
   		 <display:column titleKey="book.display.chapter.title"><jstl:out value="${chapter.title}"/></display:column>
   		 <display:column titleKey="book.display.chapter.number"><jstl:out value="${chapter.number}"/></display:column>
   		 <jstl:choose>
			<jstl:when test="${logged}">
				<display:column titleKey="book.display.chapter.display">
	   				 <acme:button url="chapter/writer/display.do?idChapter=${chapter.id}" type="button" code="book.display.chapter.display"/>
	   			 </display:column>
	   			<jstl:if test="${book.draft}">
	   			 <display:column titleKey="book.display.chapter.edit">
	   				 <acme:button url="chapter/writer/edit.do?idChapter=${chapter.id}" type="button" code="book.display.chapter.edit"/>
	   			 </display:column>
	   			 
	   			 <display:column titleKey="book.display.chapter.delete">
	   				 <acme:button url="chapter/writer/delete.do?idChapter=${chapter.id}" type="button" code="book.display.chapter.delete"/>
	   			 </display:column>
	   			</jstl:if>
			</jstl:when>
			
			<jstl:when test="${publisher}">
			<display:column titleKey="book.display.chapter.display">
   				 <acme:button url="chapter/publisher/display.do?idChapter=${chapter.id}" type="button" code="book.display.chapter.display"/>
   			 </display:column>
			 </jstl:when>
			 
			<jstl:otherwise>
			<display:column titleKey="book.display.chapter.display">
   				 <acme:button url="chapter/display.do?idChapter=${chapter.id}" type="button" code="book.display.chapter.display"/>
   			 </display:column>
			 </jstl:otherwise>
   		 </jstl:choose>
</display:table>

<!-- SI ES UN LECTOR Y NO TIENE UNA OPINION A ESTE LIBRO, SE PUEDE PONER UN BOTON -->
<h4><spring:message code="book.display.opinions"/></h4>
<display:table pagesize="5" name="opinions" id="opinion" requestURI="${requestURIOpinion}">
   		 <display:column titleKey="book.display.opinion.reader"><jstl:out value="${opinion.reader.name}"/> <jstl:out value="${opinon.reader.surname}"/></display:column>
   		 <display:column titleKey="book.display.opinion.moment"><jstl:out value="${opinion.moment}"/></display:column>
   		 <display:column titleKey="book.display.opinion.positiveOpinion">
   		 <jstl:choose>
			<jstl:when test="${opinion.positiveOpinion}">
				:)
			</jstl:when>
			<jstl:otherwise>
				:(
			</jstl:otherwise>
		</jstl:choose>
   		 </display:column>
   		 <display:column titleKey="book.display.opinion.review"><jstl:out value="${opinion.review}"/></display:column>
</display:table>

<security:authorize access="hasRole('READER')">
	<jstl:if test="${!book.draft and !book.cancelled}">
		<hr/>
		<a type="button" href="book/downloadBook.do?idBook=${book.id}" target="_blank" class="button"><spring:message code="book.display.download" /></a>
	</jstl:if>
</security:authorize>

<jstl:if test="${logged}">
	<hr/>
	<a type="button" href="book/downloadBook.do?idBook=${book.id}" target="_blank" class="button"><spring:message code="book.display.download" /></a>
</jstl:if>

<style>

    .button {
    -moz-box-shadow:inset 0 1px 0 0 #fff;
    -webkit-box-shadow:inset 0 1px 0 0 #fff;
    box-shadow:inset 0 1px 0 0 #fff;
    background:-webkit-gradient( linear, left top, left bottom, color-stop(0.05, #ffffff), color-stop(1, #d1d1d1) );
    background:-moz-linear-gradient( center top, #ffffff 5%, #d1d1d1 100% );
 filter:progid:DXImageTransform.Microsoft.gradient(startColorstr='#ffffff', endColorstr='#d1d1d1');
    background-color:#fff;
    -moz-border-radius:6px;
    -webkit-border-radius:6px;
    border-radius:6px;
    border:1px solid #dcdcdc;
    display:inline-block;
    color:#777;
    font-family:Helvetica;
    font-size:15px;
    font-weight:700;
    padding:6px 24px;
    text-decoration:none;
    text-shadow:1px 1px 0 #fff
}



  .button:hover {
    background:-webkit-gradient( linear, left top, left bottom, color-stop(0.05, #d1d1d1), color-stop(1, #ffffff) );
    background:-moz-linear-gradient( center top, #d1d1d1 5%, #ffffff 100% );
 filter:progid:DXImageTransform.Microsoft.gradient(startColorstr='#d1d1d1', endColorstr='#ffffff');
    background-color:#d1d1d1
}
.button:active {
    position:relative;
    top:1px
}

</style>

