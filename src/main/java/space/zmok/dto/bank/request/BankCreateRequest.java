package space.zmok.dto.bank.request;

import lombok.Data;

@Data
public class BankCreateRequest {

    private String name;
    private String identifierCode;
    private String countryCode;

}
