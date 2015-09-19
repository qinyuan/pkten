<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="inc-header.jsp" %>
<c:choose>
    <c:when test="${userAlreadyExpire}">
        <div class="user-expire shadow">您的账号有效期截止至<span class="time">${expireTime}</span>，现在已经过期</div>
    </c:when>
    <c:otherwise>
        <%@include file="index-body.jsp" %>
    </c:otherwise>
</c:choose>
<%@include file="inc-footer.jsp" %>