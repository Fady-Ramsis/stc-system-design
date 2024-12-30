package org.stc.assessment.repository;

import java.util.Optional;

import org.springframework.stereotype.Repository;
import org.stc.assessment.model.FileMetadataDTO;

@Repository
public interface CustomFileRepository {
    Optional<FileMetadataDTO> findFileMetadata(Long fileId, String userEmail);
}
