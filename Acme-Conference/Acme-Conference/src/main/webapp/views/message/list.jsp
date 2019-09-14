<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="fn"	uri="http://java.sun.com/jsp/jstl/functions"%>


<div>
	<ul class="jMenu">
	
		<li>
			<a class="fNiv" href="message/list.do"><spring:message	code="message.list.all" /></a>
		</li>
		
		<li>
			<a class="fNiv"><spring:message	code="message.list.byTopic" /></a>
			<ul>
				<li class="arrow"></li>
				<jstl:forEach var="topic" items="${topics}">				
					<li>
					<jstl:choose>
						<jstl:when test="${cookie.language.value == 'es'}">
							<a href="message/listByTopic.do?idTopic=${topic.id}"><jstl:out value="${topic.topicES}"/></a>
						</jstl:when>
						
						<jstl:otherwise>
							<a href="message/listByTopic.do?idTopic=${topic.id}"><jstl:out value="${topic.topicEN}"/></a>   
						</jstl:otherwise>
					</jstl:choose>
					</li>
				</jstl:forEach>
			</ul>
		</li>
		
		<li>
			<a class="fNiv"><spring:message	code="message.list.bySender" /></a>
			<ul>
				<li class="arrow"></li>
				<jstl:forEach var="sender" items="${senders}">				
					<li>
						<a href="message/listBySender.do?idSender=${sender.id}"><jstl:out value="${sender.email}"/></a>
			
					</li>
				</jstl:forEach>
			</ul>
		</li>
		
		<li>
			<a class="fNiv"><spring:message	code="message.list.byRecipient" /></a>
			<ul>
				<li class="arrow"></li>
				<jstl:forEach var="recipient" items="${recipients}">				
					<li>
						<a href="message/listByRecipient.do?idRecipient=${recipient.id}"><jstl:out value="${recipient.email}"/></a>
			
					</li>
				</jstl:forEach>
			</ul>
		</li>
	
	</ul>

</div>



<acme:button code="message.list.create" type="button" url="message/create.do"/>

<display:table pagesize="5" name="messagesList" id="messageObject" requestURI="${requestURI}">
	
	<display:column titleKey="message.list.sender"><jstl:out value="${messageObject.sender.email}" /></display:column>
	
	<display:column titleKey="message.list.recipients">
		<jstl:choose>
			<jstl:when test="${messageObject.sender eq actorLogged}">
				<jstl:choose>
					<jstl:when test="${fn:length(messageObject.recipients) le 5}">
						<jstl:forEach var="counter" begin="0" end="${fn:length(messageObject.recipients) - 1}">
							<jstl:out value="${messageObject.recipients.get(counter).email}"/><jstl:if test="${counter != fn:length(messageObject.recipients) - 1}">,</jstl:if>
						</jstl:forEach>
					</jstl:when>
					
					<jstl:otherwise>
						<jstl:forEach var="counter" begin="0" end="4">
							<jstl:out value="${messageObject.recipients.get(counter).email}"/><jstl:if test="${counter != 4}">,</jstl:if>            
						</jstl:forEach>
						+ <jstl:out value="${fn:length(messageObject.recipients) - 5}"/>
					</jstl:otherwise>
				</jstl:choose>
				
				
					
			</jstl:when>
			
			<jstl:otherwise>
				<jstl:out value="${actorLogged.email}"/> 
			</jstl:otherwise>
		</jstl:choose>
	
	
	</display:column>
	
	<display:column titleKey="message.list.subject"><jstl:out value="${messageObject.subject}" /></display:column>
	
	<display:column titleKey="message.list.moment"><jstl:out value="${messageObject.moment}" /></display:column>
	
	<display:column titleKey="message.list.display">
		<acme:button url="message/display.do?idMessage=${messageObject.id}" type="button" code="message.list.display"/>
	</display:column>
	
	<display:column titleKey="message.list.delete">
		<acme:button url="message/delete.do?idMessage=${messageObject.id}" type="button" code="message.list.delete"/>
	
	</display:column>
	
	
</display:table>



