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

        PredictionEvaluator.Evaluation evaluation = evaluator.evaluate(1);
        assertThat(evaluation.getRevenue()).isEqualTo(-300);
        assertThat(evaluation.getPredictValues()).containsExactly(1, 2, 3);
        assertThat(evaluation.getRealValue()).isEqualTo(4);

        evaluation = evaluator.evaluate(2);
        assertThat(evaluation.getRevenue()).isEqualTo(0);
        assertThat(evaluation.getPredictValues()).isEmpty();
        assertThat(evaluation.getRealValue()).isEqualTo(1);

        record.setResult("02,01,01,01,01,01,01,01,01,01");
        evaluator = new PredictionEvaluator(prediction, record);
        evaluation = evaluator.evaluate(1);
        assertThat(evaluation.getRevenue()).isEqualTo(700);
        assertThat(evaluation.getPredictValues()).containsExactly(1, 2, 3);
        assertThat(evaluation.getRealValue()).isEqualTo(2);
    }
}
