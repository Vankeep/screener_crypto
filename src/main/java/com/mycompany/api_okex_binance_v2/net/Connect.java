package com.mycompany.api_okex_binance_v2.net;

import com.mycompany.api_okex_binance_v2.obj.CoinCoin;
import java.util.ArrayList;
import com.mycompany.api_okex_binance_v2.HttpClient;
import com.mycompany.api_okex_binance_v2.database.SaveDownloadData;
import com.mycompany.api_okex_binance_v2.enums.Coin;
import com.mycompany.api_okex_binance_v2.enums.Exchange;
import com.mycompany.api_okex_binance_v2.enums.Tf;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Connect extends ConnGetData implements HttpClient {

    private static final Logger logger = LoggerFactory.getLogger(Connect.class.getSimpleName());
    private ArrayList<CoinCoin> list;
    public Connect(Exchange ex) {
        super(ex);
    }

    @Override
    public boolean updateAllExInfo() {
        ArrayList<ArrayList<String>> allPair = getAllExInfo();
        if (allPair != null) {
            if(new SaveDownloadData(ex).insertAllExInfo(allPair)){
                logger.info("Данные всех пар {} в БД успешно скачены и обновлены", ex.getName());
                return true;
            }else{
                logger.error("Ошибка записи в бд");
                return false;
            }
        } else{
            logger.error("Массив = null");
            return false;
        }
    }

    @Override
    public boolean updateDataPair(String bCoin, Coin qCoin, Tf tf) {
        return update(getDataPair(bCoin, qCoin, tf, 0), bCoin, qCoin);

    }

    @Override
    public boolean updateDataPair(String bCoin, Coin qCoin, Tf tf, int candlesBack) {
        return update(getDataPair(bCoin, qCoin, tf, candlesBack), bCoin, qCoin);
    }
    
    private boolean update(ArrayList<CoinCoin> list, String bCoin, Coin qCoin){
        if (list != null) {
            if(new SaveDownloadData(ex).insertDataPair(list, bCoin, qCoin)){
                logger.info("Данные всех пар {} в БД успешно скачены и обновлены", ex.getName());
                return true;
            } else {
                logger.error("Ошибка записи в бд");
                return false;
            }
        } else{
            logger.error("Массив данных = null");
            return false;
        }
    }

}
