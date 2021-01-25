package org.kodluyoruz.mybank.client;

import org.kodluyoruz.mybank.card_credit.CreditCard;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface ClientRepository extends CrudRepository<Client, UUID> {

}
