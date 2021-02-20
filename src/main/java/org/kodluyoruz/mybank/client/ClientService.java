package org.kodluyoruz.mybank.client;

import org.kodluyoruz.mybank.exception.ClientNotFoundException;
import org.kodluyoruz.mybank.exception.OperationNotPermittedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class ClientService {

    private final ClientRepository clientRepository;

    @Autowired
    public ClientService(ClientRepository clientRepository){
        this.clientRepository = clientRepository;
    }

    //Client CRUD
    public Client create(Client client){
        return clientRepository.save(client);
    }

    public List<Client> list() {
        return StreamSupport.stream(clientRepository.findAll().spliterator(),false).collect(Collectors.toList());
    }

    public Client getClient(Long id){
        return clientRepository.findById(id).orElseThrow(() ->
                new ClientNotFoundException(id));
    }

    public Client delete(Long id){
        Client client = getClient(id);
        client.getInvestmentAccounts().
                forEach((investmentAccount -> {
                    if(investmentAccount.getAmount()>0){
                        throw new OperationNotPermittedException("Investment account have assets.");
                    }
                }));
        client.getChequingAccounts().forEach((chequingAccount -> {
            if(chequingAccount.getAmount()>0){
                throw new OperationNotPermittedException("Chequing account have assets.");
            }
        }));
        client.getCreditCards().forEach((creditCard -> {
            if(creditCard.getCreditAmount()>0){
                throw new OperationNotPermittedException("Client have credit debt");
            }
        }));
        clientRepository.delete(client);
        return client;
    }
    @Transactional
    public Client update(Long id, Client client){
        Client clientToEdit = getClient(id);
        clientToEdit.setName(client.getName());
        return clientToEdit;
    }


}
