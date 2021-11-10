package com.mycompany.api_okex_binance_v2.obj;

import com.mycompany.api_okex_binance_v2.enums.QCoin;

public class BCoin {
    private String bCoin;

    public BCoin(String bCoin) {
        this.bCoin = bCoin;
    }

    public String getbCoin() {
        return bCoin;
    }

    public void setbCoin(String bCoin) {
        this.bCoin = bCoin;
    }
    
    public static QCoin convertToQcoin(BCoin bCoin){
        QCoin[] qCoins = QCoin.getListQCoin();
        for (QCoin q : qCoins) {
            if (q.toString().equals(bCoin.toString())) {
                return q;
            }
        }
        System.out.println("ERROR CONVERT BCoin in QCoin. Переданный в парамерты BCoin не эквиванентен ни одному QCoin. Return null");
        return null;
    }

    @Override
    public String toString() {
        return bCoin;
    }
    

}
