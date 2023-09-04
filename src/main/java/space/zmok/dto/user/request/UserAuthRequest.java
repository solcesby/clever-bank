package space.zmok.dto.user.request;

import lombok.Data;

@Data
public class UserAuthRequest {

    private String login;
    private String password;

}
