<%@ taglib tagdir="/WEB-INF/tags" prefix="app" 
%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" 
%><%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" 
%><%@ attribute name="items" required="true" 
%><%@ attribute name="var" required="true" type="java.lang.String" rtexprvalue="false"
%><%@ attribute name="ifEmpty" required="false" 
%><%@ attribute name="title" required="false" 
%><%@ attribute name="titleTag" required="false" 
%><%@ variable name-from-attribute="var" alias="thisVar" 
%><c:if test="${!empty title}">
<c:if test="${empty titleTag}"><h3>${title}</h3></c:if><c:if test="${!empty titleTag}"><${titleTag}>${title}</${titleTag}></c:if></c:if>
<c:choose>
	<c:when test="${!empty ifEmpty && empty items}"><p class="empty">${ifEmpty}</p></c:when>
	<c:otherwise><c:forEach items="${items}" var="thisVar"><jsp:doBody /></c:forEach></c:otherwise>
</c:choose>