package com.mycompany.api_okex_binance_v2.database;

import com.mycompany.api_okex_binance_v2.constants.ConstCoin;

public class GenerateSqlMessage {

    //Таблицы
    /**
     * Создание таблицы на случай листинга
     *
     * @param coin монета для добавления
     * @return строка
     */
    public String createNewTable(CoinBigData coin) {

        String message = "CREATE TABLE " + coin.getName() + "_" + coin.getqCoin().toString() + " ("
                + " time LONG,"
                + " open VARCHAR(30),"
                + " high VARCHAR(30),"
                + " low VARCHAR(30),"
                + " close VARCHAR(30),"
                + " volume VARCHAR(30))";
        return message;
    }

    /**
     * Создание таблицы на случай листинга
     *
     * @param coin базовая монета
     * @param qCoin монета котировки
     * @return строка
     */
    public String createNewTable(String coin, ConstCoin qCoin) {

        String message = "CREATE TABLE " + coin + "_" + qCoin.toString() + " ("
                + " time LONG,"
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
     * @param coin монета для удаления
     * @return строка
     *
     */
    public String deleteTable(CoinBigData coin) {
        return "DROP TABLE " + coin.getName() + "_" + coin.getqCoin().toString();
    }

    /**
     * Удаление таблицы монеты на случай делистинга
     *
     * @param coin базовая монета
     * @param qCoin монета котировки
     * @return строка
     */
    public String deleteTable(String coin, ConstCoin qCoin) {
        return "DROP TABLE " + coin + "_" + qCoin.toString();
    }

    /**
     * Создание таблицы всех монет
     *
     * @param qCoin монета котировки
     * @return строка
     */
    public String createNewTable(ConstCoin qCoin) {

        String message = "CREATE TABLE " + qCoin.toString() + " ("
                + " id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + " coin VARCHAR(10))";
        return message;
    }

    /**
     * Удаление таблицы всех монет
     *
     * @param qCoin монета коритовки
     * @return строка
     */
    public String deleteTable(ConstCoin qCoin) {
        return "DROP TABLE " + qCoin.toString();
    }

    //Данные
    /**
     * Добавление данных
     *
     * @param coin базовая монета
     * @param index индекс элементов массива цен, времени и даты
     * @return строка
     */
    public String insertDataCoin(CoinBigData coin, int index) {
        String message = "INSERT INTO " + coin.getName() + "_" + coin.getqCoin().toString() + " ( time, open, high, low, close, volume)"
                + " VALUES ( '"
                + coin.getTime(index) + "', '"
                + coin.getOpen(index) + "', '"
                + coin.getHigh(index) + "', '"
                + coin.getLow(index) + "', '"
                + coin.getClose(index) + "', '"
                + coin.getVolume(index) + "')";
        return message;
    }

    /**
     * Добаление данных
     *
     * @param name монета для которой все записывается
     * @param time время закрытия свечи
     * @param open ценая открытия
     * @param high самая верхняя цена
     * @param low самая нижняя цена
     * @param close цена закрытия
     * @param volume обьем
     * @param qCoin монета коритовки
     * @return строка
     */
    public String insertDataCoin(String name, String time, String open, String high, String low, String close, String volume, ConstCoin qCoin) {
        String message = "INSERT INTO " + name + "_" + qCoin.toString() + " ( time, open, high, low, close, volume)"
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
     * Получение данных
     *
     * @param coin базовая монета
     * @return строка
     */
    public String readDataCoin(CoinBigData coin) {
        return "SELECT time, open, high, low, close, volume FROM " + coin.getName() + "_" + coin.getqCoin().toString();
    }

    /**
     * Получение данных
     *
     * @param coin базовая монета
     * @param qCoin монета котировки
     * @return строка
     */
    public String readDataCoin(String coin, ConstCoin qCoin) {
        return "SELECT time, open, high, low, close, volume FROM " + coin + "_" + qCoin.toString();
    }

}
