package com.mycompany.api_okex_binance_v2.net;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Json {

    private static final Logger logger = LoggerFactory.getLogger(Json.class.getSimpleName());

    /**
     *
     * @param url адрес для загрузки json
     * @return строку со скачанными данными
     */
    public static String getJsonString(URL url) {
        StringBuilder sb = new StringBuilder();
        try {
            InputStreamReader isr = new InputStreamReader(url.openStream());
            BufferedReader br = new BufferedReader(isr);
            String c;
            while ((c = br.readLine()) != null) {
                sb.append(c);
            }
            isr.close();
        } catch (IOException ex) {
            logger.error("Ошибка соединения. Метод getCoinData. {}", ex.getMessage());
        }
        return sb.toString();
    }

    /**
     *
     * @param url адрес для загрузки json
     * @param nameFile "имя.json" или "путь_к_файлу/имяю.json" файла
     * @return файл со скачанными туда данными
     */
    public static File getJsonFile(URL url, String nameFile) {
        File file = new File(nameFile);
        try {
            ReadableByteChannel rbc = Channels.newChannel(url.openStream());
            FileOutputStream fos = new FileOutputStream(file);
            fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
            fos.close();
        } catch (IOException e) {
            logger.error("Ошибка соединения. Метод getCoinData. {}", e.getMessage());
        }
        return file;
    }

}
