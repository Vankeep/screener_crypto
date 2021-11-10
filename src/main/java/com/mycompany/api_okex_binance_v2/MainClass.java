package com.mycompany.api_okex_binance_v2;


import com.mycompany.api_okex_binance_v2.database.Database;
import com.mycompany.api_okex_binance_v2.database.Update;
import com.mycompany.api_okex_binance_v2.enums.*;
import com.mycompany.api_okex_binance_v2.time.Time;
import java.util.Date;
import java.util.GregorianCalendar;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MainClass {
    public static GregorianCalendar sd = new GregorianCalendar();
    public static final Logger logger = LoggerFactory.getLogger(MainClass.class.getSimpleName());

    public static void main(String[] args) {
        ExchangeApi okex = new ExchangeApi(Exchange.EX_OKEX);
        okex.run();
        
    }

    public static void test() {
        ExchangeApi okex = new ExchangeApi(Exchange.EX_OKEX);
        for (QCoin qCoin : QCoin.getListQCoin()) {
            Update.startUpdate(qCoin, okex);
        }
    

    }
    public static void test2(){
        ExchangeApi okApi = new ExchangeApi(Exchange.EX_OKEX);
        okApi.cleaningDatabase();
    }

}
