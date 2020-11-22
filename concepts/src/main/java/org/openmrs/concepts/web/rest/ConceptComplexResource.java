package org.openmrs.concepts.web.rest;

import org.openmrs.concepts.domain.ConceptComplex;
import org.openmrs.concepts.repository.ConceptComplexRepository;
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
 * REST controller for managing {@link org.openmrs.concepts.domain.ConceptComplex}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ConceptComplexResource {

    private final Logger log = LoggerFactory.getLogger(ConceptComplexResource.class);

    private static final String ENTITY_NAME = "conceptsConceptComplex";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ConceptComplexRepository conceptComplexRepository;

    public ConceptComplexResource(ConceptComplexRepository conceptComplexRepository) {
        this.conceptComplexRepository = conceptComplexRepository;
    }

    /**
     * {@code POST  /concept-complexes} : Create a new conceptComplex.
     *
     * @param conceptComplex the conceptComplex to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new conceptComplex, or with status {@code 400 (Bad Request)} if the conceptComplex has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/concept-complexes")
    public ResponseEntity<ConceptComplex> createConceptComplex(@Valid @RequestBody ConceptComplex conceptComplex) throws URISyntaxException {
        log.debug("REST request to save ConceptComplex : {}", conceptComplex);
        if (conceptComplex.getId() != null) {
            throw new BadRequestAlertException("A new conceptComplex cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ConceptComplex result = conceptComplexRepository.save(conceptComplex);
        return ResponseEntity.created(new URI("/api/concept-complexes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /concept-complexes} : Updates an existing conceptComplex.
     *
     * @param conceptComplex the conceptComplex to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated conceptComplex,
     * or with status {@code 400 (Bad Request)} if the conceptComplex is not valid,
     * or with status {@code 500 (Internal Server Error)} if the conceptComplex couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/concept-complexes")
    public ResponseEntity<ConceptComplex> updateConceptComplex(@Valid @RequestBody ConceptComplex conceptComplex) throws URISyntaxException {
        log.debug("REST request to update ConceptComplex : {}", conceptComplex);
        if (conceptComplex.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ConceptComplex result = conceptComplexRepository.save(conceptComplex);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, conceptComplex.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /concept-complexes} : get all the conceptComplexes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of conceptComplexes in body.
     */
    @GetMapping("/concept-complexes")
    public List<ConceptComplex> getAllConceptComplexes() {
        log.debug("REST request to get all ConceptComplexes");
        return conceptComplexRepository.findAll();
    }

    /**
     * {@code GET  /concept-complexes/:id} : get the "id" conceptComplex.
     *
     * @param id the id of the conceptComplex to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the conceptComplex, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/concept-complexes/{id}")
    public ResponseEntity<ConceptComplex> getConceptComplex(@PathVariable Long id) {
        log.debug("REST request to get ConceptComplex : {}", id);
        Optional<ConceptComplex> conceptComplex = conceptComplexRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(conceptComplex);
    }

    /**
     * {@code DELETE  /concept-complexes/:id} : delete the "id" conceptComplex.
     *
     * @param id the id of the conceptComplex to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/concept-complexes/{id}")
    public ResponseEntity<Void> deleteConceptComplex(@PathVariable Long id) {
        log.debug("REST request to delete ConceptComplex : {}", id);
        conceptComplexRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
