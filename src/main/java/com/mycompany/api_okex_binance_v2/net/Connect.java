package com.mycompany.api_okex_binance_v2.net;


import com.mycompany.api_okex_binance_v2.constants.ConstCoin;
import com.mycompany.api_okex_binance_v2.constants.ConstExchange;
import com.mycompany.api_okex_binance_v2.database.CoinBigData;
import com.mycompany.api_okex_binance_v2.drivers.DriverBinance;
import com.mycompany.api_okex_binance_v2.drivers.DriverOkex;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.*;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.ArrayList;

public class Connect {
    
    private ArrayList<ArrayList<String>> list;
    private ConstExchange ex;

    public Connect(ConstExchange ex) {
        this.ex = ex;
    }
    /**
     * ВОЗВРАЩАЕТ СТРОГО ЗАДАННЫЕ ЗАРЕНЕЕ ПАРЫ К BTC ETH USDT
     * @return 1 - BTC, 2 - ETH, 3 - USDT, если проблемы с файлом вернет null;
     */
    public ArrayList<ArrayList<String>> getAllExInfo() {
        GenerateUrlMessage url = new GenerateUrlMessage(ex);
        File file = new File(ex.getName() + ".bin");
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
                return null;
        }

    }
    
    
    public boolean writeDB(ArrayList<ArrayList<String>> list){
        return false;
    }
    
    public boolean writeDB(ArrayList<ArrayList<CoinBigData>> list, ConstCoin constCoin){
        return false;
    }
}
