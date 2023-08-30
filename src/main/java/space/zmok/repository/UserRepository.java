package space.zmok.repository;

import space.zmok.entity.user.UserEntity;

import java.util.UUID;

public interface UserRepository extends BaseRepository<UserEntity, UUID> {

    UserEntity findByLogin (String login);

}
