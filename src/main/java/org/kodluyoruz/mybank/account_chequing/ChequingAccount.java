package org.kodluyoruz.mybank.account_chequing;

import org.kodluyoruz.mybank.card_account.AccountCard;
import org.kodluyoruz.mybank.client.Client;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "chequing_accounts")
public class ChequingAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long iban;
    private Double amount;

    @ManyToOne(fetch = FetchType.LAZY)
    private Client client;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "account_card_id")
    private List<AccountCard> accountCards = new ArrayList<>();

    public void addAccountCard(AccountCard accountCard){
        accountCards.add(accountCard);
    }

    public void removeAccountCard(AccountCard accountCard){
        accountCards.remove(accountCard);
    }

    public static ChequingAccount from(ChequingAccountDto chequingAccountDTO){
        ChequingAccount chequingAccount = new ChequingAccount();
        chequingAccount.setAmount(chequingAccountDTO.getAmount());
        return chequingAccount;
    }

    public Long getIban() {
        return iban;
    }

    public void setIban(Long iban) {
        this.iban = iban;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public List<AccountCard> getAccountCards() {
        return accountCards;
    }

    public void setAccountCards(List<AccountCard> accountCards) {
        this.accountCards = accountCards;
    }

}
