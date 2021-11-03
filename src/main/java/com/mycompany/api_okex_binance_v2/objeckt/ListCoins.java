package com.mycompany.api_okex_binance_v2.objeckt;

import com.mycompany.api_okex_binance_v2.constants.ConstCoin;
import com.mycompany.api_okex_binance_v2.constants.ConstTF;
import java.util.HashMap;

public class ListCoins {
    
    private HashMap<Integer, CoinBigData> list;
    private ConstTF tf;
    private ConstCoin QCoin;

    public ListCoins(HashMap<Integer, CoinBigData> list, ConstTF tf, ConstCoin QCoin) {
        this.list = list;
        this.tf = tf;
        this.QCoin = QCoin;
    }
    
}
