package org.openmrs.concepts.web.rest;

import org.openmrs.concepts.domain.ConceptDataType;
import org.openmrs.concepts.repository.ConceptDataTypeRepository;
import org.openmrs.concepts.web.rest.errors.BadRequestAlertException;

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
 * REST controller for managing {@link org.openmrs.concepts.domain.ConceptDataType}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ConceptDataTypeResource {

    private final Logger log = LoggerFactory.getLogger(ConceptDataTypeResource.class);

    private static final String ENTITY_NAME = "conceptsConceptDataType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ConceptDataTypeRepository conceptDataTypeRepository;

    public ConceptDataTypeResource(ConceptDataTypeRepository conceptDataTypeRepository) {
        this.conceptDataTypeRepository = conceptDataTypeRepository;
    }

    /**
     * {@code POST  /concept-data-types} : Create a new conceptDataType.
     *
     * @param conceptDataType the conceptDataType to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new conceptDataType, or with status {@code 400 (Bad Request)} if the conceptDataType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/concept-data-types")
    public ResponseEntity<ConceptDataType> createConceptDataType(@Valid @RequestBody ConceptDataType conceptDataType) throws URISyntaxException {
        log.debug("REST request to save ConceptDataType : {}", conceptDataType);
        if (conceptDataType.getId() != null) {
            throw new BadRequestAlertException("A new conceptDataType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ConceptDataType result = conceptDataTypeRepository.save(conceptDataType);
        return ResponseEntity.created(new URI("/api/concept-data-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /concept-data-types} : Updates an existing conceptDataType.
     *
     * @param conceptDataType the conceptDataType to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated conceptDataType,
     * or with status {@code 400 (Bad Request)} if the conceptDataType is not valid,
     * or with status {@code 500 (Internal Server Error)} if the conceptDataType couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/concept-data-types")
    public ResponseEntity<ConceptDataType> updateConceptDataType(@Valid @RequestBody ConceptDataType conceptDataType) throws URISyntaxException {
        log.debug("REST request to update ConceptDataType : {}", conceptDataType);
        if (conceptDataType.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ConceptDataType result = conceptDataTypeRepository.save(conceptDataType);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, conceptDataType.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /concept-data-types} : get all the conceptDataTypes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of conceptDataTypes in body.
     */
    @GetMapping("/concept-data-types")
    public List<ConceptDataType> getAllConceptDataTypes() {
        log.debug("REST request to get all ConceptDataTypes");
        return conceptDataTypeRepository.findAll();
    }

    /**
     * {@code GET  /concept-data-types/:id} : get the "id" conceptDataType.
     *
     * @param id the id of the conceptDataType to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the conceptDataType, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/concept-data-types/{id}")
    public ResponseEntity<ConceptDataType> getConceptDataType(@PathVariable Long id) {
        log.debug("REST request to get ConceptDataType : {}", id);
        Optional<ConceptDataType> conceptDataType = conceptDataTypeRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(conceptDataType);
    }

    /**
     * {@code DELETE  /concept-data-types/:id} : delete the "id" conceptDataType.
     *
     * @param id the id of the conceptDataType to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/concept-data-types/{id}")
    public ResponseEntity<Void> deleteConceptDataType(@PathVariable Long id) {
        log.debug("REST request to delete ConceptDataType : {}", id);
        conceptDataTypeRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
