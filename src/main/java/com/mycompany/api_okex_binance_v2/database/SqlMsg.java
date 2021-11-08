package com.mycompany.api_okex_binance_v2.database;

import com.mycompany.api_okex_binance_v2.enums.Coin;

public class SqlMsg {
    
    class Msg{
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
    public String msgCreateTable(String bCoin, String qCoin) {

        String message = "CREATE TABLE " + bCoin + "_" + qCoin + " ("
                + " time VARCHAR(30),"
                + " open VARCHAR(30),"
                + " high VARCHAR(30),"
                + " low VARCHAR(30),"
                + " close VARCHAR(30),"
                + " volume VARCHAR(30))";
        return message;
    }

    /**
     * Удаление таблицы для монеты на случай делистинга
     *
     * @param bCoin базовая монета
     * @param qCoin монета котировки
     * @return DROP TABLE bCoin_qCoin
     */
    public String msgDeleteTable(String bCoin, String qCoin) {
        return "DROP TABLE " + bCoin + "_" + qCoin;
    }

    /**
     * Создание списка всех монет к выбранной котировке (USDT, BTC, ETH)
     * <p>
     * Заголовки таблицы: id, nameCoin
     * <p>
     * Имя таблицы: BTC
     * <p>
     * CREATE TABLE nameTable ( id INTEGER PRIMARY KEY AUTOINCREMENT, nameCoin
     * VARCHAR(10))
     *
     * @param nameTable имя таблицы
     * @return строка
     */
    public String msgCreateTable(String nameTable) {
        String message = "CREATE TABLE " + nameTable + " ("
                + " id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + " nameCoin VARCHAR(10))";
        return message;
    }

    /**
     * Удаление списка всех монет к выбранной котировке (USDT, BTC, ETH)
     *
     * @param nameTable монета коритовки
     * @return DROP TABLE nameTable
     */
    public String msgDeleteTable(String nameTable) {
        return "DROP TABLE " + nameTable;
    }
    /**
     * Последнее обновление в указанной таблице
     * @param nameTable имя таблицы
     * @return 
     */
    public String msgLastUpdate(String nameTable) {
        return "SELECT * FROM " + nameTable + " ORDER BY time DESC LIMIT 1";
    }

    /**
     * Добаление данных в таблицу со значениями
     *
     * @param bCoin базовая монета
     * @param qCoin монета коритовки
     * @param time время закрытия свечи
     * @param open ценая открытия
     * @param high самая верхняя цена
     * @param low самая нижняя цена
     * @param close цена закрытия
     * @param volume обьем
     * @return INSERT INTO bCoin_qCoin ( time, open, high, low, close, volume)
     *         VALUES ('123', '321', '234', '123', '345', '654')
     */
    public String msgInsertBcoin(String bCoin, String qCoin, String time, String open, String high, String low, String close, String volume) {
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

    /**
     * Добавление монеты в список всех монет к выбранной котировке (USDT, BTC,
     * ETH)
     *
     * @param nameTable имя таблицы
     * @param bCoin base coin
     * @return INSERT INTO nameTable ( nameCoin ) VALUES ( 'bCoin' )
     */
    public String msgInsertQcoin(String nameTable, String bCoin) {
        return "INSERT INTO " + nameTable + " ( nameCoin ) VALUES ( '" + bCoin + "' )";
    }

    /**
     * Получение данных из таблиц с данными
     *
     * @param bCoin base coin
     * @param qCoin quote coin
     * @return SELECT time, open, high, low, close, volume FROM bCoin_qCoin
     */
    public String msgReadBcoin(String bCoin, String qCoin) {
        return "SELECT time, open, high, low, close, volume FROM " + bCoin + "_" + qCoin;
    }

    /**
     * Получение списка монет к выбранной котировке (USDT, BTC, ETH)
     *
     * @param qCoin quote coin
     * @return SELECT nameCoin FROM qCoin
     */
    public String msgReadQcoin(Coin qCoin) {
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
