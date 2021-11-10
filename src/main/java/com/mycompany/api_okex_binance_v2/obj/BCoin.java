package com.mycompany.api_okex_binance_v2.obj;

public class BCoin {

    private String bCoin;

    public BCoin(String bCoin) {
        this.bCoin = bCoin;
    }

    public String getbCoin() {
        return bCoin;
    }

    public void setbCoin(String bCoin) {
        this.bCoin = bCoin;
    }

    @Override
    public String toString() {
        return bCoin;
    }

}
