package com.mycompany.api_okex_binance_v2.time;

import com.mycompany.api_okex_binance_v2.constants.Const;
import java.time.Instant;

public class Time extends Const {

    /**
     * @return 1636059450000 msec
     */
    public static long getUTC() {
        Instant instant = Instant.now();
        return instant.getEpochSecond() * 1000;
    }

    /**
     * Функция на случай неполадок с соединением. Возвращает количество
     * пропущенных свечей в тайфрейме переданном в параметры
     *
     * @param timeFromDb last time update in database
     * @param tf need timeframe
     * @return возвращает количсетво пропущенных свечей
     */
    public static float getOffset(long timeFromDb, Const.TF tf) {
        float utc = getUTC();
        float time = timeFromDb;
        float tframe = tf.getMsec();
        return (utc - time) / tframe;
    }

    /**
     * Функция нужна для ориетирования по ценам закрытия на различных
     * таймфреймах
     *
     * @param tf need timeframe
     * @return время закрытия актуальной свечи
     */
    public static long getTimeClose(Const.TF tf) {
        double utc = Time.getUTC();
        double one_hour = tf.getMsec();
        return ((long) (utc / one_hour) + 1) * tf.getMsec();
    }

}
