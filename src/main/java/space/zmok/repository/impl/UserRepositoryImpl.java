package space.zmok.repository.impl;

import space.zmok.entity.user.UserEntity;
import space.zmok.repository.UserRepository;
import space.zmok.util.ConnectionUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class UserRepositoryImpl implements UserRepository {

    private static final String UPSERT_USER = """
            INSERT INTO "user" (user_id, first_name, last_name, login, password, created_at, updated_at, deleted_at)
            VALUES (?,?,?,?,?,?,?,?)
            ON CONFLICT (user_id) DO UPDATE
            SET first_name = excluded.first_name,
                last_name = excluded.last_name,
                login = excluded.login,
                password = excluded.password,
                created_at = excluded.created_at,
                updated_at = excluded.updated_at,
                deleted_at = excluded.deleted_at
            """;
    private static final String SELECT_USER_BY_USER_ID = """
            SELECT * FROM "user"
            WHERE user_id::text=?
            """;
    private static final String SELECT_USER_BY_LOGIN = """
            SELECT * FROM "user"
            WHERE login=?
            """;
    private static final String SELECT_ALL_USERS = """
            SELECT * FROM "user"
            """;

    @Override
    public UserEntity save(UserEntity entity) {
        try (Connection conn = ConnectionUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(UPSERT_USER)) {

            pstmt.setString(1, String.valueOf(entity.getId()));
            pstmt.setString(2, String.valueOf(entity.getFirstName()));
            pstmt.setString(3, String.valueOf(entity.getLastName()));
            pstmt.setString(4, String.valueOf(entity.getLogin()));
            pstmt.setString(5, String.valueOf(entity.getPassword()));
            pstmt.setString(6, String.valueOf(entity.getCreatedAt()));
            pstmt.setString(7, String.valueOf(entity.getUpdatedAt()));
            pstmt.setString(8, String.valueOf(entity.getDeletedAt()));

            if (pstmt.executeUpdate() != 0) {
                return entity;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }


    @Override
    public UserEntity findById(UUID entityId) {
        try (Connection conn = ConnectionUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SELECT_USER_BY_USER_ID)) {

            pstmt.setString(1, String.valueOf(entityId));

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return buildUserEntityFromResultSet(rs);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public List<UserEntity> findAll() {
        List<UserEntity> foundUsers = new ArrayList<>();

        try (Connection conn = ConnectionUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SELECT_ALL_USERS);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                foundUsers.add(buildUserEntityFromResultSet(rs));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return foundUsers;
    }

    @Override
    public UserEntity findByLogin(String login) {
        try (Connection conn = ConnectionUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SELECT_USER_BY_LOGIN)) {

            pstmt.setString(1, String.valueOf(login));

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return buildUserEntityFromResultSet(rs);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    private UserEntity buildUserEntityFromResultSet(ResultSet rs) throws SQLException {
        return UserEntity.builder()
                .id((UUID) rs.getObject("user_id"))
                .firstName(rs.getString("first_name"))
                .lastName(rs.getString("last_name"))
                .login(rs.getString("login"))
                .password(rs.getString("password"))
                .createdAt((LocalDateTime) rs.getObject("created_at"))
                .updatedAt((LocalDateTime) rs.getObject("updated_at"))
                .deletedAt((LocalDateTime) rs.getObject("deleted_at"))
                .build();
    }
}
