package com.mycompany.api_okex_binance_v2.net;

import com.mycompany.api_okex_binance_v2.obj.CoinCoin;
import java.util.ArrayList;
import com.mycompany.api_okex_binance_v2.HttpClient;
import com.mycompany.api_okex_binance_v2.enums.QCoin;
import com.mycompany.api_okex_binance_v2.enums.Exchange;
import com.mycompany.api_okex_binance_v2.enums.Tf;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Connect extends ConnectGetData implements HttpClient {

    private static final Logger logger = LoggerFactory.getLogger(Connect.class.getSimpleName());
    private ArrayList<CoinCoin> list;
    
    public Connect(Exchange exchange) {
        super(exchange);
    }

    @Override
    public boolean updateAllExInfo() {
        
        ArrayList<ArrayList<String>> allPair = getAllExInfo();
        if (allPair != null) {
            if(dbClient.insertAllExInfo(allPair)){
                logger.info("{} - данные всех пар в БД успешно обновлены", exchange.getName());
                return true;
            }else{
                logger.error("{} - ошибка записи в бд", exchange.getName());
                return false;
            }
        } else{
            logger.error("{} - массив = null",exchange.getName());
            return false;
        }
    }

    @Override
    public boolean updateDataPair(String bCoin, QCoin qCoin, Tf tf) {
        return update(getDataPair(bCoin, qCoin, tf, 0), bCoin, qCoin);

    }

    @Override
    public boolean updateDataPair(String bCoin, QCoin qCoin, Tf tf, int candlesBack) {
        if(candlesBack<0){
            logger.info("{} - обновлений не требуется", exchange.getName());
            return false;
        }
        return update(getDataPair(bCoin, qCoin, tf, candlesBack), bCoin, qCoin);
    }
    
    private boolean update(ArrayList<CoinCoin> list, String bCoin, QCoin qCoin){
        if (list != null) {
            if(dbClient.insertDataPair(list, bCoin, qCoin)){
                logger.info("{} - данные всех пары {}_{} в БД успешно скачены и обновлены", exchange.getName(), bCoin, qCoin.toString());
                return true;
            } else {
                logger.error("{} - ошибка записи в бд", exchange.getName());
                return false;
            }
        } else{
            logger.error("{} - массив данных = null", exchange.getName());
            return false;
        }
    }

}
