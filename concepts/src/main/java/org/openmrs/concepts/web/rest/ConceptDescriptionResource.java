package org.openmrs.concepts.web.rest;

import org.openmrs.concepts.domain.ConceptDescription;
import org.openmrs.concepts.repository.ConceptDescriptionRepository;
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
 * REST controller for managing {@link org.openmrs.concepts.domain.ConceptDescription}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ConceptDescriptionResource {

    private final Logger log = LoggerFactory.getLogger(ConceptDescriptionResource.class);

    private static final String ENTITY_NAME = "conceptsConceptDescription";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ConceptDescriptionRepository conceptDescriptionRepository;

    public ConceptDescriptionResource(ConceptDescriptionRepository conceptDescriptionRepository) {
        this.conceptDescriptionRepository = conceptDescriptionRepository;
    }

    /**
     * {@code POST  /concept-descriptions} : Create a new conceptDescription.
     *
     * @param conceptDescription the conceptDescription to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new conceptDescription, or with status {@code 400 (Bad Request)} if the conceptDescription has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/concept-descriptions")
    public ResponseEntity<ConceptDescription> createConceptDescription(@Valid @RequestBody ConceptDescription conceptDescription) throws URISyntaxException {
        log.debug("REST request to save ConceptDescription : {}", conceptDescription);
        if (conceptDescription.getId() != null) {
            throw new BadRequestAlertException("A new conceptDescription cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ConceptDescription result = conceptDescriptionRepository.save(conceptDescription);
        return ResponseEntity.created(new URI("/api/concept-descriptions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /concept-descriptions} : Updates an existing conceptDescription.
     *
     * @param conceptDescription the conceptDescription to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated conceptDescription,
     * or with status {@code 400 (Bad Request)} if the conceptDescription is not valid,
     * or with status {@code 500 (Internal Server Error)} if the conceptDescription couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/concept-descriptions")
    public ResponseEntity<ConceptDescription> updateConceptDescription(@Valid @RequestBody ConceptDescription conceptDescription) throws URISyntaxException {
        log.debug("REST request to update ConceptDescription : {}", conceptDescription);
        if (conceptDescription.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ConceptDescription result = conceptDescriptionRepository.save(conceptDescription);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, conceptDescription.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /concept-descriptions} : get all the conceptDescriptions.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of conceptDescriptions in body.
     */
    @GetMapping("/concept-descriptions")
    public List<ConceptDescription> getAllConceptDescriptions() {
        log.debug("REST request to get all ConceptDescriptions");
        return conceptDescriptionRepository.findAll();
    }

    /**
     * {@code GET  /concept-descriptions/:id} : get the "id" conceptDescription.
     *
     * @param id the id of the conceptDescription to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the conceptDescription, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/concept-descriptions/{id}")
    public ResponseEntity<ConceptDescription> getConceptDescription(@PathVariable Long id) {
        log.debug("REST request to get ConceptDescription : {}", id);
        Optional<ConceptDescription> conceptDescription = conceptDescriptionRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(conceptDescription);
    }

    /**
     * {@code DELETE  /concept-descriptions/:id} : delete the "id" conceptDescription.
     *
     * @param id the id of the conceptDescription to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/concept-descriptions/{id}")
    public ResponseEntity<Void> deleteConceptDescription(@PathVariable Long id) {
        log.debug("REST request to delete ConceptDescription : {}", id);
        conceptDescriptionRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
