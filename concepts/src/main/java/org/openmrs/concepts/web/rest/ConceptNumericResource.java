package org.openmrs.concepts.web.rest;

import org.openmrs.concepts.domain.ConceptNumeric;
import org.openmrs.concepts.repository.ConceptNumericRepository;
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
 * REST controller for managing {@link org.openmrs.concepts.domain.ConceptNumeric}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ConceptNumericResource {

    private final Logger log = LoggerFactory.getLogger(ConceptNumericResource.class);

    private static final String ENTITY_NAME = "conceptsConceptNumeric";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ConceptNumericRepository conceptNumericRepository;

    public ConceptNumericResource(ConceptNumericRepository conceptNumericRepository) {
        this.conceptNumericRepository = conceptNumericRepository;
    }

    /**
     * {@code POST  /concept-numerics} : Create a new conceptNumeric.
     *
     * @param conceptNumeric the conceptNumeric to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new conceptNumeric, or with status {@code 400 (Bad Request)} if the conceptNumeric has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/concept-numerics")
    public ResponseEntity<ConceptNumeric> createConceptNumeric(@Valid @RequestBody ConceptNumeric conceptNumeric) throws URISyntaxException {
        log.debug("REST request to save ConceptNumeric : {}", conceptNumeric);
        if (conceptNumeric.getId() != null) {
            throw new BadRequestAlertException("A new conceptNumeric cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ConceptNumeric result = conceptNumericRepository.save(conceptNumeric);
        return ResponseEntity.created(new URI("/api/concept-numerics/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /concept-numerics} : Updates an existing conceptNumeric.
     *
     * @param conceptNumeric the conceptNumeric to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated conceptNumeric,
     * or with status {@code 400 (Bad Request)} if the conceptNumeric is not valid,
     * or with status {@code 500 (Internal Server Error)} if the conceptNumeric couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/concept-numerics")
    public ResponseEntity<ConceptNumeric> updateConceptNumeric(@Valid @RequestBody ConceptNumeric conceptNumeric) throws URISyntaxException {
        log.debug("REST request to update ConceptNumeric : {}", conceptNumeric);
        if (conceptNumeric.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ConceptNumeric result = conceptNumericRepository.save(conceptNumeric);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, conceptNumeric.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /concept-numerics} : get all the conceptNumerics.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of conceptNumerics in body.
     */
    @GetMapping("/concept-numerics")
    public List<ConceptNumeric> getAllConceptNumerics() {
        log.debug("REST request to get all ConceptNumerics");
        return conceptNumericRepository.findAll();
    }

    /**
     * {@code GET  /concept-numerics/:id} : get the "id" conceptNumeric.
     *
     * @param id the id of the conceptNumeric to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the conceptNumeric, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/concept-numerics/{id}")
    public ResponseEntity<ConceptNumeric> getConceptNumeric(@PathVariable Long id) {
        log.debug("REST request to get ConceptNumeric : {}", id);
        Optional<ConceptNumeric> conceptNumeric = conceptNumericRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(conceptNumeric);
    }

    /**
     * {@code DELETE  /concept-numerics/:id} : delete the "id" conceptNumeric.
     *
     * @param id the id of the conceptNumeric to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/concept-numerics/{id}")
    public ResponseEntity<Void> deleteConceptNumeric(@PathVariable Long id) {
        log.debug("REST request to delete ConceptNumeric : {}", id);
        conceptNumericRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
