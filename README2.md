###Получение данных с интернета
Создаем обьект класса `Connect` 

В `list` возвращается три массива со всеми парами биржи: BTC, ETH, USDT.
```java
Connect Binance = new Connect(ConstExchange.EX_BINANCE);
ArrayList<ArrayList<String>> list = Binance.getAllExInfo();
```

