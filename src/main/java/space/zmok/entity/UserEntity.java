package space.zmok.entity;

import lombok.*;

import java.util.Set;
import java.util.UUID;

@Data
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {

    private UUID id;
    private String firstName;
    private String lastName;
    private Set<AccountEntity> accounts;

}
