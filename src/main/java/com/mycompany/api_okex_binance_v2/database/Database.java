package com.mycompany.api_okex_binance_v2.database;

import com.mycompany.api_okex_binance_v2.DatabaseClient;
import com.mycompany.api_okex_binance_v2.constants.Const;
import com.mycompany.api_okex_binance_v2.enums.*;
import com.mycompany.api_okex_binance_v2.obj.CoinCoin;
import com.mycompany.api_okex_binance_v2.time.Time;
import java.util.ArrayList;
import java.util.HashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Database extends DBInsertAndRead implements DatabaseClient {

    private static final Logger logger = LoggerFactory.getLogger(Database.class.getSimpleName());

    public Database(Exchange exchange) {
        super(exchange);
    }

    @Override
    public boolean sendMessage(String message) {
        boolean ok = false;
        if (connect()) {
            ok = insert(message);
            close();
        }
        return ok;
    }

    @Override
    public boolean createAllTable(Coin qCoin) {
        boolean ok = false;
        HashMap<Integer, String> list = getAllPair(qCoin);
        if (connect()) {
            for (int i = 1; i <= list.size(); i++) {
                ok = insert(msgCreateTable(list.get(i), qCoin.toString()));
            }
            close();
        }
        return ok;
    }

    @Override
    public boolean deleteAllTable(Coin qCoin) {
        boolean ok = false;
        HashMap<Integer, String> list = getAllPair(qCoin);
        if (connect()) {
            for (int i = 0; i < list.size(); i++) {
                ok = insert(msgDeleteTable(list.get(i), qCoin.toString()));
            }
            close();
        }
        return ok;
    }

    @Override
    public int getLastUpdatePair(String bCoin, Coin qCoin) {
        String lastUpdateIso = "";
        if (connect()) {
            lastUpdateIso = readLastUpdate(msgLastUpdate(bCoin + "_" + qCoin));
            System.out.println(lastUpdateIso);
            close();
        }
        if (lastUpdateIso.equals("")){
            logger.error("{} - таблица {} пустая", exchange.getName(), bCoin + "_" + qCoin.toString());
            return -1;
        }
        double lastUpdateUnix = Time.isoToUnix(lastUpdateIso);
        double utcNowUnix = Time.getUTCunix();
        double offset = utcNowUnix - lastUpdateUnix;
        double to_hour = offset/Tf.HOUR_ONE.quantityMsec();
        if (to_hour>1){
            logger.info("{} - для обновления пары {} нужно {} свечей", exchange.getName(), bCoin + "_" + qCoin.toString(), String.valueOf((int)to_hour));
            return (int)to_hour-1;
        } else {
            logger.info("{} - данные для пары {} актуальны, обновления не требуется", exchange.getName(),bCoin+"_"+qCoin.toString());
            return -1;
            
        }

    }

    @Override
    public HashMap<Integer, String> getAllPair(Coin qCoin) {
        logger.info("{} - чтение таблицы {}", exchange.getName(), qCoin);
        if (connect()) {
            try {
                HashMap<Integer, String> map = readAllPairToQcoin(msgReadQcoin(qCoin));
                close();
                return map;
            } catch (NullPointerException ex) {
                logger.error("{} - HashMap пустой. {}", exchange.getName(), ex.getMessage());
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

    @Override
    public boolean insertAllExInfo(ArrayList<ArrayList<String>> list) {
        boolean ok = connect();
        if (ok) {
            for (ArrayList<String> arrayList : list) {
                logger.info("{} - запись пар к {} в базу даннах", exchange.getName(), arrayList.get(0));
                insert(msgDeleteTable(arrayList.get(0)));
                insert(msgCreateTable(arrayList.get(0)));
                for (int i = 1; i < arrayList.size(); i++) {
                    ok = insert(msgInsertQcoin(arrayList.get(0), arrayList.get(i)));
                    if (!ok) {
                        break;
                    }
                }
                if (!ok) {
                    break;
                }
            }
            close();
            return ok;
        } else {
            logger.info("{} - неудачное подключение к бд или ArrayList пустой", exchange.getName());
            return ok;
        }
    }

    @Override
    public boolean insertDataPair(ArrayList<CoinCoin> list, String bCoin, Coin qCoin) {
        boolean ok = connect();
        if (ok) {
            logger.info("{} - запись пары {}_{}", exchange.getName(), bCoin, qCoin.toString());
            for (CoinCoin c : list) {
                ok = insert(msgInsertBcoin(bCoin, qCoin.toString(),
                        c.getTime(),
                        c.getOpen(),
                        c.getHigh(),
                        c.getLow(),
                        c.getClose(),
                        c.getVolume()));
                if (!ok) {
                    break;
                }
            }
            close();
            return ok;
        } else {
            close();
            logger.error("{} - нет соединения с бд", exchange.getName());
            return ok;
        }
    }

}
