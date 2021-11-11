package com.mycompany.api_okex_binance_v2.indicators;

import com.mycompany.api_okex_binance_v2.ExchangeApi;
import com.mycompany.api_okex_binance_v2.obj.NameTable;

public interface IndicatorClient {
    
    public String getNameIndicator();
    
    public boolean getData(NameTable nameTable, ExchangeApi exchangeApi);
    
    public boolean setData(NameTable nameTable, ExchangeApi exchangeApi);
    
    public double calculatoin();

}
