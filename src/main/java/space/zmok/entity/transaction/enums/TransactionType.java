package space.zmok.entity.transaction.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TransactionType {

    TOP_UP("Top up"),
    WITHDRAW("Withdraw"),
    TRANSFER("Transfer");

    private final String type;

}
