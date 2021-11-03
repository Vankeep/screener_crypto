package com.mycompany.api_okex_binance_v2.database;

import com.mycompany.api_okex_binance_v2.constants.ConstCoin; 

public class CoinBigData {
    
    private String name;
    private String[] time;
    private String[] open;
    private String[] high;
    private String[] low;
    private String[] close;
    private String[] volume;
    private ConstCoin qCoin;

    public CoinBigData(String name, String[] time, String[] open, String[] high, String[] low, String[] close, String[] volume, ConstCoin qCoin) {
        this.name = name;
        this.time = time;
        this.open = open;
        this.high = high;
        this.low = low;
        this.close = close;
        this.volume = volume;
        this.qCoin = qCoin;
    }

    public String[] getTime() {
        return time;
    }

    public String[] getOpen() {
        return open;
    }

    public String[] getHigh() {
        return high;
    }

    public String[] getLow() {
        return low;
    }

    public String[] getClose() {
        return close;
    }

    public String[] getVolume() {
        return volume;
    }
    
    public String getTime(int index) {
        return time[index];
    }

    public String getOpen(int index) {
        return open[index];
    }

    public String getHigh(int index) {
        return high[index];
    }

    public String getLow(int index) {
        return low[index];
    }

    public String getClose(int index) {
        return close[index];
    }

    public String getVolume(int index) {
        return volume[index];
    }

    public String getName() {
        return name;
    }

    public ConstCoin getqCoin() {
        return qCoin;
    }
    
    
    

   

    
}
