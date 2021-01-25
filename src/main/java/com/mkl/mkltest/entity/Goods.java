package com.mkl.mkltest.entity;

public class Goods {
    private String id;
    private String session;
    private String ownerUserName;
    private String ownerBankAccount;
    private long value;
    private long dateOnSell;
    private int dateOnSellInt;
    private boolean isActive;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getDateOnSell() {
        return dateOnSell;
    }

    public void setDateOnSell(long dateOnSell) {
        this.dateOnSell = dateOnSell;
    }

    public int getDateOnSellInt() {
        return dateOnSellInt;
    }

    public void setDateOnSellInt(int dateOnSellInt) {
        this.dateOnSellInt = dateOnSellInt;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }

    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }

    public String getOwnerBankAccount() {
        return ownerBankAccount;
    }

    public void setOwnerBankAccount(String ownerBankAccount) {
        this.ownerBankAccount = ownerBankAccount;
    }

    public String getOwnerUserName() {
        return ownerUserName;
    }

    public void setOwnerUserName(String ownerUserName) {
        this.ownerUserName = ownerUserName;
    }

    public long getValue() {
        return value;
    }

    public void setValue(long value) {
        this.value = value;
    }
    
}