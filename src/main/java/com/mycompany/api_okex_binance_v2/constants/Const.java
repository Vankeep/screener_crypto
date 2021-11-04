package com.mycompany.api_okex_binance_v2.constants;

public class Const {
    
    public enum Exchange{
        EX_OKEX("okex"), EX_BINANCE("binance");

        private String name;

        private Exchange(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }
    
    public enum Coin {
        BTC("BTC"), ETH("ETH"), USDT("USDT");

        String name;

        private Coin(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }
    }
    
    public enum TF{
        HOUR_ONE, HOUR_TWO, HOUR_FOUR, HOUR_SIX, HOUR_TWENTY, DAY_ONE;
    }
    
    public enum OHLC{
        OPEN, HIGH, LOW, CLOSE;
    }

}
