package org.stc.assessment.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;
import org.stc.assessment.model.FileMetadataDTO;

@Repository
public class CustomFileRepositoryImpl implements CustomFileRepository {
    @PersistenceContext
    private EntityManager entityManager;

    private static Optional<FileMetadataDTO> toFileMetadataDTO(List<Object[]> results) {
        if (results.isEmpty()) {
            return Optional.empty();
        }

        Object[] row = results.get(0);
        return Optional.of(new FileMetadataDTO(
                ((Long) row[0]),
                (String) row[1],
                (String) row[2],
                (String) row[3],
                (String) row[4],
                (String) row[5]
        ));
    }

    public Optional<FileMetadataDTO> findFileMetadata(Long fileId, String userEmail) {
        String sql = "SELECT " +
                "i.id AS item_id, " +
                "i.name AS file_name, " +
                "i.type AS item_type, " +
                "p.user_email, " +
                "p.permission_level, " +
                "g.group_name " +
                "FROM item_ i " +
                "JOIN permission_group g ON i.permission_group_id = g.id " +
                "JOIN permission_ p ON g.id = p.group_id " +
                "WHERE i.id = :fileId " +
                "AND p.user_email = :userEmail " +
                "AND (p.permission_level = 'VIEW' OR p.permission_level = 'EDIT')";


        List<Object[]> results = entityManager.createNativeQuery(sql)
                .setParameter("fileId", fileId)
                .setParameter("userEmail", userEmail)
                .getResultList();
        return toFileMetadataDTO(results);
    }
}
