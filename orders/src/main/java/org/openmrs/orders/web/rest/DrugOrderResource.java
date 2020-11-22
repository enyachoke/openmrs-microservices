package org.openmrs.orders.web.rest;

import org.openmrs.orders.domain.DrugOrder;
import org.openmrs.orders.repository.DrugOrderRepository;
import org.openmrs.orders.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link org.openmrs.orders.domain.DrugOrder}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class DrugOrderResource {

    private final Logger log = LoggerFactory.getLogger(DrugOrderResource.class);

    private static final String ENTITY_NAME = "ordersDrugOrder";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DrugOrderRepository drugOrderRepository;

    public DrugOrderResource(DrugOrderRepository drugOrderRepository) {
        this.drugOrderRepository = drugOrderRepository;
    }

    /**
     * {@code POST  /drug-orders} : Create a new drugOrder.
     *
     * @param drugOrder the drugOrder to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new drugOrder, or with status {@code 400 (Bad Request)} if the drugOrder has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/drug-orders")
    public ResponseEntity<DrugOrder> createDrugOrder(@Valid @RequestBody DrugOrder drugOrder) throws URISyntaxException {
        log.debug("REST request to save DrugOrder : {}", drugOrder);
        if (drugOrder.getId() != null) {
            throw new BadRequestAlertException("A new drugOrder cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DrugOrder result = drugOrderRepository.save(drugOrder);
        return ResponseEntity.created(new URI("/api/drug-orders/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /drug-orders} : Updates an existing drugOrder.
     *
     * @param drugOrder the drugOrder to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated drugOrder,
     * or with status {@code 400 (Bad Request)} if the drugOrder is not valid,
     * or with status {@code 500 (Internal Server Error)} if the drugOrder couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/drug-orders")
    public ResponseEntity<DrugOrder> updateDrugOrder(@Valid @RequestBody DrugOrder drugOrder) throws URISyntaxException {
        log.debug("REST request to update DrugOrder : {}", drugOrder);
        if (drugOrder.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DrugOrder result = drugOrderRepository.save(drugOrder);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, drugOrder.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /drug-orders} : get all the drugOrders.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of drugOrders in body.
     */
    @GetMapping("/drug-orders")
    public List<DrugOrder> getAllDrugOrders() {
        log.debug("REST request to get all DrugOrders");
        return drugOrderRepository.findAll();
    }

    /**
     * {@code GET  /drug-orders/:id} : get the "id" drugOrder.
     *
     * @param id the id of the drugOrder to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the drugOrder, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/drug-orders/{id}")
    public ResponseEntity<DrugOrder> getDrugOrder(@PathVariable Long id) {
        log.debug("REST request to get DrugOrder : {}", id);
        Optional<DrugOrder> drugOrder = drugOrderRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(drugOrder);
    }

    /**
     * {@code DELETE  /drug-orders/:id} : delete the "id" drugOrder.
     *
     * @param id the id of the drugOrder to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/drug-orders/{id}")
    public ResponseEntity<Void> deleteDrugOrder(@PathVariable Long id) {
        log.debug("REST request to delete DrugOrder : {}", id);
        drugOrderRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
