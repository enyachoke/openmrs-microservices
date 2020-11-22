package org.openmrs.concepts.repository;

import org.openmrs.concepts.domain.ConceptWord;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ConceptWord entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ConceptWordRepository extends JpaRepository<ConceptWord, Long> {
}
