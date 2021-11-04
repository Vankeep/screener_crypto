package com.mycompany.api_okex_binance_v2.constants;

public enum ConstExchange {

    EX_OKEX("okex"), EX_BINANCE("binance");

    private String name;

    private ConstExchange(String name) {
        this.name = name;
    }

    public static ConstExchange getEX_OKEX() {
        return EX_OKEX;
    }

    public static ConstExchange getEX_BINANCE() {
        return EX_BINANCE;
    }

    public String getName() {
        return name;
    }
}
