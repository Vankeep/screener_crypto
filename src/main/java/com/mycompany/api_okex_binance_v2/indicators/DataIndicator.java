package com.mycompany.api_okex_binance_v2.indicators;

public class DataIndicator {
    
    private double one_hour;
    private double four_hour;
    private double one_day;

    public DataIndicator(double one_hour, double four_hour, double one_day) {
        this.one_hour = one_hour;
        this.four_hour = four_hour;
        this.one_day = one_day;
    }

    public double getOne_hour() {
        return one_hour;
    }

    public void setOne_hour(double one_hour) {
        this.one_hour = one_hour;
    }

    public double getFour_hour() {
        return four_hour;
    }

    public void setFour_hour(double four_hour) {
        this.four_hour = four_hour;
    }

    public double getOne_day() {
        return one_day;
    }

    public void setOne_day(double one_day) {
        this.one_day = one_day;
    }
    
}
