(function () {
    // code about adding user
    var userAdder = JSUtils.buildFloatPanel({
        $floatPanel: $('#addUserForm'),
        beforeShow: function () {
            this.showErrorInfo('');
        },
        doSubmit: function () {
            var self = this;
            $.post('admin-add-user.json', this.$floatPanel.serialize(), function (data) {
                if (data.success) {
                    alert('用户添加成功');
                    //location.reload();
                } else {
                    self.showErrorInfo(data.detail);
                }
            });
        },
        showErrorInfo: function (text) {
            this.$errorInfo.html(text);
            if (text) {
                this.$errorInfo.twinkle(4);
            }
        },
        validateUsername: function () {
            var username = this.$username.val();
            if ($.trim(username) == '') {
                this.showErrorInfo('用户名不能为空');
                return false;
            }
            if (username.length < 2) {
                this.showErrorInfo('用户名不能少于2位字符');
                return false;
            }
            if (username.indexOf(' ') >= 0) {
                this.showErrorInfo('用户名不能包含空格符');
                return false;
            }
            this.showErrorInfo('');
            return true;
        },
        validatePassword: function () {
            var password = this.$password.val();
            if ($.trim(password) == '') {
                this.showErrorInfo('密码不能为空');
                return false;
            } else if (password.indexOf(' ') >= 0) {
                this.showErrorInfo('密码不能包含空格符');
                return false;
            } else if (password.length < 6) {
                this.showErrorInfo('密码至少包含6个字符');
                return false;
            }
            this.showErrorInfo('');
            return true;
        },
        validateExpireTime: function () {
            if (this.$expireTime.attr('disabled')) {
                return true;
            }
            var expireTime = this.$expireTime.val();
            if ($.trim(expireTime) == '') {
                this.showErrorInfo('有效期截止时间不能为空');
                return false;
            } else if (!JSUtils.isDateTimeString(expireTime)) {
                this.showErrorInfo('有效期截止时间格式错误');
                return false;
            }
            this.showErrorInfo('');
            return true;
        },
        validateInput: function () {
            if (!this.validateUsername()) {
                this.$username.focusOrSelect();
                return false;
            } else if (!this.validatePassword()) {
                this.$password.focusOrSelect();
                return false;
            } else if (!this.validateExpireTime()) {
                this.$expireTime.focusOrSelect();
                return false;
            } else {
                return true;
            }
        },
        postInit: function () {
            var self = this;
            this.$errorInfo = this.$floatPanel.find('div.error-info');

            this.$username = setBlurEven(this.$floatPanel.getInputByName('username'), this.validateUsername);
            this.$password = setBlurEven(this.$floatPanel.getInputByName('password'), this.validatePassword);
            this.$expireTime = setBlurEven(this.$floatPanel.getInputByName('expireTime'), this.validateExpireTime);
            this.$floatPanel.find('div.close-icon').click(function () {
                self.get$CancelButton().trigger('click');
            });

            this.$floatPanel.find('input[name=alwaysValid]').click(function () {
                if (this.checked) {
                    self.$expireTime.attr('disabled', true);
                } else {
                    self.$expireTime.attr('disabled', false).focusOrSelect();
                }
            });

            this.$floatPanel.find('div.to-manage-panel a').click(function () {
                self.$floatPanel.hide();
                userManager.show();
            });

            function setBlurEven($target, func) {
                $target.blur(function () {
                    setTimeout(function () {
                        //func.call(self);
                    }, 300);
                });
                return $target;
            }
        }
    });
    $('#addUserLink').click(function () {
        userAdder.show();
    });

    // code about managing user
    var userManager = ({
        $floatPanel: $('#manageUserPanel'),
        loadData: function (callback) {
            var self = this;
            $.post('admin-user-query.json', function (data) {
                if (data.success) {
                    var $html = $(JSUtils.handlebars('userTableRowTpl', {rows: data.detail}));
                    $html.find('img.delete').click(function () {
                        var id = $(this).getParentByTagName('tr').data('id');
                        $.post('admin-user-delete.json', {id: id}, function (data) {
                            if (data.success) {
                                self.loadData();
                            } else {
                                alert(data.detail);
                            }
                        });
                    });

                    self.$tbody.empty();
                    $html.appendTo(self.$tbody);

                    typeof(callback) == 'function' && callback();
                } else {
                    alert(data.detail);
                }
            });
        },
        show: function () {
            var self = this;
            this.loadData(function () {
                JSUtils.showTransparentBackground(1);
                self.$floatPanel.fadeIn(200);
                JSUtils.scrollToVerticalCenter(self.$floatPanel);
            });
        },
        hide: function () {
            this.$floatPanel.fadeOut(200, function () {
                JSUtils.hideTransparentBackground();
            });
        },
        init: function () {
            var self = this;
            this.$tbody = this.$floatPanel.find('tbody');
            this.$floatPanel.find('div.close-icon').click(function () {
                self.hide();
            });
            this.$floatPanel.find('div.to-add-panel a').click(function () {
                self.$floatPanel.hide();
                userAdder.show();
            });
            return this;
        }
    }).init();
    $('#manageUserLink').click(function () {
        userManager.show();
    });
})();
