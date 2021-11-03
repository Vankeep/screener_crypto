package com.mycompany.api_okex_binance_v2.constants;

public enum ConstCoin {
    BTC("BTC"), ETH("ETH"), USDT("USDT");

    String name;

    private ConstCoin(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

}
