package com.mycompany.api_okex_binance_v2.database;

public class GenerateSqlMessage {

    /**
     * Создание таблицы на случай листинга.
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
    public String createTable(String bCoin, String qCoin) {

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
     * Удаление таблицы монеты на случай делистинга
     *
     * @param bCoin базовая монета
     * @param qCoin монета котировки
     * @return DROP TABLE bCoin_qCoin
     */
    public String deleteTable(String bCoin, String qCoin) {
        return "DROP TABLE " + bCoin + "_" + qCoin;
    }

    /**
     * Создание таблицы всех монет
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
    public String createTable(String nameTable) {
        String message = "CREATE TABLE " + nameTable + " ("
                + " id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + " nameCoin VARCHAR(10))";
        return message;
    }

    /**
     * Удаление таблицы всех монет
     *
     * @param nameTable монета коритовки
     * @return DROP TABLE nameTable
     */
    public String deleteTable(String nameTable) {
        return "DROP TABLE " + nameTable;
    }

    //Данные
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
    public String insertBcoin(String bCoin, String qCoin, String time, String open, String high, String low, String close, String volume) {
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
     * Добавление в таблицу со списом всех пар
     *
     * @param nameTable имя таблицы
     * @param bCoin монета для втавки
     * @return INSERT INTO nameTable ( nameCoin ) VALUES ( 'bCoin' )
     */
    public String insertQcoin(String nameTable, String bCoin) {
        return "INSERT INTO " + nameTable + " ( nameCoin ) VALUES ( '" + bCoin + "' )";
    }

    /**
     * Получение данных из таблиц с данными
     *
     * @param bCoin базовая монета
     * @param qCoin монета котировки
     * @return SELECT time, open, high, low, close, volume FROM bCoin_qCoin
     */
    public String readBcoin(String bCoin, String qCoin) {
        return "SELECT time, open, high, low, close, volume FROM " + bCoin + "_" + qCoin;
    }
    
    /**
     * Получение всей таблицы со списком монет
     *
     * @param qCoin монета котировки
     * @return SELECT nameCoin FROM qCoin
     */
    public String readQcoin(String qCoin) {
        return "SELECT nameCoin FROM " + qCoin;
    }

}
