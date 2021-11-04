package com.mycompany.api_okex_binance_v2;

import com.mycompany.api_okex_binance_v2.constants.Const;


public interface ApiClient {
    
    public abstract boolean updateAllPair();
    
    public abstract boolean updateCoinData(String bCoin, Const.Coin qCoin);
    
    
}
