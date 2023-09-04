package space.zmok.entity.bank.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum BankName {

    ABSOLUTBANK("Абсолют Банк"),
    ALFABANK("Альфа-Банк"),
    BELAGROPROMBANK("Белагропромбанк"),
    BELARUSBANK("Беларусбанк"),
    BELINVESTBANK("Белинвестбанк"),
    BELVEBBANK("Банк БелВЭБ"),
    CLEVERBANK("Клевер Банк"),
    DABRABYTBANK("Банк Дабрабыт"),
    PRIORBANK("Приорбанк"),
    TECHNOBANK("Технобанк");


    private final String bankName;

}
