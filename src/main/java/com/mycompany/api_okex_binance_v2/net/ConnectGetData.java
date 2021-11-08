package com.mycompany.api_okex_binance_v2.net;

import com.mycompany.api_okex_binance_v2.DatabaseClient;
import com.mycompany.api_okex_binance_v2.drivers.okex.*;
import com.mycompany.api_okex_binance_v2.drivers.binance.*;
import com.mycompany.api_okex_binance_v2.constants.Const;
import com.mycompany.api_okex_binance_v2.database.Database;
import com.mycompany.api_okex_binance_v2.drivers.Driver;
import com.mycompany.api_okex_binance_v2.enums.*;
import com.mycompany.api_okex_binance_v2.obj.CoinCoin;
import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConnectGetData {

    private static final Logger logger = LoggerFactory.getLogger(ConnectGetData.class.getSimpleName());
    private ArrayList<ArrayList<String>> list;
    public Driver driver;
    public Exchange exchange;
    public DatabaseClient dbClient;

    public ConnectGetData(Exchange exchange) {
        if (exchange == Exchange.EX_BINANCE){
            logger.info("Создаю обьккт DriverBinance");
            this.driver = new DriverBinance();
            this.dbClient = new Database(exchange);
        } else {
            logger.info("Создаю обьект DriverOkex");
            this.driver = new DriverOkex();
            this.dbClient = new Database(exchange);
        }
        this.exchange = exchange;
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
        logger.info("{} - загружаю все пары в файл bin", exchange.getName());
        HttpURLConnection url = driver.urlAllExchangeInfo();
        logger.info("{} - драйвер создан", driver.getExchangeName());
        logger.info("{} - ссылка на загрузку пар: {}", exchange.getName(), url.getURL().toString());

        try {
            if (!checkResponseCode(url.getResponseCode())) {
                return null;
            }
        } catch (IOException e) {
            logger.error("{} - ошибка соединения. {}",exchange.getName(), e.getMessage());
            return null;
        }

        String nameFile = Const.PATH_DATABASE + exchange.getName() + ".bin";
        File file = ConnectJson.getJsonFile(url, nameFile);
        if (file != null) {
            return driver.fileToArray(file);
        } else {
            logger.error("{} - getAllExInfo вернул null", exchange.getName());
            return null;

        }

    }

    /**
     * Загрузка данных по выбранной паре
     *
     * @param bCoin base currency
     * @param qCoin quote currency
     * @param tf need timeframe
     * @param candlesBack свечей назад
     * @return true if evrethink is ok
     */
    public ArrayList<CoinCoin> getDataPair(String bCoin, Coin qCoin, Tf tf, int candlesBack) {
        logger.info("{} - загрузка данных пары {}_{}", exchange.getName(), bCoin, qCoin);
        HttpURLConnection url = driver.urlPairMarketData(bCoin, qCoin, tf, candlesBack);

        try {
            if (!checkResponseCode(url.getResponseCode())) {
                return null;
            }
        } catch (IOException e) {
            logger.error("{} - ошибка соединения. {}",exchange.getName(), e.getMessage());
            return null;
        }

        String json = ConnectJson.getJsonString(url);
        if (json != null) {
            return driver.stringToArray(json);
        } else {
            return null;
        }
    }

    /**
     * Проверяет ошибки севера и пишет что за ошибка
     *
     * @param code код ошибки
     * @return
     */
    public boolean checkResponseCode(int code) {
        if (code == 200) {
            return true;
        } else {
            return driver.checkResponseCode(code);

        }

    }

}
