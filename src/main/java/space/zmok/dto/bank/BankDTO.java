package space.zmok.dto.bank;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import space.zmok.dto.account.AccountDTO;
import space.zmok.dto.user.UserDTO;

import java.util.Set;
import java.util.UUID;

@Data
public class BankDTO {

    private UUID id;
    private String name;
    private String identifierCode;
    private String countryCode;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<UserDTO> users;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<AccountDTO> accounts;

}
