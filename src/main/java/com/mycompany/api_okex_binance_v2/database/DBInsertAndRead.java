package com.mycompany.api_okex_binance_v2.database;

import com.mycompany.api_okex_binance_v2.constants.Const;
import com.mycompany.api_okex_binance_v2.obj.BCoin;
import com.mycompany.api_okex_binance_v2.enums.Exchange;
import com.mycompany.api_okex_binance_v2.enums.QCoin;
import com.mycompany.api_okex_binance_v2.obj.NameTable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import java.util.logging.Level;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sqlite.SQLiteErrorCode;

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
            //logger.info("{} - база подключена", exchange.getName());
            return true;
        } catch (ClassNotFoundException | SQLException ex) {
            logger.error("Ошибка подключения к базе данных. {}", ex.getMessage());
            return false;
        }
    }

    public boolean insert(String message) {
        try {
            statement = connection.createStatement();
            statement.executeUpdate(message);
            return true;

        } catch (SQLException ex) {
            logger.error("{} - {}. Код ошибки - {}", exchange, ex.getMessage(), ex.getErrorCode());
            return false;
        }

    }

    public String readLastUpdatePair(String message) {
        String time = "";
        try {
            statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(message);
            while (rs.next()) {
                time = rs.getString("time");
            }
            return time;
        } catch (SQLException ex) {
            logger.error("{} - чтение последней записи в таблице не удалось. {}. Сообщение - {} Код ошибки - {}", exchange, ex.getMessage(), message, ex.getErrorCode());
            return "";
        }
    }

    public Set<NameTable> readAllTableName(String message) {
        QCoin[] qCoins = QCoin.getListQCoin();
        Set<NameTable> list = new HashSet<>();
        Set<String> deleteList = new HashSet<>();
        boolean notEqual = true;
        try {
            statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(message);
            while (rs.next()) {
                try {
                    String[] name = rs.getString("name").split("_");
                    for (QCoin qCoin : qCoins) {
                        if (qCoin.toString().equals(name[1])) {
                            list.add(new NameTable(new BCoin(name[0]), qCoin));
                        }
                    }
                } catch (ArrayIndexOutOfBoundsException ex) {
                    
                }
            }
            return list;
        } catch (SQLException ex) {
            logger.error("{} - чтение всех таблиц не удалось. {}. Сообщение - {}", exchange, ex.getMessage(), message);
            return null;
        }

    }

    public Map<Integer, BCoin> readAllPair(String message) {
        Map<Integer, BCoin> map = new HashMap<>();
        try {
            statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(message);

            while (rs.next()) {
                int key = rs.getInt("id");
                BCoin nameCoin = new BCoin(rs.getString("nameCoin"));
                map.put(key, nameCoin);
            }
            return map;
        } catch (SQLException ex) {
            logger.error("{} - {}. Сообщение - {}", exchange, ex.getMessage(), message);
            return null;
        }

    }

    public void close() {
        try {
            //logger.info("{} - база отключена", exchange.getName());
            connection.close();
        } catch (SQLException ex) {
            logger.error(ex.getMessage());
        }
    }

}
