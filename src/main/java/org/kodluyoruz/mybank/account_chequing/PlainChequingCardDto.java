package org.kodluyoruz.mybank.account_chequing;

import lombok.Data;
import org.kodluyoruz.mybank.client.PlainClientDto;

import java.util.Objects;

@Data
public class PlainChequingCardDto {
    private Long iban;
    private Double amount;
    private PlainClientDto plainClientDto;

    public static PlainChequingCardDto from(ChequingAccount chequingAccount){
        PlainChequingCardDto plainChequingCardDto = new PlainChequingCardDto();
        plainChequingCardDto.setIban(chequingAccount.getIban());
        plainChequingCardDto.setAmount(chequingAccount.getAmount());

        if(Objects.nonNull(chequingAccount.getClient())){
            plainChequingCardDto.setPlainClientDto(PlainClientDto.from(chequingAccount.getClient()));
        }

        return plainChequingCardDto;
    }
}
