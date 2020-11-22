package org.openmrs.concepts.repository;

import org.openmrs.concepts.domain.ConceptAnswer;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ConceptAnswer entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ConceptAnswerRepository extends JpaRepository<ConceptAnswer, Long> {
}
