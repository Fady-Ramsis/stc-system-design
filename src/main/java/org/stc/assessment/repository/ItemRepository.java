package org.stc.assessment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.stc.assessment.model.Item;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {}
