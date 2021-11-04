package com.mycompany.api_okex_binance_v2;


import com.mycompany.api_okex_binance_v2.constants.Const;
import java.util.HashMap;

public interface DatabaseClient {

    public abstract HashMap<Integer,String> getAllPair(Const.Coin qCoin);

    public abstract long[] getDataCoin(Const.TF tf, String bCoin, Const.Coin qCoin, Const.OHLC ohlc);

    public abstract long[][] getDataCoin(Const.TF tf, String bCoin, Const.Coin qCoin, Const.OHLC ohlc1, Const.OHLC ohlc2);

    public abstract long[][] getDataCoin(Const.TF tf, String bCoin, Const.Coin qCoin, Const.OHLC ohlc1, Const.OHLC ohlc2, Const.OHLC ohlc3);

    public abstract long[][] getDataCoin(Const.TF tf, String bCoin, Const.Coin qCoin, Const.OHLC ohlc1, Const.OHLC ohlc2, Const.OHLC ohlc3, Const.OHLC ohlc4);

}
