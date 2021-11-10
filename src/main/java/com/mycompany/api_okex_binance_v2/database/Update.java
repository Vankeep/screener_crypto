package com.mycompany.api_okex_binance_v2.database;

import com.mycompany.api_okex_binance_v2.ExchangeApi;
import com.mycompany.api_okex_binance_v2.constants.Const;
import com.mycompany.api_okex_binance_v2.enums.QCoin;
import com.mycompany.api_okex_binance_v2.enums.Tf;
import com.mycompany.api_okex_binance_v2.obj.CoinCoin;
import com.mycompany.api_okex_binance_v2.obj.UpdateCoin;
import java.util.Set;

public class Update {

    public static boolean startUpdate(QCoin qCoin, ExchangeApi exchangeApi) {
        return startOneQcoin(qCoin, exchangeApi, -1111, false);

    }

    public static boolean startUpdate(QCoin qCoin, ExchangeApi exchangeApi, int candlesBack) {
        return startOneQcoin(qCoin, exchangeApi, candlesBack, true);

    }

    public static boolean startUpdate(QCoin[] qCoin, ExchangeApi exchangeApi) {
        return startArrayQCoin(qCoin, exchangeApi, -1111, false);

    }

    public static boolean startUpdate(QCoin[] qCoin, ExchangeApi exchangeApi, int candlesBack) {
        return startArrayQCoin(qCoin, exchangeApi, candlesBack, true);

    }

    private static boolean startOneQcoin(QCoin qCoin, ExchangeApi exchangeApi, int candlesBack, boolean valueCandlesBack) {
        Set<UpdateCoin> set;
        if (exchangeApi.cleaningDatabase()) {
            if (valueCandlesBack) {
                set = exchangeApi.getLastUpdateTime(qCoin, candlesBack);
            } else {
                set = exchangeApi.getLastUpdateTime(qCoin);
            }
            Set<Set<CoinCoin>> ss = exchangeApi.downloadDatePair(set, Tf.HOUR_ONE);
            exchangeApi.insertDataPair(ss);
            return true;
        }
        return false;

    }

    private static boolean startArrayQCoin(QCoin[] qCoin, ExchangeApi exchangeApi, int candlesBack, boolean valueCandlesBack) {
        Set<UpdateCoin> set;
        if (exchangeApi.cleaningDatabase()) {
            for (QCoin qcoin : qCoin) {
                if (valueCandlesBack) {
                    set = exchangeApi.getLastUpdateTime(qcoin);
                } else {
                    set = exchangeApi.getLastUpdateTime(qcoin, candlesBack);
                }
                Set<Set<CoinCoin>> ss = exchangeApi.downloadDatePair(set, Tf.HOUR_ONE);
                exchangeApi.insertDataPair(ss);
                return true;
            }
        }
        return false;

    }

}
