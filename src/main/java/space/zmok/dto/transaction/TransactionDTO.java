package space.zmok.dto.transaction;

import lombok.Data;
import space.zmok.dto.account.AccountDTO;
import space.zmok.dto.bank.BankDTO;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class TransactionDTO {

    private UUID id;
    private String type;
    private BankDTO receiverBank;
    private BankDTO senderBank;
    private AccountDTO senderAccount;
    private AccountDTO receiverAccount;
    private String description;
    private BigDecimal amount;
    private String currency;
    private LocalDateTime createdAt;

}
