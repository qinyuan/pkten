<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
</div>
</div>
<div class="footer">
    <span>Copyright©2005-2015 <a href="index.html">pkten</a> 版权所有 未经许可 请勿转载</span>
</div>
<form class="float-panel" id="passwordEditForm">
    <div class="title">修改密码</div>
    <div class="body">
        <div class="input">
            <label>原密码</label>
            <input type="password" name="oldPassword" class="form-control" maxlength="20" placeholder="输入原密码"/>
        </div>
        <div class="input">
            <label>新密码</label>
            <input type="password" name="newPassword" class="form-control" maxlength="20" placeholder="输入新密码"/>
        </div>
        <div class="input">
            <label>确认新密码</label>
            <input type="password" name="newPassword2" class="form-control" maxlength="20" placeholder="再次输入新密码"/>
        </div>
        <div class="submit">
            <button type="button" class="button button-primary ok">确定</button>
            <button type="button" class="button button-default cancel">取消</button>
        </div>
        <div class="error-info"></div>
    </div>
</form>
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
