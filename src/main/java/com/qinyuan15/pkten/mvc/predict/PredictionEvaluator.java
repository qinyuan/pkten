package com.qinyuan15.pkten.mvc.predict;

import com.qinyuan15.pkten.mvc.dao.DrawnRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Evaluate prediction result
 * Created by qinyuan on 15-9-12.
 */
public class PredictionEvaluator {
    private final static Logger LOGGER = LoggerFactory.getLogger(PredictionEvaluator.class);

    public final static int COST = 100;
    public final static int WIN_GAIN = 1000;

    private final ResultPrediction prediction;
    private final int[] realResults;


    public PredictionEvaluator(ResultPrediction prediction, DrawnRecord realRecord) {
        this.prediction = prediction;
        this.realResults = realRecord.getResultItems();
    }

    public Evaluation evaluate(int position) {
        if (position > realResults.length) {
            LOGGER.error("Invalid position: {}", position);
        }

        Integer result = realResults[position - 1];
        List<Integer> predictValues = prediction.getAvailableValues(position);

        int revenue = predictValues.size() * -1 * COST;
        boolean win = false;
        if (predictValues.contains(result)) {
            revenue += WIN_GAIN;
            win = true;
        }
        return new Evaluation(revenue, predictValues, result, win);
    }

    public static class Evaluation {
        private final int revenue;
        private final List<Integer> predictValues;
        private final int realValue;
        private final boolean win;

        public Evaluation(int revenue, List<Integer> predictValues, int realValue, boolean win) {
            this.revenue = revenue;
            this.predictValues = predictValues;
            this.realValue = realValue;
            this.win = win;
        }

        public int getRevenue() {
            return revenue;
        }

        public List<Integer> getPredictValues() {
            return predictValues;
        }

        public int getRealValue() {
            return realValue;
        }

        public boolean isWin() {
            return win;
        }
    }
}
