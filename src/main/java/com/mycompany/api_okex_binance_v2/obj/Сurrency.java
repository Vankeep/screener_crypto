package com.mycompany.api_okex_binance_v2.obj;

public class Сurrency {

    String time;
    String open;
    String high;
    String low;
    String close;
    String volume;

    public Сurrency(String time, String open, String high, String low, String close, String volume) {
        this.time = time;
        this.open = open;
        this.high = high;
        this.low = low;
        this.close = close;
        this.volume = volume;
    }

    public long getTime() {
        return Long.parseLong(time);
    }

    public double getOpen() {
        return Double.parseDouble(open);
    }

    public double getHigh() {
        return Double.parseDouble(high);
    }

    public double getLow() {
        return Double.parseDouble(low);
    }

    public double getClose() {
        return Double.parseDouble(close);
    }

    public double getVolume() {
        return Double.parseDouble(volume);
    }

}
