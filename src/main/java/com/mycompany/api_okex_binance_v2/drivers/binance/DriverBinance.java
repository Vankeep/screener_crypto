package com.mycompany.api_okex_binance_v2.drivers.binance;

import com.mycompany.api_okex_binance_v2.obj.BCoin;
import com.google.gson.*;
import com.mycompany.api_okex_binance_v2.enums.*;
import com.mycompany.api_okex_binance_v2.drivers.Driver;
import com.mycompany.api_okex_binance_v2.obj.CoinCoin;
import com.mycompany.api_okex_binance_v2.obj.NameTable;
import com.mycompany.api_okex_binance_v2.time.Time;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DriverBinance implements Driver{

    private static final Logger logger = LoggerFactory.getLogger(DriverBinance.class.getSimpleName());
    
    @Override
    public HashMap<QCoin,HashSet<BCoin>> fileToArray(File fail) {
        
        logger.info("Перетаскиваю даные из binance.bin в HashMap");
        HashMap<QCoin, HashSet<BCoin>> list = new HashMap<>();
        QCoin[] qCoins =QCoin.getListQCoin();
        try {
            FileReader fileReader = new FileReader(fail);
            JsonElement jsonElement = JsonParser.parseReader(fileReader);
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            JsonArray jsonArray = jsonObject.get("symbols").getAsJsonArray();
            for (QCoin qCoin : qCoins){
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
            logger.error("Проблемы с файлом binance.bin, return null ", e.getMessage());
            return null;
        }
        return list;
    }
    
    @Override
    public Set<CoinCoin> stringToArray(String json, NameTable nameTable) {
        logger.info("Делаю из строки массив");
        Set<CoinCoin> set = new HashSet<>();
        String[] split = json.replaceAll("]", "").replace("[", "").replace("\"", "").split(",");
        int counter = 0;
        for (int i = 0; i < split.length / 12; i++) {
            String convertTime = Time.unixToIso(Long.parseLong(split[counter]));
            set.add(new CoinCoin(convertTime, 
                    split[counter + 1],
                    split[counter + 2], 
                    split[counter + 3], 
                    split[counter + 4], 
                    split[counter + 5],
                    nameTable));
            counter = counter + 12;
        }
        return set;
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
    public HttpURLConnection urlPairMarketData(BCoin bCoin, QCoin qCoin, Tf tF, int candlesBack) {
        return DriverURLGeneratorBinance.PairMarketData(bCoin, qCoin, tF, candlesBack);
    }

    @Override
    public boolean checkResponseCode(int code) {
        return DriverResponseCodeBinance.checkResponseCode(code);
    }

}
