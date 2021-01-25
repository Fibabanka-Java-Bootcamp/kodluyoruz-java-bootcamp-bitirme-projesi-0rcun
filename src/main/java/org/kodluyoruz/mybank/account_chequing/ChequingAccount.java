package org.kodluyoruz.mybank.account_chequing;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.kodluyoruz.mybank.card_account.AccountCard;
import org.kodluyoruz.mybank.client.Client;

import javax.persistence.*;
import java.util.Set;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "chequing_accounts")
public class ChequingAccount {
    @Id
    @GeneratedValue
    private UUID iban;

    /*TL, Euro or Dolar*/
    private String type;
    private Double amount;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    @OneToMany(mappedBy = "chequingAccount")
    private Set<AccountCard> accountCards;
}
