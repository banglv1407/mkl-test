package com.mkl.mkltest.entity;

public class User {
    private String id;
    private String fullName;
    private String phoneNumber;
    private String userName;
    private String password;
    private String bankAccountNumber;
    private String bankCode;
    private long birthDay;
    private int birthDayInt;
    private String zalo;
    private int rice;
    private String secretWord;
    private boolean isActive;
    private Double totalAmountCharge = 0d;
    private Double totalAmountBet = 0d;
    private Double totalAmountWin = 0d;
    private Double currentAmount = 0d;
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getBankAccountNumber() {
        return bankAccountNumber;
    }

    public void setBankAccountNumber(String bankAccountNumber) {
        this.bankAccountNumber = bankAccountNumber;
    }

    public long getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(long birthDay) {
        this.birthDay = birthDay;
    }

    public String getZalo() {
        return zalo;
    }

    public void setZalo(String zalo) {
        this.zalo = zalo;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public int getBirthDayInt() {
        return birthDayInt;
    }

    public void setBirthDayInt(int birthDayInt) {
        this.birthDayInt = birthDayInt;
    }

    public int getRice() {
        return rice;
    }

    public void setRice(int rice) {
        this.rice = rice;
    }

    public String getSecretWord() {
        return secretWord;
    }

    public void setSecretWord(String secretWord) {
        this.secretWord = secretWord;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }

    public Double getTotalAmountCharge() {
        return totalAmountCharge;
    }

    public void setTotalAmountCharge(Double totalAmountCharge) {
        this.totalAmountCharge = totalAmountCharge;
    }

    public Double getTotalAmountWin() {
        return totalAmountWin;
    }

    public void setTotalAmountWin(Double totalAmountWin) {
        this.totalAmountWin = totalAmountWin;
    }

    public Double getTotalAmountBet() {
        return totalAmountBet;
    }

    public void setTotalAmountBet(Double totalAmountBet) {
        this.totalAmountBet = totalAmountBet;
    }

    public Double getCurrentAmount() {
        return currentAmount;
    }

    public void setCurrentAmount(Double currentAmount) {
        this.currentAmount = currentAmount;
    }
}
