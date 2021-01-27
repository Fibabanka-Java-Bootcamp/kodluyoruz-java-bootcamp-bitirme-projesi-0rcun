package org.kodluyoruz.mybank.client;

import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

public interface ClientRepository extends CrudRepository<Client, UUID> {
    @Override
    public List<Client> findAll();
}
