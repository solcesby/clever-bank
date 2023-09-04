package space.zmok.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import space.zmok.dto.account.AccountDTO;
import space.zmok.dto.account.request.AccountCreateRequest;
import space.zmok.entity.account.AccountEntity;

@Mapper
public interface AccountMapper {

    AccountMapper ACCOUNT_MAPPER = Mappers.getMapper(AccountMapper.class);

    AccountDTO toDto(AccountEntity entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "accountNumber", ignore = true)
    @Mapping(target = "amount", ignore = true)
    AccountEntity toEntity(AccountCreateRequest createRequest);

    AccountEntity toEntity(AccountDTO dto);

}
