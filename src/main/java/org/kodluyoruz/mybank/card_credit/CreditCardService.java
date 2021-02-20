package org.kodluyoruz.mybank.card_credit;

import org.kodluyoruz.mybank.client.Client;
import org.kodluyoruz.mybank.client.ClientService;
import org.kodluyoruz.mybank.exception.CreditCardNotFoundException;
import org.kodluyoruz.mybank.exception.InsufficientBalanceException;
import org.kodluyoruz.mybank.exception.OperationNotPermittedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


@Service
public class CreditCardService {
    private final CreditCardRepository creditCardRepository;
    private final ClientService clientService;

    @Autowired
    public CreditCardService(CreditCardRepository creditCardRepository, ClientService clientService) {
        this.creditCardRepository = creditCardRepository;
        this.clientService = clientService;
    }

    public CreditCard create(Long clientId, CreditCard creditCard){
        Client client = clientService.getClient(clientId);
        CreditCard createdCreditCard = creditCardRepository.save(creditCard);
        client.addCreditCard(createdCreditCard);
        createdCreditCard.setClient(client);
        return createdCreditCard;
    }

    public List<CreditCard> getCreditCards(){
        return StreamSupport
                .stream(creditCardRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    public CreditCard getCreditCard(Long id){
        return creditCardRepository.findById(id).orElseThrow(() ->
                new CreditCardNotFoundException(id));
    }

    public CreditCard delete(Long id){
        CreditCard creditCard = getCreditCard(id);
        if(creditCard.getCreditAmount() >0){
            throw new OperationNotPermittedException("Client have credit debt");
        }
        creditCardRepository.delete(creditCard);
        return creditCard;
    }

    @Transactional
    public CreditCard changeAmount(Long cardId, Double amount){
        // amount değeri negatif ise borç yatırılmaktadır.
        // amount değeri pozitif ise para harcanmaktadır.
        CreditCard creditCard = getCreditCard(cardId);
        synchronized (this){
            if(creditCard.getCreditAmount() + amount <= creditCard.getCreditLimit()){
                creditCard.setCreditAmount(creditCard.getCreditAmount() + amount);
            }else{
                throw new InsufficientBalanceException(cardId);
            }
        }
        return creditCard;
    }

    @Transactional
    public CreditCard changeCreditLimit(Long cardId, Double limit){
        CreditCard creditCard = getCreditCard(cardId);
        creditCard.setCreditLimit(limit);
        return creditCard;
    }
}
