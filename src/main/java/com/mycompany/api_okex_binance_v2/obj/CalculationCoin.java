package com.mycompany.api_okex_binance_v2.obj;

public class CalculationCoin {
    private double open;
    private double high;
    private double low;
    private double close;
    private double volume;

    public CalculationCoin(String open, String high, String low, String close, String volume) {
        this.open = Double.parseDouble(open);
        this.high = Double.parseDouble(high);
        this.low = Double.parseDouble(low);
        this.close = Double.parseDouble(close);
        this.volume = Double.parseDouble(volume);
    }

    public double getOpenD() {
        return open;
    }

    public double getHighD() {
        return high;
    }

    public double getLowD() {
        return low;
    }

    public double getCloseD() {
        return close;
    }

    public double getVolumeD() {
        return volume;
    }

    public String getOpenS() {
        return String.valueOf(open);
    }

    public String getHighS() {
        return String.valueOf(high);
    }

    public String getLowS() {
        return String.valueOf(low);
    }

    public String getCloseS() {
        return String.valueOf(close);
    }

    public String getVolumeS() {
       return String.valueOf(volume);
    }
    

}
