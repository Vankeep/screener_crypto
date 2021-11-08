package com.mycompany.api_okex_binance_v2.enums;

public enum Exchange {
    EX_OKEX("okex"), EX_BINANCE("binance");

    private final String name;

    private Exchange(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
