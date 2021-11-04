package com.mycompany.api_okex_binance_v2.net;

import com.mycompany.api_okex_binance_v2.constants.Const;
import com.mycompany.api_okex_binance_v2.drivers.DriverBinance;
import com.mycompany.api_okex_binance_v2.drivers.DriverOkex;

public class GenerateUrlMessage {

    private Const.Exchange ex;

    public GenerateUrlMessage(Const.Exchange ex) {
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
     * Ссылка на период от -> до
     *
     * @param coin базовая монета
     * @param qCoin монета котировки
     * @param tF таймфрейм
     * @return строка
     */
    public String getCoinData(String coin, Const.Coin qCoin, Const.TF tF) {
        switch (ex) {
            case EX_OKEX:
                return DriverOkex.getUrl() + "api/spot/v3/instruments/" + coin + "-" + qCoin.name()
                        + "/candles?granularity=" + DriverOkex.getTf(tF)
                        + "&start=2021-10-20T01:00:00.000Z&end=2021-10-21T11:00:00.000Z";
            case EX_BINANCE:
                return DriverBinance.getUrl() + "api/v3/klines?symbol=" + coin + qCoin.name()
                        + "&interval=" + DriverBinance.getTf(tF)
                        + "&startTime=1634140800000&endTime=1634148000000";
            default:
                return null;
        }
    }

}
