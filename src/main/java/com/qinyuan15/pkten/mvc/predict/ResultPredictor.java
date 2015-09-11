package com.qinyuan15.pkten.mvc.predict;

import com.qinyuan15.pkten.mvc.dao.DrawnRecord;

import java.util.List;

public interface ResultPredictor {
    void setOldRecords(List<DrawnRecord> records);

    ResultPrediction predict();
}
