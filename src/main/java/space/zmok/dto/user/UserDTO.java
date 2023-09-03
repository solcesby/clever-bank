package space.zmok.dto.user;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import space.zmok.dto.account.AccountDTO;

import java.util.Set;
import java.util.UUID;

@Data
public class UserDTO {

    private UUID id;
    private String firstName;
    private String lastName;
    private String login;
    private String password;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<AccountDTO> accounts;

}
