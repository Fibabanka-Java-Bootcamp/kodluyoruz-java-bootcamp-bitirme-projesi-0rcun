package org.kodluyoruz.mybank.card_account;

import org.kodluyoruz.mybank.account_chequing.ChequingAccount;
import org.kodluyoruz.mybank.account_chequing.ChequingAccountService;
import org.kodluyoruz.mybank.exception.AccountCardNotFoundException;
import org.kodluyoruz.mybank.exception.InsufficientBalanceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class AccountCardService {

    private final AccountCardRepository accountCardRepository;
    private final ChequingAccountService chequingAccountService;

    @Autowired
    public AccountCardService(AccountCardRepository accountCardRepository, ChequingAccountService chequingAccountService) {
        this.accountCardRepository = accountCardRepository;
        this.chequingAccountService = chequingAccountService;
    }

    public AccountCard create(Long chequingAccountId, AccountCard accountCard){
        ChequingAccount chequingAccount = chequingAccountService.getChequingAccount(chequingAccountId);
        AccountCard createdCard = accountCardRepository.save(accountCard);
        chequingAccount.addAccountCard(createdCard);
        createdCard.setChequingAccount(chequingAccount);
        return createdCard;
    }

    public List<AccountCard> getAccountCards(){
        return StreamSupport
                .stream(accountCardRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    public AccountCard getAccountCard(Long id){
        return accountCardRepository.findById(id).orElseThrow(() ->
                new AccountCardNotFoundException(id));
    }

    public AccountCard delete(Long id){
        AccountCard accountCard = getAccountCard(id);
        accountCardRepository.delete(accountCard);
        return accountCard;
    }

    @Transactional
    public ChequingAccount changeAmount(Long accountCardId, Double amount){
        //Amount değeri azalatacaksa "-" arttıracaksa "+" gelmelidir.
        AccountCard accountCard = getAccountCard(accountCardId);
        synchronized (this){
            if(accountCard.getChequingAccount().getAmount() + amount >= 0){
                accountCard.getChequingAccount().setAmount(accountCard.getChequingAccount().getAmount() + amount);
            }else{
                throw new InsufficientBalanceException(accountCardId);
            }
        }
        return accountCard.getChequingAccount();
    }

}
