package com.qinyuan15.pkten.mvc.predict;

import com.qinyuan.lib.database.test.DatabaseTestCase;
import com.qinyuan15.pkten.mvc.dao.DrawnRecordDao;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SimpleProbabilityPredictorTest extends DatabaseTestCase {

    @Test
    public void testPredict() throws Exception {
        SimpleProbabilityPredictor predictor = new SimpleProbabilityPredictor();
        predictor.setOldRecords(new DrawnRecordDao().getInstances());
        ResultPrediction prediction = predictor.predict();

        assertThat(prediction.getAvailableValues(1)).containsExactly(2, 6, 7, 4, 5, 8, 9);
        assertThat(prediction.getAvailableValues(2)).containsExactly(5, 9, 1, 8, 10, 6);
        assertThat(prediction.getAvailableValues(8)).containsExactly(3, 4, 7, 8);
    }

    @Test
    public void testPredict2() {
        SimpleProbabilityPredictor predictor = new SimpleProbabilityPredictor();
        predictor.setOldRecords(new DrawnRecordDao().getInstances());
        ResultPrediction prediction = predictor.predict(510777);

        assertThat(prediction.getAvailableValues(1)).containsExactly(6, 10);
        assertThat(prediction.getAvailableValues(2)).containsExactly(8, 10);
        assertThat(prediction.getAvailableValues(3)).containsExactly(3);
    }

    @Test
    public void testSetReferSize() {
        SimpleProbabilityPredictor predictor = new SimpleProbabilityPredictor();
        predictor.setOldRecords(new DrawnRecordDao().getInstances());
        predictor.setReferSize(3);
        ResultPrediction prediction = predictor.predict();

        assertThat(prediction.getAvailableValues(1)).containsExactly(2, 4);
        assertThat(prediction.getAvailableValues(2)).containsExactly(5);
        assertThat(prediction.getAvailableValues(10)).containsExactly(5, 10);
    }
}
