package space.zmok.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import space.zmok.dto.bank.BankDTO;
import space.zmok.dto.bank.request.BankCreateRequest;
import space.zmok.entity.bank.BankEntity;

@Mapper
public interface BankMapper {

    BankMapper BANK_MAPPER = Mappers.getMapper(BankMapper.class);

    BankDTO toDto(BankEntity entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "users", ignore = true)
    @Mapping(target = "accounts", ignore = true)
    BankEntity toEntity(BankCreateRequest createRequest);

    BankEntity toEntity(BankDTO dto);

}
