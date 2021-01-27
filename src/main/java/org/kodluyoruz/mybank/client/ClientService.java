package org.kodluyoruz.mybank.client;

import org.kodluyoruz.mybank.card_credit.CreditCard;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ClientService {

    private final ClientRepository clientRepository;

    public ClientService(ClientRepository clientRepository){
        this.clientRepository = clientRepository;
    }

    public Client create(Client client){
        return clientRepository.save(client);
    }

    public void delete(Client client){
        clientRepository.delete(client);
    }

    public Client update(Client client){
        clientRepository.deleteById(client.getId());
        return clientRepository.save(client);
    }

    public Optional<Client> getClient(UUID id){
        return clientRepository.findById(id);
    }

    public List<Client> list() {
        return clientRepository.findAll();
    }
}
