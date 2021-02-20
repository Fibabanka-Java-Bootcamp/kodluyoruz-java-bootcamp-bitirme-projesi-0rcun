package org.kodluyoruz.mybank.account_investment;

import org.kodluyoruz.mybank.account_chequing.ChequingAccount;
import org.kodluyoruz.mybank.client.Client;
import org.kodluyoruz.mybank.client.ClientService;
import org.kodluyoruz.mybank.exception.InsufficientBalanceException;
import org.kodluyoruz.mybank.exception.InvestmentAccountNotFoundException;
import org.kodluyoruz.mybank.exception.OperationNotPermittedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class InvestmentAccountService {

    private final InvestmentAccountRepository investmentAccountRepository;
    private final ClientService clientService;

    @Autowired
    public InvestmentAccountService(InvestmentAccountRepository investmentAccountRepository, ClientService clientService) {
        this.investmentAccountRepository = investmentAccountRepository;
        this.clientService = clientService;
    }

    public InvestmentAccount create(Long clientId, InvestmentAccount investmentAccount){
        Client client = clientService.getClient(clientId);
        InvestmentAccount createdInvestmentAccount = investmentAccountRepository.save(investmentAccount);
        client.addInvestmentAccount(createdInvestmentAccount);
        createdInvestmentAccount.setClient(client);
        return createdInvestmentAccount;
    }

    public List<InvestmentAccount> list() {
        return StreamSupport
                .stream(investmentAccountRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    public InvestmentAccount getInvestmentAccount(Long id){
        return investmentAccountRepository.findById(id).orElseThrow(()->
                new InvestmentAccountNotFoundException(id));
    }

    public InvestmentAccount delete(Long id){
        InvestmentAccount investmentAccount = getInvestmentAccount(id);
        if(investmentAccount.getAmount()>0){
            throw new OperationNotPermittedException("Investment account have assets.");
        }
        investmentAccountRepository.delete(investmentAccount);
        return investmentAccount;
    }

    @Transactional
    public InvestmentAccount sendMoney(Long accountId, Long chequingId, Double amount){
        InvestmentAccount investmentAccount;
        synchronized (this){
            AtomicBoolean chequingFlag= new AtomicBoolean(false);
            final ChequingAccount[] receiverChequingAccount = new ChequingAccount[1];
            investmentAccount = getInvestmentAccount(accountId);
            investmentAccount.getClient().getChequingAccounts().forEach((chequingAccount -> {
                if(chequingAccount.getIban().equals(chequingId)){
                    chequingFlag.set(true);
                    receiverChequingAccount[0] = chequingAccount;
                }
            }));
            if(chequingFlag.get()){
                if(investmentAccount.getAmount() - amount >= 0){
                    investmentAccount.setAmount(investmentAccount.getAmount() - amount);
                    receiverChequingAccount[0].setAmount(receiverChequingAccount[0].getAmount()+amount);
                }else{
                    throw new InsufficientBalanceException(accountId);
                }
            }else{
                throw new OperationNotPermittedException("Chequing account is not depend same Client.");
            }
        }
        return investmentAccount;
    }
}
