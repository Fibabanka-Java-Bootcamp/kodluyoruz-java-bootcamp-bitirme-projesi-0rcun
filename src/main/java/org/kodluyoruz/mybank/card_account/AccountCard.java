package org.kodluyoruz.mybank.card_account;

import org.kodluyoruz.mybank.account_chequing.ChequingAccount;

import javax.persistence.*;


@Entity
@Table(name = "account_cards")
public class AccountCard {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long cardNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    private ChequingAccount chequingAccount;

    public static AccountCard from(AccountCardDto accountCardDto){
        return new AccountCard();
    }

    public Long getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(Long cardNumber) {
        this.cardNumber = cardNumber;
    }

    public ChequingAccount getChequingAccount() {
        return chequingAccount;
    }

    public void setChequingAccount(ChequingAccount chequingAccount) {
        this.chequingAccount = chequingAccount;
    }

}
