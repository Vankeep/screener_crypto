package com.mycompany.api_okex_binance_v2.database;

import com.mycompany.api_okex_binance_v2.constants.Const;
import com.mycompany.api_okex_binance_v2.obj.BCoin;
import com.mycompany.api_okex_binance_v2.enums.Exchange;
import com.mycompany.api_okex_binance_v2.enums.ColumnsTable;
import com.mycompany.api_okex_binance_v2.enums.QCoin;
import com.mycompany.api_okex_binance_v2.obj.CalculationCoin;
import com.mycompany.api_okex_binance_v2.obj.DataCoin;
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

    /**
     * Чтение последней строки в таблице, чтобы узнать последнее время
     * обновления
     *
     * @param nameTable имя таблицы
     * @return значение последней колонки time
     */
    public String readLastUpdatePair(NameTable nameTable) {
        String time = "";
        String message = msgLastUpdatePair(nameTable);
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

    /**
     * Чтение всех таблиц в файле базы данных
     *
     * @return HashSet со всеми таблицами в бд
     */
    public Set<NameTable> readAllTableName() {
        Set<NameTable> list = new HashSet<>();
        Set<String> deleteList = new HashSet<>();
        String message = msgSeeAllTable();
        boolean notEqual = true;
        try {
            statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(message);
            while (rs.next()) {
                try {
                    String[] name = rs.getString("name").split("_");
                    for (QCoin qCoin : QCoin.values()) {
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

    /**
     * Чтение всех таблиц соответвующих актуальным монетам в списках BTC ETH
     * USDT
     *
     * @param message
     * @param qCoin
     * @return HashSet с актуальными таблицами
     */
    public Set<NameTable> readAllPair(String message, QCoin qCoin) {
        Set<NameTable> set = new HashSet<>();
        try {
            statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(message);

            while (rs.next()) {
                int key = rs.getInt("id");
                BCoin nameCoin = new BCoin(rs.getString("nameCoin"));
                NameTable nameTable = new NameTable(nameCoin, qCoin);
                set.add(nameTable);
            }
            return set;
        } catch (SQLException ex) {
            logger.error("{} - {}. Сообщение - {}", exchange, ex.getMessage(), message);
            return null;
        }
    }

    /**
     * Чтение данных в указанном диапазоне candlesBack из указанной таблицы
     *
     * @param candlesBack сколько свечей назад. Отсчет от нуля
     * @param nameTable   имя таблицы
     * @return Лист с данными по указзаной таблице
     */
    public List<DataCoin> readDataPair(NameTable nameTable, int candlesBack) {
        List<DataCoin> data = new ArrayList<>(candlesBack);
        String message = msgReadDataCoin(nameTable, candlesBack);
        try {
            statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(message);
            int counter = 0;
            while (rs.next()) {
                String time = rs.getString("time");
                double open = rs.getDouble("open");
                double high = rs.getDouble("high");
                double low = rs.getDouble("low");
                double close = rs.getDouble("close");
                double volume = rs.getDouble("volume");
                data.add(new DataCoin(time, open, high, low, close, volume));
            }
            return data;

        } catch (SQLException ex) {
            logger.error("{} - {}. Сообщение - {}", exchange, ex.getMessage(), message);
            return null;
        }

    }

    /**
     * Закрыть подключение к БД
     */
    public void close() {
        try {
            //logger.info("{} - база отключена", exchange.getName());
            connection.close();
        } catch (SQLException ex) {
            logger.error(ex.getMessage());
        }
    }

}
