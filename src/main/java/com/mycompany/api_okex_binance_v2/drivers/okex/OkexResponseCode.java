package com.mycompany.api_okex_binance_v2.drivers.okex;

import com.mycompany.api_okex_binance_v2.constants.Const;
import com.mycompany.api_okex_binance_v2.enums.Exchange;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OkexResponseCode {

    private static final Logger logger = LoggerFactory.getLogger(OkexResponseCode.class.getSimpleName());

    public static boolean check(int code) {
        switch (code) {
            case Const.CODE_400:
                logger.error("{}", ResponseMessage.OKEX_400.getMessageError());
                return false;
            case Const.CODE_401:
                logger.error("{}", ResponseMessage.OKEX_401.getMessageError());
                return false;
            case Const.CODE_403:
                logger.error("{}", ResponseMessage.OKEX_403.getMessageError());
                return false;
            case Const.CODE_404:
                logger.error("{}", ResponseMessage.OKEX_404.getMessageError());
                return false;
            case Const.CODE_500:
                logger.error("{}", ResponseMessage.OKEX_500.getMessageError());
                return false;
            default:
                logger.error("Неизвесный код возврата с сервера OKEX: {}.", code);
        }
        return false;
    }

    enum ResponseMessage {

        OKEX_400("EX_OKEX - КОД 400. Плохой запрос - Неверный формат запроса"),
        OKEX_401("EX_OKEX - КОД 401. Неверный ключ APIKey"),
        OKEX_403("EX_OKEX - КОД 403. Запрещено - У вас нет доступа к запрашиваемому ресурсу"),
        OKEX_404("EX_OKEX - КОД 404. Не найдено"),
        OKEX_500("EX_OKEX - КОД 500. Внутренняя ошибка сервера - У нас возникли проблемы с нашим сервером");

        private String messageError;

        private ResponseMessage(String messageError) {
            this.messageError = messageError;
        }

        public String getMessageError() {
            return messageError;
        }

    }
}
