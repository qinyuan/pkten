package com.qinyuan15.pkten.mvc.dao;

import com.qinyuan.lib.database.hibernate.PersistObject;

public class DrawnRecord extends PersistObject {
    private Integer phase;
    private String drawTime;
    private String result;

    public Integer getPhase() {
        return phase;
    }

    public String getDrawTime() {
        return drawTime;
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
}
