package space.zmok.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import space.zmok.dto.user.UserDTO;
import space.zmok.dto.user.request.UserCreateRequest;
import space.zmok.entity.user.UserEntity;

@Mapper
public interface UserMapper {

    UserMapper USER_MAPPER = Mappers.getMapper(UserMapper.class);

    UserDTO toDto(UserEntity entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "accounts", ignore = true)
    UserEntity toEntity(UserCreateRequest userCreateRequest);

    UserEntity toEntity(UserDTO dto);

}
