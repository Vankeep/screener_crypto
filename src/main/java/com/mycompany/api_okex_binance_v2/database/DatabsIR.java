package com.mycompany.api_okex_binance_v2.database;

import com.mycompany.api_okex_binance_v2.constants.Const;
import com.mycompany.api_okex_binance_v2.enums.Exchange;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DatabsIR {
    private static final Logger logger = LoggerFactory.getLogger(DatabsIR.class.getSimpleName());
    GenerateSqlMessage sqlMessage = new GenerateSqlMessage();
    Connection connection;
    Statement statement;
    Exchange exchange;

    public DatabsIR(Exchange exchange) {
        this.exchange = exchange;
    }

    /**
     * Подключение к БД
     *
     * @return true - успех, false - неудачное подключение
     */
    public boolean connect() {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("JDBC:sqlite:" + Const.PATH_DATABASE() + exchange.getName() + ".db");
            logger.info("База {}.db подключена", exchange.getName());
            return true;
        } catch (ClassNotFoundException | SQLException ex) {
            logger.error(ex.getMessage());
            return false;
        }
    }

    public void insert(String message) {
        try {
            statement = connection.createStatement();
            statement.executeUpdate(message);
        } catch (SQLException ex) {
            logger.error("{}. Сообщение - {}", ex.getMessage(), message);
        }

    }

    public HashMap<Integer, String> readAllPair(String message) {
        HashMap<Integer, String> map = new HashMap<>();
        try {
            statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(message);

            while (rs.next()) {
                int key = rs.getInt("id");
                String nameCoin = rs.getString("nameCoin");
                map.put(key, nameCoin);
            }
            return map;
        } catch (SQLException ex) {
            logger.error("{}. Сообщение - {}", ex.getMessage(), message);
            return null;
        }

    }

    public void close() {
        try {
            logger.info("База {}.db отключена", exchange.getName());
            connection.close();
        } catch (SQLException ex) {
            logger.error(ex.getMessage());
        }
    }

}
