package org.kodluyoruz.mybank.account_investment;

import org.kodluyoruz.mybank.exception.ClientNotFoundException;
import org.kodluyoruz.mybank.exception.InsufficientBalanceException;
import org.kodluyoruz.mybank.exception.InvestmentAccountNotFoundException;
import org.kodluyoruz.mybank.exception.OperationNotPermittedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/investments")
public class InvestmentAccountController {

    private final InvestmentAccountService investmentAccountService;

    @Autowired
    public InvestmentAccountController(InvestmentAccountService investmentAccountService) {
        this.investmentAccountService = investmentAccountService;
    }


    @PostMapping(value = "{clientId}")
    public ResponseEntity<InvestmentAccountDto> createInvestmentAccount(@PathVariable final Long clientId,
                                                                        @RequestBody final InvestmentAccountDto investmentAccountDto){
        InvestmentAccount investmentAccount;
        try{
            investmentAccount = investmentAccountService.create(clientId, InvestmentAccount.from(investmentAccountDto));
        }catch (ClientNotFoundException e){
            return new ResponseEntity(e.getMessage(),HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(InvestmentAccountDto.from(investmentAccount), HttpStatus.OK);
    }


    @DeleteMapping(value = "{accountId}")
    public ResponseEntity<InvestmentAccountDto> delete(@PathVariable final Long accountId){
        InvestmentAccount investmentAccount;
        try{
            investmentAccount = investmentAccountService.delete(accountId);
        }catch (InvestmentAccountNotFoundException e){
            return new ResponseEntity(e.getMessage(),HttpStatus.NOT_FOUND);
        }catch (OperationNotPermittedException e){
            return new ResponseEntity(e.getMessage(),HttpStatus.METHOD_NOT_ALLOWED);
        }
        return new ResponseEntity<>(InvestmentAccountDto.from(investmentAccount), HttpStatus.OK);
    }

    @PutMapping(value = "{accountId}/chequing/{chequingId}/{amount}")
    public ResponseEntity<InvestmentAccountDto> sendMoney(@PathVariable final Long accountId,
                                                          @PathVariable final Long chequingId,
                                                          @PathVariable final Double amount){
        InvestmentAccount investmentAccount;
        try{
            investmentAccount = investmentAccountService.sendMoney(accountId,chequingId,amount);
        }catch (InvestmentAccountNotFoundException e){
            return new ResponseEntity(e.getMessage(),HttpStatus.NOT_FOUND);
        }catch (OperationNotPermittedException | InsufficientBalanceException e){
            return new ResponseEntity(e.getMessage(),HttpStatus.METHOD_NOT_ALLOWED);
        }

        return new ResponseEntity<>(InvestmentAccountDto.from(investmentAccount), HttpStatus.OK);
    }
}
