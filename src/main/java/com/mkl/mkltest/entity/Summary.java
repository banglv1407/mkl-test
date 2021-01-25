package com.mkl.mkltest.entity;

public class Summary {
    private String id;
    private String chartId;
    private Double totalBetDownAmount= 0d;
    private Double totalBetUpAmount = 0d;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getChartId() {
        return chartId;
    }

    public void setChartId(String chartId) {
        this.chartId = chartId;
    }

    public Double getTotalBetDownAmount() {
        return totalBetDownAmount;
    }

    public void setTotalBetDownAmount(Double totalBetDownAmount) {
        this.totalBetDownAmount = totalBetDownAmount;
    }

    public Double getTotalBetUpAmount() {
        return totalBetUpAmount;
    }

    public void setTotalBetUpAmount(Double totalBetUpAmount) {
        this.totalBetUpAmount = totalBetUpAmount;
    }
}
