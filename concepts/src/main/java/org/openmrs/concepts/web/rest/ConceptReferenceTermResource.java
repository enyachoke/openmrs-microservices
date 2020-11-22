package org.openmrs.concepts.web.rest;

import org.openmrs.concepts.domain.ConceptReferenceTerm;
import org.openmrs.concepts.repository.ConceptReferenceTermRepository;
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
 * REST controller for managing {@link org.openmrs.concepts.domain.ConceptReferenceTerm}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ConceptReferenceTermResource {

    private final Logger log = LoggerFactory.getLogger(ConceptReferenceTermResource.class);

    private static final String ENTITY_NAME = "conceptsConceptReferenceTerm";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ConceptReferenceTermRepository conceptReferenceTermRepository;

    public ConceptReferenceTermResource(ConceptReferenceTermRepository conceptReferenceTermRepository) {
        this.conceptReferenceTermRepository = conceptReferenceTermRepository;
    }

    /**
     * {@code POST  /concept-reference-terms} : Create a new conceptReferenceTerm.
     *
     * @param conceptReferenceTerm the conceptReferenceTerm to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new conceptReferenceTerm, or with status {@code 400 (Bad Request)} if the conceptReferenceTerm has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/concept-reference-terms")
    public ResponseEntity<ConceptReferenceTerm> createConceptReferenceTerm(@Valid @RequestBody ConceptReferenceTerm conceptReferenceTerm) throws URISyntaxException {
        log.debug("REST request to save ConceptReferenceTerm : {}", conceptReferenceTerm);
        if (conceptReferenceTerm.getId() != null) {
            throw new BadRequestAlertException("A new conceptReferenceTerm cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ConceptReferenceTerm result = conceptReferenceTermRepository.save(conceptReferenceTerm);
        return ResponseEntity.created(new URI("/api/concept-reference-terms/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /concept-reference-terms} : Updates an existing conceptReferenceTerm.
     *
     * @param conceptReferenceTerm the conceptReferenceTerm to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated conceptReferenceTerm,
     * or with status {@code 400 (Bad Request)} if the conceptReferenceTerm is not valid,
     * or with status {@code 500 (Internal Server Error)} if the conceptReferenceTerm couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/concept-reference-terms")
    public ResponseEntity<ConceptReferenceTerm> updateConceptReferenceTerm(@Valid @RequestBody ConceptReferenceTerm conceptReferenceTerm) throws URISyntaxException {
        log.debug("REST request to update ConceptReferenceTerm : {}", conceptReferenceTerm);
        if (conceptReferenceTerm.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ConceptReferenceTerm result = conceptReferenceTermRepository.save(conceptReferenceTerm);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, conceptReferenceTerm.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /concept-reference-terms} : get all the conceptReferenceTerms.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of conceptReferenceTerms in body.
     */
    @GetMapping("/concept-reference-terms")
    public List<ConceptReferenceTerm> getAllConceptReferenceTerms() {
        log.debug("REST request to get all ConceptReferenceTerms");
        return conceptReferenceTermRepository.findAll();
    }

    /**
     * {@code GET  /concept-reference-terms/:id} : get the "id" conceptReferenceTerm.
     *
     * @param id the id of the conceptReferenceTerm to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the conceptReferenceTerm, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/concept-reference-terms/{id}")
    public ResponseEntity<ConceptReferenceTerm> getConceptReferenceTerm(@PathVariable Long id) {
        log.debug("REST request to get ConceptReferenceTerm : {}", id);
        Optional<ConceptReferenceTerm> conceptReferenceTerm = conceptReferenceTermRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(conceptReferenceTerm);
    }

    /**
     * {@code DELETE  /concept-reference-terms/:id} : delete the "id" conceptReferenceTerm.
     *
     * @param id the id of the conceptReferenceTerm to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/concept-reference-terms/{id}")
    public ResponseEntity<Void> deleteConceptReferenceTerm(@PathVariable Long id) {
        log.debug("REST request to delete ConceptReferenceTerm : {}", id);
        conceptReferenceTermRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
