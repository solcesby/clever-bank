package space.zmok.entity.bank.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum BankIdentifierCode {

    ABSOLUTBANK("ABLT"),
    ALFABANK("ALFA"),
    BELAGROPROMBANK("BAPB"),
    BELARUSBANK("AKBB"),
    BELINVESTBANK("BLBB"),
    BELVEBBANK("BELB"),
    CLEVERBANK("CLVR"),
    DABRABYTBANK("MMBN"),
    PRIORBANK("PJCB"),
    TECHNOBANK("TECN");

    private final String code;
}
