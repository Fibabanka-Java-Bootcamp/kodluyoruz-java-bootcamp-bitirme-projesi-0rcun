package org.kodluyoruz.mybank.client;

import lombok.Getter;
import lombok.Setter;
import org.kodluyoruz.mybank.account_chequing.ChequingAccount;
import org.kodluyoruz.mybank.account_investment.InvestmentAccount;
import org.kodluyoruz.mybank.card_credit.CreditCard;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@Entity
@Table(name = "clients")
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "investment_account_id")
    private List<InvestmentAccount> investmentAccounts = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "chequing_account_id")
    private List<ChequingAccount> chequingAccounts = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "credit_card_id")
    private List<CreditCard> creditCards = new ArrayList<>();

    public void addChequingAccount(ChequingAccount chequingAccount){
        chequingAccounts.add(chequingAccount);
    }

    public void removeChequingAccount(ChequingAccount chequingAccount){
        chequingAccounts.remove(chequingAccount);
    }

    public void addInvestmentAccount(InvestmentAccount investmentAccount){
        investmentAccounts.add(investmentAccount);
    }

    public void removeInvestmentAccount(InvestmentAccount investmentAccount){
        investmentAccounts.remove(investmentAccount);
    }

    public void addCreditCard(CreditCard creditCard){
        creditCards.add(creditCard);
    }

    public void removeCreditCard(CreditCard creditCard){
        creditCards.remove(creditCard);
    }

    public static Client from(ClientDto clientDto){
        Client client = new Client();
        client.setName(clientDto.getName());
        return client;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<InvestmentAccount> getInvestmentAccounts() {
        return investmentAccounts;
    }

    public void setInvestmentAccounts(List<InvestmentAccount> investmentAccounts) {
        this.investmentAccounts = investmentAccounts;
    }

    public List<ChequingAccount> getChequingAccounts() {
        return chequingAccounts;
    }

    public void setChequingAccounts(List<ChequingAccount> chequingAccounts) {
        this.chequingAccounts = chequingAccounts;
    }

    public List<CreditCard> getCreditCards() {
        return creditCards;
    }

    public void setCreditCards(List<CreditCard> creditCards) {
        this.creditCards = creditCards;
    }
}
