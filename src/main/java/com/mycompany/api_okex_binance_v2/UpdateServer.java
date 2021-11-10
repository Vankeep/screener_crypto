package com.mycompany.api_okex_binance_v2;

import com.google.gson.Gson;
import com.mycompany.api_okex_binance_v2.database.Update;
import com.mycompany.api_okex_binance_v2.enums.Exchange;
import com.mycompany.api_okex_binance_v2.enums.QCoin;
import com.mycompany.api_okex_binance_v2.enums.Tf;
import com.mycompany.api_okex_binance_v2.time.Time;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Pattern;

public class UpdateServer {

    public GregorianCalendar gc = new GregorianCalendar();
    ExchangeApi exchangeApi;
    Date startDate;

    public UpdateServer(ExchangeApi exchangeApi) {
        this.exchangeApi = exchangeApi;
        this.gc.setTimeInMillis(Time.getCloseCurrentСandle(Tf.HOUR_ONE) + 5000);
        this.startDate = gc.getTime();
        System.out.println("Время старта обновлений "+startDate);
    }
    

    public void run() {
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                
                for (QCoin qCoin : QCoin.getListQCoin()) {
                    Update.startUpdate(qCoin, exchangeApi);
                }
            }
        };
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(task, startDate, 3600 * 1000);
    }

}
