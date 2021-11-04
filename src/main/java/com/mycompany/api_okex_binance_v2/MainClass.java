package com.mycompany.api_okex_binance_v2;

import com.mycompany.api_okex_binance_v2.constants.Const;
import com.mycompany.api_okex_binance_v2.database.CoinBigData;
import com.mycompany.api_okex_binance_v2.database.Database;
import com.mycompany.api_okex_binance_v2.database.GenerateSqlMessage;
import com.mycompany.api_okex_binance_v2.net.Connect;
import com.mycompany.api_okex_binance_v2.time.Time;
import java.io.IOException;
import java.nio.CharBuffer;

public class MainClass {

    public String[] open;
    public String[] time;
    public String[] high;
    public String[] low;
    public String[] close;
    public String[] volume;
    CoinBigData name;
    boolean updateOkex = false;
    boolean updateBinance = false;

    public static void main(String[] args) throws InterruptedException {
        MainClass mainClass = new MainClass();
        mainClass.momo();

    }

    public void momo() throws InterruptedException {
        ApiClient okex = new Connect(Const.Exchange.EX_OKEX);
        ApiClient binance = new Connect(Const.Exchange.EX_BINANCE);

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
         
        

    }

}
