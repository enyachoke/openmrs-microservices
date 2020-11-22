package org.openmrs.concepts.web.rest;

import org.openmrs.concepts.domain.ConceptNameTagMap;
import org.openmrs.concepts.repository.ConceptNameTagMapRepository;
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
 * REST controller for managing {@link org.openmrs.concepts.domain.ConceptNameTagMap}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ConceptNameTagMapResource {

    private final Logger log = LoggerFactory.getLogger(ConceptNameTagMapResource.class);

    private static final String ENTITY_NAME = "conceptsConceptNameTagMap";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ConceptNameTagMapRepository conceptNameTagMapRepository;

    public ConceptNameTagMapResource(ConceptNameTagMapRepository conceptNameTagMapRepository) {
        this.conceptNameTagMapRepository = conceptNameTagMapRepository;
    }

    /**
     * {@code POST  /concept-name-tag-maps} : Create a new conceptNameTagMap.
     *
     * @param conceptNameTagMap the conceptNameTagMap to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new conceptNameTagMap, or with status {@code 400 (Bad Request)} if the conceptNameTagMap has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/concept-name-tag-maps")
    public ResponseEntity<ConceptNameTagMap> createConceptNameTagMap(@Valid @RequestBody ConceptNameTagMap conceptNameTagMap) throws URISyntaxException {
        log.debug("REST request to save ConceptNameTagMap : {}", conceptNameTagMap);
        if (conceptNameTagMap.getId() != null) {
            throw new BadRequestAlertException("A new conceptNameTagMap cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ConceptNameTagMap result = conceptNameTagMapRepository.save(conceptNameTagMap);
        return ResponseEntity.created(new URI("/api/concept-name-tag-maps/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /concept-name-tag-maps} : Updates an existing conceptNameTagMap.
     *
     * @param conceptNameTagMap the conceptNameTagMap to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated conceptNameTagMap,
     * or with status {@code 400 (Bad Request)} if the conceptNameTagMap is not valid,
     * or with status {@code 500 (Internal Server Error)} if the conceptNameTagMap couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/concept-name-tag-maps")
    public ResponseEntity<ConceptNameTagMap> updateConceptNameTagMap(@Valid @RequestBody ConceptNameTagMap conceptNameTagMap) throws URISyntaxException {
        log.debug("REST request to update ConceptNameTagMap : {}", conceptNameTagMap);
        if (conceptNameTagMap.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ConceptNameTagMap result = conceptNameTagMapRepository.save(conceptNameTagMap);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, conceptNameTagMap.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /concept-name-tag-maps} : get all the conceptNameTagMaps.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of conceptNameTagMaps in body.
     */
    @GetMapping("/concept-name-tag-maps")
    public List<ConceptNameTagMap> getAllConceptNameTagMaps() {
        log.debug("REST request to get all ConceptNameTagMaps");
        return conceptNameTagMapRepository.findAll();
    }

    /**
     * {@code GET  /concept-name-tag-maps/:id} : get the "id" conceptNameTagMap.
     *
     * @param id the id of the conceptNameTagMap to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the conceptNameTagMap, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/concept-name-tag-maps/{id}")
    public ResponseEntity<ConceptNameTagMap> getConceptNameTagMap(@PathVariable Long id) {
        log.debug("REST request to get ConceptNameTagMap : {}", id);
        Optional<ConceptNameTagMap> conceptNameTagMap = conceptNameTagMapRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(conceptNameTagMap);
    }

    /**
     * {@code DELETE  /concept-name-tag-maps/:id} : delete the "id" conceptNameTagMap.
     *
     * @param id the id of the conceptNameTagMap to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/concept-name-tag-maps/{id}")
    public ResponseEntity<Void> deleteConceptNameTagMap(@PathVariable Long id) {
        log.debug("REST request to delete ConceptNameTagMap : {}", id);
        conceptNameTagMapRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
