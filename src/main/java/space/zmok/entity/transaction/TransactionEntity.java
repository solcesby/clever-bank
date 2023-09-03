package space.zmok.entity.transaction;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import space.zmok.entity.AbstractBaseEntity;
import space.zmok.entity.account.AccountEntity;
import space.zmok.entity.bank.BankEntity;

import java.math.BigDecimal;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class TransactionEntity extends AbstractBaseEntity {

    private String type;

    private BankEntity receiverBank;

    private BankEntity senderBank;

    private AccountEntity senderAccount;

    private AccountEntity receiverAccount;

    private String description;

    private BigDecimal amount;

    private String currency;

}
