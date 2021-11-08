package com.mycompany.api_okex_binance_v2.drivers.okex;

import com.google.gson.Gson;
import com.mycompany.api_okex_binance_v2.enums.*;
import com.mycompany.api_okex_binance_v2.drivers.Driver;
import com.mycompany.api_okex_binance_v2.obj.CoinCoin;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DriverOkex implements Driver{
    
    private static final Logger logger = LoggerFactory.getLogger(DriverOkex.class.getSimpleName());


  
    @Override
    public ArrayList<ArrayList<String>> fileToArray(File fail) {
        logger.info("Перетаскиваю даные из okex.bin в массив");
        ArrayList<ArrayList<String>> list = new ArrayList<>();

        ArrayList<String> btc = new ArrayList<>();
        btc.add(Coin.BTC.toString());

        ArrayList<String> eth = new ArrayList<>();
        eth.add(Coin.ETH.toString());

        ArrayList<String> usdt = new ArrayList<>();
        usdt.add(Coin.USDT.toString());

        StringBuilder sb = new StringBuilder();
        FileInputStream fis;
        try {
            fis = new FileInputStream(fail);
            int c;
            while ((c = fis.read()) != -1) {
                sb.append((char) c);
            }
            fis.close();

            Gson gson = new Gson();
            CoinOKEX[] pair = gson.fromJson(sb.toString(), CoinOKEX[].class);
            for (CoinOKEX cokex : pair) {
                if (cokex.getQuote_currency().equals(Coin.BTC.toString())) {
                    btc.add(cokex.getBase_currency());
                }
                if (cokex.getQuote_currency().equals(Coin.ETH.toString())) {
                    eth.add(cokex.getBase_currency());
                }
                if (cokex.getQuote_currency().equals(Coin.USDT.toString())) {
                    usdt.add(cokex.getBase_currency());
                }
            }
        } catch (IOException ex) {
            logger.error("Проблемы с файлом okex.bin, return null {}", ex.getMessage());
            return null;
        }
        list.add(btc);
        list.add(eth);
        list.add(usdt);
        return list;
    }
    
    @Override
    public ArrayList<CoinCoin> stringToArray(String json){
        logger.info("Делаю из строки массив");
        ArrayList<CoinCoin> array = new ArrayList<>();
        String[] split = json.replaceAll("]", "").replace("[", "").replace("\"", "").split(",");
        int counter = split.length;
        for (int i = 0; i < split.length/6; i++) {
            counter = counter-6;
            array.add(new CoinCoin(split[counter], //String.valueOf(Time.isoToUnix(split[counter]))
                    split[counter+1], 
                    split[counter + 2], 
                    split[counter + 3], 
                    split[counter + 4], 
                    split[counter + 5]));
        }
        return array;
    }

    @Override
    public HttpURLConnection urlAllExchangeInfo() {
        return DriverURLGeneratorOkex.urlAllExchangeInfo();
    }

    @Override
    public HttpURLConnection urlPairMarketData(String bCoin, Coin qCoin, Tf tF, int candlesBack) {
        return DriverURLGeneratorOkex.urlPairMarketData(bCoin, qCoin, tF, candlesBack);
    }

    @Override
    public boolean checkResponseCode(int code) {
        return DriverResponseCodeOkex.check(code);
    }

    @Override
    public String getExchangeName() {
        return Exchange.EX_OKEX.getName();
    }

    /**
     * Создан для сортировки в методе fileToArrayOKEX
     */
    class CoinOKEX {

        String base_currency;
        String quote_currency;

        public CoinOKEX(String base_currency, String quote_currency) {
            this.base_currency = base_currency;
            this.quote_currency = quote_currency;
        }

        public String getBase_currency() {
            return base_currency;
        }

        public String getQuote_currency() {
            return quote_currency;
        }

    }

   
    
    
}
