package space.zmok.repository.impl;

import space.zmok.entity.AccountEntity;
import space.zmok.repository.AccountRepository;
import space.zmok.util.ConnectionUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.UUID;

public class AccountRepositoryImpl implements AccountRepository {

    private static final String SELECT_ACCOUNT_BY_ACCOUNT_ID = """
            SELECT * FROM account 
            WHERE account_id::text=?
            """;
    private static final String UPSERT_ACCOUNT = """
            INSERT INTO account (account_id, amount, currency, user_id)
            VALUES (?,?,?,?)
            ON CONFLICT (account_id) DO UPDATE
            SET amount = excluded.amount,
                currency = excluded.currency;
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
                    AccountEntity foundAccount = AccountEntity.builder()
                            .id((UUID) rs.getObject("account_id"))
                            .amount(rs.getBigDecimal("amount"))
                            .currency(rs.getString("currency"))
                            .build();
                    System.out.println(foundAccount);
                    return foundAccount;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
