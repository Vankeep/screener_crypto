package com.mycompany.api_okex_binance_v2.interfaces;

import com.mycompany.api_okex_binance_v2.obj.BCoin;
import com.mycompany.api_okex_binance_v2.enums.*;
import com.mycompany.api_okex_binance_v2.obj.CoinCoin;
import java.util.*;

public interface DatabaseClient {

    /**
     * Универсальный метод отправки одного сообщения
     *
     * @param message
     * @return true если все успешно
     */
    public boolean sendMessage(String message);

    /**
     * Создать все таблицы к указанной монете котировки
     *
     * @param qCoin монета котировки
     * @return true если все успешно
     */
    public boolean createAllTable(QCoin qCoin);

    /**
     * Удалить все таблицы к указанной монете котировки
     *
     * @param qCoin монета котировки
     * @return true если все успешно
     */
    public boolean deleteAllTable(QCoin qCoin);

    /**
     * Возвращает в часах когда было последниее обновление<p>
     * -1 данные актуальные, 0 - один час, 1 - два часа, и.т.п.
     *
     * @param bCoin базовая монета
     * @param qCoin монета котировки
     * @return количество необходимых свечей
     */
    public int getLastUpdateTimePair(BCoin bCoin, QCoin qCoin);

    /**
     * Получить список всех таблиц в базе данных
     *
     * @return лист всех пар
     */
    public boolean cleaningDatabase();

    /**
     * Получить из базы данных все пары переданной в парамерты функции монеты
     * котировки. Отсчет индексов в HashMap начинается с 1
     *
     * @param qCoin монета котировки
     * @return HashMap
     */
    public Map<Integer, BCoin> getAllPair(QCoin qCoin);

    /**
     * Чтение данных из бд
     *
     * @param tf          нужный таймфрейм
     * @param candlesBack сколько свечей назад
     * @param bCoin       base currency
     * @param qCoin       qoute currency
     * @param ohlc        open high low close
     * @return
     */
    public ArrayList<CoinCoin> getDataCoin(Tf tf, int candlesBack, BCoin bCoin, QCoin qCoin, Ohlc ohlc);

    public ArrayList<CoinCoin> getDataCoin(Tf tf, int candlesBack, BCoin bCoin, QCoin qCoin, Ohlc ohlc1, Ohlc ohlc2);

    public ArrayList<CoinCoin> getDataCoin(Tf tf, int candlesBack, BCoin bCoin, QCoin qCoin, Ohlc ohlc1, Ohlc ohlc2, Ohlc ohlc3);

    public ArrayList<CoinCoin> getDataCoin(Tf tf, int candlesBack, BCoin bCoin, QCoin qCoin, Ohlc ohlc1, Ohlc ohlc2, Ohlc ohlc3, Ohlc ohlc4);

    /**
     * Запись принятых всех пар биржи в БД
     *
     * @param list массив всех пар
     * @return true если все успешно
     */
    public boolean insertAllExInfo(HashMap<QCoin, HashSet<BCoin>> list);

    /**
     * Запись принятых данных по паре в БД
     *
     * @param list  данные по паре
     * @param bCoin базовая монета
     * @param qCoin монета котировки
     * @return true если все успешно
     */
    @Deprecated
    public boolean insertDataPair(Set<CoinCoin> list, BCoin bCoin, QCoin qCoin);
    /**
     * Запись принятых данных по паре в БД. Получить сет в функции 
     *
     * @param pairs двумерный сет с данными по парам
     * @return true если все успешно
     */
    public boolean insertDataPair(Set<Set<CoinCoin>> pairs);

}
