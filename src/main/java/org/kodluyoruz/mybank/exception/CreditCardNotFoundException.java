package org.kodluyoruz.mybank.exception;

import java.text.MessageFormat;

public class CreditCardNotFoundException extends RuntimeException{
    public CreditCardNotFoundException(final Long id){
        super(MessageFormat.format("Could not find Credit Card with id: {0}",id));
    }
}
