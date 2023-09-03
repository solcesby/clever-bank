package space.zmok.entity.bank;

import lombok.*;
import lombok.experimental.SuperBuilder;
import space.zmok.entity.AbstractBaseEntity;
import space.zmok.entity.account.AccountEntity;
import space.zmok.entity.user.UserEntity;

import java.util.Set;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class BankEntity extends AbstractBaseEntity {

    private String name;

    private String identifierCode;

    private String countryCode;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<UserEntity> users;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<AccountEntity> accounts;

}
