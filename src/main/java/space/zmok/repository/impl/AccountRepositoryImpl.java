package space.zmok.repository.impl;

import space.zmok.entity.account.AccountEntity;
import space.zmok.entity.bank.BankEntity;
import space.zmok.entity.user.UserEntity;
import space.zmok.repository.AccountRepository;
import space.zmok.util.ConnectionUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class AccountRepositoryImpl implements AccountRepository {

    private static final String UPSERT_ACCOUNT = """
            INSERT INTO account (account_id, amount, currency, user_id, bank_id, created_at, updated_at, deleted_at)
            VALUES (?,?,?,?,?,?,?,?)
            ON CONFLICT (account_id) DO UPDATE
            SET amount = excluded.amount,
                currency = excluded.currency,
                user_id = excluded.user_id,
                bank_id = excluded.bank_id,
                created_at = excluded.created_at,
                updated_at = excluded.updated_at,
                deleted_at = excluded.deleted_at
            """;
    private static final String SELECT_ACCOUNT_BY_ACCOUNT_ID = """
            SELECT * FROM account
            WHERE account_id::text=?
            """;
    private static final String SELECT_ACCOUNT_BY_ACCOUNT_NUMBER = """
            SELECT * FROM account
            WHERE account_number=?
            """;
    private static final String SELECT_ALL_ACCOUNTS = """
            SELECT * FROM account
            """;
    private static final String SELECT_ALL_ACCOUNTS_BY_USER_ID = """
            SELECT * FROM account
            WHERE user_id::text=?
            """;

    @Override
    public AccountEntity save(AccountEntity entity) {
        try (Connection conn = ConnectionUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(UPSERT_ACCOUNT)) {

            pstmt.setString(1, String.valueOf(entity.getId()));
            pstmt.setString(2, String.valueOf(entity.getAmount()));
            pstmt.setString(3, String.valueOf(entity.getCurrency()));
            pstmt.setString(4, String.valueOf(entity.getUser().getId()));
            pstmt.setString(5, String.valueOf(entity.getBank().getId()));
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
    public AccountEntity findById(UUID entityId) {
        try (Connection conn = ConnectionUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SELECT_ACCOUNT_BY_ACCOUNT_ID)) {

            pstmt.setString(1, String.valueOf(entityId));

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

    @Override
    public List<AccountEntity> findAllByUserId(UUID userId) {
        List<AccountEntity> foundAccounts = new ArrayList<>();

        try (Connection conn = ConnectionUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SELECT_ALL_ACCOUNTS_BY_USER_ID)) {

            pstmt.setString(1, String.valueOf(userId));

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    foundAccounts.add(buildAccountEntityFromResultSet(rs));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return foundAccounts;
    }

    @Override
    public AccountEntity findByAccountNumber(String accountNumber) {
        try (Connection conn = ConnectionUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SELECT_ACCOUNT_BY_ACCOUNT_NUMBER)) {

            pstmt.setString(1, String.valueOf(accountNumber));

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

    private AccountEntity buildAccountEntityFromResultSet(ResultSet rs) throws SQLException {
        return AccountEntity.builder()
                .id((UUID) rs.getObject("account_id"))
                .amount(rs.getBigDecimal("amount"))
                .currency(rs.getString("currency"))
                .accountNumber(rs.getString("account_number"))
                .createdAt((LocalDateTime) rs.getObject("created_at"))
                .updatedAt((LocalDateTime) rs.getObject("updated_at"))
                .deletedAt((LocalDateTime) rs.getObject("deleted_at"))
                .user(UserEntity.builder()
                        .id((UUID) rs.getObject("user_id"))
                        .build())
                .bank(BankEntity.builder()
                        .id((UUID) rs.getObject("bank_id"))
                        .build())
                .build();
    }

}
