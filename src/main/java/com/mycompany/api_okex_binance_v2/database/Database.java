package com.mycompany.api_okex_binance_v2.database;

import com.mycompany.api_okex_binance_v2.DatabaseClient;
import com.mycompany.api_okex_binance_v2.constants.Const;
import com.mycompany.api_okex_binance_v2.enums.*;
import com.mycompany.api_okex_binance_v2.net.Connect;
import com.mycompany.api_okex_binance_v2.obj.CoinCoin;
import com.mycompany.api_okex_binance_v2.time.Time;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Database extends DBInsertAndRead implements DatabaseClient {

    private static final Logger logger = LoggerFactory.getLogger(Database.class.getSimpleName());

    public Database(Exchange exchange) {
        super(exchange);
    }

    @Override
    public boolean sendMessage(String message) {
        boolean ok = false;
        if (connect()) {
            ok = insert(message);
            close();
        }
        return ok;
    }

    @Override
    public boolean createAllTable(QCoin qCoin) {
        boolean ok = false;
        Map<Integer, String> list = getAllPair(qCoin);
        if (connect()) {
            for (int i = 1; i <= list.size(); i++) {
                ok = insert(msgCreateTable(list.get(i), qCoin.toString()));
            }
            close();
        }
        return ok;
    }

    @Override
    public boolean deleteAllTable(QCoin qCoin) {
        boolean ok = false;
        Map<Integer, String> list = getAllPair(qCoin);
        if (connect()) {
            for (int i = 0; i < list.size(); i++) {
                ok = insert(msgDeleteTable(list.get(i), qCoin.toString()));
            }
            close();
        }
        return ok;
    }

    @Override
    public int getLastUpdatePair(String bCoin, QCoin qCoin) {
        String lastUpdateIso = "";
        if (connect()) {
            lastUpdateIso = readLastUpdatePair(msgLastUpdatePair(bCoin + "_" + qCoin));
            close();
        }
        if (lastUpdateIso.equals("")) {
            logger.error("{} - таблица {}_{} пустая. Пробую скачать последнюю свечу", exchange.getName(), bCoin,qCoin.toString());
            new Connect(exchange).updateDataPair(bCoin, qCoin, Tf.HOUR_ONE);
            return -1;
        }
        double lastUpdateUnix = Time.isoToUnix(lastUpdateIso);
        double utcNowUnix = Time.getUTCunix();
        double offset = utcNowUnix - lastUpdateUnix;
        double to_hour = offset / Tf.HOUR_ONE.quantityMsec();
        if (to_hour > 1) {
            logger.info("{} - для обновления пары {}_{} нужно {} свечей", exchange.getName(), bCoin, qCoin.toString(), String.valueOf((int) to_hour));
            return (int) to_hour - 1;
        } else if (to_hour > 200) {
            logger.info("{} - {}_{} промежуток более 200 свечей, будет обновлено 200 свечей", exchange.getName(), bCoin, qCoin.toString());
            return 200;
        } else {
            logger.info("{} - данные для пары {} актуальны, обновления не требуется", exchange.getName(), bCoin + "_" + qCoin.toString());
            return -1;
        }

    }

    @Override
    public boolean cleaningDatabase() {
        Map<Integer, String> listAllTable = null;
        HashSet<String> listAllPair = new HashSet<>();
        QCoin[] listAllQcoin = QCoin.getListQCoin();
        //Получаем все таблицы
        if (connect()) {
            listAllTable = readAllNameTablePair(msgSeeAllTable());
            close();
            if (listAllTable == null) {
                logger.error("{} - лист со всеми таблицами = null", exchange.getName());
                return false;
            }
        }
        //Получаем все пары из базы 
        int counter = 1;
        for (QCoin qCoin : listAllQcoin) {
            Map<Integer, String> list = getAllPair(qCoin);
            for (int i = 1; i <=list.size(); i++) {
                listAllPair.add(list.get(i) + "_" + qCoin);
            }
        }
        //Ищем делистинг
        HashSet<String> delisting = new HashSet<>();
        boolean findDelist = false;
        for (Map.Entry<Integer,String> table: listAllTable.entrySet()) {
            findDelist = false;
            for (String pair : listAllPair) {
                if(table.getValue().equals(pair)){
                    findDelist = true;
                    break;
                }
            }
            if (!findDelist) delisting.add(table.getValue());
        }
        //Ищем листинг
        boolean findList = false;
        HashSet<String> listing = new HashSet<>();
        for (String pair : listAllPair) {
            findList = false;
            for (Map.Entry<Integer, String> table : listAllTable.entrySet()) {
                if (pair.equals(table.getValue())){
                    findList = true;
                    break;
                }
                
            }
            if (!findList) listing.add(pair);
        }
        logger.info("Список листинга: {}", listing.toString());
        //Удаляем делистинг
        for (QCoin qCoin : listAllQcoin) {
            delisting.remove(qCoin.toString());
        }
        for (String table: delisting){
            logger.info("Пара {} удалена",table);
            sendMessage("DROP TABLE " +table);
        }
        return true;

    }

    @Override
    public Map<Integer, String> getAllPair(QCoin qCoin) {
        logger.info("{} - чтение таблицы {}", exchange.getName(), qCoin);
        if (connect()) {
            try {
                Map<Integer, String> map = readAllPair(msgReadQcoin(qCoin));
                close();
                return map;
            } catch (NullPointerException ex) {
                logger.error("{} - HashMap пустой. {}", exchange.getName(), ex.getMessage());
                close();
                return null;
            }
        }
        return null;
    }

    @Override
    public ArrayList<CoinCoin> getDataCoin(Tf tf, int candlesBack, String bCoin, QCoin qCoin, Ohlc ohlc) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<CoinCoin> getDataCoin(Tf tf, int candlesBack, String bCoin, QCoin qCoin, Ohlc ohlc1, Ohlc ohlc2) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<CoinCoin> getDataCoin(Tf tf, int candlesBack, String bCoin, QCoin qCoin, Ohlc ohlc1, Ohlc ohlc2, Ohlc ohlc3) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<CoinCoin> getDataCoin(Tf tf, int candlesBack, String bCoin, QCoin qCoin, Ohlc ohlc1, Ohlc ohlc2, Ohlc ohlc3, Ohlc ohlc4) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean insertAllExInfo(ArrayList<ArrayList<String>> list) {
        boolean ok = connect();
        if (ok) {
            for (ArrayList<String> arrayList : list) {
                logger.info("{} - запись пар к {} в базу даннах", exchange.getName(), arrayList.get(0));
                insert(msgDeleteTable(arrayList.get(0)));
                insert(msgCreateTable(arrayList.get(0)));
                for (int i = 1; i < arrayList.size(); i++) {
                    ok = insert(msgInsertQcoin(arrayList.get(0), arrayList.get(i)));
                    if (!ok) {
                        break;
                    }
                }
                if (!ok) {
                    break;
                }
            }
            close();
            return ok;
        } else {
            logger.info("{} - неудачное подключение к бд или ArrayList пустой", exchange.getName());
            return ok;
        }
    }

    @Override
    public boolean insertDataPair(ArrayList<CoinCoin> list, String bCoin, QCoin qCoin) {
        boolean ok = connect();
        if (ok) {
            logger.info("{} - запись пары {}_{}", exchange.getName(), bCoin, qCoin.toString());
            for (CoinCoin c : list) {
                ok = insert(msgInsertBcoin(bCoin, qCoin.toString(),
                        c.getTime(),
                        c.getOpen(),
                        c.getHigh(),
                        c.getLow(),
                        c.getClose(),
                        c.getVolume()));
                if (!ok) {
                    break;
                }
            }
            close();
            return ok;
        } else {
            close();
            logger.error("{} - нет соединения с бд", exchange.getName());
            return ok;
        }
    }

}
