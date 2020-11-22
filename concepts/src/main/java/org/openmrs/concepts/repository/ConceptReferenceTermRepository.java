package org.openmrs.concepts.repository;

import org.openmrs.concepts.domain.ConceptReferenceTerm;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ConceptReferenceTerm entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ConceptReferenceTermRepository extends JpaRepository<ConceptReferenceTerm, Long> {
}
