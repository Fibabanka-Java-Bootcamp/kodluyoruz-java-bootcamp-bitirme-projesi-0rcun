package org.kodluyoruz.mybank.account_investment;

import lombok.Data;
import org.kodluyoruz.mybank.client.PlainClientDto;
import java.util.Objects;

@Data
public class InvestmentAccountDto {
    private Long iban;
    private Double amount;
    private PlainClientDto plainClientDto;

    public static InvestmentAccountDto from(InvestmentAccount investmentAccount){
        InvestmentAccountDto investmentAccountDto = new InvestmentAccountDto();
        investmentAccountDto.setIban(investmentAccount.getIban());
        investmentAccountDto.setAmount(investmentAccount.getAmount());

        if(Objects.nonNull(investmentAccount.getClient())){
            investmentAccountDto.setPlainClientDto(PlainClientDto.from(investmentAccount.getClient()));
        }
        return investmentAccountDto;
    }
}
