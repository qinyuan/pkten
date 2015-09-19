var angularUtils = {
    _module: null,
    /**
     * Usage:
     * controller(controllerName, func)
     * or
     * controller(func)
     */
    controller: function () {
        if (!this._module) {
            this._module = angular.module('main', []);
        }
        var argSize = arguments.length;
        if (argSize == 1) {
            this._module.controller('ContentController', ['$scope', '$http', arguments[0]]);
        } else if (argSize >= 2) {
            this._module.controller(arguments[0], ['$scope', '$http', arguments[1]]);
        }
        return this;
    }
};
(function () {
    // code about editing password
    var password = JSUtils.buildFloatPanel({
        $floatPanel: $('#passwordEditForm'),
        beforeShow: function () {
            this.showErrorInfo('');
        },
        doSubmit: function () {
            $.post('index-change-password.json', this.$floatPanel.serialize(), function (data) {
                if (data.success) {
                    location.reload();
                } else {
                    this.showErrorInfo(data.detail);
                }
            });
        },
        showErrorInfo: function (text) {
            this.$errorInfo.html(text).twinkle(4);
        },
        validateOldPassword: function () {
            var oldPassword = this.$oldPassword.val();
            if ($.trim(oldPassword) == '') {
                this.showErrorInfo('原密码不能为空');
                return false;
            }
            return true;
        },
        validateNewPassword: function () {
            var newPassword = this.$newPassword.val();
            if ($.trim(newPassword) == '') {
                this.showErrorInfo('新密码不能为空');
                return false;
            } else if (newPassword.indexOf(' ') >= 0) {
                this.showErrorInfo('新密码不能包含空格符');
                return false;
            } else if (newPassword.length < 6) {
                this.showErrorInfo('新密码至少包含6个字符');
                return false;
            }
            return true;
        },
        validateNewPassword2: function () {
            var newPassword2 = this.$newPassword2.val();
            if ($.trim(newPassword2) == '') {
                this.showErrorInfo('确认密码不能为空');
                return false;
            } else if (newPassword2 != this.$newPassword.val()) {
                this.showErrorInfo('两次输入的新密码不一致');
                return false;
            }
            return true;
        },
        validateInput: function () {
            if (!this.validateOldPassword()) {
                this.$oldPassword.focusOrSelect();
                return false;
            } else if (!this.validateNewPassword()) {
                this.$newPassword.focusOrSelect();
                return false;
            } else if (!this.validateNewPassword2()) {
                this.$newPassword2.focusOrSelect();
                return false;
            } else {
                return true;
            }
        },
        postInit: function () {
            this.$errorInfo = this.$floatPanel.find('div.error-info');
            this.$oldPassword = setBlurEven(this.$floatPanel.getInputByName('oldPassword'), this.validateOldPassword);
            this.$newPassword = setBlurEven(this.$floatPanel.getInputByName('newPassword'), this.validateNewPassword);
            this.$newPassword2 = setBlurEven(this.$floatPanel.getInputByName('newPassword2'), this.validateNewPassword2);

            var self = this;

            function setBlurEven($target, func) {
                $target.blur(function () {
                    setTimeout(function () {
                        func.call(self);
                    }, 300);
                });
                return $target;
            }
        }
    });
    $('#changePassword').click(function () {
        password.show();
    });
})
();
