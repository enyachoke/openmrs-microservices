package org.openmrs.concepts.repository;

import org.openmrs.concepts.domain.ConceptDataType;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ConceptDataType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ConceptDataTypeRepository extends JpaRepository<ConceptDataType, Long> {
}
