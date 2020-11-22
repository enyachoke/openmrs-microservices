package org.openmrs.concepts.repository;

import org.openmrs.concepts.domain.ConceptNameTag;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ConceptNameTag entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ConceptNameTagRepository extends JpaRepository<ConceptNameTag, Long> {
}
