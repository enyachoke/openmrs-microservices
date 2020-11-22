package org.openmrs.concepts.web.rest;

import org.openmrs.concepts.domain.ConceptName;
import org.openmrs.concepts.repository.ConceptNameRepository;
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
 * REST controller for managing {@link org.openmrs.concepts.domain.ConceptName}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ConceptNameResource {

    private final Logger log = LoggerFactory.getLogger(ConceptNameResource.class);

    private static final String ENTITY_NAME = "conceptsConceptName";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ConceptNameRepository conceptNameRepository;

    public ConceptNameResource(ConceptNameRepository conceptNameRepository) {
        this.conceptNameRepository = conceptNameRepository;
    }

    /**
     * {@code POST  /concept-names} : Create a new conceptName.
     *
     * @param conceptName the conceptName to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new conceptName, or with status {@code 400 (Bad Request)} if the conceptName has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/concept-names")
    public ResponseEntity<ConceptName> createConceptName(@Valid @RequestBody ConceptName conceptName) throws URISyntaxException {
        log.debug("REST request to save ConceptName : {}", conceptName);
        if (conceptName.getId() != null) {
            throw new BadRequestAlertException("A new conceptName cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ConceptName result = conceptNameRepository.save(conceptName);
        return ResponseEntity.created(new URI("/api/concept-names/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /concept-names} : Updates an existing conceptName.
     *
     * @param conceptName the conceptName to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated conceptName,
     * or with status {@code 400 (Bad Request)} if the conceptName is not valid,
     * or with status {@code 500 (Internal Server Error)} if the conceptName couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/concept-names")
    public ResponseEntity<ConceptName> updateConceptName(@Valid @RequestBody ConceptName conceptName) throws URISyntaxException {
        log.debug("REST request to update ConceptName : {}", conceptName);
        if (conceptName.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ConceptName result = conceptNameRepository.save(conceptName);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, conceptName.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /concept-names} : get all the conceptNames.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of conceptNames in body.
     */
    @GetMapping("/concept-names")
    public List<ConceptName> getAllConceptNames() {
        log.debug("REST request to get all ConceptNames");
        return conceptNameRepository.findAll();
    }

    /**
     * {@code GET  /concept-names/:id} : get the "id" conceptName.
     *
     * @param id the id of the conceptName to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the conceptName, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/concept-names/{id}")
    public ResponseEntity<ConceptName> getConceptName(@PathVariable Long id) {
        log.debug("REST request to get ConceptName : {}", id);
        Optional<ConceptName> conceptName = conceptNameRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(conceptName);
    }

    /**
     * {@code DELETE  /concept-names/:id} : delete the "id" conceptName.
     *
     * @param id the id of the conceptName to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/concept-names/{id}")
    public ResponseEntity<Void> deleteConceptName(@PathVariable Long id) {
        log.debug("REST request to delete ConceptName : {}", id);
        conceptNameRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
