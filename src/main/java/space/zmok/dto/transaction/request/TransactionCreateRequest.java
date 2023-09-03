package space.zmok.dto.transaction.request;

import lombok.Data;
import space.zmok.dto.account.AccountDTO;
import space.zmok.dto.bank.BankDTO;

import java.math.BigDecimal;

@Data
public class TransactionCreateRequest {

    private String type;
    private BigDecimal amount;
    private String currency;
    private String description;
    private BankDTO receiverBank;
    private BankDTO senderBank;
    private AccountDTO senderAccount;
    private AccountDTO receiverAccount;

}
