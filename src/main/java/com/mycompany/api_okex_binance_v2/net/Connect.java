package com.mycompany.api_okex_binance_v2.net;

import com.mycompany.api_okex_binance_v2.constants.Const;
import com.mycompany.api_okex_binance_v2.database.Database;
import com.mycompany.api_okex_binance_v2.drivers.DriverBinance;
import com.mycompany.api_okex_binance_v2.drivers.DriverOkex;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.*;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.ArrayList;
import com.mycompany.api_okex_binance_v2.HttpClient;

public class Connect implements HttpClient {

    private ArrayList<ArrayList<String>> list;
    private Const.Exchange ex;

    public Connect(Const.Exchange ex) {
        this.ex = ex;
    }

    /**
     * ВОЗВРАЩАЕТ СТРОГО ЗАДАННЫЕ ЗАРЕНЕЕ ПАРЫ К BTC ETH USDT
     *
     * @return три массива вида. Первное значение в массиве это quote_coin
     * <p>1 - > [BTC, GO, BNB, ETH, USDT,....]
     * <p>2 - > [ETH, GO, BTC, ETH, SOL,.....]
     * <p>3 - > [USDT, BTC, ETH, BNB, DODO...]
     */
    public ArrayList<ArrayList<String>> getAllExInfo() {
        GenerateUrlMessage url = new GenerateUrlMessage(ex);
        File file = new File(Const.PATH_DATABASE()+ex.getName() + ".bin");
        System.out.println("Загружаю все пары в файл " + file.toString() + "....");
        try {
            ReadableByteChannel rbc = Channels.newChannel(new URL(url.getAllCoinsData()).openStream());
            FileOutputStream fos = new FileOutputStream(file);
            fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
            fos.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        switch (ex) {
            case EX_BINANCE:
                return DriverBinance.fileToArrayBINANCE(file);
            case EX_OKEX:
                return DriverOkex.fileToArrayOKEX(file);
            default:
                System.out.println("Ошибка! getAllExInfo вернула null");
                return null;
        }

    }

    @Override
    public boolean updateAllPair() {
        boolean ok = false;
        ArrayList<ArrayList<String>> allPair = getAllExInfo();
        if (allPair != null) {
            ok = new Database(ex).insertAllPairToDatabase(allPair);
        }
        return ok;
    }

    @Override
    public boolean updateCoinData(String bCoin, Const.Coin qCoin) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
