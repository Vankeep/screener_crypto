package com.mycompany.api_okex_binance_v2.net;

import com.mycompany.api_okex_binance_v2.Api;
import com.mycompany.api_okex_binance_v2.obj.BCoin;
import com.mycompany.api_okex_binance_v2.constants.Const;
import com.mycompany.api_okex_binance_v2.enums.*;
import com.mycompany.api_okex_binance_v2.obj.DataCoin;
import com.mycompany.api_okex_binance_v2.obj.NameTable;
import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Connect extends Api{

    private static final Logger logger = LoggerFactory.getLogger(Connect.class.getSimpleName());

    public Connect(Exchange exchange) {
        super(exchange);
    }
    /**
     * Возвращает массив со скачанными и отсортированными монетами. Количество
     * элементов в мапе будет ровняться количеству обьектов в enum QCoin.
     *
     * @return
     */
    protected HashMap<QCoin, HashSet<BCoin>> getAllExInfo() {
        logger.info("{} - загружаю все пары в файл bin", exchange);
        HttpURLConnection url = driver.urlAllExchangeInfo();
        logger.info("{} - драйвер создан", exchange);
        logger.info("{} - ссылка на загрузку пар: {}", exchange, url.getURL().toString());

        try {
            if (!checkResponseCode(url.getResponseCode())) {
                return null;
            }
        } catch (IOException e) {
            logger.error("{} - ошибка соединения. {}", exchange, e.getMessage());
            return null;
        }

        String nameFile = Const.PATH_DATABASE + exchange + ".bin";
        File file = ConnectJson.getJsonFile(url, nameFile);
        if (file != null) {
            return driver.fileToArray(file);
        } else {
            logger.error("{} - getAllExInfo вернул null", exchange);
            return null;

        }

    }

    /**
     * Загрузка из сети данных по указанной таблице
     *
     * @param nameTable 
     * @param tf          need timeframe
     * @param candlesBack свечей назад
     * @return true if evrethink is ok
     */
    protected List<DataCoin> getDataPair(NameTable nameTable, Tf tf, int candlesBack) {
        logger.debug("{} - загрузка данных пары {}", exchange, nameTable);
        HttpURLConnection url = driver.urlPairMarketData(nameTable.getbCoin(), nameTable.getqCoin(), tf, candlesBack);

        try {
            if (!checkResponseCode(url.getResponseCode())) {
                return null;
            }
        } catch (IOException e) {
            logger.error("{} - ошибка соединения. {}", exchange, e.getMessage());
            return null;
        }

        String json = ConnectJson.getJsonString(url);
        if (json != null) {
            return driver.stringToArray(json, nameTable);
        } else {
            return null;
        }
    }

    /**
     * Проверяет ошибки севера и выводит в консоль ошибку
     *
     * @param code код ошибки
     * @return
     */
    protected boolean checkResponseCode(int code) {
        if (code == 200) {
            return true;
        } else {
            return driver.checkResponseCode(code);

        }

    }

}
