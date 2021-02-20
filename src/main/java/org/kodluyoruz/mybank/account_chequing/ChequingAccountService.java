package org.kodluyoruz.mybank.account_chequing;


import org.kodluyoruz.mybank.account_investment.InvestmentAccountService;
import org.kodluyoruz.mybank.card_credit.CreditCardService;
import org.kodluyoruz.mybank.client.Client;
import org.kodluyoruz.mybank.client.ClientService;
import org.kodluyoruz.mybank.exception.ChequingAccountNotFoundException;
import org.kodluyoruz.mybank.exception.InsufficientBalanceException;
import org.kodluyoruz.mybank.exception.OperationNotPermittedException;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class ChequingAccountService {

    private final ChequingAccountRepository chequingAccountRepository;
    private final ClientService clientService;
    private final InvestmentAccountService investmentAccountService;
    private final CreditCardService creditCardService;

    public ChequingAccountService(ChequingAccountRepository chequingAccountRepository, ClientService clientService, InvestmentAccountService investmentAccountService, CreditCardService creditCardService) {
        this.chequingAccountRepository = chequingAccountRepository;
        this.clientService = clientService;
        this.investmentAccountService = investmentAccountService;
        this.creditCardService = creditCardService;
    }

    //ChequingAccount CRUD
    public ChequingAccount create(Long clientId, ChequingAccount chequingAccount){
        Client client = clientService.getClient(clientId);
        ChequingAccount createdChequingAccount = chequingAccountRepository.save(chequingAccount);
        client.addChequingAccount(createdChequingAccount);
        createdChequingAccount.setClient(client);
        return createdChequingAccount;
    }

    public List<ChequingAccount> list() {
        return StreamSupport
                .stream(chequingAccountRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    public ChequingAccount getChequingAccount(Long id){
        return chequingAccountRepository.findById(id).orElseThrow(()->
                new ChequingAccountNotFoundException(id));
    }

    public ChequingAccount delete(Long id){
        ChequingAccount chequingAccount = getChequingAccount(id);
        if(chequingAccount.getAmount()>0){
            throw new OperationNotPermittedException("Chequing account have assets.");
        }
        chequingAccountRepository.delete(chequingAccount);
        return chequingAccount;
    }

    @Transactional
    public ChequingAccount sendMoneyToChequing(Long accountId, Long chequingId, Double amount){
        ChequingAccount chequingAccount;
        synchronized (this){
            chequingAccount = getChequingAccount(accountId);
            if(chequingAccount.getAmount() - amount >= 0){
                chequingAccount.setAmount(chequingAccount.getAmount() - amount);
                getChequingAccount(chequingId).setAmount(getChequingAccount(chequingId).getAmount()+amount);
            }else{
                throw new InsufficientBalanceException(accountId);
            }
        }
        return chequingAccount;
    }

    @Transactional
    public ChequingAccount sendMoneyToInvestment(Long accountId, Long investmentId, Double amount){
        ChequingAccount chequingAccount;
        synchronized (this){
            chequingAccount = getChequingAccount(accountId);
            if(chequingAccount.getAmount() - amount >= 0){
                chequingAccount.setAmount(chequingAccount.getAmount() - amount);
                investmentAccountService.getInvestmentAccount(investmentId)
                        .setAmount(investmentAccountService.getInvestmentAccount(investmentId).getAmount()+amount);
            }else{
                throw new InsufficientBalanceException(accountId);
            }
        }
        return chequingAccount;
    }

    @Transactional
    public ChequingAccount sendMoneyToCreditCard(Long accountId, Long creditCardId, Double amount){
        ChequingAccount chequingAccount;
        synchronized (this){
            chequingAccount = getChequingAccount(accountId);
            if(chequingAccount.getAmount() - amount >= 0){
                chequingAccount.setAmount(chequingAccount.getAmount() - amount);
                creditCardService.getCreditCard(creditCardId)
                        .setCreditAmount(creditCardService.getCreditCard(creditCardId).getCreditAmount() - amount);
            }else{
                throw new InsufficientBalanceException(accountId);
            }
        }
        return chequingAccount;
    }
}
