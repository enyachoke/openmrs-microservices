package org.openmrs.concepts.repository;

import org.openmrs.concepts.domain.Concept;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Concept entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ConceptRepository extends JpaRepository<Concept, Long> {
}
