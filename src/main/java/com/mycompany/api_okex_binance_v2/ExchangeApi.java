package com.mycompany.api_okex_binance_v2;

import com.mycompany.api_okex_binance_v2.obj.BCoin;
import com.mycompany.api_okex_binance_v2.enums.*;
import com.mycompany.api_okex_binance_v2.net.Connect;
import com.mycompany.api_okex_binance_v2.obj.*;
import com.mycompany.api_okex_binance_v2.time.Time;
import java.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExchangeApi extends Connect {
    public GregorianCalendar gc = new GregorianCalendar();
    private static final Logger logger = LoggerFactory.getLogger(ExchangeApi.class.getSimpleName());

    public ExchangeApi(Exchange exchange) {
        super(exchange);
    }
    
    public void run() {
        gc.setTimeInMillis(Time.getCloseCurrentСandle(Tf.HOUR_ONE) + 5000);
        Date startDate = gc.getTime();
        logger.info("Время стрта обновлений {}", startDate);
        
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                long sd = System.currentTimeMillis();
                for (QCoin qCoin : QCoin.getListQCoin()) {
                    Set<UpdateCoin> set = getLastUpdateTimeDatabase(qCoin);
                    Map<NameTable, List<DataCoin>> ss = downloadDatePairFromNet(set, Tf.HOUR_ONE);
                    insertDataPairFromDatabase(ss);
                    
                }
                System.out.println(System.currentTimeMillis()-sd);
            }
        };
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(task, startDate, 3600 * 1000);
    }
    
    public boolean updateAllExInfo() {
        HashMap<QCoin,HashSet<BCoin>> allPair = getAllExInfo();
        if (allPair != null) {
            if (database.insertAllExInfo(allPair)) {
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
        return database.cleaningDatabase();
    }
    /**
     * Для того чтобы набрать много данных 
     * @param qCoin
     * @param candlesBack
     * @return 
     */
    public Set<UpdateCoin> getLastUpdateTimeDatabase(QCoin qCoin, int candlesBack) {
        Set<UpdateCoin> set = new HashSet<>();
        Map<Integer, BCoin> mapBCoin = database.getAllPair(qCoin);
        for (Map.Entry<Integer, BCoin> entry : mapBCoin.entrySet()) {
            set.add(new UpdateCoin(candlesBack, new NameTable(entry.getValue(), qCoin)));
        }
        return set;
    }

    public Set<UpdateCoin> getLastUpdateTimeDatabase(QCoin qCoin) {
        
        Set<UpdateCoin> set = new HashSet<>();
        Map<Integer, BCoin> mapBCoin = database.getAllPair(qCoin);
        for (Map.Entry<Integer, BCoin> entry : mapBCoin.entrySet()) {
            int candlesBack = database.getLastUpdateTimePair(entry.getValue(), qCoin);
            set.add(new UpdateCoin(candlesBack, new NameTable(entry.getValue(), qCoin)));
        }
        return set;
    }

    public Map<Integer, BCoin> getAllPairFromDatabase(QCoin qCoin) {
        return database.getAllPair(qCoin);
    }
    
    public Map<NameTable, List<DataCoin>> getDataPairFromDatabase(NameTable[] nameTables, int candlesBack){
        return database.getDataPair(nameTables, candlesBack);
    }

    /**
     *
     * @param lastUpdateTimeDatabase
     * @param tf  таймфрейм
     * @return
     */

    public Map<NameTable, List<DataCoin>> downloadDatePairFromNet(Set<UpdateCoin> lastUpdateTimeDatabase, Tf tf) {
        Map<NameTable, List<DataCoin>> setPairs = new HashMap();
        if (lastUpdateTimeDatabase == null) {
            return null;
        }
        int counter = 0;
        int cycle = 20;
        List<Long> list = new ArrayList<>();
        list.add(System.currentTimeMillis());
        //Цикл загрузки пар
        for (UpdateCoin coin : lastUpdateTimeDatabase) {
            if (coin.getCandlesBack() >= 0) {
                logger.info("{} - загружаю {}...",exchange, coin.getNameTable());
                List<DataCoin> pair = getDataPair(coin.getNameTable().getbCoin(), coin.getNameTable().getqCoin(), tf, coin.getCandlesBack());
                setPairs.put(coin.getNameTable(), pair);
            }
            if(coin.getCandlesBack()==-1){
                logger.info("{} - {} данные актуальны", exchange,coin.getNameTable() );
                cycle++;
            }
            if(coin.getCandlesBack()==-2){
                logger.info("{} - Таблица {} пустая, загружаю последние данные", exchange,coin.getNameTable());
                List<DataCoin> pair = getDataPair(coin.getNameTable().getbCoin(), coin.getNameTable().getqCoin(), tf,0);
                setPairs.put(coin.getNameTable(), pair);
                
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
        return setPairs;
    }

    public boolean insertDataPairFromDatabase(Map<NameTable,List<DataCoin>> downloadingDataPairFromNet) {
        boolean ok = database.insertDataPair(downloadingDataPairFromNet);
        if (ok) {
            logger.info("{} - Данные успешно записаны в БД", exchange);
            return true;
        }
        return false;
    }

}
