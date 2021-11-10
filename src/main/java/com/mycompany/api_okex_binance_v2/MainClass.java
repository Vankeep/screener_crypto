package com.mycompany.api_okex_binance_v2;

import com.mycompany.api_okex_binance_v2.database.Database;
import com.mycompany.api_okex_binance_v2.interfaces.HttpClient;

import com.mycompany.api_okex_binance_v2.enums.Exchange;
import com.mycompany.api_okex_binance_v2.enums.QCoin;
import com.mycompany.api_okex_binance_v2.enums.Tf;
import com.mycompany.api_okex_binance_v2.interfaces.DatabaseClient;
import com.mycompany.api_okex_binance_v2.net.Connect;
import com.mycompany.api_okex_binance_v2.obj.CoinCoin;
import com.mycompany.api_okex_binance_v2.obj.UpdateCoin;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MainClass {

    public static final Logger logger = LoggerFactory.getLogger(MainClass.class.getSimpleName());
    boolean updateBinance = false;
    boolean updateOkex = false;

    public static void main(String[] args) throws InterruptedException {
        test();
        //Set<UpdateCoin> set = okex.getLastUpdateTime(QCoin.BTC);

//        okex.cleaningDatabase();
        //okexDb.cleaningDatabase();
        //binDb.cleaningDatabase();
    }

    public static void test() {
        ExchangeApi okex = new ExchangeApi(Exchange.EX_OKEX);
        
        okex.cleaningDatabase();
        Set<UpdateCoin> set = okex.getLastUpdateTime(QCoin.ETH);
//
////        
        System.out.println(set);
        Set<Set<CoinCoin>> ss = okex.downloadDatePair(set, Tf.HOUR_ONE);
        okex.insertDataPair(ss);

    }

    public boolean allExUpdateAllExInfo(HttpClient okex, HttpClient binance) {
        Thread binanceThread = new Thread(() -> {
            if (binance.updateAllExInfo()) {
                logger.error("Ошибка обновления, поток binanceThread");
                updateBinance = false;
            }

        });
        binanceThread.start();
        Thread okeThread = new Thread(() -> {
            if (!okex.updateAllExInfo()) {
                logger.error("Ошибка обновления, поток okeThread");
                updateBinance = false;
            }
        });
        okeThread.start();
        try {
            binanceThread.join();
            okeThread.join();
        } catch (InterruptedException ex) {
            logger.error("Ошибка с потоками, метод allExUpdateAllExInfo");
            return false;
        }
        if (!(updateBinance && updateOkex)) {
            logger.error("Ошибка обновления всех пар, метод allExUpdateAllExInfo");
            return false;
        } else {
            logger.info("Данные успешно обновлены");
            return true;
        }
    }
    /*
     * public void downloadLastUpdate(Exchange exchange, QCoin qCoin){
     * ExchangeApi okex = new ExchangeApi(exchange);
     * Set<UpdateCoin> set = okex.getLastUpdateTime(qCoin);
     * System.out.println(set);
     * Thread thread = new Thread(() -> {
     * logger.info("Поток открыт");
     * int plusplus = 0;
     * int counter = 20;
     * ArrayList<Long> list = new ArrayList<>();
     * list.add(System.currentTimeMillis());
     * for (UpdateCoin entry : set) {
     * okex.updateDataPair(entry.getbCoin(), entry.getqCoin(), Tf.HOUR_ONE,
     * entry.getCandlesBack());
     * if (plusplus == counter) {
     * list.add(System.currentTimeMillis());
     * long timeSleep = list.get(list.size() - 1) - list.get(list.size() - 2);
     * if (timeSleep < 2000) {
     * logger.debug("Sleep {} msec...",(2000 - timeSleep));
     * try {
     * Thread.sleep(2000 - timeSleep);
     * } catch (InterruptedException ex) {
     * ex.printStackTrace();
     * }
     * }
     * counter += 20;
     * }
     * plusplus++;
     * }
     * logger.info("Затрачено времени {}", (list.get(list.size() - 1) -
     * list.get(0)) / 1000);
     * list.clear();
     * });
     * thread.start();
     * try {
     * thread.join();
     * } catch (InterruptedException ex) {
     * java.util.logging.Logger.getLogger(MainClass.class.getName()).log(Level.SEVERE,
     * null, ex);
     * }
     * }
     */

}
