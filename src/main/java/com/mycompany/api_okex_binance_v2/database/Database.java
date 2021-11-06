package com.mycompany.api_okex_binance_v2.database;

import com.mycompany.api_okex_binance_v2.DatabaseClient;
import com.mycompany.api_okex_binance_v2.enums.*;
import com.mycompany.api_okex_binance_v2.obj.CoinCoin;
import java.util.ArrayList;
import java.util.HashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Database extends InsertAndRead implements DatabaseClient {

    private static final Logger logger = LoggerFactory.getLogger(Database.class.getSimpleName());

    public Database(Exchange exchange) {
        super(exchange);
    }
    
    @Override
    public void sendMessage(String message){
        if(connect()){
            insert(message);
            close();
        }
    }
    
    
    @Override
    public void createAllTable(Coin qCoin){
        HashMap<Integer, String> list = getAllPair(qCoin);
        if(connect()){
            for (int i = 1; i <= list.size(); i++) {
                insert(SqlMessage.createTable(list.get(i), qCoin.toString()));
            }
            close();
        }
        
    }
    
    
    @Override
    public HashMap<Integer, String> getAllPair(Coin qCoin) {
        logger.info("Чтение таблицы {}",qCoin);
        if (connect()) {
            try {
                HashMap<Integer, String> map = readAllPair(SqlMessage.readQcoin(qCoin));
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
    public ArrayList<CoinCoin> getDataCoin(Tf tf, int candlesBack, String bCoin, Coin qCoin, Ohlc ohlc) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<CoinCoin> getDataCoin(Tf tf, int candlesBack, String bCoin, Coin qCoin, Ohlc ohlc1, Ohlc ohlc2) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<CoinCoin> getDataCoin(Tf tf, int candlesBack, String bCoin, Coin qCoin, Ohlc ohlc1, Ohlc ohlc2, Ohlc ohlc3) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<CoinCoin> getDataCoin(Tf tf, int candlesBack, String bCoin, Coin qCoin, Ohlc ohlc1, Ohlc ohlc2, Ohlc ohlc3, Ohlc ohlc4) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
