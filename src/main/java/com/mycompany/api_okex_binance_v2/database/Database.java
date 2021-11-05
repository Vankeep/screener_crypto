package com.mycompany.api_okex_binance_v2.database;

import com.mycompany.api_okex_binance_v2.DatabaseClient;
import com.mycompany.api_okex_binance_v2.enums.*;
import com.mycompany.api_okex_binance_v2.obj.Сurrency;
import java.util.ArrayList;
import java.util.HashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Database extends DatabsIR implements DatabaseClient {

    private static final Logger logger = LoggerFactory.getLogger(Database.class.getSimpleName());

    public Database(Exchange exchange) {
        super(exchange);
    }

    public boolean insertAllPairToDatabase(ArrayList<ArrayList<String>> list) {
        if (connect() && list != null) {
            for (ArrayList<String> arrayList : list) {
                logger.info("Запись пар к {}", arrayList.get(0));
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

    @Override
    public HashMap<Integer, String> getAllPair(Coin qCoin) {
        if (connect()) {
            try {
                HashMap<Integer, String> map = readAllPair(sqlMessage.readQcoin(qCoin));
                close();
                return map;
            } catch (NullPointerException ex) {
                logger.error("HashMap пустой. {}", ex.getMessage());
                close();
                return null;
            }
        }
        return null;
    }

    @Override
    public ArrayList<Сurrency> getDataCoin(Tf tf, int candlesBack, String bCoin, Coin qCoin, Ohlc ohlc) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<Сurrency> getDataCoin(Tf tf, int candlesBack, String bCoin, Coin qCoin, Ohlc ohlc1, Ohlc ohlc2) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<Сurrency> getDataCoin(Tf tf, int candlesBack, String bCoin, Coin qCoin, Ohlc ohlc1, Ohlc ohlc2, Ohlc ohlc3) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<Сurrency> getDataCoin(Tf tf, int candlesBack, String bCoin, Coin qCoin, Ohlc ohlc1, Ohlc ohlc2, Ohlc ohlc3, Ohlc ohlc4) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
