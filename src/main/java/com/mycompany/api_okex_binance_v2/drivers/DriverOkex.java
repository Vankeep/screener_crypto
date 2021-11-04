package com.mycompany.api_okex_binance_v2.drivers;

import com.google.gson.Gson;
import com.mycompany.api_okex_binance_v2.constants.Const;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class DriverOkex {
    
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

    /**
     * Биржа OKEX. Конвертирует файл с json в двумерный массив. В каждом
     * подмасивве первое число массива это quote_currency этого массива
     *
     * @param fail файл с json
     * @return возвращает двумерный массив
     */
    public static ArrayList<ArrayList<String>> fileToArrayOKEX(File fail) {
        System.out.println("Перетаскиваю даные из okex.bin в массив....");
        ArrayList<ArrayList<String>> list = new ArrayList<>(3);

        ArrayList<String> btc = new ArrayList<>();
        btc.add(Const.Coin.BTC.toString());

        ArrayList<String> eth = new ArrayList<>();
        eth.add(Const.Coin.ETH.toString());

        ArrayList<String> usdt = new ArrayList<>();
        usdt.add(Const.Coin.USDT.toString());

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
                if (cokex.getQuote_currency().equals(Const.Coin.BTC.toString())) {
                    btc.add(cokex.getBase_currency());
                }
                if (cokex.getQuote_currency().equals(Const.Coin.ETH.toString())) {
                    eth.add(cokex.getBase_currency());
                }
                if (cokex.getQuote_currency().equals(Const.Coin.USDT.toString())) {
                    usdt.add(cokex.getBase_currency());
                }
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            return null;
        }
        list.add(btc);
        list.add(eth);
        list.add(usdt);
        return list;
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

    /**
     * Конвертирование времени для биржи OKEX
     *
     * @param isoFormat строка вида 2021-10-21T11:00:00.000Z
     * @return
     */
    public static long isoToUnixOKEX(String isoFormat) {
        long unixDataTime = 0;
        try {
            Date date = sdf.parse(isoFormat);
            unixDataTime = date.getTime();
        } catch (ParseException e) {
            System.out.println("Несовпадение формата даты " + e.getMessage());
        }
        return unixDataTime;
    }

    public static String unixToIsoOKEX(long unixFormat) {
        return sdf.format(new Date(unixFormat));
    }
    
    public static String getTf(Const.TF tf) {
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
