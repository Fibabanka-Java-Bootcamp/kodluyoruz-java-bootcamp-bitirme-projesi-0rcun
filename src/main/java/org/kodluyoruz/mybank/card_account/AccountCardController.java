package org.kodluyoruz.mybank.card_account;


import org.kodluyoruz.mybank.account_chequing.ChequingAccount;
import org.kodluyoruz.mybank.account_chequing.ChequingAccountDto;
import org.kodluyoruz.mybank.exception.AccountCardNotFoundException;
import org.kodluyoruz.mybank.exception.ChequingAccountNotFoundException;
import org.kodluyoruz.mybank.exception.InsufficientBalanceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/card_account")
public class AccountCardController {

    private final AccountCardService accountCardService;

    @Autowired
    public AccountCardController(AccountCardService accountCardService) {
        this.accountCardService = accountCardService;
    }

    @PostMapping(value = "{accountId}")
    public ResponseEntity<AccountCardDto> create(@PathVariable final Long accountId,
                                                 @RequestBody final AccountCardDto accountCardDto){
        AccountCard accountCard ;
        try{
            accountCard = accountCardService.create(accountId, AccountCard.from(accountCardDto));
        }catch (ChequingAccountNotFoundException e){
            return new ResponseEntity(e.getMessage(),HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(AccountCardDto.from(accountCard), HttpStatus.OK);
    }

    @DeleteMapping(value = "{cardId}")
    public ResponseEntity<AccountCardDto> delete(@PathVariable final Long cardId){
        AccountCard accountCard;
        try{
            accountCard = accountCardService.delete(cardId);
        }catch (AccountCardNotFoundException e){
            return new ResponseEntity(e.getMessage(),HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(AccountCardDto.from(accountCard), HttpStatus.OK);
    }

    @PutMapping(value = "{cardId}/{amount}")
    public ResponseEntity<ChequingAccountDto> moneyExchange(@PathVariable final Long cardId,
                                                       @PathVariable final Double amount){
        ChequingAccount chequingAccount;
        try{
            chequingAccount = accountCardService.changeAmount(cardId, amount);
        }catch (AccountCardNotFoundException e){
            return new ResponseEntity(e.getMessage(),HttpStatus.NOT_FOUND);
        }catch (InsufficientBalanceException e){
            return new ResponseEntity(e.getMessage(),HttpStatus.METHOD_NOT_ALLOWED);
        }
        return new ResponseEntity<>(ChequingAccountDto.from(chequingAccount), HttpStatus.OK);
    }
}
