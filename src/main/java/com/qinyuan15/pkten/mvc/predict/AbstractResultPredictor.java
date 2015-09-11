package com.qinyuan15.pkten.mvc.predict;

import com.qinyuan15.pkten.mvc.dao.DrawnRecord;

import java.util.List;

public abstract class AbstractResultPredictor implements ResultPredictor {
    protected List<DrawnRecord> oldRecords;

    @Override
    public void setOldRecords(List<DrawnRecord> oldRecords) {
        this.oldRecords = oldRecords;
    }
}
