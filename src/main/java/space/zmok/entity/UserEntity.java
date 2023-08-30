package space.zmok.entity;

import lombok.*;

import java.util.Set;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {

    private UUID id;

    private String firstName;

    private String lastName;

    private String login;

    private String password;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<AccountEntity> accounts;

}
