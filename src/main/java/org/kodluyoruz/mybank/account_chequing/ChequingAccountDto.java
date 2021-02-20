package org.kodluyoruz.mybank.account_chequing;

import lombok.Data;
import org.kodluyoruz.mybank.card_account.AccountCardDto;
import org.kodluyoruz.mybank.client.PlainClientDto;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Data
public class ChequingAccountDto {
    private Long iban;
    private Double amount;
    private PlainClientDto plainClientDto;
    private List<AccountCardDto> accountCardDtos = new ArrayList<>();

    public static ChequingAccountDto from(ChequingAccount chequingAccount){
        ChequingAccountDto chequingAccountDTO = new ChequingAccountDto();
        chequingAccountDTO.setIban(chequingAccount.getIban());
        chequingAccountDTO.setAmount(chequingAccount.getAmount());
        chequingAccountDTO.setAccountCardDtos(chequingAccount.getAccountCards().stream().map(AccountCardDto::from).collect(Collectors.toList()));

        if(Objects.nonNull(chequingAccount.getClient())){
            chequingAccountDTO.setPlainClientDto(PlainClientDto.from(chequingAccount.getClient()));
        }

        return chequingAccountDTO;
    }

}
