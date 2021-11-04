package com.mycompany.api_okex_binance_v2;

import com.mycompany.api_okex_binance_v2.constants.Const;
import com.mycompany.api_okex_binance_v2.database.Database;
import com.mycompany.api_okex_binance_v2.net.Connect;
import java.util.HashMap;

public class MainClass {

    public static void main(String[] args) throws InterruptedException {
        MainClass mainClass = new MainClass();
        mainClass.momo();

    }

    public void momo() throws InterruptedException {
        ApiClient okex = new Connect(Const.Exchange.EX_OKEX);
        ApiClient binance = new Connect(Const.Exchange.EX_BINANCE);
        DatabaseClient okexdb = new Database(Const.Exchange.EX_OKEX);
        DatabaseClient binancedb = new Database(Const.Exchange.EX_BINANCE);
//
//        Thread one = new Thread(() -> {
//            if (binance.updateAllPair()) {
//                updateBinance = true;
//            }
//        });
//        one.start();
//        Thread two = new Thread(() -> {
//            if (okex.updateAllPair()) {
//                updateOkex = true;
//            }
//        });
//        two.start();
//        one.join();
//        two.join();
        
         HashMap<Integer,String> btc_b = binancedb.getAllPair(Const.Coin.BTC);
         HashMap<Integer,String> eth_b = binancedb.getAllPair(Const.Coin.ETH);
         HashMap<Integer,String> usdt_b = binancedb.getAllPair(Const.Coin.USDT);
         
         for (int i = 1; i <= btc_b.size(); i++) {
             System.out.print(i + "="+btc_b.get(i)+", ");
        }
         System.out.println();
         for (int i = 1; i <= eth_b.size(); i++) {
             System.out.print(i + "="+eth_b.get(i)+", ");
        }
         System.out.println();
         for (int i = 1; i <= usdt_b.size(); i++) {
             System.out.print(i + "="+usdt_b.get(i)+", ");
        }
         

    }

}
