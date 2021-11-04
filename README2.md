###Получение данных с интернета
Создаем обьект класса `Connect` 

В `list` возвращается три массива со всеми парами биржи: BTC, ETH, USDT.
```java
        Connect connectOkex = new Connect(ConstExchange.EX_OKEX);
        ArrayList<ArrayList<String>> list = connectOkex.getAllExInfo();
        if (list != null) {
                //дальнейшие действия с массивом
            }
        } else {
        System.out.println("Ошибка");
        }
```

