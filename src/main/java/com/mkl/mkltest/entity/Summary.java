package com.mkl.mkltest.entity;

public class Summary {
    private Long id;
    private Long chartId;
    private Double totalBetDownAmount= 0d;
    private Double totalBetUpAmount = 0d;

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

    public Long getChartId() {
        return chartId;
    }

    public void setChartId(Long chartId) {
        this.chartId = chartId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
