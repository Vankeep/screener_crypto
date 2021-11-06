package com.mycompany.api_okex_binance_v2.net;

import com.mycompany.api_okex_binance_v2.obj.CoinCoin;
import com.mycompany.api_okex_binance_v2.database.Database;
import com.mycompany.api_okex_binance_v2.drivers.DriverBinance;
import com.mycompany.api_okex_binance_v2.drivers.DriverOkex;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.ArrayList;
import com.mycompany.api_okex_binance_v2.HttpClient;
import com.mycompany.api_okex_binance_v2.enums.Coin;
import com.mycompany.api_okex_binance_v2.enums.Exchange;
import com.mycompany.api_okex_binance_v2.enums.Tf;
import com.mycompany.api_okex_binance_v2.time.Time;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Connect extends ConnGetData implements HttpClient {

    private static final Logger logger = LoggerFactory.getLogger(Connect.class.getSimpleName());

    public Connect(Exchange ex) {
        super(ex);
    }
    
    @Override
    public boolean updateAllExInfo() {
        ArrayList<ArrayList<String>> allPair = getAllExInfo();
        try {
            boolean ok = new Database(ex).insertAllExInfo(allPair);
            logger.info("Данные всех пар {} в БД успешно скачены и обновлены", ex.getName());
            return ok;
        } catch (NullPointerException e) {
            logger.error("Массив данных = null. {}", e.getMessage());
            return false;
        }
    }
    /**
     * НЕ РАБОТАЕТ
     * @param bCoin
     * @param qCoin
     * @param tf
     * @return 
     */
    @Override
    public boolean updateDataPair(String bCoin, Coin qCoin, Tf tf) {
        return false;
        
    }

    @Override
    public boolean updateDataPair(String bCoin, Coin qCoin, Tf tf, int candlectick) {
        
        ArrayList<CoinCoin> array = getDataPair(bCoin, qCoin, tf, Time.getStartTime(Tf.HOUR_ONE, candlectick), Time.getEndTime(tf), candlectick);
        for (CoinCoin coinCoin : array) {
            System.out.println(coinCoin.toString());
        }
        return false;
    }
    /**
     * НЕ РАБОТАЕТ
     * @param bCoin
     * @param qCoin
     * @param tf
     * @param startTime
     * @param endTime
     * @return 
     */
    @Override
    public boolean updateDataPair(String bCoin, Coin qCoin, Tf tf, long startTime, long endTime) {
        //URL url = new GenerateUrl(ex).urlPairMarketData(bCoin, qCoin, tf, startTime, endTime);
        return false;
    }


}
