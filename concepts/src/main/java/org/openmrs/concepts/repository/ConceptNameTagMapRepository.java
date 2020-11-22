package org.openmrs.concepts.repository;

import org.openmrs.concepts.domain.ConceptNameTagMap;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ConceptNameTagMap entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ConceptNameTagMapRepository extends JpaRepository<ConceptNameTagMap, Long> {
}
