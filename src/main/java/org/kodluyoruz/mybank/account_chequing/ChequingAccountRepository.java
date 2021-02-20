package org.kodluyoruz.mybank.account_chequing;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ChequingAccountRepository extends CrudRepository<ChequingAccount, Long> {
}
