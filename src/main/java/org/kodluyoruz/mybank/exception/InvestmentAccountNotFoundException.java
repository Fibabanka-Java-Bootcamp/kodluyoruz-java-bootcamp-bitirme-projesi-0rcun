package org.kodluyoruz.mybank.exception;

import java.text.MessageFormat;

public class InvestmentAccountNotFoundException extends RuntimeException{
    public InvestmentAccountNotFoundException(final Long id){
        super(MessageFormat.format("Could not find Investment Account with id: {0}",id));
    }
}
