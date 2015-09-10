package com.qinyuan15.pkten.mvc.dao;

import com.qinyuan.lib.database.hibernate.PersistObject;

public class DrawnRecord extends PersistObject {
    private Integer term;
    private String drawTime;
    private String result;

    public Integer getTerm() {
        return term;
    }

    public String getDrawTime() {
        return drawTime;
    }

    public String getResult() {
        return result;
    }

    public void setTerm(Integer term) {
        this.term = term;
    }

    public void setDrawTime(String drawTime) {
        this.drawTime = drawTime;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
