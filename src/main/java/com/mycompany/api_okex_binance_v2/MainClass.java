package com.mycompany.api_okex_binance_v2;

import com.mycompany.api_okex_binance_v2.database.CoinBigData;
import com.mycompany.api_okex_binance_v2.constants.ConstExchange;
import com.mycompany.api_okex_binance_v2.net.Connect;
import com.mycompany.api_okex_binance_v2.net.Driver;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class MainClass {

    public String[] open;
    public String[] time;
    public String[] high;
    public String[] low;
    public String[] close;
    public String[] volume;
    CoinBigData name;

    public static void main(String[] args) {
        MainClass mainClass = new MainClass();
        mainClass.momo();

    }

    public void momo() {
//        GenerateSqlMessage gsm = new GenerateSqlMessage();
//        time = new String[]{"1635747564", "1635747564", "1635747564", "1635747564"};
//        open = new String[]{"0.000541", "0.000881", "0.000798", "0.000715"};
//        high = new String[]{"0.000542", "0.000882", "0.000798", "0.000715"};
//        low = new String[]{"0.000543", "0.000883", "0.000798", "0.000715"};
//        close = new String[]{"0.000544", "0.000884", "0.000798", "0.000715"};
//        volume = new String[]{"12.23", "13.44", "0.000798", "0.000715"};
//        
//        CoinBigData dodo = new CoinBigData("DODO", time, open, high, low, close, volume, ConstCoin.BTC);
//        CoinBigData slp = new CoinBigData("SLP", time, open, high, low, close, volume, ConstCoin.BTC);
//        
//       
//        GenerateUrlMessage gumOK = new GenerateUrlMessage(ConstExchange.EX_OKEX);
//        GenerateUrlMessage gumBN = new GenerateUrlMessage(ConstExchange.EX_BINANCE);
//        
//        System.out.println(gumOK.getCoinData("BTC", ConstCoin.USDT, ConstTF.HOUR_ONE));
//        System.out.println(gumBN.getCoinData("BTC", ConstCoin.USDT, ConstTF.HOUR_ONE));

        //Тру способ получить данные 
        /*
        Connect connectBinance = new Connect(ConstExchange.EX_BINANCE);
        ArrayList<ArrayList<String>> list = connectBinance.getAllExInfo();
        if (list != null) {
            for (int i = 0; i < 3; i++) {
                System.out.println(list.get(i).toString());
            }
        } else {
            System.out.println("Ошибка FileNotFoundException");
        }
        */
        Connect connectOkex = new Connect(ConstExchange.EX_OKEX);
        ArrayList<ArrayList<String>> list = connectOkex.getAllExInfo();
        if (list != null) {
            for (int i = 0; i < 3; i++) {
                System.out.println(list.get(i).toString());
            }
        } else {
            System.out.println("Ошибка");
        }
        
        

    }
}
