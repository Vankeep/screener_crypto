package com.mycompany.api_okex_binance_v2.net;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mycompany.api_okex_binance_v2.constants.ConstExchange;
import com.mycompany.api_okex_binance_v2.constants.ConstTF;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Класс помогает соствковать несостыкуемое
 *
 * @author Asus
 */
public class Driver {

    /**
     * Вспомогательный метод для конверирования пришедшего таймфрейма в строку
     * понятную для выбранной биржи
     *
     * @param tf таймфрейм
     * @param ex
     * @return строка
     */
    public static String tfDriver(ConstTF tf, ConstExchange ex) {
        switch (tf) {
            case HOUR_ONE:
                return ex.getHOUR_ONE();
            case HOUR_TWO:
                return ex.getHOUR_TWO();
            case HOUR_FOUR:
                return ex.getHOUR_FOUR();
            case HOUR_SIX:
                return ex.getHOUR_SIX();
            case HOUR_TWENTY:
                return ex.getHOUR_TWENTY();
            case DAY_ONE:
                return ex.getDAY_ONE();
            default:
                return null;
        }
    }

    /**
     * Биржа BINANCE. Конвертирует файл с json в двумерный массив.
     *
     * @param fail файл с json
     * @return возвращает двумерный массив
     */
    public static ArrayList<ArrayList<String>> fileToArrayBINANCE(File fail) {
        ArrayList<ArrayList<String>> list = new ArrayList<>(3);
        ArrayList<String> btc = new ArrayList<>();
        btc.add("1");
        ArrayList<String> eth = new ArrayList<>();
        eth.add("2");
        ArrayList<String> usdt = new ArrayList<>();
        usdt.add("3");
        try {
            FileReader fileReader = new FileReader(fail);
            JsonElement jsonElement = JsonParser.parseReader(fileReader);
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            JsonArray jsonArray = jsonObject.get("symbols").getAsJsonArray();
            for (JsonElement je : jsonArray) {
                JsonObject symbolJsonObj = je.getAsJsonObject();
                String status = symbolJsonObj.get("status").getAsString();
                String baseAsset = symbolJsonObj.get("baseAsset").getAsString();
                String quoteAsset = symbolJsonObj.get("quoteAsset").getAsString();
                if (status.equals("TRADING")) {
                    if (quoteAsset.equals("BTC")) {
                        btc.add(baseAsset);
                    }
                    if (quoteAsset.equals("ETH")) {
                        eth.add(baseAsset);
                    }
                    if (quoteAsset.equals("USDT")) {
                        usdt.add(baseAsset);
                    }
                }
            }

        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
            return null;
        }
        list.add(btc);
        list.add(eth);
        list.add(usdt);
        return list;
    }

    /**
     * Биржа OKEX. Конвертирует файл с json в двумерный массив.
     *
     * @param fail файл с json
     * @return возвращает двумерный массив
     */
    public static ArrayList<ArrayList<String>> fileToArrayOKEX(File fail) {
        ArrayList<ArrayList<String>> list = new ArrayList<>(3);
        ArrayList<String> btc = new ArrayList<>();
        btc.add("1");
        ArrayList<String> eth = new ArrayList<>();
        eth.add("2");
        ArrayList<String> usdt = new ArrayList<>();
        usdt.add("3");

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
                if (cokex.getQuote_currency().equals("BTC")) {
                    btc.add(cokex.getBase_currency());
                }
                if (cokex.getQuote_currency().equals("ETH")) {
                    eth.add(cokex.getBase_currency());
                }
                if (cokex.getQuote_currency().equals("USDT")) {
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

}
