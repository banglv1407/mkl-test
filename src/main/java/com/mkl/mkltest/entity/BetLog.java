package com.mkl.mkltest.entity;

public class BetLog {
    private String id;
    private String userId;
    private Double betDownAmount;
    private Double betUpAmout;
    private String chartId;
    private Long createdDate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Double getBetDownAmount() {
        return betDownAmount;
    }

    public void setBetDownAmount(Double betDownAmount) {
        this.betDownAmount = betDownAmount;
    }

    public Double getBetUpAmout() {
        return betUpAmout;
    }

    public void setBetUpAmout(Double betUpAmout) {
        this.betUpAmout = betUpAmout;
    }

    public String getChartId() {
        return chartId;
    }

    public void setChartId(String chartId) {
        this.chartId = chartId;
    }

    public Long getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Long createdDate) {
        this.createdDate = createdDate;
    }
    
}
