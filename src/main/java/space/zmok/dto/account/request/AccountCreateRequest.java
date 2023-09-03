package space.zmok.dto.account.request;

import lombok.Data;
import space.zmok.dto.bank.BankDTO;
import space.zmok.dto.user.UserDTO;

@Data
public class AccountCreateRequest {

    private String currency;
    private UserDTO owner;
    private BankDTO bank;

}
