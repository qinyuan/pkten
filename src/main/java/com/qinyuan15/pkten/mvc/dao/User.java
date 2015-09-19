package com.qinyuan15.pkten.mvc.dao;

import com.qinyuan.lib.lang.DateUtils;

public class User extends com.qinyuan.lib.mvc.security.User {
    private String expireTime;

    public String getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(String expireTime) {
        this.expireTime = DateUtils.trimMilliSecond(expireTime);
    }
}
