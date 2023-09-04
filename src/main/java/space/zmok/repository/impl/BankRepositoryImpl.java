package space.zmok.repository.impl;

import space.zmok.entity.bank.BankEntity;
import space.zmok.repository.BankRepository;
import space.zmok.util.ConnectionUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class BankRepositoryImpl implements BankRepository {

    private static final String UPSERT_BANK = """
            INSERT INTO bank (bank_id, name, identifier_code, country_code, created_at, updated_at, deleted_at)
            VALUES (?,?,?,?,?,?,?)
            ON CONFLICT (bank_id) DO UPDATE
            SET name = excluded.name,
                identifier_code = excluded.identifier_code,
                country_code = excluded.country_code,
                created_at = excluded.created_at,
                updated_at = excluded.updated_at,
                deleted_at = excluded.deleted_at
            """;
    private static final String SELECT_BANK_BY_BANK_ID = """
            SELECT * FROM bank
            WHERE bank_id::text=?
            """;
    private static final String SELECT_ALL_BANKS = """
            SELECT * FROM bank
            """;

    @Override
    public BankEntity save(BankEntity entity) {
        try (Connection conn = ConnectionUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(UPSERT_BANK)) {

            pstmt.setString(1, String.valueOf(entity.getId()));
            pstmt.setString(2, String.valueOf(entity.getName()));
            pstmt.setString(3, String.valueOf(entity.getIdentifierCode()));
            pstmt.setString(4, String.valueOf(entity.getCountryCode()));
            pstmt.setString(5, String.valueOf(entity.getCreatedAt()));
            pstmt.setString(6, String.valueOf(entity.getUpdatedAt()));
            pstmt.setString(7, String.valueOf(entity.getDeletedAt()));

            if (pstmt.executeUpdate() != 0) {
                return entity;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public BankEntity findById(UUID entityId) {
        try (Connection conn = ConnectionUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SELECT_BANK_BY_BANK_ID)) {

            pstmt.setString(1, String.valueOf(entityId));

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return buildBankEntityFromResultSet(rs);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public List<BankEntity> findAll() {
        List<BankEntity> foundBanks = new ArrayList<>();

        try (Connection conn = ConnectionUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SELECT_ALL_BANKS);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                foundBanks.add(buildBankEntityFromResultSet(rs));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return foundBanks;
    }

    private BankEntity buildBankEntityFromResultSet(ResultSet rs) throws SQLException {
        return BankEntity.builder()
                .id((UUID) rs.getObject("bank_id"))
                .name(rs.getString("name"))
                .identifierCode(rs.getString("identifier_code"))
                .countryCode(rs.getString("country_code"))
                .createdAt((LocalDateTime) rs.getObject("created_at"))
                .updatedAt((LocalDateTime) rs.getObject("updated_at"))
                .deletedAt((LocalDateTime) rs.getObject("deleted_at"))
                .build();
    }
}
