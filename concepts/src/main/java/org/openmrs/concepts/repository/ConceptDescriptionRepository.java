package org.openmrs.concepts.repository;

import org.openmrs.concepts.domain.ConceptDescription;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ConceptDescription entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ConceptDescriptionRepository extends JpaRepository<ConceptDescription, Long> {
}
