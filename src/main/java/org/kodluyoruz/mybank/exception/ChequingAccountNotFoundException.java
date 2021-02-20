package org.kodluyoruz.mybank.exception;

import java.text.MessageFormat;

public class ChequingAccountNotFoundException extends RuntimeException{
    public ChequingAccountNotFoundException(final Long id){
        super(MessageFormat.format("Could not find Chequing Account with id: {0}",id));
    }
}
