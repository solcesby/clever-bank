package space.zmok.entity;

import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountEntity {

    private UUID id;
    private BigDecimal amount;
    private String currency;
    private UserEntity owner;

}
