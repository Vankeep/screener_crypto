package com.mycompany.api_okex_binance_v2;

import com.mycompany.api_okex_binance_v2.database.Database;
import com.mycompany.api_okex_binance_v2.enums.Exchange;
import com.mycompany.api_okex_binance_v2.net.Connect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MainClass {

    public static final Logger logger = LoggerFactory.getLogger(MainClass.class.getSimpleName());
    boolean updateBinance = false;
    boolean updateOkex = false;

    public static void main(String[] args) throws InterruptedException {
        MainClass mainClass = new MainClass();
        HttpClient binance = new Connect(Exchange.EX_BINANCE);
        HttpClient okex = new Connect(Exchange.EX_OKEX);
        DatabaseClient okexDb = new Database(Exchange.EX_OKEX);
        DatabaseClient binDb = new Database(Exchange.EX_BINANCE);
        
        //okex.updateAllExInfo();
//        QCoin[] qCoin = QCoin.getListQCoin();
//        for (QCoin qCoin1 : qCoin) {
//            okexDb.createAllTable(qCoin1);
//        }
        
//        QCoin[] listQcoin = QCoin.getListQCoin();
//        for (QCoin qCoin : listQcoin) {
//            okexDb.createAllTable(qCoin);
//        }
        //okexDb.cleaningDatabase();
        binDb.cleaningDatabase();
    }

    public boolean allExUpdateAllExInfo(HttpClient okex, HttpClient binance) {
        Thread binanceThread = new Thread(() -> {
            if (binance.updateAllExInfo()) {
                logger.error("Ошибка обновления, поток binanceThread");
                updateBinance = false;
            }

        });
        binanceThread.start();
        Thread okeThread = new Thread(() -> {
            if (!okex.updateAllExInfo()) {
                logger.error("Ошибка обновления, поток okeThread");
                updateBinance = false;
            }
        });
        okeThread.start();
        try {
            binanceThread.join();
            okeThread.join();
        } catch (InterruptedException ex) {
            logger.error("Ошибка с потоками, метод allExUpdateAllExInfo");
            return false;
        }
        if (!(updateBinance && updateOkex)) {
            logger.error("Ошибка обновления всех пар, метод allExUpdateAllExInfo");
            return false;
        } else {
            logger.info("Данные успешно обновлены");
            return true;
        }
    }

}
