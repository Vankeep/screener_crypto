package com.mycompany.api_okex_binance_v2.obj;

public class DataCoin {

    String time;
    String open;
    String high;
    String low;
    String close;
    String volume;
    NameTable nameTable;

    public DataCoin(String time, String open, String high, String low, String close, String volume) {
        this.time = time;
        this.open = open;
        this.high = high;
        this.low = low;
        this.close = close;
        this.volume = volume;
    }

    public DataCoin(String time, String open, String high, String low, String close, String volume, NameTable nameTable) {
        this.nameTable = nameTable;
        this.time = time;
        this.open = open;
        this.high = high;
        this.low = low;
        this.close = close;
        this.volume = volume;
    }

    public String getTime() {
        return time;
    }

    public String getOpen() {
        return open;
    }

    public String getHigh() {
        return high;
    }

    public String getLow() {
        return low;
    }

    public String getClose() {
        return close;
    }

    public String getVolume() {
        return volume;
    }

    public NameTable getNameTable() {
        return nameTable;
    }

    public void setNameTable(NameTable nameTable) {
        this.nameTable = nameTable;
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
        return "{" + "time=" + time + ", open=" + open + ", high=" + high + ", low=" + low + ", close=" + close + ", volume=" + volume + ", nameTable=" + nameTable + '}';
    }

}
