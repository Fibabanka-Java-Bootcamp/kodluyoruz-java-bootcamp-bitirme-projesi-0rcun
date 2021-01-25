package org.kodluyoruz.mybank.client;

import lombok.*;

import java.util.UUID;
import javax.validation.constraints.NotBlank;


@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClientDto {

    private UUID id;
    @NotBlank(message = "Name for the client is mandatory")
    private String name;

    public Client toClient(){
        return Client.builder()
                .id(this.id)
                .name(this.name)
                .build();
    }
}
