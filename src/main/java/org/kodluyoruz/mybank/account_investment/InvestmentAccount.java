package org.kodluyoruz.mybank.account_investment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.kodluyoruz.mybank.client.Client;

import javax.persistence.*;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "investment_accounts")
public class InvestmentAccount {
    @Id
    @GeneratedValue
    private UUID iban;

    private Double amount;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;
}
