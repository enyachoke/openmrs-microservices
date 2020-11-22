package org.openmrs.orders.repository;

import org.openmrs.orders.domain.OrderType;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the OrderType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OrderTypeRepository extends JpaRepository<OrderType, Long> {
}
