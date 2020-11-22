package org.openmrs.concepts.repository;

import org.openmrs.concepts.domain.ConceptStopWord;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ConceptStopWord entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ConceptStopWordRepository extends JpaRepository<ConceptStopWord, Long> {
}
