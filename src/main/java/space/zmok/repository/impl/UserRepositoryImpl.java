package space.zmok.repository.impl;

import space.zmok.entity.user.UserEntity;
import space.zmok.repository.UserRepository;
import space.zmok.util.ConnectionUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class UserRepositoryImpl implements UserRepository {

    private static final String UPSERT_USER = """
            INSERT INTO "user" (user_id, first_name, last_name, login, password)
            VALUES (?,?,?,?,?)
            ON CONFLICT (user_id) DO UPDATE
            SET first_name = excluded.first_name,
                last_name = excluded.last_name,
                login = excluded.login,
                password = excluded.password
            """;
    private static final String SELECT_USER_BY_USER_ID = """
            SELECT * FROM "user"
            WHERE user_id::text=?
            """;
    private static final String SELECT_USER_BY_LOGIN = """
            SELECT * FROM "user"
            WHERE login::text=?
            """;
    private static final String SELECT_ALL_USERS = """
            SELECT * FROM "user"
            """;

    @Override
    public UserEntity save(UserEntity userEntity) {
        try (Connection conn = ConnectionUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(UPSERT_USER)) {

            pstmt.setString(1, String.valueOf(userEntity.getId()));
            pstmt.setString(2, String.valueOf(userEntity.getFirstName()));
            pstmt.setString(3, String.valueOf(userEntity.getLastName()));
            pstmt.setString(4, String.valueOf(userEntity.getLogin()));
            pstmt.setString(5, String.valueOf(userEntity.getPassword()));

            if (pstmt.executeUpdate() != 0) {
                return userEntity;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }


    @Override
    public UserEntity findById(UUID userId) {
        try (Connection conn = ConnectionUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SELECT_USER_BY_USER_ID)) {

            pstmt.setString(1, String.valueOf(userId));

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
                .build();
    }
}
