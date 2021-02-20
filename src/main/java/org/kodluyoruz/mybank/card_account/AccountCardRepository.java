package org.kodluyoruz.mybank.card_account;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountCardRepository extends CrudRepository<AccountCard, Long> {
}
