package org.stc.assessment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.stc.assessment.model.Permission;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, Long> {}
