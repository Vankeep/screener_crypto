package com.mycompany.api_okex_binance_v2.database;

import com.mycompany.api_okex_binance_v2.constants.Const;
import java.sql.*;

public class Database {

    GenerateSqlMessage sqlMessage;
    Connection connection;
    Statement statement;
    Const.Exchange exchange;

    public Database(Const.Exchange exchange) {
        this.exchange = exchange;
    }

    public void go() {
        if (connect()) {
            //database.write();
            //read();
            close();
        }
    }

    /**
     * Подключение к БД
     *
     * @return true - успех, false - неудачное подключение
     */
    private boolean connect()  {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("JDBC:sqlite:" + exchange.getName() + ".db");
            System.out.println("База данных подключена");
            return true;
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println(ex.getMessage());
            return false;
        }
    }

    private void write(CoinBigData coin) {
        try {
            long counter = coin.getClose().length;
            for (int i = 0; i < counter; i++) {
                String message = sqlMessage.insertDataCoin(coin, i);
                statement = connection.createStatement();
                statement.executeUpdate(message);
            }

            System.out.println("Данные успешно записаны в базу");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

    }

    private void write(String name, String time, String open, String high, String low, String close, String volume, Const.Coin qCoin) {
        try {
            String message = sqlMessage.insertDataCoin(name, time, open, high, low, close, volume, qCoin);
            statement = connection.createStatement();
            statement.executeUpdate(message);
            System.out.println("Данные успешно записаны в базу");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

    }

    private void read() {
        try {
            String value = "SELECT тут запись"
                    + " FROM last";

            statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(value);

            while (rs.next()) {
                double a = rs.getDouble("BTC");
                System.out.println(a);
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private void close() {
        try {
            connection.close();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

}
