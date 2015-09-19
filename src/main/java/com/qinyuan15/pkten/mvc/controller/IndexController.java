package com.qinyuan15.pkten.mvc.controller;

import com.qinyuan.lib.database.hibernate.HibernateUtils;
import com.qinyuan.lib.lang.IntegerUtils;
import com.qinyuan.lib.mvc.controller.BaseController;
import com.qinyuan.lib.mvc.security.PasswordValidator;
import com.qinyuan.lib.mvc.security.SecurityUtils;
import com.qinyuan.lib.mvc.security.User;
import com.qinyuan15.pkten.mvc.dao.DrawnRecord;
import com.qinyuan15.pkten.mvc.dao.DrawnRecordDao;
import com.qinyuan15.pkten.mvc.dao.UserDao;
import com.qinyuan15.pkten.mvc.predict.PredictMethodEvaluator;
import com.qinyuan15.pkten.mvc.predict.ResultPrediction;
import com.qinyuan15.pkten.mvc.predict.ResultPredictor;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class IndexController extends BaseController {
    private final static Logger LOGGER = LoggerFactory.getLogger(IndexController.class);
    private final static String TITLE = "北京赛车-PK10 下期预测";
    private final static int SHOW_SIZE = 50;
    private final static int PREDICT_REFER_SIZE = 15;

    @Autowired
    private ResultPredictor predictor;

    @RequestMapping("/index")
    public String index(@RequestParam(value = "evaluatePosition", required = false) Integer evaluatePosition) {
        setTitle(TITLE);

        // records
        List<DrawnRecord> records = new DrawnRecordDao().getInstances(0, SHOW_SIZE + PREDICT_REFER_SIZE);
        setAttribute("records", records.subList(0, SHOW_SIZE));

        // predictor
        predictor.setOldRecords(records);
        ResultPrediction prediction = predictor.predict();
        setAttribute("prediction", prediction);
        addJavaScriptData("prediction", prediction);

        // evaluator
        if (!IntegerUtils.isPositive(evaluatePosition)) {
            evaluatePosition = 1;
        }
        PredictMethodEvaluator evaluator = new PredictMethodEvaluator();
        evaluator.setPredictor(predictor);
        evaluator.setEvaluateSize(SHOW_SIZE);
        evaluator.setReferenceRecordSize(PREDICT_REFER_SIZE);
        evaluator.setRecords(records);
        setAttribute("evaluatePosition", evaluatePosition);
        addJavaScriptData("evaluation", evaluator.evaluate(evaluatePosition));

        addCssAndJs("index");
        ControllerUtils.setCommonParameters(this);
        return "index";
    }

    @RequestMapping("/max-phase.json")
    @ResponseBody
    public String maxPhase() {
        Map<String, Integer> map = new HashMap<>();
        map.put("phase", new DrawnRecordDao().getMaxPhase());
        return toJson(map);
    }

    @RequestMapping("/index-change-password.json")
    @ResponseBody
    public String changePassword(@RequestParam(value = "oldPassword", required = true) String oldPassword,
                                 @RequestParam(value = "newPassword", required = true) String newPassword) {
        User user = new UserDao().getInstanceByName(SecurityUtils.getUsername());
        if (user == null) {
            return fail("请重登录");
        }

        if (StringUtils.isBlank(oldPassword)) {
            return fail("原密码不能为空");
        }

        Pair<Boolean, String> passwordValidation = new PasswordValidator().validate(newPassword);
        if (!passwordValidation.getLeft()) {
            return fail(passwordValidation.getRight());
        }

        if (oldPassword.equals(user.getPassword())) {
            return fail("原密码输入错误");
        }

        try {
            user.setPassword(newPassword);
            HibernateUtils.update(user);
            return success();
        } catch (Exception e) {
            LOGGER.error("fail to update password, userId: {}, oldPassword: {}, newPassword: {}, info: {}",
                    user.getId(), oldPassword, newPassword, e);
            return failByDatabaseError();
        }
    }
}
