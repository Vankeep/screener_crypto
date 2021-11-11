package com.mycompany.api_okex_binance_v2.database;

import com.mycompany.api_okex_binance_v2.enums.*;
import com.mycompany.api_okex_binance_v2.obj.BCoin;
import com.mycompany.api_okex_binance_v2.obj.NameTable;

public class SqlMsg {

    /**
     * Создание таблицы монеты котровки (USDT, BTC, ETH)
     * <p>
     * Заголовки таблицы: id, nameCoin
     *
     * @param qCoin имя таблицы
     * @return строка
     */
    public String msgCreateTable(QCoin qCoin) {
        String message = "CREATE TABLE " + qCoin + " ("
                + " id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + " nameCoin VARCHAR(10))";
        return message;
    }

    /**
     * Создание таблицы для монеты на случай листинга.
     * <p>
     * Заголовки таблицы: time, open, high, low, close, volume
     *
     * @param nameTable имя таблицы
     * @return CREATE TABLE nameTable ( id INTNGER PRIMARY KEY AUTOINCREMENT,
     *         time DOUBLE, open DOUBLE, high DOUBLE, low DOUBLE, close DOUBLE,
     *         volume DOUBLE)
     */
    public String msgCreateTable(NameTable nameTable) {
        ColumnsTable[] columns = ColumnsTable.values();
        String message = "CREATE TABLE " + nameTable + " ( " + columns[0];
        for (int i = 1; i < columns.length; i++) {
            message += ", " + columns[i];
        }
        return message + " )";
    }

    /**
     * Удаление таблицы монеты котировки
     *
     * @param qCoin монета котировки чью таблицу надо удалить
     * @return DROP TABLE USDT
     */
    public String msgDeleteTable(QCoin qCoin) {
        return "DROP TABLE " + qCoin;
    }

    /**
     * Удаление таблицы для монеты на случай делистинга
     *
     * @param nameTable имя таблицы
     * @return DROP TABLE bCoin_qCoin
     */
    public String msgDeleteTable(NameTable nameTable) {
        return "DROP TABLE " + nameTable;
    }

    /**
     * Последнее обновление в указанной таблице
     *
     * @param nameTable
     * @return
     */
    public String msgLastUpdatePair(NameTable nameTable) {
        return "SELECT * FROM " + nameTable + " ORDER BY time DESC LIMIT 1";
    }

    /**
     * Добаление данных в таблицу со значениями
     *
     * @param bCoin  базовая монета
     * @param qCoin  монета коритовки
     * @param time   время закрытия свечи
     * @param open   ценая открытия
     * @param high   самая верхняя цена
     * @param low    самая нижняя цена
     * @param close  цена закрытия
     * @param volume обьем
     * @return INSERT INTO bCoin_qCoin ( time, open, high, low, close, volume)
     *         VALUES ('123', '321', '234', '123', '345', '654')
     */
    public String msgInsertDataCoin(BCoin bCoin, QCoin qCoin, String time, double open, double high, double low, double close, double volume) {
        String message = "INSERT INTO " + bCoin + "_" + qCoin + " ( "
                + ColumnsTable.TIME.getName() + ", "
                + ColumnsTable.OPEN.getName() + ", "
                + ColumnsTable.HIGH.getName() + ", "
                + ColumnsTable.LOW.getName() + ", "
                + ColumnsTable.CLOSE.getName() + ", "
                + ColumnsTable.VOLUME.getName() + ")"
                + " VALUES ( '"
                + time + "', '"
                + open + "', '"
                + high + "', '"
                + low + "', '"
                + close + "', '"
                + volume + "')";
        return message;

    }

    @Deprecated
    public String msgInsertDataCoin(NameTable nameTable, String time, double open, double high, double low, double close, double volume) {
        String message = "INSERT INTO " + nameTable + " ( "
                + ColumnsTable.TIME.getName() + ", "
                + ColumnsTable.OPEN.getName() + ", "
                + ColumnsTable.HIGH.getName() + ", "
                + ColumnsTable.LOW.getName() + ", "
                + ColumnsTable.CLOSE.getName() + ", "
                + ColumnsTable.VOLUME.getName() + ")"
                + " VALUES ( '"
                + time + "', '"
                + open + "', '"
                + high + "', '"
                + low + "', '"
                + close + "', '"
                + volume + "')";
        return message;

    }

    /**
     * Добавление монеты в список всех монет к выбранной котировке (USDT, BTC,
     * ETH)
     *
     * @param nameTable имя таблицы
     * @param bCoin     base coin
     * @return INSERT INTO nameTable ( nameCoin ) VALUES ( 'bCoin' )
     */
    @Deprecated
    public String msgInsertQcoin(NameTable nameTable, BCoin bCoin) {
        return "INSERT INTO " + nameTable + " ( nameCoin ) VALUES ( '" + bCoin + "' )";
    }

    /**
     * Добавление монеты в список всех монет к выбранной котировке (USDT, BTC,
     * ETH)
     *
     * @param qCoin quote coin
     * @param bCoin base coin
     * @return INSERT INTO nameTable ( nameCoin ) VALUES ( 'bCoin' )
     */
    public String msgInsertQcoin(QCoin qCoin, BCoin bCoin) {
        return "INSERT INTO " + qCoin + " ( nameCoin ) VALUES ( '" + bCoin + "' )";
    }

    /**
     * Получение данных из таблиц с данными
     *
     * @param bCoin base coin
     * @param qCoin quote coin
     * @return SELECT time, open, high, low, close, volume FROM bCoin_qCoin
     */
    @Deprecated
    public String msgReadBcoin(BCoin bCoin, QCoin qCoin) {
        return "SELECT time, open, high, low, close, volume FROM " + bCoin + "_" + qCoin;
    }

    /**
     * Получить ограниченнное количество данных от последнего записанного
     * значения до lengthData.
     *
     * @param nameTable  имя таблицы
     * @param lengthData сколько данных с конца надо
     * @return SELECT time, open, high, low, close, volume FROM nameTable ORDER
     *         BY id DESC LIMIT lengthData
     */
    public String msgReadDataCoin(NameTable nameTable, int lengthData) {
        String message = "SELECT "
                + ColumnsTable.TIME.getName() + ", "
                + ColumnsTable.OPEN.getName() + ", "
                + ColumnsTable.HIGH.getName() + ", "
                + ColumnsTable.LOW.getName() + ", "
                + ColumnsTable.CLOSE.getName() + ", "
                + ColumnsTable.VOLUME.getName() + " FROM "
                + nameTable + " ORDER BY id DESC LIMIT "
                + lengthData;
        return message;
        //return "SELECT time, open, high, low, close, volume FROM " + nameTable + " ORDER BY id DESC LIMIT " + lengthData;
    }
    /**
     * Считать данные индикатора
     * @param nameTable имя таблицы
     * @param lengthData количество данных
     * @param one_hour тф выбранного индикатора
     * @param four_hour тф выбранного индикатора
     * @param one_day тф выбранного индикатора
     * @return строку
     */
    public String msgReadDataIndicator(NameTable nameTable, int lengthData, ColumnsTable one_hour, ColumnsTable four_hour, ColumnsTable one_day){
        String message = "SELECT "
                + one_hour + ", "
                + four_hour + ", "
                + one_day + " FROM "
                + nameTable + " ORDER BY id DESC LIMIT "
                + lengthData;
        return message;
    }

    /**
     * Получение списка монет к выбранной котировке (USDT, BTC, ETH)
     *
     * @param qCoin quote coin
     * @return SELECT nameCoin FROM qCoin
     */
    public String msgReadQcoin(QCoin qCoin) {
        return "SELECT id, nameCoin FROM " + qCoin;
    }

    /**
     * Показать все таблицы в базе данных;
     *
     * @return
     */
    public String msgSeeAllTable() {
        return "SELECT name FROM sqlite_master WHERE type = \"table\"";
    }

}
