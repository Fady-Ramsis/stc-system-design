package org.stc.assessment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.stc.assessment.model.File;

@Repository
public interface FileRepository extends JpaRepository<File, Long>, CustomFileRepository {
}
