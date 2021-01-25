package org.kodluyoruz.mybank.card_account;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.kodluyoruz.mybank.account_chequing.ChequingAccount;

import javax.persistence.*;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "account_cards")
public class AccountCard {

    @Id
    @GeneratedValue
    private UUID cardNumber;

    @ManyToOne
    @JoinColumn(name = "chequingAccount_iban")
    private ChequingAccount chequingAccount;
}
