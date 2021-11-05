package com.mycompany.api_okex_binance_v2.enums;

public enum Coin {

    BTC("BTC"), ETH("ETH"), USDT("USDT");

    String name;

    private Coin(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
