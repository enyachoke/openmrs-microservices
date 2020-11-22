package org.openmrs.orders.repository;

import org.openmrs.orders.domain.DrugOrder;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the DrugOrder entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DrugOrderRepository extends JpaRepository<DrugOrder, Long> {
}
