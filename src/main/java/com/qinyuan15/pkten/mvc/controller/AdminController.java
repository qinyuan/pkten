package com.qinyuan15.pkten.mvc.controller;

import com.qinyuan.lib.lang.DateUtils;
import com.qinyuan.lib.mvc.controller.BaseController;
import com.qinyuan.lib.mvc.security.PasswordValidator;
import com.qinyuan15.pkten.mvc.dao.UserDao;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
public class AdminController extends BaseController {
    private final static Logger LOGGER = LoggerFactory.getLogger(AdminController.class);

    @RequestMapping("admin-add-user.json")
    @ResponseBody
    public String addUser(@RequestParam(value = "username", required = true) String username,
                          @RequestParam(value = "password", required = true) String password,
                          @RequestParam(value = "expireTime", required = false) String expireTime) {
        UserDao userDao = new UserDao();

        if (StringUtils.isBlank(username)) {
            return fail("用户名不能为空");
        } else if (username.length() < 2) {
            return fail("用户名至少包含2个字符");
        } else if (username.contains(" ")) {
            return fail("用户名不能包含空格");
        } else if (userDao.hasUsername(username)) {
            return fail("该用户名已经存在！");
        }

        Pair<Boolean, String> passwordValidation = new PasswordValidator().validate(password);
        if (!passwordValidation.getLeft()) {
            return fail(passwordValidation.getRight());
        }

        if (StringUtils.isNotBlank(expireTime) && !DateUtils.isDateTime(expireTime)) {
            return fail("有效期截止时间格式错误，正确的格式如'2015-01-02 12:13:14'");
        }

        try {
            userDao.addNormal(username, password, expireTime);
            return success();
        } catch (Exception e) {
            LOGGER.error("fail to add user, username: {}, password: {}, expireTime: {}",
                    username, password, expireTime);
            return failByDatabaseError();
        }
    }

    @RequestMapping("admin-user-delete.json")
    @ResponseBody
    public String deleteUser(@RequestParam(value = "id", required = true) Integer id) {
        try {
            new UserDao().deleteNormal(id);
            return success();
        } catch (Exception e) {
            LOGGER.error("fail to delete user, id: {}", id);
            return failByDatabaseError();
        }
    }

    @RequestMapping("admin-user-query.json")
    @ResponseBody
    public String queryUser() {
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("success", true);
            map.put("detail", new UserDao().getNormalInstances());
            return toJson(map);
        } catch (Exception e) {
            LOGGER.error("fail to query users");
            return failByDatabaseError();
        }
    }
}
