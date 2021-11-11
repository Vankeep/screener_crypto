package com.mycompany.api_okex_binance_v2;

import com.mycompany.api_okex_binance_v2.obj.BCoin;
import com.mycompany.api_okex_binance_v2.enums.*;
import com.mycompany.api_okex_binance_v2.indicators.DataIndicator;
import com.mycompany.api_okex_binance_v2.indicators.IndicatorClient;
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

    /**
     * Функция для запуска кода по таймеру
     */
    @Deprecated
    public void run() {
        gc.setTimeInMillis(Time.getCloseCurrentСandle(Tf.HOUR_ONE) + 5000);
        Date startDate = gc.getTime();
        logger.info("Время стрта обновлений {}", startDate);

        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                long sd = System.currentTimeMillis();
                for (QCoin qCoin : QCoin.values()) {
                    Set<UpdateCoin> set = getLastUpdateTimeDatabase(qCoin);
                    Map<NameTable, List<DataCoin>> ss = downloadDatePairFromNet(set, Tf.HOUR_ONE);
                    insertDataPairFromDatabase(ss);

                }
                System.out.println(System.currentTimeMillis() - sd);
            }
        };
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(task, startDate, 3600 * 1000);
    }

    /**
     * Загрузить список всех монет биржи в БД по таблицам монет котировок
     *
     * @return
     */
    public boolean updateAllExInfo() {
        HashMap<QCoin, HashSet<BCoin>> allPair = getAllExInfo();
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

    /**
     * Собирает список всех таблиц в файле, сравнивает со списком всех
     * актуальных таблиц соответсвующих монетам котировок. Лишние таблицы
     * удаляет, новые создает.
     *
     * @return
     */
    public boolean cleaningDatabase() {
        return database.cleaningDatabase();
    }

    /**
     * Параметризированная функция. candlesBack параметр для ручной установки
     * количества нужных данных
     *
     * @param qCoin       монета котировки
     * @param candlesBack часы. Отсчет от нуля.
     * @return обькт UpdateCoin[candlesBack,
     *         NameTable]
     */
    public Set<UpdateCoin> getLastUpdateTimeDatabase(QCoin qCoin, int candlesBack) {
        Set<UpdateCoin> set = new HashSet<>();
        Set<NameTable> setNameTables = database.getAllPair(qCoin);
        for (NameTable nameTable : setNameTables) {
            set.add(new UpdateCoin(candlesBack, nameTable));
        }
        return set;
    }

    /**
     * Возвращает Set UpdateCoin [candlesBack, NameTable]. В параметре
     * candlesBack будет количество часов прошедших с последнего обновления.
     *
     * @param qCoin монета котировки
     * @return обькт UpdateCoin[candlesBack,
     *         NameTable]
     */
    public Set<UpdateCoin> getLastUpdateTimeDatabase(QCoin qCoin) {

        Set<UpdateCoin> set = new HashSet<>();
        Set<NameTable> setNameTables = database.getAllPair(qCoin);
        for (NameTable nameTable : setNameTables) {
            int candlesBack = database.getLastUpdateTimePair(nameTable);
            set.add(new UpdateCoin(candlesBack, nameTable));
        }
        return set;
    }

    /**
     * Возвращает Set со всеми актуальными таблицами в БД. Таблицы беруться в
     * соотвествии со списом монет в таблицах монет котировок
     *
     * @param qCoin
     * @return
     */
    public Set<NameTable> getAllPairFromDatabase(QCoin qCoin) {
        return database.getAllPair(qCoin);
    }

    /**
     * Возвращает Map, где ключ - имя таблицы, значение - лист с DataCoin
     * time, open, high, low, close, volume  в указанном диапазоне candlesBack
     *
     * @param nameTables  имя таблицы
     * @param candlesBack Отсчет от 1. сколько часов назад.
     * @return см описание
     */
    public Map<NameTable, List<DataCoin>> getDataPairFromDatabase(Set<NameTable> nameTables, int candlesBack) {
        return database.getDataPair(nameTables, candlesBack);
    }

    public Map<NameTable, List<DataIndicator>> insertDataIndicatorFromDatabase(NameTable[] nameTables) {
        return null;
    }

    /**
     * Вставляет полученные данные из функции downloadDatePairFromNet в БД
     *
     * @param downloadingDataPairFromNet дынные из ф-ции downloadDatePairFromNet
     * @return
     */
    public boolean insertDataPairFromDatabase(Map<NameTable, List<DataCoin>> downloadingDataPairFromNet) {
        boolean ok = database.insertDataPair(downloadingDataPairFromNet);
        if (ok) {
            logger.info("{} - Данные успешно записаны в БД", exchange);
            return true;
        }
        return false;
    }

    /**
     * Загружает данные из интернета. Можно запустить только с помощью данных из
     * функции getLastUpdateTimeDatabase
     *
     * @param lastUpdateTimeDatabase список из функции getLastUpdateTimeDatabase
     * @param tf                     таймфрейм
     * @return возвращает Мар где ключ - имя таблицы, значение - лист с обьктами
     *         DataCoin содержащих данные
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
                logger.info("{} - загружаю {}...", exchange, coin.getNameTable());
                List<DataCoin> pair = getDataPair(coin.getNameTable(), tf, coin.getCandlesBack());
                setPairs.put(coin.getNameTable(), pair);
            }
            if (coin.getCandlesBack() == -1) {
                logger.info("{} - {} данные актуальны", exchange, coin.getNameTable());
                cycle++;
            }
            if (coin.getCandlesBack() == -2) {
                logger.info("{} - Таблица {} пустая, загружаю последние данные", exchange, coin.getNameTable());
                List<DataCoin> pair = getDataPair(coin.getNameTable(), tf, 0);
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

}
