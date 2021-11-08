package com.mycompany.api_okex_binance_v2.drivers.binance;

import com.mycompany.api_okex_binance_v2.constants.Const;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DriverResponseCodeBinance {

    private static final Logger logger = LoggerFactory.getLogger(DriverResponseCodeBinance.class.getSimpleName());

    public static boolean checkResponseCode(int code) {
        switch (code) {
            case Const.CODE_400:
                logger.error("{}", ResponseMessage.BINANCE_400.getMessageError());
                return false;
            case Const.CODE_403:
                logger.error("{}", ResponseMessage.BINANCE_403.getMessageError());
                return false;
            case Const.CODE_429:
                logger.error("{}", ResponseMessage.BINANCE_429.getMessageError());
                return false;
            case Const.CODE_418:
                logger.error("{}", ResponseMessage.BINANCE_418.getMessageError());
                return false;
            case Const.CODE_500:
                logger.error("{}", ResponseMessage.BINANCE_500.getMessageError());
                return false;
            default:
                logger.error("Неизвесный код возврата с сервера BINANCE: {}.", code);
        }
        return false;
    }

    enum ResponseMessage {

        BINANCE_400("Некорректный запрос - Неверный формат запроса"),
        BINANCE_403("Был нарушен предел WAF (брандмауэр веб-приложений"),
        BINANCE_429("Нарушение ограничения скорости запросов"),
        BINANCE_418("IP-адрес был автоматически заблокирован для продолжения отправки запросов после получения 429 кодов"),
        BINANCE_500("Внутренняя ошибка сервера - У нас возникли проблемы с нашим сервером");

        private String messageError;

        private ResponseMessage(String messageError) {
            this.messageError = messageError;
        }

        public String getMessageError() {
            return messageError;
        }
    }
}
/*
 * 4XXКоды возврата HTTP используются для некорректных запросов; вопрос на стороне отправителя.
403Код возврата HTTP используется, когда был нарушен предел WAF (брандмауэр веб-приложений).
429Код возврата HTTP используется при нарушении ограничения скорости запросов.
418Код возврата HTTP используется, когда IP-адрес был автоматически заблокирован для продолжения отправки запросов после получения 429кодов.
5XXКоды возврата HTTP используются для внутренних ошибок; вопрос на стороне Binance. Важно НЕ рассматривать это как операцию с ошибкой; статус выполнения UNKNOWN и мог быть успешным.
 */