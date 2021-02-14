package org.kodluyoruz.mybank.account_chequing;

import org.kodluyoruz.mybank.client.Client;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ChequingAccountService {

    private final ChequingAccountRepository chequingAccountRepository;


    public ChequingAccountService(ChequingAccountRepository chequingAccountRepository) {
        this.chequingAccountRepository = chequingAccountRepository;
    }

    //ChequingAccount CRUD
    public ChequingAccount create(ChequingAccount chequingAccount){
        return chequingAccountRepository.save(chequingAccount);
    }

    public void delete(ChequingAccount chequingAccount){
        chequingAccountRepository.delete(chequingAccount);
    }

    public ChequingAccount update(ChequingAccount chequingAccount){
        chequingAccountRepository.deleteById(chequingAccount.getIban());
        return chequingAccountRepository.save(chequingAccount);
    }

    public Optional<ChequingAccount> getChequingAccount(UUID iban){
        return chequingAccountRepository.findById(iban);
    }

    public List<ChequingAccount> list() {
        return chequingAccountRepository.findAll();
    }

    public void changeAmount(double amount, UUID iban){
        Optional<ChequingAccount> chequingAccount = getChequingAccount(iban);
        if(chequingAccount.isPresent()){
            chequingAccount.get().setAmount(chequingAccount.get().getAmount()+amount);
        }else {
            throw HttpClientErrorException.create(HttpStatus.NOT_FOUND,"Account not Found", HttpHeaders.EMPTY,null,null);
        }
    }
}
