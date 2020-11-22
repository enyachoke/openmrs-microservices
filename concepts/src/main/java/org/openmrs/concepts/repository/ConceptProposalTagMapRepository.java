package org.openmrs.concepts.repository;

import org.openmrs.concepts.domain.ConceptProposalTagMap;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ConceptProposalTagMap entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ConceptProposalTagMapRepository extends JpaRepository<ConceptProposalTagMap, Long> {
}
