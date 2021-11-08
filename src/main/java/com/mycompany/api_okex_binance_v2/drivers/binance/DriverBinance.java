package com.mycompany.api_okex_binance_v2.drivers.binance;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mycompany.api_okex_binance_v2.enums.Coin;
import com.mycompany.api_okex_binance_v2.drivers.Driver;
import com.mycompany.api_okex_binance_v2.enums.Exchange;
import com.mycompany.api_okex_binance_v2.enums.Tf;
import com.mycompany.api_okex_binance_v2.obj.CoinCoin;
import com.mycompany.api_okex_binance_v2.time.Time;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DriverBinance implements Driver{

    private static final Logger logger = LoggerFactory.getLogger(DriverBinance.class.getSimpleName());
    /**
     * Биржа BINANCE. Конвертирует файл с json в двумерный массив. В каждом
     * подмасивве первое число массива это quote_currency этого массива
     *
     * @param fail файл с json
     * @return возвращает двумерный массив
     */
    @Override
    public ArrayList<ArrayList<String>> fileToArray(File fail) {
        
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
    
    @Override
    public ArrayList<CoinCoin> stringToArray(String json) {
        logger.info("Делаю из строки массив");
        ArrayList<CoinCoin> array = new ArrayList<>();
        String[] split = json.replaceAll("]", "").replace("[", "").replace("\"", "").split(",");
        logger.info("Получен массив {}",Arrays.toString(split));
        logger.info("Длинна массива {}", split.length);
        int counter = 0;
        for (int i = 0; i < split.length / 12; i++) {
            String convertTime = Time.unixToIso(Long.parseLong(split[counter]));
            array.add(new CoinCoin(convertTime, 
                    split[counter + 1],
                    split[counter + 2], 
                    split[counter + 3], 
                    split[counter + 4], 
                    split[counter + 5]));
            counter = counter + 12;
        }
        return array;
    }

    @Override
    public String getExchangeName() {
        return Exchange.EX_BINANCE.getName();
    }

    @Override
    public HttpURLConnection urlAllExchangeInfo() {
       return DriverURLGeneratorBinance.AllExchangeInfo();
    }

    @Override
    public HttpURLConnection urlPairMarketData(String bCoin, Coin qCoin, Tf tF, int candlesBack) {
        return DriverURLGeneratorBinance.AllExchangeInfo();
    }

    @Override
    public boolean checkResponseCode(int code) {
        return DriverResponseCodeBinance.checkResponseCode(code);
    }

}