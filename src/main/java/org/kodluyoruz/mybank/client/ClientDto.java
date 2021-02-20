package org.kodluyoruz.mybank.client;

import lombok.Data;
import org.kodluyoruz.mybank.account_chequing.ChequingAccountDto;
import org.kodluyoruz.mybank.account_investment.InvestmentAccountDto;
import org.kodluyoruz.mybank.card_credit.CreditCardDto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class ClientDto {
    private Long id;
    private String name;
    private List<ChequingAccountDto> chequingAccountDtos = new ArrayList<>();
    private List<InvestmentAccountDto> investmentAccountDtos = new ArrayList<>();
    private List<CreditCardDto> creditCardDtos = new ArrayList<>();

    public static ClientDto from(Client client){
        ClientDto clientDto = new ClientDto();
        clientDto.setId(client.getId());
        clientDto.setName(client.getName());

        clientDto.setCreditCardDtos(client.getCreditCards().stream().map(CreditCardDto::from).collect(Collectors.toList()));
        clientDto.setChequingAccountDtos(client.getChequingAccounts().stream().map(ChequingAccountDto::from).collect(Collectors.toList()));
        clientDto.setInvestmentAccountDtos(client.getInvestmentAccounts().stream().map(InvestmentAccountDto::from).collect(Collectors.toList()));
        return clientDto;
    }

}
