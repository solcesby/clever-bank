package space.zmok.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import space.zmok.dto.transaction.TransactionDTO;
import space.zmok.dto.transaction.request.TransactionCreateRequest;
import space.zmok.entity.transaction.TransactionEntity;

@Mapper
public interface TransactionMapper {

    TransactionMapper TRANSACTION_MAPPER = Mappers.getMapper(TransactionMapper.class);

    TransactionDTO toDto(TransactionEntity entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    TransactionEntity toEntity(TransactionCreateRequest createRequest);

    TransactionEntity toEntity(TransactionDTO dto);

}
