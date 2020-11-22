package org.openmrs.forms.repository;

import org.openmrs.forms.domain.FieldAnswer;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the FieldAnswer entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FieldAnswerRepository extends JpaRepository<FieldAnswer, Long> {
}
