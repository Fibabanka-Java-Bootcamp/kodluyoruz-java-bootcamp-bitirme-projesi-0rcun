package org.kodluyoruz.mybank.card_account;

import lombok.Data;
import org.kodluyoruz.mybank.account_chequing.PlainChequingCardDto;

import java.util.Objects;

@Data
public class AccountCardDto {

    private Long cardNumber;
    private PlainChequingCardDto plainChequingCardDto;


    public static AccountCardDto from(AccountCard accountCard){
        AccountCardDto accountCardDto = new AccountCardDto();
        accountCardDto.setCardNumber(accountCard.getCardNumber());

        if(Objects.nonNull(accountCard.getChequingAccount())){
            accountCardDto.setPlainChequingCardDto(PlainChequingCardDto.from(accountCard.getChequingAccount()));
        }

        return accountCardDto;
    }

}
