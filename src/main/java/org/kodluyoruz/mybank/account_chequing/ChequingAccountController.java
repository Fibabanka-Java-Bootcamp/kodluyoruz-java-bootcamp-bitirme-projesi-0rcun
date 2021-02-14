package org.kodluyoruz.mybank.account_chequing;

import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;


import javax.validation.Valid;
import java.util.UUID;

@Validated
@RestController
@RequestMapping("/api/chequingAccount")
public class ChequingAccountController {

    private final ChequingAccountService chequingAccountService;

    public ChequingAccountController(ChequingAccountService chequingAccountService) {
        this.chequingAccountService = chequingAccountService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ChequingAccountDTO create(@Valid @RequestBody ChequingAccountDTO chequingAccountDTO) {
        return chequingAccountService.create(chequingAccountDTO.toChequingAccount()).toChequingAccountDTO();
    }

    @DeleteMapping
    @ResponseStatus
    public void delete(@RequestBody UUID iban){
        ChequingAccount chequingAccount = chequingAccountService.getChequingAccount(iban)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Account not found with iban : " + iban));
        chequingAccountService.delete(chequingAccount);
    }

    @PutMapping
    @ResponseStatus
    public ChequingAccountDTO update(@Valid @RequestBody ChequingAccountDTO chequingAccountDTO){
        return chequingAccountService.update(chequingAccountDTO.toChequingAccount()).toChequingAccountDTO();
    }

    @PutMapping
    @ResponseStatus
    public ChequingAccountDTO changeAmount(@Valid @RequestBody ChequingAccountDTO chequingAccountDTO){
        return chequingAccountService.update(chequingAccountDTO.toChequingAccount()).toChequingAccountDTO();
    }
}

/*
    @PutMapping
    @ResponseStatus
    public ClientDto update(@Valid @RequestBody ClientDto clientDto){
        return clientService.update(clientDto.toClient()).toClientDto();
    }

    //TODO: It should be deleted after the presentation
    @GetMapping
    @ResponseStatus
    public List<ClientDto> list(){
        return clientService.list().stream()
                .map(Client::toClientDto)
                .collect(Collectors.toList());
    }


}
*/
