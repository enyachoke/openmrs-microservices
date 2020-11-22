package org.openmrs.concepts.repository;

import org.openmrs.concepts.domain.ConceptReferenceSource;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ConceptReferenceSource entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ConceptReferenceSourceRepository extends JpaRepository<ConceptReferenceSource, Long> {
}
