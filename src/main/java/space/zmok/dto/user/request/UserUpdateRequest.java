package space.zmok.dto.user.request;

import lombok.Data;

import java.util.UUID;

@Data
public class UserUpdateRequest {

    private UUID id;
    private String firstName;
    private String lastName;
    private String login;
    private String password;

}
