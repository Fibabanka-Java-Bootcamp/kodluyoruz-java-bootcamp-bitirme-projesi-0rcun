package org.kodluyoruz.mybank.exception;

import org.aspectj.bridge.Message;

import java.text.MessageFormat;

public class InsufficientBalanceException extends RuntimeException{

    public InsufficientBalanceException(final Long id) {
        super(MessageFormat.format("{0} does not have sufficient funds.",id));
    }
}
