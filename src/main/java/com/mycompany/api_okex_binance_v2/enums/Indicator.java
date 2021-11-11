package com.mycompany.api_okex_binance_v2.enums;

public enum Indicator {
    RSI("rsi", ColumnsTable.ONE_HOUR_RSI, ColumnsTable.FOUR_HOUR_RSI, ColumnsTable.ONE_DAY_RSI);
    
    String name;
    ColumnsTable one_hour;
    ColumnsTable four_hour;
    ColumnsTable one_day;

    private Indicator(String name, ColumnsTable one_hour, ColumnsTable four_hour, ColumnsTable one_day) {
        this.name = name;
        this.one_hour = one_hour;
        this.four_hour = four_hour;
        this.one_day = one_day;
    }

    
}
