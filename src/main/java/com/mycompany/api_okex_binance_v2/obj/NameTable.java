package com.mycompany.api_okex_binance_v2.obj;

import com.mycompany.api_okex_binance_v2.enums.QCoin;
import java.util.Objects;

public class NameTable {
    
    private String nameTable;
    private BCoin bCoin;
    private QCoin qCoin;

    public NameTable(BCoin bCoin, QCoin qCoin) {
        this.nameTable = bCoin + "_" + qCoin.toString();
        this.bCoin = bCoin;
        this.qCoin = qCoin;
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

    @Override
    public boolean equals(Object obj) {
        return super.equals((NameTable)obj); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 59 * hash + Objects.hashCode(this.nameTable);
        hash = 59 * hash + Objects.hashCode(this.bCoin);
        hash = 59 * hash + Objects.hashCode(this.qCoin);
        return hash;
    }

   
    
    
    
}
