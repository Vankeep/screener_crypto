package com.mycompany.api_okex_binance_v2;


import com.mycompany.api_okex_binance_v2.database.Database;
import com.mycompany.api_okex_binance_v2.database.SqlMsg;
import com.mycompany.api_okex_binance_v2.enums.*;
import com.mycompany.api_okex_binance_v2.obj.*;
import java.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MainClass {
    public static GregorianCalendar sd = new GregorianCalendar();
    public static final Logger logger = LoggerFactory.getLogger(MainClass.class.getSimpleName());

    public static void main(String[] args) {
        ExchangeApi okex = new ExchangeApi(Exchange.EX_OKEX);
//        okex.updateAllExInfo();
//        okex.cleaningDatabase();
//        for (QCoin arg : QCoin.values()) {
//            ok.deleteAllTable(arg);
//        }
//        okex.cleaningDatabase();
        //okex.insertDataPairFromDatabase(okex.downloadDatePairFromNet(okex.getLastUpdateTimeDatabase(QCoin.ETH), Tf.HOUR_ONE));
       
        NameTable[] nameTable = new NameTable[]{new NameTable(new BCoin("XLM"), QCoin.ETH)};
        Map<NameTable, List<DataCoin>> data = okex.getDataPairFromDatabase(nameTable, 14);
        int co = 1;
        for (Map.Entry<NameTable, List<DataCoin>> entry : data.entrySet()) {
            co = 1;
            for (DataCoin coinData : entry.getValue()) {
                logger.info("{} {} {} {} {} {} {} {}", entry.getKey(),
                        co,
                        coinData.getTime(),
                        coinData.getOpen(),
                        coinData.getHigh(),
                        coinData.getLow(),
                        coinData.getClose(),
                        coinData.getVolume());
                co++;
            }
 
           
        }
        
    }

//    public static void test() {
//        ExchangeApi okex = new ExchangeApi(Exchange.EX_OKEX);
//        for (QCoin qCoin : QCoin.getListQCoin()) {
//            Update.startUpdate(qCoin, okex);
//        }
//    
//
//    }
    public static void test2(){
        ExchangeApi okApi = new ExchangeApi(Exchange.EX_OKEX);
        okApi.cleaningDatabase();
    }

}
