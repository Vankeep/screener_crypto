package com.mycompany.api_okex_binance_v2.database;

import com.mycompany.api_okex_binance_v2.DatabaseClient;
import com.mycompany.api_okex_binance_v2.constants.Const;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Database implements DatabaseClient {

    GenerateSqlMessage sqlMessage = new GenerateSqlMessage();
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
    private boolean connect() {
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

    private void write(String message) {
        try {
            statement = connection.createStatement();
            statement.executeUpdate(message);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

    }

    private HashMap<Integer,String> readQcoin(String message) {
        HashMap<Integer,String> map = new HashMap<>();
        try {
            statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(message);

            while (rs.next()) {
                int key = rs.getInt("id");
                String nameCoin = rs.getString("nameCoin");
                map.put(key, nameCoin);
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return map;
    }

    private void close() {
        try {
            System.out.println("База данных отключена");
            connection.close();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public boolean insertAllPairToDatabase(ArrayList<ArrayList<String>> list) {
        if (connect()) {
            for (ArrayList<String> arrayList : list) {
                System.out.println("Запись пар к " + arrayList.get(0) + "...");
                write(sqlMessage.deleteTable(arrayList.get(0)));
                write(sqlMessage.createTable(arrayList.get(0)));
                for (int i = 1; i < arrayList.size(); i++) {
                    write(sqlMessage.insertQcoin(arrayList.get(0), arrayList.get(i)));
                }
            }
            close();
            return true;
        } else {
            return false;
        }

    }

    @Override
    public HashMap<Integer,String> getAllPair(Const.Coin qCoin) {
        if(connect()){
            HashMap<Integer,String> map = readQcoin(sqlMessage.readQcoin(qCoin));
            close();
            return map;
        }
        return null;
    }

    @Override
    public long[] getDataCoin(Const.TF tf, String bCoin, Const.Coin qCoin, Const.OHLC ohlc) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public long[][] getDataCoin(Const.TF tf, String bCoin, Const.Coin qCoin, Const.OHLC ohlc1, Const.OHLC ohlc2) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public long[][] getDataCoin(Const.TF tf, String bCoin, Const.Coin qCoin, Const.OHLC ohlc1, Const.OHLC ohlc2, Const.OHLC ohlc3) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public long[][] getDataCoin(Const.TF tf, String bCoin, Const.Coin qCoin, Const.OHLC ohlc1, Const.OHLC ohlc2, Const.OHLC ohlc3, Const.OHLC ohlc4) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
