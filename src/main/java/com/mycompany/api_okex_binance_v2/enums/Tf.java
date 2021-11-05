package com.mycompany.api_okex_binance_v2.enums;

public enum Tf {

    HOUR_ONE(3600000), HOUR_TWO(7200000), HOUR_FOUR(14400000), HOUR_SIX(21600000), HOUR_TWENTY(43200000), DAY_ONE(86400000);
    long msec;

    private Tf(long msec) {
        this.msec = msec;
    }

    public long getMsec() {
        return msec;
    }
}
