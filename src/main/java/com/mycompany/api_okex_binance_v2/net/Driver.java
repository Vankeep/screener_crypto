package com.mycompany.api_okex_binance_v2.net;

import com.mycompany.api_okex_binance_v2.constants.ConstExchange;
import com.mycompany.api_okex_binance_v2.constants.ConstTF;

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
    //Метод для считывания информации с файла 
//    public static List<Symbol> readAllDataFromJson(File fail) {
//        System.out.println("Отсеивам не рабочие пары...");
//        List<Symbol> symbols = new ArrayList<>();
//        try {
//            FileReader fileReader = new FileReader(fail);
//            JsonElement jsonElement = JsonParser.parseReader(fileReader);
//            JsonObject jsonObject = jsonElement.getAsJsonObject();
//            JsonArray jsonArray = jsonObject.get("symbols").getAsJsonArray();
//
//            for (JsonElement je : jsonArray) {
//                JsonObject symbolJsonObj = je.getAsJsonObject();
//                String symbol = symbolJsonObj.get("symbol").getAsString();
//                String status = symbolJsonObj.get("status").getAsString();
//                String baseAsset = symbolJsonObj.get("baseAsset").getAsString();
//                String quoteAsset = symbolJsonObj.get("quoteAsset").getAsString();
//                if (status.equals("TRADING")) {
//                    Symbol newSymbol = new Symbol(symbol, baseAsset, quoteAsset);
//                    symbols.add(newSymbol);
//                }
//            }
//
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//        return symbols;
//    }
}
