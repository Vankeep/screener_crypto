package com.mycompany.api_okex_binance_v2.constants;

import java.io.File;

public class Const {
    private static final String pachToDbFile = "src"+File.separator+"main"+File.separator+"java"
                                                +File.separator+"com"+File.separator+"mycompany"
                                                +File.separator+"api_okex_binance_v2"+File.separator+"database"+ File.separator;
    public static String PATH_DATABASE(){
        return pachToDbFile;
    }
    
    public enum EXCHANGE{
        EX_OKEX("okex"), EX_BINANCE("binance");

        private String name;

        private EXCHANGE(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }
    
    public enum COIN {
        BTC("BTC"), ETH("ETH"), USDT("USDT");

        String name;

        private COIN(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }
    }
    
    public enum TF{
        //HOUR_ONE, HOUR_TWO, HOUR_FOUR, HOUR_SIX, HOUR_TWENTY, DAY_ONE;
        HOUR_ONE(3600000), HOUR_TWO(7200000), HOUR_FOUR(14400000), HOUR_SIX(21600000), HOUR_TWENTY(43200000), DAY_ONE(86400000);
        long msec;

        private TF(long msec) {
            this.msec = msec;
        }

        public long getMsec() {
            return msec;
        }
    }
    
    public enum OHLC{
        OPEN, HIGH, LOW, CLOSE;
    }

}
