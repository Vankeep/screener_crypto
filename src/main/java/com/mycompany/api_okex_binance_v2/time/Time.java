package com.mycompany.api_okex_binance_v2.time;

import com.mycompany.api_okex_binance_v2.enums.Tf;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Time {

    private static final Logger logger = LoggerFactory.getLogger(Time.class.getSimpleName());
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
    
    /**
     * @return 2021-11-06T21:13:17.000Z
     */
    public static String getUTCiso() {
        sdf.setTimeZone(TimeZone.getTimeZone("Europe/London"));
        return  sdf.format(GregorianCalendar.getInstance().getTimeInMillis());
    }
    
    public static long getUTCunix(){
        return isoToUnix(getUTCiso());
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
        float utc = getUTCunix();
        float time = timeFromDb;
        float tframe = tf.quantityMsec();
        return (utc - time) / tframe;
    }

    /**
     * Время закрытия актуальной свечи в укзанном в параметрах ф-ции таймфрейме
     *
     * @param tf need timeframe
     * @return время закрытия актуальной свечи
     */
    public static long getCloseCurrentСandle(Tf tf) {
        double utc = getUTCunix();
        double one_hour = tf.quantityMsec();
        return ((long) (utc / one_hour) + 1) * tf.quantityMsec();
    }

    /**
     * Время открытия актуальной свечи в указанном в параметрах ф-ции таймфрейме
     *
     * @param tf need timeframe
     * @return
     */
    public static long getEndTime(Tf tf) {
        return getCloseCurrentСandle(tf) - tf.quantityMsec();

    }

    /**
     * Время open/close на переданном таймфреме и количестве свечей назад;
     *
     * @param tf need timeframe
     * @param candlesBack сколько свечей назад
     * @return
     */
    public static long getStartTime(Tf tf, int candlesBack) {
        if (candlesBack < 1) {
            logger.debug("Значениие candlesBack в функции {} Отправлено время последней свечи", candlesBack);
            return getEndTime(tf);
        }
        return getEndTime(tf) - (tf.quantityMsec() * candlesBack);
    }

    /**
     * Конвертирование времени (для биржи OKEX)
     *
     * @param isoFormat строка вида 2021-10-21T11:00:00.000Z
     * @return
     */
    public static long isoToUnix(String isoFormat) {
        Date date;
        try {
            date = sdf.parse(isoFormat);
            return date.getTime();
        } catch (ParseException ex) {
            System.err.println(ex.getMessage());
            return -1;
        }
    }

    public static String unixToIso(long unixFormat) {
        return sdf.format(new Date(unixFormat));
    }

}
