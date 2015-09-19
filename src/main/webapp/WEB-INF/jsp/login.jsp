<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="inc-header.jsp" %>
<div class="login shadow">
    <div class="title">
        <span>用户登录</span>
    </div>
    <q:spring-login-form>
        <div class="input">
            <label>用户名</label>
            <input type="text" id="username" name="j_username" class="form-control" placeholder="在此输入用户名"/>
        </div>
        <div class="input">
            <label>密码</label>
            <input type="password" id="password" name="j_password" class="form-control" placeholder="在此输入密码"/>
        </div>
        <div class="submit">
            <q:spring-remember-login/>
            <span id="rememberMeLabel">记住我</span>
            <button type="submit" class="button button-primary" id="loginSubmit">登 录</button>
        </div>
    </q:spring-login-form>
    <div class="error-info" id="errorInfo"></div>
</div>
<%@include file="inc-footer.jsp" %>
