package com.mycompany.api_okex_binance_v2.indicators;

import com.mycompany.api_okex_binance_v2.ExchangeApi;
import com.mycompany.api_okex_binance_v2.obj.NameTable;

public class Rsi implements IndicatorClient{

    @Override
    public boolean getData(NameTable nameTable, ExchangeApi exchangeApi) {
        return false;
    }

    @Override
    public boolean setData(NameTable nameTable, ExchangeApi exchangeApi) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public double calculatoin() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getNameIndicator() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

   

}
