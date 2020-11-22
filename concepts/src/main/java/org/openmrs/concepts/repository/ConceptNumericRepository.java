package org.openmrs.concepts.repository;

import org.openmrs.concepts.domain.ConceptNumeric;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ConceptNumeric entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ConceptNumericRepository extends JpaRepository<ConceptNumeric, Long> {
}
