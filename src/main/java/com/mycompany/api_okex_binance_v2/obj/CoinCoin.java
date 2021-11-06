package com.mycompany.api_okex_binance_v2.obj;

public class CoinCoin {

    String time;
    String open;
    String high;
    String low;
    String close;
    String volume;

    public CoinCoin(String time, String open, String high, String low, String close, String volume) {
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

    public void setTime(String time) {
        this.time = time;
    }

    public void setOpen(String open) {
        this.open = open;
    }

    public void setHigh(String high) {
        this.high = high;
    }

    public void setLow(String low) {
        this.low = low;
    }

    public void setClose(String close) {
        this.close = close;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }

    @Override
    public String toString() {
        return "CoinCoin{" + "time=" + time + ", open=" + open + ", high=" + high + ", low=" + low + ", close=" + close + ", volume=" + volume + '}';
    }

}
