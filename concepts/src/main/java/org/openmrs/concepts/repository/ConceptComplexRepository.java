package org.openmrs.concepts.repository;

import org.openmrs.concepts.domain.ConceptComplex;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ConceptComplex entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ConceptComplexRepository extends JpaRepository<ConceptComplex, Long> {
}
