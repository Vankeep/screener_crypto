package com.mycompany.api_okex_binance_v2.drivers;

import com.mycompany.api_okex_binance_v2.enums.Coin;
import com.mycompany.api_okex_binance_v2.enums.Tf;
import com.mycompany.api_okex_binance_v2.obj.CoinCoin;
import java.io.File;
import java.net.HttpURLConnection;
import java.util.ArrayList;

public interface Driver {

    /**
     * Переделывает файл со всеми парами биржи в двумерный массив данных
     *
     * @param fail файл с данными
     * @return
     */
    public ArrayList<ArrayList<String>> fileToArray(File fail);

    /**
     * Принимает строку с данными по выбранной паре и записывает ее в ArrayList
     *
     * @param json строка с данными
     * @return массив обьктов CoinCoin
     */
    public ArrayList<CoinCoin> stringToArray(String json);

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
    public HttpURLConnection urlPairMarketData(String bCoin, Coin qCoin, Tf tF, int candlesBack);

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
