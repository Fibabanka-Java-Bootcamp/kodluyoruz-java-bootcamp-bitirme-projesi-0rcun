package org.kodluyoruz.mybank.client;

import org.kodluyoruz.mybank.exception.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/clients")
public class ClientController {

    private final ClientService clientService;

    @Autowired
    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }


    @PostMapping
    public ResponseEntity<ClientDto> createClient(@RequestBody final ClientDto clientDto){
        Client client = clientService.create(Client.from(clientDto));
        return new ResponseEntity<>(ClientDto.from(client), HttpStatus.OK);
    }

    @DeleteMapping(value = "{clientId}")
    public ResponseEntity<ClientDto> delete(@PathVariable final Long clientId){
        Client client;
        try{
            client = clientService.delete(clientId);
        }catch (ClientNotFoundException e){
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        }catch (OperationNotPermittedException e){
            return new ResponseEntity(e.getMessage(), HttpStatus.METHOD_NOT_ALLOWED);
        }
        return new ResponseEntity<>(ClientDto.from(client), HttpStatus.OK);
    }

    @PutMapping(value = "{clientId}")
    public ResponseEntity<ClientDto> updateClient(@PathVariable final Long clientId,
                                            @RequestBody final ClientDto clientDto){
        Client client;
        try{
            client = clientService.update(clientId, Client.from(clientDto));
        }catch (ClientNotFoundException e){
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(ClientDto.from(client), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<ClientDto>> getClients(){
        List<Client> list = clientService.list();
        List<ClientDto> clientDtos = list.stream().map(ClientDto::from).collect(Collectors.toList());
        return new ResponseEntity<>(clientDtos, HttpStatus.OK);
    }



}
