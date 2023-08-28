package space.zmok.repository;

import space.zmok.entity.AccountEntity;

import java.util.UUID;

public interface AccountRepository {

    AccountEntity save(AccountEntity accountEntity);

    AccountEntity findById(UUID accountId);

}
