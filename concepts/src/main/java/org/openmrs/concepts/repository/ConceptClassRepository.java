package org.openmrs.concepts.repository;

import org.openmrs.concepts.domain.ConceptClass;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ConceptClass entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ConceptClassRepository extends JpaRepository<ConceptClass, Long> {
}
