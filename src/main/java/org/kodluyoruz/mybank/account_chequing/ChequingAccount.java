package org.kodluyoruz.mybank.account_chequing;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.kodluyoruz.mybank.card_account.AccountCard;
import org.kodluyoruz.mybank.client.Client;
import org.kodluyoruz.mybank.client.ClientDto;

import javax.persistence.*;
import java.util.Set;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "chequing_accounts")
public class ChequingAccount {
    @Id
    @GeneratedValue
    private UUID iban;

    private Double amount;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    @OneToMany(mappedBy = "chequingAccount")
    private Set<AccountCard> accountCards;


    public ChequingAccountDTO toChequingAccountDTO() {
        return ChequingAccountDTO.builder()
                .iban(this.iban)
                .amount(this.amount)
                .build();
    }
}
