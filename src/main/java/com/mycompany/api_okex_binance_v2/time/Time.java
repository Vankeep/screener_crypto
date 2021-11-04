package com.mycompany.api_okex_binance_v2.time;

import java.time.Instant;
import java.util.concurrent.TimeUnit;

public class Time {
    
    public static long getUTC(){
        Instant instant = Instant.now();
        return instant.getEpochSecond();
    }
    
    public static void fd(){
        System.out.println(TimeUnit.SECONDS.toString());
    }
}
