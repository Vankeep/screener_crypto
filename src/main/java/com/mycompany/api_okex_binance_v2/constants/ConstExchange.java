package com.mycompany.api_okex_binance_v2.constants;

public enum ConstExchange {
    EX_OKEX("okex", "https://www.okex.com/", "3600", "7200","14400","21600","43200", "86400","base_currency","quote_currency"), 
    EX_BINANCE("binance","https://api.binance.com/", "1h", "2h", "4h", "6h", "12h", "1d","baseAsset","quoteAsset");
    
    private String name;
    private String url;
    private String HOUR_ONE;
    private String HOUR_TWO;
    private String HOUR_FOUR;
    private String HOUR_SIX;
    private String HOUR_TWENTY;
    private String DAY_ONE;
    private String bCoin;
    private String qCoin;

    private ConstExchange(String name, String url, 
            String HOUR_ONE, String HOUR_TWO, String HOUR_FOUR, String HOUR_SIX, String HOUR_TWENTY, String DAY_ONE, 
            String bCoin, String qCoin) {
        this.name = name;
        this.url = url;
        this.HOUR_ONE = HOUR_ONE;
        this.HOUR_TWO = HOUR_TWO;
        this.HOUR_FOUR = HOUR_FOUR;
        this.HOUR_SIX = HOUR_SIX;
        this.HOUR_TWENTY = HOUR_TWENTY;
        this.DAY_ONE = DAY_ONE;
        this.bCoin = bCoin;
        this.qCoin = qCoin;
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

    public String getUrl() {
        return url;
    }

    public String getHOUR_ONE() {
        return HOUR_ONE;
    }

    public String getHOUR_TWO() {
        return HOUR_TWO;
    }

    public String getHOUR_FOUR() {
        return HOUR_FOUR;
    }

    public String getHOUR_SIX() {
        return HOUR_SIX;
    }

    public String getHOUR_TWENTY() {
        return HOUR_TWENTY;
    }

    public String getDAY_ONE() {
        return DAY_ONE;
    }
    
    
    

    
    

}
