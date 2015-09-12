<%@ page import="org.apache.commons.lang3.StringUtils" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="inc-header.jsp" %>
<div class="header">
    <div class="page-width">
        <div class="title">${title}</div>
    </div>
</div>
<div class="main-body">
    <div class="page-width">
        <div class="left-page shadow">
            <div class="title">
                近期开奖结果
            </div>
            <table>
                <thead>
                <tr class="dark">
                    <th>开奖日期</th>
                    <th>开奖时刻</th>
                    <th>期号</th>
                    <th>开奖号码</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="record" items="${records}" varStatus="status">
                    <tr<c:if test="${status.index % 2 == 1}"> class="dark"</c:if>>
                        <td>${record.drawTimeDatePart}</td>
                        <td>${record.drawTimeTimePart}</td>
                        <td>${record.phase}</td>
                        <td class="result">
                            <c:forEach var="resultItem" items="${record.resultItems}">
                                <span class="ball">${resultItem}</span>
                            </c:forEach>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
        <div class="right-page shadow">
            <div class="title">
                下期<span class="predict-phase">(${prediction.predictPhase})</span>预测
            </div>
            <table>
                <thead>
                <tr>
                    <th>位置</th>
                    <th>推荐买号</th>
                </tr>
                </thead>
                <tbody>
                <%for (String title : "冠军,亚军,季军,第四名,第五名,第六名,第七名,第八名,第九名,第十名".split(",")) {%>
                <tr>
                    <td><%=title%>
                    </td>
                    <td><%=StringUtils.repeat("<span class=\"ball\">&nbsp;&nbsp;</span> ", 10)%>
                    </td>
                </tr>
                <%}%>
                </tbody>
            </table>
        </div>
    </div>
</div>
<%@include file="inc-footer.jsp" %>