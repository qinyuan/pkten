package com.qinyuan15.pkten.mvc.controller;

import com.qinyuan.lib.mvc.controller.BaseController;
import com.qinyuan15.pkten.mvc.dao.DrawnRecord;
import com.qinyuan15.pkten.mvc.dao.DrawnRecordDao;
import com.qinyuan15.pkten.mvc.predict.ResultPrediction;
import com.qinyuan15.pkten.mvc.predict.ResultPredictor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class IndexController extends BaseController {

    private final static String TITLE = "北京赛车-PK10 下期预测";
    private final static int LIMIT_SIZE = 30;

    @Autowired
    private ResultPredictor predictor;

    @RequestMapping("/index")
    public String index() {
        setTitle(TITLE);

        List<DrawnRecord> records = new DrawnRecordDao().getInstances(0, LIMIT_SIZE);
        setAttribute("records", records);

        predictor.setOldRecords(records);
        ResultPrediction prediction = predictor.predict();
        setAttribute("prediction", prediction);
        addJavaScriptData("prediction", prediction);

        addCssAndJs("index");
        return "index";
    }

    /*
    @RequestMapping("/hello-world.json")
    @ResponseBody
    public String json(){
        return success();
    }
    */
}
