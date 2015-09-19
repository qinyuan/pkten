(function () {
    $('div.login').focusFirstTextInput();
    $('#loginSubmit').click(function (e) {
        var $username = $('#username');
        var username = $username.val();
        if ($.trim(username) == '') {
            showErrorInfo('用户名不能为空！');
            $username.focusOrSelect();
            e.preventDefault();
            return false;
        }

        var $password = $('#password');
        var password = $password.val();
        if ($.trim(password) == '') {
            showErrorInfo('密码不能为空！');
            $password.focusOrSelect();
            e.preventDefault();
            return false;
        }

        return true;
    });

    if ($.url.param('login_error') == '1') {
        showErrorInfo('用户名或密码错误！');
    }

    function showErrorInfo(text) {
        $('#errorInfo').text(text).twinkle(4);
    }
})();