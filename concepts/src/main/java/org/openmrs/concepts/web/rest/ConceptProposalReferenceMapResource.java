package org.openmrs.concepts.web.rest;

import org.openmrs.concepts.domain.ConceptProposalReferenceMap;
import org.openmrs.concepts.repository.ConceptProposalReferenceMapRepository;
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
 * REST controller for managing {@link org.openmrs.concepts.domain.ConceptProposalReferenceMap}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ConceptProposalReferenceMapResource {

    private final Logger log = LoggerFactory.getLogger(ConceptProposalReferenceMapResource.class);

    private static final String ENTITY_NAME = "conceptsConceptProposalReferenceMap";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ConceptProposalReferenceMapRepository conceptProposalReferenceMapRepository;

    public ConceptProposalReferenceMapResource(ConceptProposalReferenceMapRepository conceptProposalReferenceMapRepository) {
        this.conceptProposalReferenceMapRepository = conceptProposalReferenceMapRepository;
    }

    /**
     * {@code POST  /concept-proposal-reference-maps} : Create a new conceptProposalReferenceMap.
     *
     * @param conceptProposalReferenceMap the conceptProposalReferenceMap to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new conceptProposalReferenceMap, or with status {@code 400 (Bad Request)} if the conceptProposalReferenceMap has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/concept-proposal-reference-maps")
    public ResponseEntity<ConceptProposalReferenceMap> createConceptProposalReferenceMap(@Valid @RequestBody ConceptProposalReferenceMap conceptProposalReferenceMap) throws URISyntaxException {
        log.debug("REST request to save ConceptProposalReferenceMap : {}", conceptProposalReferenceMap);
        if (conceptProposalReferenceMap.getId() != null) {
            throw new BadRequestAlertException("A new conceptProposalReferenceMap cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ConceptProposalReferenceMap result = conceptProposalReferenceMapRepository.save(conceptProposalReferenceMap);
        return ResponseEntity.created(new URI("/api/concept-proposal-reference-maps/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /concept-proposal-reference-maps} : Updates an existing conceptProposalReferenceMap.
     *
     * @param conceptProposalReferenceMap the conceptProposalReferenceMap to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated conceptProposalReferenceMap,
     * or with status {@code 400 (Bad Request)} if the conceptProposalReferenceMap is not valid,
     * or with status {@code 500 (Internal Server Error)} if the conceptProposalReferenceMap couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/concept-proposal-reference-maps")
    public ResponseEntity<ConceptProposalReferenceMap> updateConceptProposalReferenceMap(@Valid @RequestBody ConceptProposalReferenceMap conceptProposalReferenceMap) throws URISyntaxException {
        log.debug("REST request to update ConceptProposalReferenceMap : {}", conceptProposalReferenceMap);
        if (conceptProposalReferenceMap.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ConceptProposalReferenceMap result = conceptProposalReferenceMapRepository.save(conceptProposalReferenceMap);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, conceptProposalReferenceMap.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /concept-proposal-reference-maps} : get all the conceptProposalReferenceMaps.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of conceptProposalReferenceMaps in body.
     */
    @GetMapping("/concept-proposal-reference-maps")
    public List<ConceptProposalReferenceMap> getAllConceptProposalReferenceMaps() {
        log.debug("REST request to get all ConceptProposalReferenceMaps");
        return conceptProposalReferenceMapRepository.findAll();
    }

    /**
     * {@code GET  /concept-proposal-reference-maps/:id} : get the "id" conceptProposalReferenceMap.
     *
     * @param id the id of the conceptProposalReferenceMap to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the conceptProposalReferenceMap, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/concept-proposal-reference-maps/{id}")
    public ResponseEntity<ConceptProposalReferenceMap> getConceptProposalReferenceMap(@PathVariable Long id) {
        log.debug("REST request to get ConceptProposalReferenceMap : {}", id);
        Optional<ConceptProposalReferenceMap> conceptProposalReferenceMap = conceptProposalReferenceMapRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(conceptProposalReferenceMap);
    }

    /**
     * {@code DELETE  /concept-proposal-reference-maps/:id} : delete the "id" conceptProposalReferenceMap.
     *
     * @param id the id of the conceptProposalReferenceMap to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/concept-proposal-reference-maps/{id}")
    public ResponseEntity<Void> deleteConceptProposalReferenceMap(@PathVariable Long id) {
        log.debug("REST request to delete ConceptProposalReferenceMap : {}", id);
        conceptProposalReferenceMapRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
