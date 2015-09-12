package com.qinyuan15.pkten.mvc.predict;

import com.qinyuan15.pkten.mvc.dao.DrawnRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public abstract class AbstractResultPredictor implements ResultPredictor {
    private final static Logger LOGGER = LoggerFactory.getLogger(AbstractResultPredictor.class);
    private List<DrawnRecord> oldRecords;
    private int referSize = -1;

    protected List<DrawnRecord> getReferredRecords(int phase) {
        List<DrawnRecord> records = new ArrayList<>();

        if (oldRecords == null || oldRecords.size() == 0) {
            LOGGER.error("old records is null or empty, old records: {}", oldRecords);
            return records;
        }

        for (DrawnRecord record : oldRecords) {
            if (record.getPhase() < phase) {
                records.add(record);
                if (referSize > 0 && records.size() >= referSize) {
                    break;
                }
            }
        }
        return records;
    }

    @Override
    public void setReferSize(int referSize) {
        this.referSize = referSize;
    }

    @Override
    public ResultPrediction predict() {
        return predict(oldRecords.get(0).getPhase() + 1);
    }

    @Override
    public void setOldRecords(List<DrawnRecord> oldRecords) {
        this.oldRecords = new ArrayList<>(oldRecords);
        Collections.sort(this.oldRecords, new Comparator<DrawnRecord>() {
            @Override
            public int compare(DrawnRecord o1, DrawnRecord o2) {
                // order by draw time desc
                return o2.getDrawTime().compareTo(o1.getDrawTime());
            }
        });
    }
}
