package com.mycompany.api_okex_binance_v2.obj;

import com.mycompany.api_okex_binance_v2.enums.QCoin;

public class UpdateCoin {

    private int candlesBack;
    private NameTable nameTable;

    public UpdateCoin(int candlesBack, NameTable nameTable) {
        this.candlesBack = candlesBack;
        this.nameTable = nameTable;
    }

    public int getCandlesBack() {
        return candlesBack;
    }

    public NameTable getNameTable() {
        return nameTable;
    }

    @Override
    public String toString() {
        return "UpdateCoin{" + "candlesBack=" + candlesBack + ", nameTable=" + nameTable.getNameTable() + '}';
    }

    

}
