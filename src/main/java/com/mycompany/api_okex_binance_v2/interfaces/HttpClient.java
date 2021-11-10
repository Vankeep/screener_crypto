package com.mycompany.api_okex_binance_v2.interfaces;

import com.mycompany.api_okex_binance_v2.enums.QCoin;
import com.mycompany.api_okex_binance_v2.enums.Tf;

public interface HttpClient {

    /**
     * Скачивает и загружает в бд данные по всем парам на бирже.<p>
     * Сначала данные скачиваеются, если все скачалось успешно, то удаляются
     * старые и записыватся новые
     *
     * @return true if everythink is ok
     */
    @Deprecated
    public boolean updateAllExInfo();

    /**
     * Метод дописывает в БД указанную пару.<p>
     * Количество свечей не указано, поэтому метод загрузит только последнюю
     * свечу
     *
     * @param bCoin base currency
     * @param qCoin quote currency
     * @param tf need timeframe
     * @return
     */
    @Deprecated
    public boolean updateDataPair(String bCoin, QCoin qCoin, Tf tf);

    /**
     * Метод дописывает в БД указанную пару на указаном временном промежутке
     * времени.
     *
     * @param bCoin base currency
     * @param qCoin quote currency
     * @param tf need timeframe
     * @param candlesBack сколько свечей необходимо
     * @return
     */
    @Deprecated
    public boolean updateDataPair(String bCoin, QCoin qCoin, Tf tf, int candlesBack);

}
