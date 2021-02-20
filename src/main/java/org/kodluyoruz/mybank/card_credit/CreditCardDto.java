package org.kodluyoruz.mybank.card_credit;

import lombok.Data;
import org.kodluyoruz.mybank.client.PlainClientDto;
import java.util.Objects;

@Data
public class CreditCardDto {
    private Long cardNumber;

    private Double creditAmount;
    private Double creditLimit;
    private PlainClientDto plainClientDto;

    public static CreditCardDto from(CreditCard creditCard){
        CreditCardDto creditCardDto = new CreditCardDto();
        creditCardDto.setCardNumber(creditCard.getCardNumber());
        creditCardDto.setCreditAmount(creditCard.getCreditAmount());
        creditCardDto.setCreditLimit(creditCard.getCreditLimit());

        if(Objects.nonNull(creditCard.getClient())){
            creditCardDto.setPlainClientDto(PlainClientDto.from(creditCard.getClient()));
        }
        return creditCardDto;
    }
}