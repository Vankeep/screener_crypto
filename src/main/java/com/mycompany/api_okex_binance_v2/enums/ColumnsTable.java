package com.mycompany.api_okex_binance_v2.enums;

public enum ColumnsTable {
    ID("id"),
    TIME("time"),
    OPEN("open"),
    HIGH("hign"),
    LOW("low"),
    CLOSE("close"),
    VOLUME("volume");

    String value;

    private ColumnsTable(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }

    public ColumnsTable[] ohlc() {
        return new ColumnsTable[]{OPEN, HIGH, LOW, CLOSE};
    }

    public ColumnsTable[] ohlcv() {
        return new ColumnsTable[]{OPEN, HIGH, LOW, CLOSE, VOLUME};
    }

    public ColumnsTable[] tohlcv() {
        return new ColumnsTable[]{TIME, OPEN, HIGH, LOW, CLOSE, VOLUME};
    }
}
