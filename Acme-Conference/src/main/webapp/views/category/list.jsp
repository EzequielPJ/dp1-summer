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

<acme:button url="category/administrator/create.do" type="button" code="category.list.create"/>

<section>

		<display:table name="categories" id="category" pagesize="5" requestURI="${requestURI}">
			<display:column titleKey="category.list.parent" ><jstl:out value="${category.parent.categoryEN}"/> / <jstl:out value="${category.parent.categoryES}"/></display:column>
			<display:column titleKey="category.list.name" ><jstl:out value="${category.categoryEN}"/> / <jstl:out value="${category.categoryES}"/></display:column>
			<display:column titleKey="category.list.children" >
			
				<jstl:choose>
							<jstl:when test="${not empty category.children}">
								<jstl:forEach items="${category.children}" var="children">
									<jstl:out value="${children.categoryEN}"/> / <jstl:out value="${children.categoryES}"/><br>
								</jstl:forEach>		
							</jstl:when>
							
							<jstl:otherwise>
								<jstl:out value="N/A" />
							</jstl:otherwise>
				</jstl:choose>
				
			</display:column>
			<display:column titleKey="category.list.edit" ><acme:button url="category/administrator/edit.do?idCategory=${category.id}" type="button" code="category.list.edit"/></display:column>
			<display:column titleKey="category.list.delete" ><acme:button url="category/administrator/delete.do?idCategory=${category.id}" type="button" code="category.list.delete"/></display:column>
		</display:table>

</section>




