package com.mycompany.api_okex_binance_v2;

import com.mycompany.api_okex_binance_v2.enums.Coin;
import com.mycompany.api_okex_binance_v2.enums.Ohlc;
import com.mycompany.api_okex_binance_v2.enums.Tf;
import com.mycompany.api_okex_binance_v2.obj.CoinCoin;
import java.util.ArrayList;
import java.util.HashMap;

public interface DatabaseClient {

    public void sendMessage(String message);
    
    public void createAllTable(Coin qCoin);

    public HashMap<Integer, String> getAllPair(Coin qCoin);

    /**
     * Чтение данных из бд
     *
     * @param tf нужный таймфрейм
     * @param candlesBack сколько свечей назад
     * @param bCoin base currency
     * @param qCoin qoute currency
     * @param ohlc open high low close
     * @return
     */
    public ArrayList<CoinCoin> getDataCoin(Tf tf, int candlesBack, String bCoin, Coin qCoin, Ohlc ohlc);

    public ArrayList<CoinCoin> getDataCoin(Tf tf, int candlesBack, String bCoin, Coin qCoin, Ohlc ohlc1, Ohlc ohlc2);

    public ArrayList<CoinCoin> getDataCoin(Tf tf, int candlesBack, String bCoin, Coin qCoin, Ohlc ohlc1, Ohlc ohlc2, Ohlc ohlc3);

    public ArrayList<CoinCoin> getDataCoin(Tf tf, int candlesBack, String bCoin, Coin qCoin, Ohlc ohlc1, Ohlc ohlc2, Ohlc ohlc3, Ohlc ohlc4);

}
