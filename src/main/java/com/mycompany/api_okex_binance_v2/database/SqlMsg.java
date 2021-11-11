package com.mycompany.api_okex_binance_v2.database;

import com.mycompany.api_okex_binance_v2.enums.ColumnsTable;
import com.mycompany.api_okex_binance_v2.obj.BCoin;
import com.mycompany.api_okex_binance_v2.enums.QCoin;
import com.mycompany.api_okex_binance_v2.obj.NameTable;

public class SqlMsg {

    class Msg {

        String message;

        public Msg(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

    }

    /**
     * Удаление таблицы для монеты на случай делистинга
     *
     * @param bCoin базовая монета
     * @param qCoin монета котировки
     * @return DROP TABLE bCoin_qCoin
     */
    public String msgDeleteTable(BCoin bCoin, QCoin qCoin) {
        return "DROP TABLE " + bCoin + "_" + qCoin;
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
     * Создание списка всех монет к выбранной котировке (USDT, BTC, ETH)
     * <p>
     * Заголовки таблицы: id, nameCoin
     * <p>
     * Имя таблицы: BTC
     * <p>
     * CREATE TABLE qCoin ( id INTEGER PRIMARY KEY AUTOINCREMENT, nameCoin
     * VARCHAR(10))
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
     * <p>
     * Имя таблицы: BTC_USDT
     *
     * @param bCoin базовая монета
     * @param qCoin монета котировки
     * @return CREATE TABLE bCoin_qCoin ( time VARCHAR(30), open VARCHAR(30),
     *         high VARCHAR(30), low VARCHAR(30), close VARCHAR(30), volume
     *         VARCHAR(30))
     */
    public String msgCreateTable(BCoin bCoin, QCoin qCoin) {
        String message = "CREATE TABLE " + bCoin + "_" + qCoin + " ("
                + " id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + " time DOUBLE,"
                + " open DOUBLE,"
                + " high DOUBLE,"
                + " low DOUBLE,"
                + " close DOUBLE,"
                + " volume DOUBLE)";
        return message;
    }

    /**
     * Создание таблицы для монеты на случай листинга.
     * <p>
     * Заголовки таблицы: time, open, high, low, close, volume
     * <p>
     * Имя таблицы: BTC_USDT
     *
     * @param nameTable имя таблицы
     * @return CREATE TABLE bCoin_qCoin ( time VARCHAR(30), open VARCHAR(30),
     *         high VARCHAR(30), low VARCHAR(30), close VARCHAR(30), volume
     *         VARCHAR(30))
     */
    public String msgCreateTable(NameTable nameTable) {

        String message = "CREATE TABLE " + nameTable + " ("
                + " id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + " time DOUBLE,"
                + " open DOUBLE,"
                + " high DOUBLE,"
                + " low DOUBLE,"
                + " close DOUBLE,"
                + " volume DOUBLE)";
        return message;
    }

    /**
     * Последнее обновление в указанной таблице
     *
     * @param bCoin базовая монета
     * @param qCoin монета котировки
     * @return
     */
    public String msgLastUpdatePair(BCoin bCoin, QCoin qCoin) {
        return "SELECT * FROM " + bCoin + "_" + qCoin + " ORDER BY time DESC LIMIT 1";
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
    public String msgInsertDataBcoin(BCoin bCoin, QCoin qCoin, String time, double open, double high, double low, double close, double volume) {
        String message = "INSERT INTO " + bCoin + "_" + qCoin + " ( time, open, high, low, close, volume)"
                + " VALUES ( '"
                + time + "', '"
                + open + "', '"
                + high + "', '"
                + low + "', '"
                + close + "', '"
                + volume + "')";
        return message;

    }

    public String msgInsertDataBcoin(NameTable nameTable, String time, double open, double high, double low, double close, double volume) {
        String message = "INSERT INTO " + nameTable + " ( time, open, high, low, close, volume)"
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
    public String msgReadBcoin(BCoin bCoin, QCoin qCoin) {
        return "SELECT time, open, high, low, close, volume FROM " + bCoin + "_" + qCoin;
    }

    /**
     * Получить ограниченнное количество данных от последнего записанного
     * значения до lengthData.
     *
     * @param nameTable  имя таблицы
     * @param lengthData сколько данных с конца надо
     * @return
     */
    public String msgReadDataBcoin(NameTable nameTable, int lengthData) {
        
        return "SELECT open, high, low, close FROM " + nameTable + " ORDER BY id DESC LIMIT " + lengthData;
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
