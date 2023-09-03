package space.zmok.entity.account;

import lombok.*;
import lombok.experimental.SuperBuilder;
import space.zmok.entity.AbstractBaseEntity;
import space.zmok.entity.bank.BankEntity;
import space.zmok.entity.transaction.TransactionEntity;
import space.zmok.entity.user.UserEntity;

import java.math.BigDecimal;
import java.util.Set;

@Data
@SuperBuilder()
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class AccountEntity extends AbstractBaseEntity {

    private String accountNumber;
    private BigDecimal amount;
    private String currency;
    private UserEntity owner;
    private BankEntity bank;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<TransactionEntity> transactions;

}
