package space.zmok.repository;

import space.zmok.entity.account.AccountEntity;

import java.util.List;
import java.util.UUID;

public interface AccountRepository extends BaseRepository<AccountEntity, UUID> {

    AccountEntity findByAccountNumber(String accountNumber);

    List<AccountEntity> findAllByUserId(UUID userId);

}
