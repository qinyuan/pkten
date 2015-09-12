package com.qinyuan15.pkten.mvc.predict;

import com.qinyuan.lib.database.test.DatabaseTestCase;
import org.junit.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class PredictMethodEvaluatorTest extends DatabaseTestCase {
    @Test
    public void testEvaluate() throws Exception {
        PredictMethodEvaluator evaluator = new PredictMethodEvaluator();
        evaluator.setPredictor(new SimpleProbabilityPredictor());
        evaluator.setEvaluateSize(3);
        evaluator.setReferenceRecordSize(10);

        Map<Integer, PredictionEvaluator.Evaluation> result = evaluator.evaluate(1);
        PredictionEvaluator.Evaluation evaluation = result.get(510789);
        assertThat(evaluation.getPredictValues()).hasSize(6);
        assertThat(evaluation.getRevenue()).isEqualTo(400);
        assertThat(evaluation.getRealValue()).isEqualTo(2);
        assertThat(evaluation.isWin()).isTrue();

        evaluation = result.get(510790);
        assertThat(evaluation.getPredictValues()).hasSize(6);
        assertThat(evaluation.getRevenue()).isEqualTo(400);
        assertThat(evaluation.getRealValue()).isEqualTo(10);
        assertThat(evaluation.isWin()).isTrue();

        evaluation = result.get(510788);
        assertThat(evaluation.getPredictValues()).hasSize(5);
        assertThat(evaluation.getRevenue()).isEqualTo(-500);
        assertThat(evaluation.getRealValue()).isEqualTo(4);
        assertThat(evaluation.isWin()).isFalse();
    }
}
