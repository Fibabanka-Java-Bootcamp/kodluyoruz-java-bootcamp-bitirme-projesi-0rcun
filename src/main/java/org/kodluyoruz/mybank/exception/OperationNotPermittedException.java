package org.kodluyoruz.mybank.exception;

import java.text.MessageFormat;

public class OperationNotPermittedException extends RuntimeException{
    public OperationNotPermittedException(final String cause) {
        super(MessageFormat.format("Operation not permitted: {0}", cause));
    }
}
