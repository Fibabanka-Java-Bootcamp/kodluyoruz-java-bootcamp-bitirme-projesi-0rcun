package org.kodluyoruz.mybank.account_investment;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvestmentAccountRepository extends CrudRepository<InvestmentAccount, Long> {
}
