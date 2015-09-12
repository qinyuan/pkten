package com.qinyuan15.pkten.mvc.predict;

import com.google.common.collect.Lists;
import com.qinyuan15.pkten.mvc.dao.DrawnRecord;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class PredictionEvaluatorTest {
    @Test
    public void testEvaluate() throws Exception {
        ResultPrediction prediction = new ResultPrediction(200000);
        prediction.addPositionalPrediction(1, Lists.newArrayList(1, 2, 3));
        DrawnRecord record = new DrawnRecord();
        record.setResult("04,01,01,01,01,01,01,01,01,01");

        PredictionEvaluator evaluator = new PredictionEvaluator(prediction, record);
        assertThat(evaluator.evaluate(1)).isEqualTo(-300);
        assertThat(evaluator.evaluate(2)).isEqualTo(0);

        record.setResult("02,01,01,01,01,01,01,01,01,01");
        evaluator = new PredictionEvaluator(prediction, record);
        assertThat(evaluator.evaluate(1)).isEqualTo(700);
    }
}
