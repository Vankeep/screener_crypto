package com.mycompany.api_okex_binance_v2.obj;

import com.mycompany.api_okex_binance_v2.enums.QCoin;

public class NameTable {

    private String nameTable;
    private BCoin bCoin;
    private QCoin qCoin;

    public NameTable(BCoin bCoin, QCoin qCoin) {
        this.nameTable = bCoin + "_" + qCoin.toString();
        this.bCoin = bCoin;
        this.qCoin = qCoin;
    }
    @Deprecated
    public NameTable(String nameTable) {
        this.nameTable = nameTable;
        String[] sp = nameTable.split("_");
        this.bCoin = new BCoin(sp[0]);
        QCoin[] arr = QCoin.getListQCoin();
        for (QCoin coin : arr) {
            if (coin.toString().equals(sp[1])) {
                this.qCoin = coin;
            }
        }
    }

    public String getNameTable() {
        return nameTable;
    }

    public BCoin getbCoin() {
        return bCoin;
    }

    public QCoin getqCoin() {
        return qCoin;
    }

    @Override
    public String toString() {
        return nameTable;
    }
    
}
