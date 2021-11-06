package com.mycompany.api_okex_binance_v2;

import com.mycompany.api_okex_binance_v2.enums.Coin;
import com.mycompany.api_okex_binance_v2.enums.Tf;

public interface HttpClient {

    /**
     * Скачивает и загружает в бд данные по всем парам на бирже.<p>
     * Сначала данные скачиваеются, если все скачалось успешно, то удаляются
     * старые и записыватся новые
     *
     * @return true if everythink is ok
     */
    public boolean updateAllExInfo();

    /**
     * Метод грузит данные напрямую в БД.<p>
     * Время не задано, поэтому метод загрузит только последнюю свечу
     *
     * @param bCoin base currency
     * @param qCoin quote currency
     * @param tf need timeframe
     * @return
     */
    public boolean updateDataPair(String bCoin, Coin qCoin, Tf tf);

    /**
     * Метод грузит данные напрямую в БД.<p>
     * endTime будет атоматически выбран с последней свечи.
     *
     * @param bCoin base currency
     * @param qCoin quote currency
     * @param tf need timeframe
     * @param startTime time to start downloading data
     * @return
     */
    public boolean updateDataPair(String bCoin, Coin qCoin, Tf tf, int canlestic);
    

    /**
     * Метод грузит данные напрямую в БД.
     *
     * @param bCoin base currency
     * @param qCoin quote currency
     * @param tf need timeframe
     * @param startTime time to start downloading data
     * @param endTime opening time of the current candle
     * @return true if everythink is ok
     */
    public boolean updateDataPair(String bCoin, Coin qCoin, Tf tf, long startTime, long endTime);

}
