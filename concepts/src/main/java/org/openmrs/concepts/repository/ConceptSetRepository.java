package org.openmrs.concepts.repository;

import org.openmrs.concepts.domain.ConceptSet;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ConceptSet entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ConceptSetRepository extends JpaRepository<ConceptSet, Long> {
}
