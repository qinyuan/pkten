package com.qinyuan15.pkten.mvc.predict;

import com.qinyuan.lib.lang.FrequencyCounter;
import com.qinyuan15.pkten.mvc.dao.DrawnRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SimpleProbabilityPredictor extends AbstractResultPredictor {
    private final static Logger LOGGER = LoggerFactory.getLogger(SimpleProbabilityPredictor.class);
    private final static int BALL_COUNT = 10;
    private final static int TOP_VALUE_COUNT = 7;

    @Override
    public ResultPrediction predict() {
        if (oldRecords == null || oldRecords.size() == 0) {
            LOGGER.error("old records is null or empty, unable to predict, old records: {}", oldRecords);
            return new ResultPrediction(-1);
        }

        Collections.sort(oldRecords, new Comparator<DrawnRecord>() {
            @Override
            public int compare(DrawnRecord o1, DrawnRecord o2) {
                // order by draw time desc
                return o2.getDrawTime().compareTo(o1.getDrawTime());
            }
        });
        ResultPrediction prediction = new ResultPrediction(oldRecords.get(0).getPhase() + 1);

        FrequencyCounter[] fCounters = buildFrequencyCounter();
        boolean first = true;
        for (DrawnRecord record : oldRecords) {
            String[] resultItems = record.getResultItems();
            if (first) {
                for (int i = 0; i < resultItems.length && i < BALL_COUNT; i++) {
                    fCounters[i].addExclude(Integer.parseInt(resultItems[i]));
                }
                first = false;
            } else {
                for (int i = 0; i < resultItems.length && i < BALL_COUNT; i++) {
                    fCounters[i].add(Integer.parseInt(resultItems[i]));
                }
            }
        }

        for (int i = 0; i < BALL_COUNT; i++) {
            prediction.addPositionalPrediction(i + 1, getFrequentlyAppearValues(fCounters[i]));
        }

        return prediction;
    }

    @SuppressWarnings("unchecked")
    private List<Integer> getFrequentlyAppearValues(FrequencyCounter frequencyCounter) {
        List<Integer> values = (List) frequencyCounter.top(TOP_VALUE_COUNT);
        int i = 1;
        while (values.size() == 0 && i <= BALL_COUNT) {
            values = (List) frequencyCounter.top(TOP_VALUE_COUNT + i);
            i++;
        }
        return values;
    }

    private FrequencyCounter[] buildFrequencyCounter() {
        FrequencyCounter[] fCounters = new FrequencyCounter[BALL_COUNT];
        for (int i = 0; i < fCounters.length; i++) {
            fCounters[i] = new FrequencyCounter();
        }
        return fCounters;
    }
}
