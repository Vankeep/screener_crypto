package com.mycompany.api_okex_binance_v2.net;

import com.mycompany.api_okex_binance_v2.drivers.DriverBinance;
import com.mycompany.api_okex_binance_v2.drivers.DriverOkex;
import com.mycompany.api_okex_binance_v2.enums.Coin;
import com.mycompany.api_okex_binance_v2.enums.Exchange;
import com.mycompany.api_okex_binance_v2.enums.Tf;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GenerateUrl {

    private Exchange ex;
    private String spec;

    public GenerateUrl(Exchange ex) {
        this.ex = ex;
    }

    /**
     * Сыылка на все пары биржи
     *
     * @return строка
     */
    public URL getAllCoinsData() {
        switch (ex) {
            case EX_OKEX:
                try {
                return new URL(DriverOkex.getUrl() + "api/spot/v3/instruments");
            } catch (MalformedURLException ex) {
                Logger.getLogger(GenerateUrl.class.getName()).log(Level.SEVERE, null, ex);
            }

            case EX_BINANCE:
                try {
                return new URL(DriverBinance.getUrl() + "api/v3/exchangeInfo");
            } catch (MalformedURLException ex) {
                Logger.getLogger(GenerateUrl.class.getName()).log(Level.SEVERE, null, ex);
            }
            default:
                return null;
        }
    }
//start= 2021-10-20  01:00:00
//end  = 2021-10-21  11:00:00
    
    /**
     * Url to a period of time
     *
     * @param bCoin base currency
     * @param qCoin quote currensy
     * @param tF timeframe
     * @return example:
     *         https://www.okex.com/api/spot/v3/instruments/BTC-USDT/candles?granularity=86400&start=2021-10-20T01:00:00.000Z&end=2021-10-21T11:00:00.000Z
     */
    public URL getCoinData(String bCoin, Coin qCoin, Tf tF) {
        switch (ex) {
            case EX_OKEX:
                try {
                    return new URL(DriverOkex.getUrl() + "api/spot/v3/instruments/" + bCoin + "-" + qCoin.name()
                            + "/candles?granularity=" + DriverOkex.getTf(tF)
                            + "&start=2021-10-20T01:00:00.000Z&end=2021-10-21T11:00:00.000Z");
                } catch (MalformedURLException ex) {
                    Logger.getLogger(GenerateUrl.class.getName()).log(Level.SEVERE, null, ex);
                }
            case EX_BINANCE:
                try {
                    return new URL(DriverBinance.getUrl() + "api/v3/klines?symbol=" + bCoin + qCoin.name()
                            + "&interval=" + DriverBinance.getTf(tF)
                            + "&startTime=1634140800000&endTime=1634148000000");
                } catch (MalformedURLException ex) {
                    Logger.getLogger(GenerateUrl.class.getName()).log(Level.SEVERE, null, ex);
                }
            default:
                return null;
        }
    }

}
