package com.mycompany.api_okex_binance_v2;

import com.mycompany.api_okex_binance_v2.database.Database;
import com.mycompany.api_okex_binance_v2.enums.Coin;
import com.mycompany.api_okex_binance_v2.enums.Exchange;
import com.mycompany.api_okex_binance_v2.enums.Tf;
import com.mycompany.api_okex_binance_v2.net.Connect;
import java.util.HashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MainClass {
    public static final Logger logger = LoggerFactory.getLogger(MainClass.class.getSimpleName());
    boolean updateBinance = false;
    boolean updateOkex = false;

    public static void main(String[] args) throws InterruptedException {
        MainClass mainClass = new MainClass();
        mainClass.momo();
            
    }
        

    public void momo() throws InterruptedException {
        
        HttpClient okex = new Connect(Exchange.EX_OKEX);
        HttpClient binance = new Connect(Exchange.EX_BINANCE);
        DatabaseClient okexdb = new Database(Exchange.EX_OKEX);
        DatabaseClient binancedb = new Database(Exchange.EX_BINANCE);

        Thread one = new Thread(() -> {
            if (binance.updateAllPair()) {
                updateBinance = true;
            }
        });
       one.start();
        Thread two = new Thread(() -> {
            if (okex.updateAllPair()) {
                updateOkex = true;
            }
        });
        two.start();
        one.join();
        two.join();
        if (updateBinance && updateOkex) {
            System.out.println("Данные успешно обновлены");
        }

        HashMap<Integer, String> btc_o = okexdb.getAllPair(Coin.BTC);
        HashMap<Integer, String> eth_o = okexdb.getAllPair(Coin.ETH);
        HashMap<Integer, String> usdt_o = okexdb.getAllPair(Coin.USDT);
        HashMap<Integer, String> btc_b = binancedb.getAllPair(Coin.BTC);
        HashMap<Integer, String> eth_b = binancedb.getAllPair(Coin.ETH);
        HashMap<Integer, String> usdt_b = binancedb.getAllPair(Coin.USDT);
        
        try {
            System.out.println("\nПАРЫ ОКЕХ");
            System.out.println(btc_o.toString()+"\n"+eth_o.toString() + "\n"+usdt_o.toString() + "\n");           
            System.out.println("\nПАРЫ BINANCE");
            System.out.println(btc_b.toString()+"\n"+eth_b.toString() + "\n"+usdt_b.toString() + "\n");           
        } catch (NullPointerException e) {
            logger.error("HashMap пустой. {}",e.getMessage());
        }
       

    }

}
