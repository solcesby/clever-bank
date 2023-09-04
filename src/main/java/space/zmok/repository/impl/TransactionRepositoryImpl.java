package space.zmok.repository.impl;

import space.zmok.entity.transaction.TransactionEntity;
import space.zmok.repository.TransactionRepository;
import space.zmok.util.ConnectionUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TransactionRepositoryImpl implements TransactionRepository {
    private static final String UPSERT_TRANSACTION = """
            INSERT INTO transaction (transaction_id, type, amount, currency, description, created_at, updated_at, deleted_at)
            VALUES (?,?,?,?,?,?,?,?)
            ON CONFLICT (transaction_id) DO UPDATE
            SET type = excluded.type,
                amount = excluded.amount,
                currency = excluded.currency,
                description = excluded.description,
                created_at = excluded.created_at,
                updated_at = excluded.updated_at,
                deleted_at = excluded.deleted_at
            """;
    private static final String SELECT_TRANSACTION_BY_TRANSACTION_ID = """
            SELECT * FROM transaction
            WHERE transaction_id::text=?
            """;
    private static final String SELECT_ALL_TRANSACTIONS = """
            SELECT * FROM transaction
            """;

    @Override
    public TransactionEntity save(TransactionEntity entity) {
        try (Connection conn = ConnectionUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(UPSERT_TRANSACTION)) {

            pstmt.setString(1, String.valueOf(entity.getId()));
            pstmt.setString(2, String.valueOf(entity.getType()));
            pstmt.setString(3, String.valueOf(entity.getAmount()));
            pstmt.setString(4, String.valueOf(entity.getCurrency()));
            pstmt.setString(5, String.valueOf(entity.getDescription()));
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
    public TransactionEntity findById(UUID entityId) {
        try (Connection conn = ConnectionUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SELECT_TRANSACTION_BY_TRANSACTION_ID)) {

            pstmt.setString(1, String.valueOf(entityId));

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return buildTransactionEntityFromResultSet(rs);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public List<TransactionEntity> findAll() {
        List<TransactionEntity> foundTransactions = new ArrayList<>();

        try (Connection conn = ConnectionUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SELECT_ALL_TRANSACTIONS);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                foundTransactions.add(buildTransactionEntityFromResultSet(rs));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return foundTransactions;
    }

    private TransactionEntity buildTransactionEntityFromResultSet(ResultSet rs) throws SQLException {
        return TransactionEntity.builder()
                .id((UUID) rs.getObject("transaction_id"))
                .type(rs.getString("type"))
                .amount(rs.getBigDecimal("amount"))
                .currency(rs.getString("currency"))
                .description(rs.getString("description"))
                .createdAt((LocalDateTime) rs.getObject("created_at"))
                .updatedAt((LocalDateTime) rs.getObject("updated_at"))
                .deletedAt((LocalDateTime) rs.getObject("deleted_at"))
                .build();
    }
}
