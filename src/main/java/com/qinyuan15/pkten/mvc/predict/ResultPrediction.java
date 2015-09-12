package com.qinyuan15.pkten.mvc.predict;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class ResultPrediction {
    private final Map<Integer, List<Integer>> positionalPredictions = new TreeMap<>();
    private final int predictPhase;

    public ResultPrediction(int predictPhase) {
        this.predictPhase = predictPhase;
    }

    public int getPredictPhase() {
        return predictPhase;
    }

    public void addPositionalPrediction(Integer position, List<Integer> availableValues) {
        positionalPredictions.put(position, availableValues);
    }

    public List<Integer> getAvailableValues(int position) {
        List<Integer> values = positionalPredictions.get(position);
        return values == null ? new ArrayList<Integer>() : values;
    }

    public Map<Integer, List<Integer>> getAllAvailableValues() {
        return new TreeMap<>(positionalPredictions);
    }
}
