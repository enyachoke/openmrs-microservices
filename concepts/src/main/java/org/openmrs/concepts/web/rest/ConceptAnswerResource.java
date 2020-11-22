package org.openmrs.concepts.web.rest;

import org.openmrs.concepts.domain.ConceptAnswer;
import org.openmrs.concepts.repository.ConceptAnswerRepository;
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
 * REST controller for managing {@link org.openmrs.concepts.domain.ConceptAnswer}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ConceptAnswerResource {

    private final Logger log = LoggerFactory.getLogger(ConceptAnswerResource.class);

    private static final String ENTITY_NAME = "conceptsConceptAnswer";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ConceptAnswerRepository conceptAnswerRepository;

    public ConceptAnswerResource(ConceptAnswerRepository conceptAnswerRepository) {
        this.conceptAnswerRepository = conceptAnswerRepository;
    }

    /**
     * {@code POST  /concept-answers} : Create a new conceptAnswer.
     *
     * @param conceptAnswer the conceptAnswer to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new conceptAnswer, or with status {@code 400 (Bad Request)} if the conceptAnswer has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/concept-answers")
    public ResponseEntity<ConceptAnswer> createConceptAnswer(@Valid @RequestBody ConceptAnswer conceptAnswer) throws URISyntaxException {
        log.debug("REST request to save ConceptAnswer : {}", conceptAnswer);
        if (conceptAnswer.getId() != null) {
            throw new BadRequestAlertException("A new conceptAnswer cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ConceptAnswer result = conceptAnswerRepository.save(conceptAnswer);
        return ResponseEntity.created(new URI("/api/concept-answers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /concept-answers} : Updates an existing conceptAnswer.
     *
     * @param conceptAnswer the conceptAnswer to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated conceptAnswer,
     * or with status {@code 400 (Bad Request)} if the conceptAnswer is not valid,
     * or with status {@code 500 (Internal Server Error)} if the conceptAnswer couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/concept-answers")
    public ResponseEntity<ConceptAnswer> updateConceptAnswer(@Valid @RequestBody ConceptAnswer conceptAnswer) throws URISyntaxException {
        log.debug("REST request to update ConceptAnswer : {}", conceptAnswer);
        if (conceptAnswer.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ConceptAnswer result = conceptAnswerRepository.save(conceptAnswer);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, conceptAnswer.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /concept-answers} : get all the conceptAnswers.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of conceptAnswers in body.
     */
    @GetMapping("/concept-answers")
    public List<ConceptAnswer> getAllConceptAnswers() {
        log.debug("REST request to get all ConceptAnswers");
        return conceptAnswerRepository.findAll();
    }

    /**
     * {@code GET  /concept-answers/:id} : get the "id" conceptAnswer.
     *
     * @param id the id of the conceptAnswer to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the conceptAnswer, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/concept-answers/{id}")
    public ResponseEntity<ConceptAnswer> getConceptAnswer(@PathVariable Long id) {
        log.debug("REST request to get ConceptAnswer : {}", id);
        Optional<ConceptAnswer> conceptAnswer = conceptAnswerRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(conceptAnswer);
    }

    /**
     * {@code DELETE  /concept-answers/:id} : delete the "id" conceptAnswer.
     *
     * @param id the id of the conceptAnswer to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/concept-answers/{id}")
    public ResponseEntity<Void> deleteConceptAnswer(@PathVariable Long id) {
        log.debug("REST request to delete ConceptAnswer : {}", id);
        conceptAnswerRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
