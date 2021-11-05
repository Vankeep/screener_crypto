package com.mycompany.api_okex_binance_v2;

import com.mycompany.api_okex_binance_v2.enums.Coin;




public interface HttpClient {
    
    public boolean updateAllPair();
    
    public boolean updateCoinData(String bCoin, Coin qCoin);
    
    
}
