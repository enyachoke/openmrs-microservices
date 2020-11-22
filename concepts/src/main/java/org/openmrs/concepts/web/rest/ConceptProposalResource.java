package org.openmrs.concepts.web.rest;

import org.openmrs.concepts.domain.ConceptProposal;
import org.openmrs.concepts.repository.ConceptProposalRepository;
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
 * REST controller for managing {@link org.openmrs.concepts.domain.ConceptProposal}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ConceptProposalResource {

    private final Logger log = LoggerFactory.getLogger(ConceptProposalResource.class);

    private static final String ENTITY_NAME = "conceptsConceptProposal";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ConceptProposalRepository conceptProposalRepository;

    public ConceptProposalResource(ConceptProposalRepository conceptProposalRepository) {
        this.conceptProposalRepository = conceptProposalRepository;
    }

    /**
     * {@code POST  /concept-proposals} : Create a new conceptProposal.
     *
     * @param conceptProposal the conceptProposal to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new conceptProposal, or with status {@code 400 (Bad Request)} if the conceptProposal has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/concept-proposals")
    public ResponseEntity<ConceptProposal> createConceptProposal(@Valid @RequestBody ConceptProposal conceptProposal) throws URISyntaxException {
        log.debug("REST request to save ConceptProposal : {}", conceptProposal);
        if (conceptProposal.getId() != null) {
            throw new BadRequestAlertException("A new conceptProposal cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ConceptProposal result = conceptProposalRepository.save(conceptProposal);
        return ResponseEntity.created(new URI("/api/concept-proposals/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /concept-proposals} : Updates an existing conceptProposal.
     *
     * @param conceptProposal the conceptProposal to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated conceptProposal,
     * or with status {@code 400 (Bad Request)} if the conceptProposal is not valid,
     * or with status {@code 500 (Internal Server Error)} if the conceptProposal couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/concept-proposals")
    public ResponseEntity<ConceptProposal> updateConceptProposal(@Valid @RequestBody ConceptProposal conceptProposal) throws URISyntaxException {
        log.debug("REST request to update ConceptProposal : {}", conceptProposal);
        if (conceptProposal.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ConceptProposal result = conceptProposalRepository.save(conceptProposal);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, conceptProposal.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /concept-proposals} : get all the conceptProposals.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of conceptProposals in body.
     */
    @GetMapping("/concept-proposals")
    public List<ConceptProposal> getAllConceptProposals() {
        log.debug("REST request to get all ConceptProposals");
        return conceptProposalRepository.findAll();
    }

    /**
     * {@code GET  /concept-proposals/:id} : get the "id" conceptProposal.
     *
     * @param id the id of the conceptProposal to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the conceptProposal, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/concept-proposals/{id}")
    public ResponseEntity<ConceptProposal> getConceptProposal(@PathVariable Long id) {
        log.debug("REST request to get ConceptProposal : {}", id);
        Optional<ConceptProposal> conceptProposal = conceptProposalRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(conceptProposal);
    }

    /**
     * {@code DELETE  /concept-proposals/:id} : delete the "id" conceptProposal.
     *
     * @param id the id of the conceptProposal to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/concept-proposals/{id}")
    public ResponseEntity<Void> deleteConceptProposal(@PathVariable Long id) {
        log.debug("REST request to delete ConceptProposal : {}", id);
        conceptProposalRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
