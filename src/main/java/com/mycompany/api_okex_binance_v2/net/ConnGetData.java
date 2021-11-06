package com.mycompany.api_okex_binance_v2.net;

import com.mycompany.api_okex_binance_v2.constants.Const;
import com.mycompany.api_okex_binance_v2.drivers.*;
import com.mycompany.api_okex_binance_v2.enums.*;
import com.mycompany.api_okex_binance_v2.obj.CoinCoin;
import com.mycompany.api_okex_binance_v2.time.Time;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConnGetData {

    private static final Logger logger = LoggerFactory.getLogger(Connect.class.getSimpleName());
    private ArrayList<ArrayList<String>> list;
    Exchange ex;

    public ConnGetData(Exchange ex) {
        this.ex = ex;
    }

    /**
     * Метод не полиморфен. Первное значение в массиве это quote_coin.
     * <p>
     * 1 - > [BTC, GO, BNB, ETH, USDT,....]
     * <p>
     * 2 - > [ETH, ALGO, BTC, XRP, SOL,.....]
     * <p>
     * 3 - > [USDT, BTC, ETH, BNB, DODO...]
     *
     * @return три массива
     */
    public ArrayList<ArrayList<String>> getAllExInfo() {
        logger.info("Загружаю все пары в файл {}.bin", ex.getName());
        
        URL url = new GenerateUrl(ex).urlAllExchangeInfo();
        String nameFile = Const.PATH_DATABASE() + ex.getName() + ".bin";
        
        File file = Json.getJsonFile(url, nameFile);
        
        switch (ex) {
            case EX_BINANCE:
                return DriverBinance.fileToArrayBINANCE(file);
            case EX_OKEX:
                return DriverOkex.fileToArrayOKEX(file);
            default:
                logger.error("getAllExInfo вернул null");
                return null;
        }

    }

    /**
     *
     * 
     *
     * @param bCoin base currency
     * @param qCoin quote currency
     * @param tf need timeframe
     * @return true if evrethink is ok
     */
    public ArrayList<CoinCoin> getDataPair(String bCoin, Coin qCoin, Tf tf, long startTime, long endTime, int sorry) {
        logger.info("Загрузка данных по паре {}-{}", bCoin, qCoin);
        switch (ex) {
            case EX_BINANCE:
                URL url = new GenerateUrl(ex).urlPairMarketData(bCoin, qCoin, tf, startTime, Time.getEndTime(tf), sorry);
                String json = Json.getJsonString(url);
                return DriverBinance.stringToArray(json);
            case EX_OKEX:
                URL url2 = new GenerateUrl(ex).urlPairMarketData(bCoin, qCoin, tf, startTime, Time.getEndTime(tf), sorry);
                String json2 = Json.getJsonString(url2);
                return DriverOkex.stringToArray(json2);
            default:
                logger.error("getCoinData выкинула null");
                return null;
        }
    }

}
