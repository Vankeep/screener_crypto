package com.mycompany.api_okex_binance_v2.enums;

public enum QCoin {

    BTC("BTC"), ETH("ETH"), USDT("USDT");

    private final String name;

    private QCoin(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
    
}
