<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="inc-header.jsp" %>
<div class="header">
    <div class="page-width">
        <div class="title">${title}</div>
    </div>
</div>
<div class="main-body">
    <div class="page-width">
        <c:set var="positions" value="${fn:split('冠军,亚军,季军,第四,第五,第六,第七,第八,第九,第十', ',')}"/>
        <div class="left-page shadow">
            <div class="title">
                近期开奖结果
            </div>
            <div class="position">
                <c:forEach var="position" items="${positions}" varStatus="status">
                    <span><input type="radio" name="position" value="${status.index+1}"<c:if
                            test="${evaluatePosition == status.index + 1}"> checked</c:if>/>${position}</span>
                </c:forEach>
            </div>
            <table>
                <thead>
                <tr class="dark">
                    <th>开奖时间</th>
                    <th>期号</th>
                    <th>开奖号码</th>
                    <th>预测买号<img title="通过本算法预测的买号" src="resources/css/images/help.png"/></th>
                    <th>状态</th>
                    <th>营利</th>
                    <th>总计</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="record" items="${records}" varStatus="status">
                    <tr<c:if test="${status.index % 2 == 1}"> class="dark"</c:if>>
                        <td>${record.shortDrawTime}</td>
                        <td class="phase">${record.phase}</td>
                        <td class="result">
                            <c:forEach var="resultItem" items="${record.resultStringItems}"><span
                                    class="ball">${resultItem}</span></c:forEach>
                        </td>
                        <%@include file="index-predict-ball.jsp" %>
                        <td class="status"></td>
                        <td class="revenue"></td>
                        <td class="sum"></td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
        <div class="right-page shadow">
            <div class="title">
                下期预测
            </div>
            <div class="phase">
                预测期号：<span class="predict-phase">${prediction.predictPhase}</span>
            </div>
            <table>
                <thead>
                <tr>
                    <th>位置</th>
                    <th>推荐买号</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="title" items="${positions}">
                    <tr>
                        <td>${title}</td>
                        <%@include file="index-predict-ball.jsp" %>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>
<%@include file="inc-footer.jsp" %>