package com.mycompany.api_okex_binance_v2.database;

import com.mycompany.api_okex_binance_v2.ExchangeApi;
import com.mycompany.api_okex_binance_v2.constants.Const;
import com.mycompany.api_okex_binance_v2.enums.QCoin;
import com.mycompany.api_okex_binance_v2.enums.Tf;
import com.mycompany.api_okex_binance_v2.obj.DataCoin;
import com.mycompany.api_okex_binance_v2.obj.UpdateCoin;
import java.util.Set;

public class Update {

    public static boolean startUpdate(QCoin qCoin, ExchangeApi exchangeApi) {
        Set<UpdateCoin> set = exchangeApi.getLastUpdateTime(qCoin);
        Set<Set<DataCoin>> ss = exchangeApi.downloadDatePair(set, Tf.HOUR_ONE);
        return exchangeApi.insertDataPair(ss);

    }

    public static boolean startUpdate(QCoin qCoin, ExchangeApi exchangeApi, int candlesBack) {
        Set<UpdateCoin> set = exchangeApi.getLastUpdateTime(qCoin, candlesBack);
        Set<Set<DataCoin>> ss = exchangeApi.downloadDatePair(set, Tf.HOUR_ONE);
        return exchangeApi.insertDataPair(ss);

    }

}
