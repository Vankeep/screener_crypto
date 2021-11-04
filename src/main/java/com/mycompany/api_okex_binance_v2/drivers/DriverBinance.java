package com.mycompany.api_okex_binance_v2.drivers;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mycompany.api_okex_binance_v2.constants.Const;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;

public class DriverBinance {
    /**
     * Биржа BINANCE. Конвертирует файл с json в двумерный массив. В каждом
     * подмасивве первое число массива это quote_currency этого массива
     *
     * @param fail файл с json
     * @return возвращает двумерный массив
     */
    public static ArrayList<ArrayList<String>> fileToArrayBINANCE(File fail) {
        System.out.println("Перетаскиваю даные из binance.bin в массив...." );
        ArrayList<ArrayList<String>> list = new ArrayList<>(3);

        ArrayList<String> btc = new ArrayList<>();
        btc.add(Const.Coin.BTC.toString());

        ArrayList<String> eth = new ArrayList<>();
        eth.add(Const.Coin.ETH.toString());

        ArrayList<String> usdt = new ArrayList<>();
        usdt.add(Const.Coin.USDT.toString());
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
                    if (quoteAsset.equals(Const.Coin.BTC.toString())) {
                        btc.add(baseAsset);
                    }
                    if (quoteAsset.equals(Const.Coin.ETH.toString())) {
                        eth.add(baseAsset);
                    }
                    if (quoteAsset.equals(Const.Coin.USDT.toString())) {
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
    
    public static String getTf(Const.TF tf){
        switch (tf) {
            case HOUR_ONE:
                return "1h";
            case HOUR_TWO:
                return "2h";
            case HOUR_FOUR:
                return "4h";
            case HOUR_SIX:
                return "6h";
            case HOUR_TWENTY:
                return "12h";
            case DAY_ONE:
                return "1d";
            default:
                return null;
        }  
    }
    
    public static String getUrl() {
        return "https://api.binance.com/";
    }
    
    
}