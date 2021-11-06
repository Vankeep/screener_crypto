package com.mycompany.api_okex_binance_v2.net;

import com.mycompany.api_okex_binance_v2.drivers.DriverBinance;
import com.mycompany.api_okex_binance_v2.drivers.DriverOkex;
import com.mycompany.api_okex_binance_v2.enums.Coin;
import com.mycompany.api_okex_binance_v2.enums.Exchange;
import com.mycompany.api_okex_binance_v2.enums.Tf;
import com.mycompany.api_okex_binance_v2.time.Time;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.slf4j.LoggerFactory;

public class GenerateUrl {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(GenerateUrl.class.getSimpleName());
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
    public URL urlAllExchangeInfo() {
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
                logger.error("Не получилось сформировать URL, возвращаю null");
                return null;
        }
    }

    /**
     * Url to a period of time
     *
     * @param bCoin base currency
     * @param qCoin quote currensy
     * @param tF timeframe
     * @param candlesBack свечей назад
     * @return example:
     * <p>
     * https://www.okex.com/api/spot/v3/instruments/BTC-USDT/
     * <p>
     * candles?granularity=86400&
     * <p>
     * start=2021-10-20T01:00:00.000Z&end=2021-10-21T11:00:00.000Z
     */
    public URL urlPairMarketData(String bCoin, Coin qCoin, Tf tF, int candlesBack) {
        switch (ex) {
            case EX_OKEX:
                try {
                return new URL(DriverOkex.getUrl() + "api/spot/v3/instruments/" + bCoin + "-" + qCoin.name()
                        + "/candles?granularity=" + DriverOkex.getTf(tF)
                        + "&start=" + Time.unixToIso(Time.getStartTime(tF, candlesBack))
                        + "&end=" + Time.getUTCStr());
            } catch (MalformedURLException ex) {
                Logger.getLogger(GenerateUrl.class.getName()).log(Level.SEVERE, null, ex);
            }
            case EX_BINANCE:
                try {
                return new URL(DriverBinance.getUrl() + "api/v3/klines?symbol=" + bCoin + qCoin.name()
                        + "&interval=" + DriverBinance.getTf(tF)
                        + "&limit=" + (candlesBack + 1));
            } catch (MalformedURLException ex) {
                Logger.getLogger(GenerateUrl.class.getName()).log(Level.SEVERE, null, ex);
            }
            default:
                logger.error("Не получилось сформировать URL, возвращаю null");
                return null;
        }
    }

}
