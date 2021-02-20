package org.kodluyoruz.mybank.card_credit;


import lombok.Getter;
import lombok.Setter;
import org.kodluyoruz.mybank.client.Client;

import javax.persistence.*;



@Getter
@Setter
@Entity
@Table(name = "credit_cards")
public class CreditCard {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long cardNumber;

    private Double creditAmount;
    private Double creditLimit;

    @ManyToOne(fetch = FetchType.LAZY)
    private Client client;

    public static CreditCard from(CreditCardDto creditCardDto){
        CreditCard creditCard = new CreditCard();
        creditCard.setCreditAmount(creditCardDto.getCreditAmount());
        creditCard.setCreditLimit(creditCardDto.getCreditLimit());
        return creditCard;
    }

    public Long getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(Long cardNumber) {
        this.cardNumber = cardNumber;
    }

    public Double getCreditAmount() {
        return creditAmount;
    }

    public void setCreditAmount(Double creditAmount) {
        this.creditAmount = creditAmount;
    }

    public Double getCreditLimit() {
        return creditLimit;
    }

    public void setCreditLimit(Double creditLimit) {
        this.creditLimit = creditLimit;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
}
