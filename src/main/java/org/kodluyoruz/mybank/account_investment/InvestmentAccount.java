package org.kodluyoruz.mybank.account_investment;

import org.kodluyoruz.mybank.client.Client;
import javax.persistence.*;

@Entity
@Table(name = "investment_accounts")
public class InvestmentAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long iban;
    private Double amount;
    @ManyToOne(fetch = FetchType.LAZY)
    private Client client;


    public static InvestmentAccount from(InvestmentAccountDto investmentAccountDto){
        InvestmentAccount investmentAccount = new InvestmentAccount();
        investmentAccount.setAmount(investmentAccountDto.getAmount());
        return investmentAccount;
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

}
