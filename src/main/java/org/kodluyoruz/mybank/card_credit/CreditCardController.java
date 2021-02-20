package org.kodluyoruz.mybank.card_credit;

import org.kodluyoruz.mybank.exception.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/card_credit")
public class CreditCardController {


    private final CreditCardService creditCardService;

    @Autowired
    public CreditCardController(CreditCardService creditCardService) {
        this.creditCardService = creditCardService;
    }

    @PostMapping(value = "{clientId}")
    public ResponseEntity<CreditCardDto> create(@PathVariable final Long clientId,
                                                 @RequestBody final CreditCardDto accountCardDto){
        CreditCard creditCard;
        try{
            creditCard = creditCardService.create(clientId, CreditCard.from(accountCardDto));
        }catch (ClientNotFoundException e){
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(CreditCardDto.from(creditCard), HttpStatus.OK);
    }

    @DeleteMapping(value = "{cardId}")
    public ResponseEntity<CreditCardDto> delete(@PathVariable final Long cardId){
        CreditCard creditCard;
        try{
            creditCard = creditCardService.delete(cardId);
        }catch (CreditCardNotFoundException e){
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (OperationNotPermittedException e){
            return new ResponseEntity(e.getMessage(), HttpStatus.METHOD_NOT_ALLOWED);
        }
        return new ResponseEntity<>(CreditCardDto.from(creditCard), HttpStatus.OK);
    }

    @PutMapping(value = "{cardId}/{amount}")
    public ResponseEntity<CreditCardDto> moneyExchange(@PathVariable final Long cardId,
                                                       @PathVariable final Double amount){
        CreditCard creditCard;
        try{
            creditCard = creditCardService.changeAmount(cardId, amount);
        }catch (CreditCardNotFoundException e){
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (InsufficientBalanceException e){
            return new ResponseEntity(e.getMessage(), HttpStatus.METHOD_NOT_ALLOWED);
        }
        return new ResponseEntity<>(CreditCardDto.from(creditCard), HttpStatus.OK);
    }

    @PutMapping(value = "{cardId}/change_limit/{limit}")
    public ResponseEntity<CreditCardDto> changeLimit(@PathVariable final Long cardId,
                                                     @PathVariable final Double limit){
        CreditCard creditCard;
        try{
            creditCard = creditCardService.changeCreditLimit(cardId, limit);
        }catch (CreditCardNotFoundException e){
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(CreditCardDto.from(creditCard), HttpStatus.OK);
    }

    @GetMapping("{cardId}")
    public ResponseEntity<CreditCardDto> getCreditCard(@PathVariable final Long cardId){
        CreditCard creditCard = creditCardService.getCreditCard(cardId);
        return new ResponseEntity<>(CreditCardDto.from(creditCard), HttpStatus.OK);
    }
}
