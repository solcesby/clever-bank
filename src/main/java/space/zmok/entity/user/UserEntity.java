package space.zmok.entity.user;

import lombok.*;
import lombok.experimental.SuperBuilder;
import space.zmok.entity.AbstractBaseEntity;
import space.zmok.entity.account.AccountEntity;
import space.zmok.entity.transaction.TransactionEntity;

import java.util.Set;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class UserEntity extends AbstractBaseEntity {

    private String firstName;

    private String lastName;

    private String login;

    private String password;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<AccountEntity> accounts;

}
