package com.mycompany.api_okex_binance_v2;

import com.mycompany.api_okex_binance_v2.database.DBInsertAndRead;
import com.mycompany.api_okex_binance_v2.database.Database;
import com.mycompany.api_okex_binance_v2.database.SqlMsg;
import com.mycompany.api_okex_binance_v2.database.Update;
import com.mycompany.api_okex_binance_v2.enums.Exchange;
import com.mycompany.api_okex_binance_v2.interfaces.HttpClient;

import com.mycompany.api_okex_binance_v2.enums.QCoin;
import com.mycompany.api_okex_binance_v2.enums.Tf;
import com.mycompany.api_okex_binance_v2.interfaces.DatabaseClient;
import com.mycompany.api_okex_binance_v2.net.Connect;
import com.mycompany.api_okex_binance_v2.obj.CoinCoin;
import com.mycompany.api_okex_binance_v2.obj.NameTable;
import com.mycompany.api_okex_binance_v2.obj.UpdateCoin;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MainClass {

    public static final Logger logger = LoggerFactory.getLogger(MainClass.class.getSimpleName());
    boolean updateBinance = false;
    boolean updateOkex = false;

    public static void main(String[] args) throws InterruptedException {
        test();
    }

    public static void test() {
        ExchangeApi okex = new ExchangeApi(Exchange.EX_OKEX);
        Database okexD = new Database(Exchange.EX_OKEX);
        okexD.deleteAllTable(QCoin.ETH);
        Update.startUpdate(QCoin.ETH, okex);

       
    }

}
