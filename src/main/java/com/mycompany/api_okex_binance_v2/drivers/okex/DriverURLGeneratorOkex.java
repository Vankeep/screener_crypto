package com.mycompany.api_okex_binance_v2.drivers.okex;

import com.mycompany.api_okex_binance_v2.enums.QCoin;
import com.mycompany.api_okex_binance_v2.enums.Tf;
import com.mycompany.api_okex_binance_v2.time.Time;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import org.slf4j.LoggerFactory;

public class DriverURLGeneratorOkex {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(DriverURLGeneratorOkex.class.getSimpleName());

    public static HttpURLConnection urlAllExchangeInfo() {
        URL url;
        try {
            url = new URL("https://www.okex.com/api/spot/v3/instruments");
            return (HttpURLConnection) url.openConnection();
        } catch (IOException e) {
            logger.error("Ошибка генерации ссылки для всех пар Okex. {}", e.getMessage());
            return null;
        }
    }

    public static HttpURLConnection urlPairMarketData(String bCoin, QCoin qCoin, Tf tF, int candlesBack) {
        try {
            URL url = new URL("https://www.okex.com/api/spot/v3/instruments/" + bCoin + "-" + qCoin.name()
                    + "/candles?granularity=" + getTf(tF)
                    + "&start=" + Time.unixToIso(Time.getStartTime(tF, candlesBack))
                    + "&end=" + Time.getUTCiso());
            return (HttpURLConnection) url.openConnection();
        } catch (IOException e) {
            logger.error("Ошибка генерации ссылки для {}-{} на Okex. {}", bCoin, qCoin.toString(), e.getMessage());
            return null;
        }
    }

    private static String getTf(Tf tf) {
        switch (tf) {
            case HOUR_ONE:
                return "3600";
            case HOUR_TWO:
                return "7200";
            case HOUR_FOUR:
                return "14400";
            case HOUR_SIX:
                return "21600";
            case HOUR_TWENTY:
                return "43200";
            case DAY_ONE:
                return "86400";
            default:
                return null;
        }
    }

}
