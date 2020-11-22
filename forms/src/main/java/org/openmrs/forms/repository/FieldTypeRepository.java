package org.openmrs.forms.repository;

import org.openmrs.forms.domain.FieldType;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the FieldType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FieldTypeRepository extends JpaRepository<FieldType, Long> {
}
