package org.kodluyoruz.mybank.client;

import lombok.Data;

@Data
public class PlainClientDto {
    private Long id;
    private String name;

    public static PlainClientDto from(Client client){
        PlainClientDto plainClientDto = new PlainClientDto();
        plainClientDto.setId(client.getId());
        plainClientDto.setName(client.getName());
        return plainClientDto;
    }
}
