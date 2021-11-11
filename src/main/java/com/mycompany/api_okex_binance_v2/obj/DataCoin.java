package com.mycompany.api_okex_binance_v2.obj;

public class DataCoin {

    private String time;
    private double open;
    private double high;
    private double low;
    private double close;
    private double volume;

    public DataCoin(String time, String open, String high, String low, String close, String volume) {
        this.time = time;
        this.open = Double.parseDouble(open);
        this.high = Double.parseDouble(high);
        this.low = Double.parseDouble(low);
        this.close = Double.parseDouble(close);
        this.volume = Double.parseDouble(volume);
    }

    public DataCoin(double open, double high, double low, double close, double volume) {
        this.open = open;
        this.high = high;
        this.low = low;
        this.close = close;
        this.volume = volume;
    }
    
    

    public String getTime() {
        return time;
    }

    public double getOpen() {
        return open;
    }

    public double getHigh() {
        return high;
    }

    public double getLow() {
        return low;
    }

    public double getClose() {
        return close;
    }

    public double getVolume() {
        return volume;
    }

    

    @Override
    public String toString() {
        return "{" + "time=" + time + ", open=" + open + ", high=" + high + ", low=" + low + ", close=" + close + ", volume=" + volume + '}';
    }

}
