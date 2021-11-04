package com.mycompany.api_okex_binance_v2;

import com.mycompany.api_okex_binance_v2.constants.Const;
import com.mycompany.api_okex_binance_v2.database.Database;
import com.mycompany.api_okex_binance_v2.net.Connect;
import java.util.HashMap;

public class MainClass {

    boolean updateBinance = false;
    boolean updateOkex = false;

    public static void main(String[] args) throws InterruptedException {
        MainClass mainClass = new MainClass();

    }
        

    public void momo() throws InterruptedException {
        long d = System.currentTimeMillis();
        
        HttpClient okex = new Connect(Const.EXCHANGE.EX_OKEX);
        HttpClient binance = new Connect(Const.EXCHANGE.EX_BINANCE);
        DatabaseClient okexdb = new Database(Const.EXCHANGE.EX_OKEX);
        DatabaseClient binancedb = new Database(Const.EXCHANGE.EX_BINANCE);

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
        HashMap<Integer, String> btc_b = binancedb.getAllPair(Const.COIN.BTC);
        HashMap<Integer, String> eth_b = binancedb.getAllPair(Const.COIN.ETH);
        HashMap<Integer, String> usdt_b = binancedb.getAllPair(Const.COIN.USDT);
        HashMap<Integer, String> btc_o = okexdb.getAllPair(Const.COIN.BTC);
        HashMap<Integer, String> eth_o = okexdb.getAllPair(Const.COIN.ETH);
        HashMap<Integer, String> usdt_o = okexdb.getAllPair(Const.COIN.USDT);
        
        System.out.println("ПАРЫ БИНАНСА");
        System.out.println(btc_b.toString() + "\n" + eth_b.toString() + "\n" + usdt_b.toString());

        System.out.println("\nПАРЫ ОКЕХ");
        System.out.println(btc_o.toString() + "\n" + eth_o.toString() + "\n" + usdt_o.toString());
        
        System.out.println("\nВремя обновления пар = " + (System.currentTimeMillis() - d) / 1000 + " сек");

    }

}
