package com.mycompany.api_okex_binance_v2;

import com.mycompany.api_okex_binance_v2.constants.Const;

public interface DatabaseClient {

    public abstract String[] getAllPair(Const.Coin qCoin);

    public abstract long[] getDataCoin(Const.TF tf, String bCoin, Const.Coin qCoin, Const.OHLC ohlc);

    public abstract long[][] getDataCoin(Const.TF tf, String bCoin, Const.Coin qCoin, Const.OHLC ohlc1, Const.OHLC ohlc2);

    public abstract long[][] getDataCoin(Const.TF tf, String bCoin, Const.Coin qCoin, Const.OHLC ohlc1, Const.OHLC ohlc2, Const.OHLC ohlc3);

    public abstract long[][] getDataCoin(Const.TF tf, String bCoin, Const.Coin qCoin, Const.OHLC ohlc1, Const.OHLC ohlc2, Const.OHLC ohlc3, Const.OHLC ohlc4);

}
