package org.kodluyoruz.mybank.account_chequing;

import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

public interface ChequingAccountRepository extends CrudRepository<ChequingAccount, UUID> {

    @Override
    public List<ChequingAccount> findAll();
}
