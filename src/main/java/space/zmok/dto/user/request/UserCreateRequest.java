package space.zmok.dto.user.request;

import lombok.Data;

@Data
public class UserCreateRequest {

    private String firstName;
    private String lastName;
    private String login;
    private String password;

}
