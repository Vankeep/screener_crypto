package com.mycompany.api_okex_binance_v2;

import com.mycompany.api_okex_binance_v2.database.Database;
import com.mycompany.api_okex_binance_v2.drivers.Driver;
import com.mycompany.api_okex_binance_v2.drivers.binance.Binance;
import com.mycompany.api_okex_binance_v2.drivers.okex.Okex;
import com.mycompany.api_okex_binance_v2.enums.Exchange;
import com.mycompany.api_okex_binance_v2.indicators.IndicatorClient;
import com.mycompany.api_okex_binance_v2.net.Connect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class Api {

    private static final Logger logger = LoggerFactory.getLogger(Api.class.getSimpleName());
    public Driver driver;
    public Exchange exchange;
    public Database database;
    public IndicatorClient indicator;

    public Api(Exchange exchange) {
        if (exchange == Exchange.EX_BINANCE) {
            logger.info("Создаю обьккт DriverBinance");
            this.driver = new Binance();

        } else {
            logger.info("Создаю обьект DriverOkex");
            this.driver = new Okex();
        }
        this.indicator = new IndicatorClient(exchange);
        this.database = new Database(exchange);
        this.exchange = exchange;
    }

}
