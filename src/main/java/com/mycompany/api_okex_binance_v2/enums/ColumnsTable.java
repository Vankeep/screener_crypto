package com.mycompany.api_okex_binance_v2.enums;

public enum ColumnsTable {
    ID("id", "INTEGER PRIMARY KEY AUTOINCREMENT"),
    TIME("time", "VARCHAR(50)"),
    OPEN("open", "DOUBLE"),
    HIGH("high", "DOUBLE"),
    LOW("low", "DOUBLE"),
    CLOSE("close", "DOUBLE"),
    VOLUME("volume", "DOUBLE"),
    ONE_HOUR_RSI("one_h_rsi", "DOUBLE"),
    FOUR_HOUR_RSI("four_h_rsi", "DOUBLE"),
    ONE_DAY_RSI("one_d_rsi", "DOUBLE");

    private String name;
    private String typeDate;

    private ColumnsTable(String name, String typeDate) {
        this.name = name;
        this.typeDate = typeDate;
    }

    public String getName() {
        return name;
    }

    public String getTypeDate() {
        return typeDate;
    }

    @Override
    public String toString() {
        return name + " " + typeDate;
    }

    
    
    

    
}
