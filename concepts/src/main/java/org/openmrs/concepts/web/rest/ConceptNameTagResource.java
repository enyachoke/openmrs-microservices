package org.openmrs.concepts.web.rest;

import org.openmrs.concepts.domain.ConceptNameTag;
import org.openmrs.concepts.repository.ConceptNameTagRepository;
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
 * REST controller for managing {@link org.openmrs.concepts.domain.ConceptNameTag}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ConceptNameTagResource {

    private final Logger log = LoggerFactory.getLogger(ConceptNameTagResource.class);

    private static final String ENTITY_NAME = "conceptsConceptNameTag";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ConceptNameTagRepository conceptNameTagRepository;

    public ConceptNameTagResource(ConceptNameTagRepository conceptNameTagRepository) {
        this.conceptNameTagRepository = conceptNameTagRepository;
    }

    /**
     * {@code POST  /concept-name-tags} : Create a new conceptNameTag.
     *
     * @param conceptNameTag the conceptNameTag to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new conceptNameTag, or with status {@code 400 (Bad Request)} if the conceptNameTag has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/concept-name-tags")
    public ResponseEntity<ConceptNameTag> createConceptNameTag(@Valid @RequestBody ConceptNameTag conceptNameTag) throws URISyntaxException {
        log.debug("REST request to save ConceptNameTag : {}", conceptNameTag);
        if (conceptNameTag.getId() != null) {
            throw new BadRequestAlertException("A new conceptNameTag cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ConceptNameTag result = conceptNameTagRepository.save(conceptNameTag);
        return ResponseEntity.created(new URI("/api/concept-name-tags/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /concept-name-tags} : Updates an existing conceptNameTag.
     *
     * @param conceptNameTag the conceptNameTag to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated conceptNameTag,
     * or with status {@code 400 (Bad Request)} if the conceptNameTag is not valid,
     * or with status {@code 500 (Internal Server Error)} if the conceptNameTag couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/concept-name-tags")
    public ResponseEntity<ConceptNameTag> updateConceptNameTag(@Valid @RequestBody ConceptNameTag conceptNameTag) throws URISyntaxException {
        log.debug("REST request to update ConceptNameTag : {}", conceptNameTag);
        if (conceptNameTag.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ConceptNameTag result = conceptNameTagRepository.save(conceptNameTag);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, conceptNameTag.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /concept-name-tags} : get all the conceptNameTags.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of conceptNameTags in body.
     */
    @GetMapping("/concept-name-tags")
    public List<ConceptNameTag> getAllConceptNameTags() {
        log.debug("REST request to get all ConceptNameTags");
        return conceptNameTagRepository.findAll();
    }

    /**
     * {@code GET  /concept-name-tags/:id} : get the "id" conceptNameTag.
     *
     * @param id the id of the conceptNameTag to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the conceptNameTag, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/concept-name-tags/{id}")
    public ResponseEntity<ConceptNameTag> getConceptNameTag(@PathVariable Long id) {
        log.debug("REST request to get ConceptNameTag : {}", id);
        Optional<ConceptNameTag> conceptNameTag = conceptNameTagRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(conceptNameTag);
    }

    /**
     * {@code DELETE  /concept-name-tags/:id} : delete the "id" conceptNameTag.
     *
     * @param id the id of the conceptNameTag to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/concept-name-tags/{id}")
    public ResponseEntity<Void> deleteConceptNameTag(@PathVariable Long id) {
        log.debug("REST request to delete ConceptNameTag : {}", id);
        conceptNameTagRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
