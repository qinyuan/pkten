package com.qinyuan15.pkten.mvc.predict;

import com.qinyuan.lib.lang.IntegerUtils;
import com.qinyuan15.pkten.mvc.dao.DrawnRecord;
import com.qinyuan15.pkten.mvc.dao.DrawnRecordDao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PredictMethodEvaluator {
    private int evaluateSize;
    private int referenceRecordSize;
    private ResultPredictor predictor;
    private List<DrawnRecord> records;

    public void setEvaluateSize(int evaluateSize) {
        this.evaluateSize = evaluateSize;
    }

    public void setReferenceRecordSize(int referenceRecordSize) {
        this.referenceRecordSize = referenceRecordSize;
    }

    public void setPredictor(ResultPredictor predictor) {
        this.predictor = predictor;
    }

    public void setRecords(List<DrawnRecord> records) {
        this.records = records;
    }

    /**
     * evaluate predict method
     *
     * @param position result position to evaluate
     * @return map whose key is phase and value is revenue of related phase
     */
    public Map<Integer, PredictionEvaluator.Evaluation> evaluate(int position) {
        if (!IntegerUtils.isPositive(evaluateSize)) {
            throw new RuntimeException("invalid evaluateSize: " + evaluateSize);
        } else if (!IntegerUtils.isPositive(referenceRecordSize)) {
            throw new RuntimeException("invalid referenceRecordSize: " + referenceRecordSize);
        } else if (predictor == null) {
            throw new RuntimeException("predictor is null");
        }


        if (records == null) {
            records = new DrawnRecordDao().getInstances(0, evaluateSize + referenceRecordSize);
        }
        predictor.setOldRecords(records);

        Map<Integer, PredictionEvaluator.Evaluation> revenues = new HashMap<>();
        for (int i = 0; i < evaluateSize && i < records.size() - 1; i++) {
            DrawnRecord record = records.get(i);
            int phase = record.getPhase();
            ResultPrediction prediction = predictor.predict(phase);
            revenues.put(phase, new PredictionEvaluator(prediction, record).evaluate(position));
        }

        return revenues;
    }
}
