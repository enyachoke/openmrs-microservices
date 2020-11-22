package org.openmrs.concepts.repository;

import org.openmrs.concepts.domain.ConceptProposal;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ConceptProposal entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ConceptProposalRepository extends JpaRepository<ConceptProposal, Long> {
}
