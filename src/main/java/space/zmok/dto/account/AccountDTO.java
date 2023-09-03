package space.zmok.dto.account;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import space.zmok.dto.bank.BankDTO;
import space.zmok.dto.transaction.TransactionDTO;
import space.zmok.dto.user.UserDTO;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Data
public class AccountDTO {

    private UUID id;
    private String accountNumber;
    private BigDecimal amount;
    private String currency;
    private UserDTO owner;
    private BankDTO bank;
    private LocalDateTime createdAt;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<TransactionDTO> transactions;

}
