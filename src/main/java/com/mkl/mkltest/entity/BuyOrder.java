package com.mkl.mkltest.entity;

public class BuyOrder {
    private String id;
    private String session;
    private User user;
    private long createDate;
    private int createDateInt;
    private boolean isActive;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }
    
}