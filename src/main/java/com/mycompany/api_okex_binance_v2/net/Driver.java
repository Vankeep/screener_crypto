package com.mycompany.api_okex_binance_v2.net;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mycompany.api_okex_binance_v2.constants.ConstCoin;
import com.mycompany.api_okex_binance_v2.constants.ConstExchange;
import com.mycompany.api_okex_binance_v2.constants.ConstTF;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.TreeSet;
/**
 * dgfjshgfjdg
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

    public static ArrayList<ArrayList<String>> fileToArrayListBINANCE(File fail) {
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
        }
        list.add(btc);
        list.add(eth);
        list.add(usdt);
        return list;
    }

}
