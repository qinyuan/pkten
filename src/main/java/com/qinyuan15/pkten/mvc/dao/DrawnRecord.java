package com.qinyuan15.pkten.mvc.dao;

import com.qinyuan.lib.database.hibernate.PersistObject;
import com.qinyuan.lib.lang.DateUtils;
import org.apache.commons.lang3.StringUtils;

public class DrawnRecord extends PersistObject {
    private Integer phase;
    private String drawTime;
    private String result;

    public Integer getPhase() {
        return phase;
    }

    public String getDrawTime() {
        return DateUtils.trimMilliSecond(drawTime);
    }

    public String getResult() {
        return result;
    }

    public void setPhase(Integer phase) {
        this.phase = phase;
    }

    public void setDrawTime(String drawTime) {
        this.drawTime = drawTime;
    }

    public void setResult(String result) {
        this.result = result;
    }

    /////////////////// derivative fields ///////////////////
    public int[] getResultItems() {
        String[] strings = StringUtils.isNotBlank(result) ? result.split(",") : new String[0];
        int[] integers = new int[strings.length];
        for (int i = 0; i < integers.length; i++) {
            integers[i] = Integer.parseInt(strings[i]);
        }
        return integers;
    }

    public String getDrawTimeDatePart() {
        return drawTime == null ? null : drawTime.replaceAll("\\s.*", "");
    }

    public String getDrawTimeTimePart() {
        return drawTime == null ? null : getDrawTime().replaceAll(".*\\s", "");
    }
}
