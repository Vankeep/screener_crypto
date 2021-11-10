package com.mycompany.api_okex_binance_v2.drivers;

import com.mycompany.api_okex_binance_v2.obj.BCoin;
import com.mycompany.api_okex_binance_v2.enums.QCoin;
import com.mycompany.api_okex_binance_v2.enums.Tf;
import com.mycompany.api_okex_binance_v2.obj.DataCoin;
import com.mycompany.api_okex_binance_v2.obj.NameTable;
import java.io.File;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public interface Driver {

    /**
     * Переделывает файл со всеми парами биржи в двумерный массив данных
     *
     * @param fail файл с данными
     * @return
     */
    public HashMap<QCoin,HashSet<BCoin>> fileToArray(File fail);

    /**
     * Принимает строку с данными по выбранной паре и записывает ее в HashSet
     *
     * @param json строка с данными
     * @param nameTable имя таблицы 
     * @return массив обьктов CoinCoin
     */
    public Set<DataCoin> stringToArray(String json, NameTable nameTable);

    /**
     * Сыылка на все монеты актуальной биржи
     *
     * @return ссылку обернутую в HttpUrlConnection
     */
    public HttpURLConnection urlAllExchangeInfo();

    /**
     * Ссылка для получения данных для выбранной пары
     *
     * @param bCoin базовая монета
     * @param qCoin монета котировки
     * @param tF таймфрейм
     * @param candlesBack количество необходимых свечей. 0 получить данные за 1
     * закрытую последнюю свечу
     * @return ссылку обернутую в HttpUrlConnection
     */
    public HttpURLConnection urlPairMarketData(BCoin bCoin, QCoin qCoin, Tf tF, int candlesBack);

    /**
     * Проверяет код ответа сервера для актуальной биржи
     *
     * @param code код сервера
     * @return true с соединением все ОК
     */
    public boolean checkResponseCode(int code);
    
    /**
     * Получить имя биржи. Должен возвращать строку такого вида "binance", "okex"
     * @return 
     */
    public String getExchangeName();

}
