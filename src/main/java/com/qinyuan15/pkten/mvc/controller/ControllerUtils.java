package com.qinyuan15.pkten.mvc.controller;

import com.qinyuan.lib.lang.DateUtils;
import com.qinyuan.lib.mvc.controller.BaseController;
import com.qinyuan.lib.mvc.security.SecurityUtils;
import com.qinyuan.lib.mvc.security.UserRole;
import com.qinyuan15.pkten.mvc.dao.User;
import com.qinyuan15.pkten.mvc.dao.UserDao;

public class ControllerUtils {
    private ControllerUtils() {
    }

    public static void setCommonParameters(BaseController controller) {
        if (SecurityUtils.hasAuthority(UserRole.ADMIN)) {
            controller.addJs("lib/handlebars.min-v1.3.0");
            controller.addJs("admin-common");
        } else if (SecurityUtils.hasAuthority(UserRole.NORMAL)) {
            User user = new UserDao().getInstanceByName(SecurityUtils.getUsername());
            if (user != null && DateUtils.isDateTime(user.getExpireTime())) {
                String expireTime = user.getExpireTime();
                controller.setAttribute("expireTime", expireTime);
                if (DateUtils.newDate(expireTime).getTime() < System.currentTimeMillis()) {
                    controller.setAttribute("userAlreadyExpire", true);
                }
            }
        }
    }
}
