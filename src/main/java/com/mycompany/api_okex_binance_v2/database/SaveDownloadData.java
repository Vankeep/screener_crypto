package com.mycompany.api_okex_binance_v2.database;

import com.mycompany.api_okex_binance_v2.enums.Coin;
import com.mycompany.api_okex_binance_v2.enums.Exchange;
import com.mycompany.api_okex_binance_v2.obj.CoinCoin;
import java.util.ArrayList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SaveDownloadData extends InsertAndRead {

    private static final Logger logger = LoggerFactory.getLogger(Database.class.getSimpleName());

    public SaveDownloadData(Exchange exchange) {
        super(exchange);
    }

    public boolean insertAllExInfo(ArrayList<ArrayList<String>> list) {
        if (connect()) {
            for (ArrayList<String> arrayList : list) {
                logger.info("Запись пар к {} в базу даннах", arrayList.get(0));
                insert(sqlMessage.deleteTable(arrayList.get(0)));
                insert(sqlMessage.createTable(arrayList.get(0)));
                for (int i = 1; i < arrayList.size(); i++) {
                    insert(sqlMessage.insertQcoin(arrayList.get(0), arrayList.get(i)));
                }
            }
            close();
            return true;
        } else {
            logger.info("Неудачное подключение к бд или ArrayList пустой");
            return false;
        }

    }

    public boolean insertDataPair(ArrayList<CoinCoin> list, String bCoin, Coin qCoin) {
        if (connect()) {
            logger.info("Запись пары ");
            for (CoinCoin c : list) {
                if(!insert(sqlMessage.insertBcoin(bCoin, qCoin.toString(),
                        c.getTime(),
                        c.getOpen(),
                        c.getHigh(),
                        c.getLow(),
                        c.getClose(),
                        c.getVolume()))) return false;
            }
            close();
            return true;
        } else {
            close();
            logger.error("Нет соединения с бд");
            return false;
        }
    }

}
