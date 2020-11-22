package org.openmrs.concepts.repository;

import org.openmrs.concepts.domain.Drug;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Drug entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DrugRepository extends JpaRepository<Drug, Long> {
}
