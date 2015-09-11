package com.qinyuan15.pkten.mvc.crawler;

import com.qinyuan15.pkten.mvc.dao.DrawnRecord;

public abstract class AbstractResultParser implements ResultParser {
    protected final String html;

    protected AbstractResultParser(String html) {
        this.html = html;
    }

    protected DrawnRecord createDrawnRecord(Integer phase, String time, String result) {
        DrawnRecord record = new DrawnRecord();
        record.setResult(result);
        record.setDrawTime(time);
        record.setPhase(phase);
        return record;
    }
}
