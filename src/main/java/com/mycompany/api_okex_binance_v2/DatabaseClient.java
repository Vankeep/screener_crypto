package com.mycompany.api_okex_binance_v2;

import com.mycompany.api_okex_binance_v2.enums.Coin;
import com.mycompany.api_okex_binance_v2.enums.Ohlc;
import com.mycompany.api_okex_binance_v2.enums.Tf;
import com.mycompany.api_okex_binance_v2.obj.CoinCoin;
import java.util.ArrayList;
import java.util.HashMap;

public interface DatabaseClient {
    /**
     * Универсальный метод отправки одного сообщения
     * @param message 
     * @return true если все успешно
     */
    public boolean sendMessage(String message);
    /**
     * Создать все таблицы к указанной монете котировки
     * @param qCoin  монета котировки
     * @return true если все успешно
     */
    public boolean createAllTable(Coin qCoin);
    /**
     * Удалить все таблицы к указанной монете котировки
     * @param qCoin монета котировки
     * @return true если все успешно
     */
    public boolean deleteAllTable(Coin qCoin);

    /**
     * Получить из базы данных все пары переданной в парамерты функции монеты
     * котировки. Отсчет индексов в HashMap начинается с 1
     *
     * @param qCoin монета котировки
     * @return HashMap
     */
    public HashMap<Integer, String> getAllPair(Coin qCoin);

    /**
     * Чтение данных из бд
     *
     * @param tf нужный таймфрейм
     * @param candlesBack сколько свечей назад
     * @param bCoin base currency
     * @param qCoin qoute currency
     * @param ohlc open high low close
     * @return
     */
    public ArrayList<CoinCoin> getDataCoin(Tf tf, int candlesBack, String bCoin, Coin qCoin, Ohlc ohlc);

    public ArrayList<CoinCoin> getDataCoin(Tf tf, int candlesBack, String bCoin, Coin qCoin, Ohlc ohlc1, Ohlc ohlc2);

    public ArrayList<CoinCoin> getDataCoin(Tf tf, int candlesBack, String bCoin, Coin qCoin, Ohlc ohlc1, Ohlc ohlc2, Ohlc ohlc3);

    public ArrayList<CoinCoin> getDataCoin(Tf tf, int candlesBack, String bCoin, Coin qCoin, Ohlc ohlc1, Ohlc ohlc2, Ohlc ohlc3, Ohlc ohlc4);

    /**
     * Запись принятых всех пар биржи в БД
     *
     * @param list массив всех пар
     * @return true если все успешно
     */
    public boolean insertAllExInfo(ArrayList<ArrayList<String>> list);

    /**
     * Запись принятых данных по паре в БД
     *
     * @param list данные по паре
     * @param bCoin базовая монета
     * @param qCoin монета котировки
     * @return true если все успешно
     */
    public boolean insertDataPair(ArrayList<CoinCoin> list, String bCoin, Coin qCoin);

}
