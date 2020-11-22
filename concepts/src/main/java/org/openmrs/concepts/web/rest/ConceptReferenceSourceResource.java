package org.openmrs.concepts.web.rest;

import org.openmrs.concepts.domain.ConceptReferenceSource;
import org.openmrs.concepts.repository.ConceptReferenceSourceRepository;
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
 * REST controller for managing {@link org.openmrs.concepts.domain.ConceptReferenceSource}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ConceptReferenceSourceResource {

    private final Logger log = LoggerFactory.getLogger(ConceptReferenceSourceResource.class);

    private static final String ENTITY_NAME = "conceptsConceptReferenceSource";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ConceptReferenceSourceRepository conceptReferenceSourceRepository;

    public ConceptReferenceSourceResource(ConceptReferenceSourceRepository conceptReferenceSourceRepository) {
        this.conceptReferenceSourceRepository = conceptReferenceSourceRepository;
    }

    /**
     * {@code POST  /concept-reference-sources} : Create a new conceptReferenceSource.
     *
     * @param conceptReferenceSource the conceptReferenceSource to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new conceptReferenceSource, or with status {@code 400 (Bad Request)} if the conceptReferenceSource has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/concept-reference-sources")
    public ResponseEntity<ConceptReferenceSource> createConceptReferenceSource(@Valid @RequestBody ConceptReferenceSource conceptReferenceSource) throws URISyntaxException {
        log.debug("REST request to save ConceptReferenceSource : {}", conceptReferenceSource);
        if (conceptReferenceSource.getId() != null) {
            throw new BadRequestAlertException("A new conceptReferenceSource cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ConceptReferenceSource result = conceptReferenceSourceRepository.save(conceptReferenceSource);
        return ResponseEntity.created(new URI("/api/concept-reference-sources/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /concept-reference-sources} : Updates an existing conceptReferenceSource.
     *
     * @param conceptReferenceSource the conceptReferenceSource to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated conceptReferenceSource,
     * or with status {@code 400 (Bad Request)} if the conceptReferenceSource is not valid,
     * or with status {@code 500 (Internal Server Error)} if the conceptReferenceSource couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/concept-reference-sources")
    public ResponseEntity<ConceptReferenceSource> updateConceptReferenceSource(@Valid @RequestBody ConceptReferenceSource conceptReferenceSource) throws URISyntaxException {
        log.debug("REST request to update ConceptReferenceSource : {}", conceptReferenceSource);
        if (conceptReferenceSource.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ConceptReferenceSource result = conceptReferenceSourceRepository.save(conceptReferenceSource);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, conceptReferenceSource.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /concept-reference-sources} : get all the conceptReferenceSources.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of conceptReferenceSources in body.
     */
    @GetMapping("/concept-reference-sources")
    public List<ConceptReferenceSource> getAllConceptReferenceSources() {
        log.debug("REST request to get all ConceptReferenceSources");
        return conceptReferenceSourceRepository.findAll();
    }

    /**
     * {@code GET  /concept-reference-sources/:id} : get the "id" conceptReferenceSource.
     *
     * @param id the id of the conceptReferenceSource to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the conceptReferenceSource, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/concept-reference-sources/{id}")
    public ResponseEntity<ConceptReferenceSource> getConceptReferenceSource(@PathVariable Long id) {
        log.debug("REST request to get ConceptReferenceSource : {}", id);
        Optional<ConceptReferenceSource> conceptReferenceSource = conceptReferenceSourceRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(conceptReferenceSource);
    }

    /**
     * {@code DELETE  /concept-reference-sources/:id} : delete the "id" conceptReferenceSource.
     *
     * @param id the id of the conceptReferenceSource to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/concept-reference-sources/{id}")
    public ResponseEntity<Void> deleteConceptReferenceSource(@PathVariable Long id) {
        log.debug("REST request to delete ConceptReferenceSource : {}", id);
        conceptReferenceSourceRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
