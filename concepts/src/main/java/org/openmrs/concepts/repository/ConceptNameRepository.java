package org.openmrs.concepts.repository;

import org.openmrs.concepts.domain.ConceptName;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ConceptName entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ConceptNameRepository extends JpaRepository<ConceptName, Long> {
}
