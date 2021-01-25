package org.kodluyoruz.mybank.card_credit;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.kodluyoruz.mybank.client.Client;

import javax.persistence.*;
import java.util.UUID;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "credit_cards")
public class CreditCard {

    @Id
    @GeneratedValue
    private UUID cardNumber;

    private Double creditAmount;
    private Double creditLimit;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;
}
