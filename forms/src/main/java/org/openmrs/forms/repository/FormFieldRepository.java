package org.openmrs.forms.repository;

import org.openmrs.forms.domain.FormField;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the FormField entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FormFieldRepository extends JpaRepository<FormField, Long> {
}
