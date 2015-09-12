package com.qinyuan15.pkten.mvc.controller;

import com.qinyuan.lib.lang.IntegerUtils;
import com.qinyuan.lib.mvc.controller.BaseController;
import com.qinyuan15.pkten.mvc.dao.DrawnRecord;
import com.qinyuan15.pkten.mvc.dao.DrawnRecordDao;
import com.qinyuan15.pkten.mvc.predict.PredictMethodEvaluator;
import com.qinyuan15.pkten.mvc.predict.ResultPrediction;
import com.qinyuan15.pkten.mvc.predict.ResultPredictor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class IndexController extends BaseController {

    private final static String TITLE = "北京赛车-PK10 下期预测";
    private final static int SHOW_SIZE = 30;
    private final static int PREDICT_REFER_SIZE = 30;

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
        addJavaScriptData("evaluation", evaluator.evaluate(evaluatePosition));

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
