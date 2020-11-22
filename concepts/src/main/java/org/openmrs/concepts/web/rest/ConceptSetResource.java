package org.openmrs.concepts.web.rest;

import org.openmrs.concepts.domain.ConceptSet;
import org.openmrs.concepts.repository.ConceptSetRepository;
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
 * REST controller for managing {@link org.openmrs.concepts.domain.ConceptSet}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ConceptSetResource {

    private final Logger log = LoggerFactory.getLogger(ConceptSetResource.class);

    private static final String ENTITY_NAME = "conceptsConceptSet";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ConceptSetRepository conceptSetRepository;

    public ConceptSetResource(ConceptSetRepository conceptSetRepository) {
        this.conceptSetRepository = conceptSetRepository;
    }

    /**
     * {@code POST  /concept-sets} : Create a new conceptSet.
     *
     * @param conceptSet the conceptSet to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new conceptSet, or with status {@code 400 (Bad Request)} if the conceptSet has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/concept-sets")
    public ResponseEntity<ConceptSet> createConceptSet(@Valid @RequestBody ConceptSet conceptSet) throws URISyntaxException {
        log.debug("REST request to save ConceptSet : {}", conceptSet);
        if (conceptSet.getId() != null) {
            throw new BadRequestAlertException("A new conceptSet cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ConceptSet result = conceptSetRepository.save(conceptSet);
        return ResponseEntity.created(new URI("/api/concept-sets/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /concept-sets} : Updates an existing conceptSet.
     *
     * @param conceptSet the conceptSet to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated conceptSet,
     * or with status {@code 400 (Bad Request)} if the conceptSet is not valid,
     * or with status {@code 500 (Internal Server Error)} if the conceptSet couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/concept-sets")
    public ResponseEntity<ConceptSet> updateConceptSet(@Valid @RequestBody ConceptSet conceptSet) throws URISyntaxException {
        log.debug("REST request to update ConceptSet : {}", conceptSet);
        if (conceptSet.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ConceptSet result = conceptSetRepository.save(conceptSet);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, conceptSet.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /concept-sets} : get all the conceptSets.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of conceptSets in body.
     */
    @GetMapping("/concept-sets")
    public List<ConceptSet> getAllConceptSets() {
        log.debug("REST request to get all ConceptSets");
        return conceptSetRepository.findAll();
    }

    /**
     * {@code GET  /concept-sets/:id} : get the "id" conceptSet.
     *
     * @param id the id of the conceptSet to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the conceptSet, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/concept-sets/{id}")
    public ResponseEntity<ConceptSet> getConceptSet(@PathVariable Long id) {
        log.debug("REST request to get ConceptSet : {}", id);
        Optional<ConceptSet> conceptSet = conceptSetRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(conceptSet);
    }

    /**
     * {@code DELETE  /concept-sets/:id} : delete the "id" conceptSet.
     *
     * @param id the id of the conceptSet to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/concept-sets/{id}")
    public ResponseEntity<Void> deleteConceptSet(@PathVariable Long id) {
        log.debug("REST request to delete ConceptSet : {}", id);
        conceptSetRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
