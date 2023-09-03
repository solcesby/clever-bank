package space.zmok.entity.bank.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum BankCountryCode {

    BELARUS("BY"),
    GERMANY("DE"),
    RUSSIA("RU");

    private final String code;
}