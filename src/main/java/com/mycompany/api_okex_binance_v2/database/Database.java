package com.mycompany.api_okex_binance_v2.database;

import com.mycompany.api_okex_binance_v2.obj.BCoin;
import com.mycompany.api_okex_binance_v2.interfaces.DatabaseClient;
import com.mycompany.api_okex_binance_v2.enums.*;
import com.mycompany.api_okex_binance_v2.obj.CalculationCoin;

import com.mycompany.api_okex_binance_v2.obj.DataCoin;
import com.mycompany.api_okex_binance_v2.obj.NameTable;
import com.mycompany.api_okex_binance_v2.time.Time;
import java.util.*;
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
        Map<Integer, BCoin> list = getAllPair(qCoin);
        if (connect()) {
            for (int i = 1; i <= list.size(); i++) {
                ok = insert(msgCreateTable(list.get(i), qCoin));
            }
            close();
        }
        return ok;
    }

    @Override
    public boolean deleteAllTable(QCoin qCoin) {
        boolean ok = false;
        Map<Integer, BCoin> list = getAllPair(qCoin);
        if (connect()) {
            logger.info("{} - yдаляю все таблицы к {}", exchange, qCoin);
            for (int i = 1; i <= list.size(); i++) {
                ok = insert(msgDeleteTable(list.get(i), qCoin));
            }
            close();
        }
        return ok;
    }

    @Override
    public int getLastUpdateTimePair(BCoin bCoin, QCoin qCoin) {
        String lastUpdateIso = "";
        if (connect()) {
            lastUpdateIso = readLastUpdatePair(msgLastUpdatePair(bCoin, qCoin));
            close();
        }
        if (lastUpdateIso.equals("")) {
            logger.error("{} - таблица {}_{} пустая", exchange, bCoin, qCoin.toString());
            return -2;
        }
        double lastUpdateUnix = Time.isoToUnix(lastUpdateIso);
        double utcNowUnix = Time.getUTCunix();
        double offset = utcNowUnix - lastUpdateUnix;
        double to_hour = offset / Tf.HOUR_ONE.quantityMsec();
        if (to_hour > 1) {
            logger.info("{} - для обновления пары {}_{} нужно {} свечей", exchange, bCoin, qCoin, String.valueOf((int) to_hour));
            return (int) to_hour - 1;
        } else if (to_hour > 200) {
            logger.info("{} - {}_{} промежуток более 200 свечей, будет обновлено 200 свечей", exchange, bCoin, qCoin);
            return 200;
        } else {
            //logger.info("{} - данные для пары {} актуальны, обновления не требуется", exchange, bCoin + "_" + qCoin.toString());
            return -1;
        }

    }

    @Override
    public boolean cleaningDatabase() {
        //Лист всех таблиц
        Set<NameTable> listAllTable = null;
        //Лист всех пар из таблиц BTC ETH USDT
        Set<NameTable> listAllPair = new HashSet<>();
        QCoin[] allQcoin = QCoin.getListQCoin();
        //Получаем все таблицы
        if (connect()) {
            logger.info("{} - сканирую все таблицы в БД...", exchange);
            listAllTable = readAllTableName(msgSeeAllTable());
            close();
            if (listAllTable == null) {
                logger.error("{} - лист со всеми таблицами = null", exchange);
                return false;
            }
        }
        //Получаем все пары таблиц BTC, ETH, USDT
        int counter = 1;
        for (QCoin qCoin : allQcoin) {
            Map<Integer, BCoin> list = getAllPair(qCoin);
            for (int i = 1; i <= list.size(); i++) {
                listAllPair.add(new NameTable(list.get(i), qCoin));
            }
        }
        logger.info("{} - обьединяю все монеты из таблиц {} в один лист...", exchange, Arrays.toString(allQcoin));
        //Ищем делистинг
        logger.info("{} - ищу делистинги...", exchange);
        HashSet<NameTable> delisting = new HashSet<>();
        boolean findDelist = false;
        for (NameTable table : listAllTable) {
            findDelist = false;
            for (NameTable pair : listAllPair) {
                if (table.toString().equals(pair.toString())) {
                    findDelist = true;
                    break;
                }
            }
            if (!findDelist) {
                delisting.add(table);
            }
        }
        //Ищем листинг
        logger.info("{} - ищу листинги...", exchange);
        boolean findList = false;
        HashSet<NameTable> listing = new HashSet<>();
        for (NameTable pair : listAllPair) {
            findList = false;
            for (NameTable table : listAllTable) {
                if (pair.toString().equals(table.toString())) {
                    findList = true;
                    break;
                }

            }
            if (!findList) {
                listing.add(pair);
            }
        }

        //Удаляем делистинг
        logger.info("{} - удаляю таблицы - {}...", exchange, delisting);
        for (NameTable table : delisting) {
            if (sendMessage(msgDeleteTable(table))) {
                logger.info("Удалена неактуальная таблица {}", table);
            }
        }

        //Создаем все недостающие таблицы
        logger.info("{} - создаю таблицы - {}...", exchange, listing);
        for (NameTable table : listing) {
            if (sendMessage(msgCreateTable(table))) {
                logger.info("Создана новая таблица {}", table);
            }
        }
        delisting.clear();
        listing.clear();
        listAllPair.clear();
        return true;

    }

    @Override
    public Map<Integer, BCoin> getAllPair(QCoin qCoin) {
        logger.info("{} - чтение таблицы {}", exchange, qCoin);
        if (connect()) {
            try {
                Map<Integer, BCoin> map = readAllPair(msgReadQcoin(qCoin));
                close();
                return map;
            } catch (NullPointerException ex) {
                logger.error("{} - HashMap пустой. {}", exchange, ex.getMessage());
                close();
                return null;
            }
        }
        return null;
    }

    @Override
    public Map<NameTable, List<DataCoin>> getDataPair(NameTable[] tables, int candlesBack) {
        int counter = 0;
        Map<NameTable, List<DataCoin>> map = new HashMap<>();
        if (connect()) {
            for (NameTable table : tables) {
                try {
                    logger.info("{} - чтение таблицы {}", exchange, table);
                    String message = msgReadDataBcoin(table, candlesBack);
                    List<DataCoin> data = readDataPair(candlesBack, message);
                    map.put(table, data);
                } catch (NullPointerException e) {
                    logger.error("{} - HashMap пустой. {}", exchange, e.getMessage());
                    close();
                    return null;
                }
            }
        }
        close();
        return map;
    }

    @Override
    public boolean insertAllExInfo(HashMap<QCoin, HashSet<BCoin>> list) {
        boolean ok = connect();
        if (ok) {
            for (Map.Entry<QCoin, HashSet<BCoin>> entry : list.entrySet()) {
                logger.info("Базовый актив - {} , Массив данных - {}", entry.getKey(), entry.getValue().toString());
                QCoin key = entry.getKey();
                insert(msgDeleteTable(entry.getKey()));
                insert(msgCreateTable(entry.getKey()));
                for (BCoin bCoin : entry.getValue()) {
                    ok = insert(msgInsertQcoin(entry.getKey(), bCoin));
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
            logger.info("{} - неудачное подключение к бд или HashSet пустой", exchange);
            return ok;
        }
    }

    @Override
    public boolean insertDataPair(Map<NameTable, List<DataCoin>> pairs) {
        try {
            boolean ok = connect();
            if (ok) {
                logger.info("{} - запись пар в БД... ", exchange);
                for (Map.Entry<NameTable, List<DataCoin>> entry : pairs.entrySet()) {
                    for (DataCoin c : entry.getValue()) {
                        ok = insert(msgInsertDataBcoin(entry.getKey(),
                                c.getTime(),
                                c.getOpen(),
                                c.getHigh(),
                                c.getLow(),
                                c.getClose(),
                                c.getVolume()));
                        if (!ok) {
                            logger.info("Ошибка записи {}", entry.getKey());
                            break;
                        }
                    }
                }
                close();
                pairs.clear();
                return ok;
            } else {
                close();
                pairs.clear();
                logger.error("{} - нет соединения с бд", exchange);
                return ok;
            }

        } catch (NullPointerException ex) {
            logger.error("Map<NameTable, List<DataCoin>> = null. Сет для записи данных в БД пустой");
            return false;
        }
    }

}
