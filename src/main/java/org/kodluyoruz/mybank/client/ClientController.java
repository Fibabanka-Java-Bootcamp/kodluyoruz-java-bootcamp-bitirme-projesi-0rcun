package org.kodluyoruz.mybank.client;


import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Validated
@RestController
@RequestMapping("/api/clients")
public class ClientController {

    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ClientDto create(@Valid @RequestBody ClientDto clientDto){
        return clientService.create(clientDto.toClient()).toClientDto();
    }

    @DeleteMapping
    @ResponseStatus
    public void delete(@RequestBody UUID uuid){
        Client client = clientService.getClient(uuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"User not found with id : " + uuid));
        clientService.delete(client);
    }

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
