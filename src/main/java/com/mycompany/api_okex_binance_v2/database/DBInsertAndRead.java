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

public class DBInsertAndRead extends SqlMsg {

    private static final Logger logger = LoggerFactory.getLogger(DBInsertAndRead.class.getSimpleName());
    SqlMsg sqlMessage = new SqlMsg();
    Connection connection;
    Statement statement;
    Exchange exchange;

    public DBInsertAndRead(Exchange exchange) {
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
            connection = DriverManager.getConnection("JDBC:sqlite:" + Const.PATH_DATABASE + exchange.getName() + ".db");
            logger.info("{} - база подключена", exchange.getName());
            return true;
        } catch (ClassNotFoundException | SQLException ex) {
            logger.error(ex.getMessage());
            return false;
        }
    }

    public boolean insert(String message) {
        try {
            statement = connection.createStatement();
            statement.executeUpdate(message);
            return true;
        } catch (SQLException ex) {
            logger.error("{} - {}. Сообщение - {}", exchange.getName(), ex.getMessage(), message);
            logger.error(String.valueOf(ex.getErrorCode()));
            return false;
        }

    }

    public HashMap<Integer, String> readAllPairToQcoin(String message) {
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
            logger.error("{} - {}. Сообщение - {}", exchange.getName(), ex.getMessage(), message);
            return null;
        }

    }

    public void close() {
        try {
            logger.info("{} - база отключена", exchange.getName());
            connection.close();
        } catch (SQLException ex) {
            logger.error(ex.getMessage());
        }
    }

}
