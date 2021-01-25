package org.kodluyoruz.mybank.client;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.kodluyoruz.mybank.account_chequing.ChequingAccount;
import org.kodluyoruz.mybank.account_investment.InvestmentAccount;
import org.kodluyoruz.mybank.card_credit.CreditCard;

import javax.persistence.*;
import java.util.Set;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "clients")
public class Client {

    @Id
    @GeneratedValue
    private UUID id;
    private String name;

    @OneToMany(mappedBy = "client")
    private Set<InvestmentAccount> investmentAccount;

    @OneToMany(mappedBy = "client")
    private Set<ChequingAccount> chequingAccount;

    @OneToMany(mappedBy = "client")
    private Set<CreditCard> creditCard;


    public ClientDto toClientDto() {
        return ClientDto.builder()
                .id(this.id)
                .name(this.name)
                .build();
    }
}
