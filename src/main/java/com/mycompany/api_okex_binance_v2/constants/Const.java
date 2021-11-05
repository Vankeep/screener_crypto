package com.mycompany.api_okex_binance_v2.constants;

import java.io.File;

public class Const {

    private static final String pachToDbFile = "src" + File.separator + "main" + File.separator + "java"
            + File.separator + "com" + File.separator + "mycompany"
            + File.separator + "api_okex_binance_v2" + File.separator + "database" + File.separator;

    public static String PATH_DATABASE() {
        return pachToDbFile;
    }
}
