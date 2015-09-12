package com.qinyuan15.pkten.mvc.controller;

import com.qinyuan.lib.database.hibernate.HibernateUtils;
import com.qinyuan.lib.mvc.controller.BaseController;
import org.springframework.stereotype.Controller;

@Controller
public class LoginController extends BaseController {

    //@RequestMapping("/login")
    public String index() {
        setTitle("用户登录");
        addCssAndJs("login");

        /*if (getLocalAddress().equals("127.0.0.1")) {
            addJs("auto-login");
        }*/

        return "login";
    }
}
