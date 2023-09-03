package space.zmok.dto.bank.request;

import lombok.Data;

import java.util.UUID;

@Data
public class BankUpdateRequest {

    private UUID id;
    private String name;
    private String identifierCode;
    private String countryCode;

}
