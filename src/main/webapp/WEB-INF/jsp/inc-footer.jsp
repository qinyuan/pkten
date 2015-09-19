<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
</div>
</div>
<div class="footer">
    <span>Copyright©2005-2015 <a href="index.html">pkten</a> 版权所有 未经许可 请勿转载</span>
</div>
<form class="float-panel" id="passwordEditForm">
    <div class="title">修改密码
        <div class="close-icon"></div>
    </div>
    <div class="body">
        <div class="input">
            <label>原密码</label>
            <input type="password" name="oldPassword" class="form-control" maxlength="20" placeholder="输入原密码"/>
        </div>
        <div class="input">
            <label>新密码</label>
            <input type="password" name="newPassword" class="form-control" maxlength="20" placeholder="输入新密码，6~20个字符"/>
        </div>
        <div class="input">
            <label>确认新密码</label>
            <input type="password" name="newPassword2" class="form-control" maxlength="20" placeholder="再次输入新密码"/>
        </div>
        <%@include file="float-panel-submit.jsp" %>
    </div>
</form>
<security:authorize ifAnyGranted="ROLE_ADMIN">
    <form class="float-panel" id="addUserForm">
        <div class="title">添加用户
            <div class="close-icon"></div>
        </div>
        <div class="body">
            <div class="input">
                <label>用户名</label>
                <input type="text" name="username" class="form-control" maxlength="20" placeholder="用户名，至少2个字符"/>
            </div>
            <div class="input">
                <label>密码</label>
                <input type="text" name="password" class="form-control" maxlength="20" placeholder="密码，6~20个字符"/>
            </div>
            <div class="input">
                <label>有效期至</label>
                <input type="text" name="expireTime" class="form-control" maxlength="19"
                       placeholder="格式如'2015-12-01 19:20:21'" disabled/>
                <input style="width:12px;" type="checkbox" name="alwaysValid" checked/><span
                    style="font-size:9pt;">长期有效</span>
            </div>
            <%@include file="float-panel-submit.jsp" %>
            <div class="to-manage-panel">
                <a href="javascript:void(0)">管理用户&gt;&gt;</a>
            </div>
        </div>
    </form>
    <div class="float-panel" id="manageUserPanel">
        <div class="title">管理用户
            <div class="close-icon"></div>
        </div>
        <div class="body">
            <table class="normal">
                <colgroup>
                    <col class="username"/>
                    <col class="password"/>
                    <col class="expire-time"/>
                    <col class="action"/>
                </colgroup>
                <thead>
                <tr>
                    <th>用户名</th>
                    <th>密码</th>
                    <th>有效期至</th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                </tbody>
            </table>
            <div class="to-add-panel">
                <a href="javascript:void(0)">添加用户&gt;&gt;</a>
            </div>
        </div>
    </div>
    <q:handlebars-template id="userTableRowTpl">
        {{#each rows}}
        <tr data-id="{{id}}">
            <td>{{username}}</td>
            <td>{{password}}</td>
            <td>{{expireTime}}</td>
            <td>
                <img class="link delete" src="resources/css/images/delete.png"/>
            </td>
        </tr>
        {{/each}}
    </q:handlebars-template>
</security:authorize>
</body>
<q:js src="lib/jquery-1.11.3"/>
<q:js src="lib/jquery.url"/>
<q:js src="lib/jquery.cookie"/>
<q:js src="lib/jquery-form-3.51.0"/>
<q:js src="lib/underscore-min"/>
<q:js src="lib/jsutils"/>
<q:js src="common"/>
<%---
<!--[if IE]>
<script src="resources/js/lib/angular/html5shiv.js"></script>
<script src="resources/js/lib/angular/json2.js"></script>
<script src="resources/js/lib/ie-patch.js"></script>
<![endif]-->
--%>
<c:forEach var="js" items="${moreJs}"><q:js src="${js.href}" version="${js.version}"/></c:forEach>
<!--[if IE]>
<c:forEach var="js" items="${ieJs}"><q:js src="${js.href}" version="${js.version}"/></c:forEach>
<![endif]-->
</html>
