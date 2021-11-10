package com.mycompany.api_okex_binance_v2.drivers.binance;

import com.mycompany.api_okex_binance_v2.obj.BCoin;
import com.mycompany.api_okex_binance_v2.enums.QCoin;
import com.mycompany.api_okex_binance_v2.enums.Tf;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import org.slf4j.LoggerFactory;

public class DriverURLGeneratorBinance {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(DriverURLGeneratorBinance.class.getSimpleName());
    
    public static HttpURLConnection AllExchangeInfo() {
        URL url;
        try {
            url = new URL("https://api.binance.com/api/v3/exchangeInfo");
            return (HttpURLConnection) url.openConnection();
        } catch (IOException e) {
            logger.error("Ошибка генерации ссылки для всех пар Binance. {}", e.getMessage());
            return null;
        }
    }

    public static HttpURLConnection PairMarketData(BCoin bCoin, QCoin qCoin, Tf tF, int candlesBack) {
        URL url;
        try {
            url = new URL("https://api.binance.com/api/v3/klines?symbol=" + bCoin + qCoin
                    + "&interval=" + getTf(tF)
                    + "&limit=" + (candlesBack + 1));
            return (HttpURLConnection) url.openConnection();
        } catch (IOException e) {
            logger.error("Ошибка генерации ссылки для {}-{} на Okex. {}", bCoin, qCoin.toString(), e.getMessage());
            return null;
        }
    }

    private static String getTf(Tf tf) {
        switch (tf) {
            case HOUR_ONE:
                return "1h";
            case HOUR_TWO:
                return "2h";
            case HOUR_FOUR:
                return "4h";
            case HOUR_SIX:
                return "6h";
            case HOUR_TWENTY:
                return "12h";
            case DAY_ONE:
                return "1d";
            default:
                return null;
        }
    }

}
