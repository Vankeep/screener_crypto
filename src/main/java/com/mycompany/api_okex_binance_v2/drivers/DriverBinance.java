package com.mycompany.api_okex_binance_v2.drivers;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mycompany.api_okex_binance_v2.enums.Coin;
import com.mycompany.api_okex_binance_v2.enums.Tf;
import com.mycompany.api_okex_binance_v2.obj.CoinCoin;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DriverBinance {

    private static final Logger logger = LoggerFactory.getLogger(DriverBinance.class.getSimpleName());
    /**
     * Биржа BINANCE. Конвертирует файл с json в двумерный массив. В каждом
     * подмасивве первое число массива это quote_currency этого массива
     *
     * @param fail файл с json
     * @return возвращает двумерный массив
     */
    public static ArrayList<ArrayList<String>> fileToArrayBINANCE(File fail) {
        logger.info("Перетаскиваю даные из binance.bin в массив");
        ArrayList<ArrayList<String>> list = new ArrayList<>();
        ArrayList<String> btc = new ArrayList<>();
        btc.add(Coin.BTC.toString());
        ArrayList<String> eth = new ArrayList<>();
        eth.add(Coin.ETH.toString());
        ArrayList<String> usdt = new ArrayList<>();
        usdt.add(Coin.USDT.toString());
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
                    if (quoteAsset.equals(Coin.BTC.toString())) {
                        btc.add(baseAsset);
                    }
                    if (quoteAsset.equals(Coin.ETH.toString())) {
                        eth.add(baseAsset);
                    }
                    if (quoteAsset.equals(Coin.USDT.toString())) {
                        usdt.add(baseAsset);
                    }
                }
            }

        } catch (FileNotFoundException e) {
            logger.error("Проблемы с файлом binance.bin, return null ", e.getMessage());
            return null;
        }
        list.add(btc);
        list.add(eth);
        list.add(usdt);
        return list;
    }
    
    public static ArrayList<CoinCoin> stringToArray(String json) {
        logger.info("Делаю из строки массив");
        System.out.println(json);
        ArrayList<CoinCoin> array = new ArrayList<>();
        String[] split = json.replaceAll("]", "").replace("[", "").replace("\"", "").split(",");
        System.out.println(Arrays.toString(split));
        int counter = 0;
        for (int i = 0; i < split.length / 12; i++) {
            array.add(new CoinCoin(split[counter], split[counter + 1],
                    split[counter + 2], split[counter + 3], split[counter + 4], split[counter + 5]));
            counter = counter + 12;
        }
        return array;
    }

    public static String getTf(Tf tf) {
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
