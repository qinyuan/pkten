<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="inc-taglib.jsp" %>
<!DOCTYPE html>
<html>
<head lang="zh-ch">
    <meta charset="UTF-8">
    <meta http-equiv="Content-Type" content="text/html;charset=utf-8">
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
    <title>${title}</title>
    <%--
    <meta name="keywords" content="<%=seoKeyword==null?"":seoKeyword.getWord()%>">
    <meta name="description" content="<%=seoKeyword==null?"":seoKeyword.getDescription()%>">
    --%>
    <link rel="shortcut icon" href="http://static.lecai.com/img/favicon_bdcp.ico?v=2.9.131"/>
    <c:if test="${favicon != null}">
        <link rel="icon" href="${favicon}" type="image/x-icon"/>
        <link rel="shortcut icon" href="${favicon}" type="image/x-icon"/>
    </c:if>
    <q:css href="resources/js/lib/bootstrap/css/bootstrap.min"/>
    <q:css href="resources/js/lib/buttons/buttons.min"/>
    <q:css href="common" version="true"/>
    <c:forEach var="css" items="${moreCss}"><q:css href="${css.href}" version="${css.version}"/></c:forEach>
    <c:forEach var="js" items="${headJs}"><q:js src="${js.href}" version="${js.version}"/></c:forEach>
</head>
<c:if test="${javascriptDatas != null}">
<script>
    <c:forEach var="entry" items="${javascriptDatas}">
    var ${entry.key}=${entry.value};
    </c:forEach>
</script>
</c:if>
<body class="ng-app:main" ng-app="main" id="ng-app">
<div class="header">
    <div class="page-width">
        <div class="title">北京赛车-PK10 下期预测</div>
        <security:authorize ifAnyGranted="ROLE_ADMIN">
            <div class="control-panel">
                <a href="javascript:void(0)" id="addUserLink">添加用户</a>
                <span class="split"></span>
                <a href="javascript:void(0)" id="manageUserLink">管理用户</a>
            </div>
        </security:authorize>
        <security:authorize ifAnyGranted="ROLE_NORMAL">
            <c:if test="${expireTime != null}">
                <div class="expire-time">您的账号有有效期至：${expireTime}</div>
            </c:if>
        </security:authorize>
        <security:authorize ifAnyGranted="ROLE_ADMIN,ROLE_NORMAL">
            <div class="user">
                [<security:authentication property="name"/>]
                <a href="javascript:void(0)" id="changePassword">修改密码</a>
                <a href="j_spring_security_logout">退出</a>
            </div>
        </security:authorize>
    </div>
</div>
<div class="main-body">
    <div class="page-width">
