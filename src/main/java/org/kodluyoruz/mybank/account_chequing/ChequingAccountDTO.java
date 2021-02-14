package org.kodluyoruz.mybank.account_chequing;

import lombok.*;
import org.kodluyoruz.mybank.client.Client;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import java.util.UUID;


@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChequingAccountDTO {

    private UUID iban;
    private Double amount;

    public ChequingAccount toChequingAccount(){
        return ChequingAccount.builder()
                .iban(this.iban)
                .amount(this.amount)
                .build();
    }

}