package com.mkl.mkltest.controller.api;

import com.mkl.mkltest.entity.User;

public class BuyOrderRequest {
    private String session;
    private User user;
    private long createDate;
    private int createDateInt;

    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public long getCreateDate() {
        return createDate;
    }

    public void setCreateDate(long createDate) {
        this.createDate = createDate;
    }

    public int getCreateDateInt() {
        return createDateInt;
    }

    public void setCreateDateInt(int createDateInt) {
        this.createDateInt = createDateInt;
    }
    
}