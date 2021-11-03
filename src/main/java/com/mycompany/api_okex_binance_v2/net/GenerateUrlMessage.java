package com.mycompany.api_okex_binance_v2.net;

import com.mycompany.api_okex_binance_v2.constants.ConstCoin;
import com.mycompany.api_okex_binance_v2.constants.ConstExchange;
import com.mycompany.api_okex_binance_v2.constants.ConstTF;

public class GenerateUrlMessage {

    private ConstExchange ex;

    public GenerateUrlMessage(ConstExchange ex) {
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
                return ex.getUrl() + "api/spot/v3/instruments";
            case EX_BINANCE:
                return ex.getUrl() + "api/v3/exchangeInfo";
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
    public String getCoinData(String coin, ConstCoin qCoin, ConstTF tF) {
        String timeFrame = Driver.tfDriver(tF, ex);
        switch (ex) {
            case EX_OKEX:
                return ex.getUrl() + "api/spot/v3/instruments/" + coin + "-" + qCoin.name()
                        + "/candles?granularity=" + timeFrame
                        + "&start=2021-10-20T01:00:00.000Z&end=2021-10-21T11:00:00.000Z";
            case EX_BINANCE:
                return ex.getUrl() + "api/v3/klines?symbol=" + coin + qCoin
                        + "&interval=" + timeFrame
                        + "&startTime=1634140800000&endTime=1634148000000";
            default:
                return null;
        }
    }

}
