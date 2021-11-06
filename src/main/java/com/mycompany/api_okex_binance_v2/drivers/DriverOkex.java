package com.mycompany.api_okex_binance_v2.drivers;

import com.google.gson.Gson;
import com.mycompany.api_okex_binance_v2.enums.*;
import com.mycompany.api_okex_binance_v2.obj.CoinCoin;
import com.mycompany.api_okex_binance_v2.time.Time;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.StringTokenizer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DriverOkex {
    
    private static final Logger logger = LoggerFactory.getLogger(DriverOkex.class.getSimpleName());

    /**
     * Биржа OKEX. Конвертирует файл с json в двумерный массив. В каждом
     * подмасивве первое число массива это quote_currency этого массива
     *
     * @param fail файл с json
     * @return возвращает двумерный массив
     */
    public static ArrayList<ArrayList<String>> fileToArrayOKEX(File fail) {
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
            logger.error("Проблемы с файлом binance.bin, return null {}", ex.getMessage());
            return null;
        }
        list.add(btc);
        list.add(eth);
        list.add(usdt);
        return list;
    }
    
    public static ArrayList<CoinCoin> stringToArray(String json){
        logger.info("Делаю из строки массив");
        ArrayList<CoinCoin> array = new ArrayList<>();
        String[] split = json.replaceAll("]", "").replace("[", "").replace("\"", "").split(",");
        int counter = 0;
        for (int i = 0; i < split.length/6; i++) {
            array.add(new CoinCoin(String.valueOf(Time.isoToUnix(split[counter])),//split[counter], //
                    split[counter+1], 
                    split[counter + 2], 
                    split[counter + 3], 
                    split[counter + 4], 
                    split[counter + 5]));
            counter=counter+6;
        }
        return array;
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

   
    public static String getTf(Tf tf) {
        switch (tf) {
            case HOUR_ONE:
                return "3600";
            case HOUR_TWO:
                return "7200";
            case HOUR_FOUR:
                return "14400";
            case HOUR_SIX:
                return "21600";
            case HOUR_TWENTY:
                return "43200";
            case DAY_ONE:
                return "86400";
            default:
                return null;
        }
    }
    
    public static String getUrl(){
        return "https://www.okex.com/";
    }
}
