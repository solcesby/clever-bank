package space.zmok.dto.account.request;

import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class AccountUpdateRequest {

    private UUID id;
    private BigDecimal amount;
    private String currency;

}
