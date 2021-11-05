package com.mycompany.api_okex_binance_v2.time;

import com.mycompany.api_okex_binance_v2.enums.Tf;
import java.time.Instant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Time {
    private static final Logger logger = LoggerFactory.getLogger(Time.class.getSimpleName());
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
    public static float getOffset(long timeFromDb, Tf tf) {
        float utc = getUTC();
        float time = timeFromDb;
        float tframe = tf.getMsec();
        return (utc - time) / tframe;
    }

    /**
     * Время закрытия актуальной свечи в укзанном в параметрах ф-ции таймфрейме
     *
     * @param tf need timeframe
     * @return время закрытия актуальной свечи
     */
    public static long getCloseCurrentTime(Tf tf) {
        double utc = Time.getUTC();
        double one_hour = tf.getMsec();
        return ((long) (utc / one_hour) + 1) * tf.getMsec();
    }

    /**
     * Время открытия актуальной свечи в указанном в параметрах ф-ции таймфрейме
     *
     * @param tf need timeframe
     * @return
     */
    public static long getEndTime(Tf tf) {
        return getCloseCurrentTime(tf) - tf.getMsec();

    }

    /**
     * Время open/close на переданном таймфреме и количестве свечей назад;
     *
     * @param tf need timeframe
     * @param candlesBack сколько свечей назад
     * @return
     */
    public static long getStartTime(Tf tf, int candlesBack) {
        if (candlesBack<1){
            logger.error("Значениие candlesBack в функции getStartTime не может быть нулем! Вернул знчение фукции getEndTime");
            return getEndTime(tf);
        }
        return getEndTime(tf) - (tf.getMsec() * candlesBack);
    }

}
