package org.kodluyoruz.mybank.exception;

import java.text.MessageFormat;

public class ClientNotFoundException extends RuntimeException{

    public ClientNotFoundException(final Long id){
        super(MessageFormat.format("Could not find account with id: {0}",id));
    }
}
