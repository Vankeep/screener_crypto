package com.mycompany.api_okex_binance_v2.net;

import com.mycompany.api_okex_binance_v2.constants.Const;
import com.mycompany.api_okex_binance_v2.drivers.DriverBinance;
import com.mycompany.api_okex_binance_v2.drivers.DriverOkex;

public class GenerateUrlMessage {

    private Const.EXCHANGE ex;

    public GenerateUrlMessage(Const.EXCHANGE ex) {
        this.ex = ex;
    }

    /**
     * Сыылка на все пары биржи
     *
     * @return строка
     */
    public String getAllCoinsData() {
        switch (ex) {
            case EX_OKEX:
                return DriverOkex.getUrl() + "api/spot/v3/instruments";
            case EX_BINANCE:
                return DriverBinance.getUrl() + "api/v3/exchangeInfo";
            default:
                return null;
        }
    }

    /**
     * Url to a period of time
     *
     * @param bCoin base currency
     * @param qCoin quote currensy
     * @param tF timeframe
     * @return example:
     *         https://www.okex.com/api/spot/v3/instruments/BTC-USDT/candles?granularity=86400&start=2021-10-20T01:00:00.000Z&end=2021-10-21T11:00:00.000Z
     */
    public String getCoinData(String bCoin, Const.COIN qCoin, Const.TF tF) {
        switch (ex) {
            case EX_OKEX:
                return DriverOkex.getUrl() + "api/spot/v3/instruments/" + bCoin + "-" + qCoin.name()
                        + "/candles?granularity=" + DriverOkex.getTf(tF)
                        + "&start=2021-10-20T01:00:00.000Z&end=2021-10-21T11:00:00.000Z";
            case EX_BINANCE:
                return DriverBinance.getUrl() + "api/v3/klines?symbol=" + bCoin + qCoin.name()
                        + "&interval=" + DriverBinance.getTf(tF)
                        + "&startTime=1634140800000&endTime=1634148000000";
            default:
                return null;
        }
    }

}
