package com.mycompany.api_okex_binance_v2;

import com.mycompany.api_okex_binance_v2.obj.BCoin;
import com.mycompany.api_okex_binance_v2.database.Database;
import com.mycompany.api_okex_binance_v2.enums.*;
import com.mycompany.api_okex_binance_v2.interfaces.DatabaseClient;
import com.mycompany.api_okex_binance_v2.net.Connect;
import com.mycompany.api_okex_binance_v2.obj.*;
import java.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExchangeApi extends Connect {

    private static final Logger logger = LoggerFactory.getLogger(ExchangeApi.class.getSimpleName());

    public ExchangeApi(Exchange exchange) {
        super(exchange);
    }

    public boolean updateAllExInfo() {
        HashMap<QCoin,HashSet<BCoin>> allPair = getAllExInfo();
        if (allPair != null) {
            if (exDatabaseClient.insertAllExInfo(allPair)) {
                logger.info("{} - данные всех пар в БД успешно обновлены", exchange);
                return true;
            } else {
                logger.error("{} - ошибка записи в бд", exchange);
                return false;
            }
        } else {
            logger.error("{} - массив = null", exchange);
            return false;
        }

    }

    public boolean cleaningDatabase() {
        return exDatabaseClient.cleaningDatabase();
    }
    /**
     * Для того чтобы набрать много данных 
     * @param qCoin
     * @param candlesBack
     * @return 
     */
    public Set<UpdateCoin> getLastUpdateTime(QCoin qCoin, int candlesBack) {
        Set<UpdateCoin> set = new HashSet<>();
        Map<Integer, BCoin> mapBCoin = exDatabaseClient.getAllPair(qCoin);
        for (Map.Entry<Integer, BCoin> entry : mapBCoin.entrySet()) {
            set.add(new UpdateCoin(candlesBack, new NameTable(entry.getValue(), qCoin)));
        }
        return set;
    }

    public Set<UpdateCoin> getLastUpdateTime(QCoin qCoin) {
        
        Set<UpdateCoin> set = new HashSet<>();
        Map<Integer, BCoin> mapBCoin = exDatabaseClient.getAllPair(qCoin);
        for (Map.Entry<Integer, BCoin> entry : mapBCoin.entrySet()) {
            int candlesBack = exDatabaseClient.getLastUpdateTimePair(entry.getValue(), qCoin);
            set.add(new UpdateCoin(candlesBack, new NameTable(entry.getValue(), qCoin)));
        }
        return set;
    }

    public Map<Integer, BCoin> getAllPair(QCoin qCoin) {
        return exDatabaseClient.getAllPair(qCoin);
    }

    /**
     *
     * @param set сет из этой getLastUpdateTime функции
     * @param tf  таймфрейм
     * @return
     */

    public Set<Set<DataCoin>> downloadDatePair(Set<UpdateCoin> set, Tf tf) {
        Set<Set<DataCoin>> setPairs = new HashSet<>();
        if (set == null) {
            return null;
        }
        int counter = 0;
        int cycle = 20;
        ArrayList<Long> list = new ArrayList<>();
        list.add(System.currentTimeMillis());
        //Цикл загрузки пар
        for (UpdateCoin coin : set) {
            if (coin.getCandlesBack() >= 0) {
                logger.info("{} - загружаю {}...",exchange, coin.getNameTable());
                Set<DataCoin> pair = getDataPair(coin.getNameTable().getbCoin(), coin.getNameTable().getqCoin(), tf, coin.getCandlesBack());
                setPairs.add(pair);
            }
            if(coin.getCandlesBack()==-1){
                logger.info("{} - {} данные актуальны", exchange,coin.getNameTable() );
                cycle++;
            }
            if(coin.getCandlesBack()==-2){
                logger.info("{} - Таблица {} пустая, загружаю последние данные", exchange,coin.getNameTable());
                Set<DataCoin> pair = getDataPair(coin.getNameTable().getbCoin(), coin.getNameTable().getqCoin(), tf,0);
                setPairs.add(pair);
                
            }
            if (counter == cycle) {
                list.add(System.currentTimeMillis());
                long timeSleep = list.get(list.size() - 1) - list.get(list.size() - 2);
                if (timeSleep < 3000) {
                    try {
                        Thread.sleep(3000 - timeSleep);
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                }
                cycle += 20;
            }
            counter++;
        }
        list.clear();

        logger.info("{} - Длинна пришедшего сета {} Длинна полученного сета {} ",exchange, set.size(), setPairs.size());
        return setPairs;
    }

    public boolean insertDataPair(Set<Set<DataCoin>> pairs) {
        boolean ok = exDatabaseClient.insertDataPair(pairs);
        if (ok) {
            logger.info("{} - Данные успешно записаны в БД", exchange);
            return true;
        }
        return false;
    }

}
