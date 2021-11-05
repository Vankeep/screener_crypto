package com.mycompany.api_okex_binance_v2.net;

import com.mycompany.api_okex_binance_v2.constants.Const;
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
import java.io.BufferedReader;
import java.io.InputStreamReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Connect implements HttpClient {

    private static final Logger logger = LoggerFactory.getLogger(Connect.class.getSimpleName());
    private ArrayList<ArrayList<String>> list;
    private Exchange ex;

    public Connect(Exchange ex) {
        this.ex = ex;
    }

    /**
     * ВОЗВРАЩАЕТ СТРОГО ЗАДАННЫЕ ЗАРЕНЕЕ ПАРЫ К BTC ETH USDT
     *
     * @return три массива вида. Первное значение в массиве это quote_coin
     * <p>
     * 1 - > [BTC, GO, BNB, ETH, USDT,....]
     * <p>
     * 2 - > [ETH, GO, BTC, ETH, SOL,.....]
     * <p>
     * 3 - > [USDT, BTC, ETH, BNB, DODO...]
     */
    private ArrayList<ArrayList<String>> getAllExInfo() {
        GenerateUrl gum = new GenerateUrl(ex);
        File file = new File(Const.PATH_DATABASE() + ex.getName() + ".bin");
        logger.info("Загружаю все пары в файл {}", file.toString());
        try {
            ReadableByteChannel rbc = Channels.newChannel(gum.getAllCoinsData().openStream());
            FileOutputStream fos = new FileOutputStream(file);
            fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
            fos.close();
        } catch (IOException e) {
            logger.error("Ошибка соединения. Метод getCoinData. {}", e.getMessage());
            return null;
        }
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

    private boolean getCoinData(String bCoin, Coin qCoin, Tf tf) {
        logger.info("Загрузка данных по паре {}-{}", bCoin, qCoin);
        GenerateUrl gmu = new GenerateUrl(ex);
        StringBuilder sb = new StringBuilder();
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(gmu.getCoinData(bCoin, qCoin, tf).openStream()));
            String c;
            while ((c = br.readLine()) != null) {
                sb.append(c);
            }
        } catch (IOException ex) {
            logger.error("Ошибка соединения. Метод getCoinData. {}", ex.getMessage());
        }
        switch (ex) {
            case EX_BINANCE:
                return false;
            case EX_OKEX:
                return false;
            default:
                logger.error("getCoinData выкинула null");
                return false;
        }
    }

    /**
     * Обнновление всех тикеров в бд. Следана на случай появления новых монет
     *
     * @return если скачивание и запись прошли успешно возвращает true
     */
    @Override
    public boolean updateAllPair() {
        ArrayList<ArrayList<String>> allPair = getAllExInfo();
        try {
            boolean ok = new Database(ex).insertAllPairToDatabase(allPair);
            logger.info("Данные всех пар {} в БД успешно скачены и обновлены", ex.getName());
            return ok;
        } catch (NullPointerException e) {
            logger.error("Массив данных = null. {}", e.getMessage());
            return false;
        }
    }

    @Override
    public boolean updateCoinData(String bCoin, Coin qCoin) {
        return false;
    }

}
