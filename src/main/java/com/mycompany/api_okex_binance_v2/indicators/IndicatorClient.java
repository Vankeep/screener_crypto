package com.mycompany.api_okex_binance_v2.indicators;

import com.mycompany.api_okex_binance_v2.Api;
import com.mycompany.api_okex_binance_v2.ExchangeApi;
import com.mycompany.api_okex_binance_v2.database.Database;
import com.mycompany.api_okex_binance_v2.enums.Exchange;
import com.mycompany.api_okex_binance_v2.enums.Indicator;
import com.mycompany.api_okex_binance_v2.enums.QCoin;
import com.mycompany.api_okex_binance_v2.net.Connect;
import com.mycompany.api_okex_binance_v2.obj.DataCoin;
import com.mycompany.api_okex_binance_v2.obj.NameTable;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author Asus
 */
public class IndicatorClient {
    Exchange exchange;

    public IndicatorClient(Exchange exchange) {
        this.exchange = exchange;
    }
    
    
    public String getNameIndicator(){
        
    }
    
    public Map<NameTable, List<DataCoin>> getData(){
        //Получаем список всех таблиц в БД
        Set<NameTable> nameTables = null;
        for (QCoin qCoin : QCoin.values()) {
            Set<NameTable> set = 
            if (set != null) {
                nameTables.addAll(set);
            }
        }
        //Получаем мапу со всеми парами биржи
        Map<NameTable, List<DataCoin>> data =  exchangeApi.getDataPairFromDatabase(nameTables, 30);
        return data;
        
    }
    
    public static boolean setData(Map<NameTable, List<DataIndicator>> data, Indicator indicator, ExchangeApi exchangeApi){
        
    }
    
    public double calculatoin(){
        
    }

}
