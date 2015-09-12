package com.qinyuan15.pkten.mvc.predict;

public class PredictMethodEvaluator {
    private int evaluateSize;
    private int referenceRecordSize;
    private ResultPredictor predictor;

    public void setEvaluateSize(int evaluateSize) {
        this.evaluateSize = evaluateSize;
    }

    public void setReferenceRecordSize(int referenceRecordSize) {
        this.referenceRecordSize = referenceRecordSize;
    }

    public void setPredictor(ResultPredictor predictor) {
        this.predictor = predictor;
    }

    /*public Result evaluate(int position) {

    }*/

    public static class Result{

    }
}
