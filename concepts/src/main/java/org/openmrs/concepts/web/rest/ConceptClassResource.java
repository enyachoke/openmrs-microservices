package org.openmrs.concepts.web.rest;

import org.openmrs.concepts.domain.ConceptClass;
import org.openmrs.concepts.repository.ConceptClassRepository;
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
 * REST controller for managing {@link org.openmrs.concepts.domain.ConceptClass}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ConceptClassResource {

    private final Logger log = LoggerFactory.getLogger(ConceptClassResource.class);

    private static final String ENTITY_NAME = "conceptsConceptClass";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ConceptClassRepository conceptClassRepository;

    public ConceptClassResource(ConceptClassRepository conceptClassRepository) {
        this.conceptClassRepository = conceptClassRepository;
    }

    /**
     * {@code POST  /concept-classes} : Create a new conceptClass.
     *
     * @param conceptClass the conceptClass to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new conceptClass, or with status {@code 400 (Bad Request)} if the conceptClass has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/concept-classes")
    public ResponseEntity<ConceptClass> createConceptClass(@Valid @RequestBody ConceptClass conceptClass) throws URISyntaxException {
        log.debug("REST request to save ConceptClass : {}", conceptClass);
        if (conceptClass.getId() != null) {
            throw new BadRequestAlertException("A new conceptClass cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ConceptClass result = conceptClassRepository.save(conceptClass);
        return ResponseEntity.created(new URI("/api/concept-classes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /concept-classes} : Updates an existing conceptClass.
     *
     * @param conceptClass the conceptClass to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated conceptClass,
     * or with status {@code 400 (Bad Request)} if the conceptClass is not valid,
     * or with status {@code 500 (Internal Server Error)} if the conceptClass couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/concept-classes")
    public ResponseEntity<ConceptClass> updateConceptClass(@Valid @RequestBody ConceptClass conceptClass) throws URISyntaxException {
        log.debug("REST request to update ConceptClass : {}", conceptClass);
        if (conceptClass.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ConceptClass result = conceptClassRepository.save(conceptClass);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, conceptClass.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /concept-classes} : get all the conceptClasses.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of conceptClasses in body.
     */
    @GetMapping("/concept-classes")
    public List<ConceptClass> getAllConceptClasses() {
        log.debug("REST request to get all ConceptClasses");
        return conceptClassRepository.findAll();
    }

    /**
     * {@code GET  /concept-classes/:id} : get the "id" conceptClass.
     *
     * @param id the id of the conceptClass to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the conceptClass, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/concept-classes/{id}")
    public ResponseEntity<ConceptClass> getConceptClass(@PathVariable Long id) {
        log.debug("REST request to get ConceptClass : {}", id);
        Optional<ConceptClass> conceptClass = conceptClassRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(conceptClass);
    }

    /**
     * {@code DELETE  /concept-classes/:id} : delete the "id" conceptClass.
     *
     * @param id the id of the conceptClass to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/concept-classes/{id}")
    public ResponseEntity<Void> deleteConceptClass(@PathVariable Long id) {
        log.debug("REST request to delete ConceptClass : {}", id);
        conceptClassRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
