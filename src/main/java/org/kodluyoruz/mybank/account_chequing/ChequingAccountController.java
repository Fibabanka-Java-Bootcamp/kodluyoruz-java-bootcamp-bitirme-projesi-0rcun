package org.kodluyoruz.mybank.account_chequing;

import org.kodluyoruz.mybank.exception.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/chequing")
public class ChequingAccountController {

    private final ChequingAccountService chequingAccountService;

    @Autowired
    public ChequingAccountController(ChequingAccountService chequingAccountService) {
        this.chequingAccountService = chequingAccountService;
    }

    @PostMapping(value = "{clientId}")
    public ResponseEntity<ChequingAccountDto> createChequingAccount(@PathVariable final Long clientId,
                                                                    @RequestBody final ChequingAccountDto chequingAccountDto){
        ChequingAccount chequingAccount;
        try{
            chequingAccount = chequingAccountService.create(clientId, ChequingAccount.from(chequingAccountDto));
        }catch (ClientNotFoundException e){
            return new ResponseEntity(e.getMessage(),HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(ChequingAccountDto.from(chequingAccount), HttpStatus.OK);
    }

    @DeleteMapping(value = "{accountId}")
    public ResponseEntity<ChequingAccountDto> delete(@PathVariable final Long accountId){
        ChequingAccount chequingAccount;
        try{
            chequingAccount = chequingAccountService.delete(accountId);
        }catch (ChequingAccountNotFoundException e){
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        }catch (OperationNotPermittedException e){
            return new ResponseEntity(e.getMessage(), HttpStatus.METHOD_NOT_ALLOWED);
        }
        return new ResponseEntity<>(ChequingAccountDto.from(chequingAccount), HttpStatus.OK);
    }


    @PutMapping(value = "{accountId}/chequing/{chequingId}/{amount}")
    public ResponseEntity<ChequingAccountDto> sendMoneyToChequing(@PathVariable final Long accountId,
                                                                  @PathVariable final Long chequingId,
                                                                  @PathVariable final Double amount){

        ChequingAccount chequingAccount;
        try{
            chequingAccount =chequingAccountService.sendMoneyToChequing(accountId,chequingId,amount);
        }catch (ChequingAccountNotFoundException e){
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        }catch (InsufficientBalanceException e){
            return new ResponseEntity(e.getMessage(), HttpStatus.METHOD_NOT_ALLOWED);
        }

        return new ResponseEntity<>(ChequingAccountDto.from(chequingAccount), HttpStatus.OK);
    }

    @PutMapping(value = "{accountId}/investment/{investmentId}/{amount}")
    public ResponseEntity<ChequingAccountDto> sendMoneyToInvestment(@PathVariable final Long accountId,
                                                                  @PathVariable final Long investmentId,
                                                                  @PathVariable final Double amount){
        ChequingAccount chequingAccount;
        try{
            chequingAccount =chequingAccountService.sendMoneyToInvestment(accountId,investmentId,amount);
        }catch (ChequingAccountNotFoundException | InvestmentAccountNotFoundException e){
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (InsufficientBalanceException e){
            return new ResponseEntity(e.getMessage(), HttpStatus.METHOD_NOT_ALLOWED);
        }

        return new ResponseEntity<>(ChequingAccountDto.from(chequingAccount), HttpStatus.OK);
    }

    @PutMapping(value = "{accountId}/creditCard/{creditCardId}/{amount}")
    public ResponseEntity<ChequingAccountDto> sendMoneyToCreditCard(@PathVariable final Long accountId,
                                                                    @PathVariable final Long creditCardId,
                                                                    @PathVariable final Double amount){
        ChequingAccount chequingAccount;
        try{
            chequingAccount =chequingAccountService.sendMoneyToInvestment(accountId,creditCardId,amount);
        }catch (ChequingAccountNotFoundException | CreditCardNotFoundException e){
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (InsufficientBalanceException e){
            return new ResponseEntity(e.getMessage(), HttpStatus.METHOD_NOT_ALLOWED);
        }
        return new ResponseEntity<>(ChequingAccountDto.from(chequingAccount), HttpStatus.OK);
    }

}
