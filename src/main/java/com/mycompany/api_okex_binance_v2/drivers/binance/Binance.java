package com.mycompany.api_okex_binance_v2.drivers.binance;

import com.mycompany.api_okex_binance_v2.obj.BCoin;
import com.google.gson.*;
import com.mycompany.api_okex_binance_v2.enums.*;
import com.mycompany.api_okex_binance_v2.drivers.Driver;
import com.mycompany.api_okex_binance_v2.obj.DataCoin;
import com.mycompany.api_okex_binance_v2.obj.NameTable;
import com.mycompany.api_okex_binance_v2.time.Time;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.net.HttpURLConnection;
import java.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Binance implements Driver{

    private static final Logger logger = LoggerFactory.getLogger(Binance.class.getSimpleName());
    
    @Override
    public HashMap<QCoin,HashSet<BCoin>> fileToArray(File fail) {
        
        logger.info("EX_BINANCE - Перетаскиваю даные из binance.bin в HashMap");
        HashMap<QCoin, HashSet<BCoin>> list = new HashMap<>();
        try {
            FileReader fileReader = new FileReader(fail);
            JsonElement jsonElement = JsonParser.parseReader(fileReader);
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            JsonArray jsonArray = jsonObject.get("symbols").getAsJsonArray();
            for (QCoin qCoin : QCoin.values()){
                HashSet<BCoin> set = new HashSet<>();
                for (JsonElement je : jsonArray) {
                    JsonObject symbolJsonObj = je.getAsJsonObject();
                    String status = symbolJsonObj.get("status").getAsString();
                    String baseAsset = symbolJsonObj.get("baseAsset").getAsString();
                    String quoteAsset = symbolJsonObj.get("quoteAsset").getAsString();
                    
                    if (status.equals("TRADING") && quoteAsset.equals(qCoin.toString())) {
                        set.add(new BCoin(baseAsset));
                    }
                }
                list.put(qCoin, set);
            }

        } catch (FileNotFoundException e) {
            logger.error("EX_BINANCE - Проблемы с файлом binance.bin, return null ", e.getMessage());
            return null;
        }
        return list;
    }
    
    @Override
    public List<DataCoin> stringToArray(String json, NameTable nameTable) {
        logger.info("EX_BINANCE - {} делаю из строки массив", nameTable);
        List<DataCoin> set = new ArrayList<>();
        String[] split = json.replaceAll("]", "").replace("[", "").replace("\"", "").split(",");
        int counter = 0;
        for (int i = 0; i < split.length / 12; i++) {
            String convertTime = Time.unixToIso(Long.parseLong(split[counter]));
            set.add(new DataCoin(convertTime, 
                    split[counter + 1],
                    split[counter + 2], 
                    split[counter + 3], 
                    split[counter + 4], 
                    split[counter + 5]));
            counter = counter + 12;
        }
        return set;
    }

    @Override
    public HttpURLConnection urlAllExchangeInfo() {
       return BinanceURLGenerator.AllExchangeInfo();
    }

    @Override
    public HttpURLConnection urlPairMarketData(BCoin bCoin, QCoin qCoin, Tf tF, int candlesBack) {
        return BinanceURLGenerator.PairMarketData(bCoin, qCoin, tF, candlesBack);
    }

    @Override
    public boolean checkResponseCode(int code) {
        return BinanceResponseCode.checkResponseCode(code);
    }

}
