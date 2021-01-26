package com.mkl.mkltest.entity;

public class BetLog {
    private Long id;
    private String userId;
    private Double betDownAmount;
    private Double betUpAmout;
    private Long chartId;
    private Long createdDate;

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


    public Long getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Long createdDate) {
        this.createdDate = createdDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getChartId() {
        return chartId;
    }

    public void setChartId(Long chartId) {
        this.chartId = chartId;
    }
    
}
