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
        String[] items = realRecord.getResultItems();
        realResults = new int[items.length];
        for (int i = 0; i < realResults.length; i++) {
            realResults[i] = Integer.parseInt(items[i]);
        }
    }

    public int evaluate(int position) {
        if (position > realResults.length) {
            LOGGER.error("Invalid position: {}", position);
        }

        Integer result = realResults[position - 1];
        List<Integer> predictValues = prediction.getAvailableValues(position);

        int revenue = predictValues.size() * -1 * COST;
        if (predictValues.contains(result)) {
            revenue += WIN_GAIN;
        }
        return revenue;
    }
}
