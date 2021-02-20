package org.kodluyoruz.mybank.exception;

import java.text.MessageFormat;

public class AccountCardNotFoundException extends RuntimeException{
    public AccountCardNotFoundException(final Long id){
        super(MessageFormat.format("Could not find Account Card with id: {0}",id));
    }
}
