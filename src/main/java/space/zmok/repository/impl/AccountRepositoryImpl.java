package space.zmok.repository.impl;

import space.zmok.entity.AccountEntity;
import space.zmok.entity.UserEntity;
import space.zmok.repository.AccountRepository;
import space.zmok.util.ConnectionUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class AccountRepositoryImpl implements AccountRepository {

    private static final String UPSERT_ACCOUNT = """
            INSERT INTO account (account_id, amount, currency, user_id)
            VALUES (?,?,?,?)
            ON CONFLICT (account_id) DO UPDATE
            SET amount = excluded.amount,
                currency = excluded.currency,
                user_id = excluded.user_id;
            """;
    private static final String SELECT_ACCOUNT_BY_ACCOUNT_ID = """
            SELECT * FROM account
            WHERE account_id::text=?
            """;
    private static final String SELECT_ALL_ACCOUNTS = """
            SELECT * FROM account
            """;

    @Override
    public AccountEntity save(AccountEntity accountEntity) {
        try (Connection conn = ConnectionUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(UPSERT_ACCOUNT)) {

            pstmt.setString(1, String.valueOf(accountEntity.getId()));
            pstmt.setString(2, String.valueOf(accountEntity.getAmount()));
            pstmt.setString(3, String.valueOf(accountEntity.getCurrency()));
            pstmt.setString(4, String.valueOf(accountEntity.getOwner().getId()));

            if (pstmt.executeUpdate() != 0) {
                return accountEntity;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public AccountEntity findById(UUID accountId) {
        try (Connection conn = ConnectionUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SELECT_ACCOUNT_BY_ACCOUNT_ID)) {

            pstmt.setString(1, String.valueOf(accountId));

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return buildAccountEntityFromResultSet(rs);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public List<AccountEntity> findAll() {
        List<AccountEntity> foundAccounts = new ArrayList<>();

        try (Connection conn = ConnectionUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SELECT_ALL_ACCOUNTS);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                foundAccounts.add(buildAccountEntityFromResultSet(rs));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return foundAccounts;
    }

    private AccountEntity buildAccountEntityFromResultSet(ResultSet rs) throws SQLException {
        return AccountEntity.builder()
                .id((UUID) rs.getObject("account_id"))
                .amount(rs.getBigDecimal("amount"))
                .currency(rs.getString("currency"))
                .owner(UserEntity.builder()
                        .id((UUID) rs.getObject("user_id"))
                        .build())
                .build();
    }

}
