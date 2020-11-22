package org.openmrs.concepts.repository;

import org.openmrs.concepts.domain.ConceptProposalReferenceMap;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ConceptProposalReferenceMap entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ConceptProposalReferenceMapRepository extends JpaRepository<ConceptProposalReferenceMap, Long> {
}
