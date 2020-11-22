package org.openmrs.concepts.web.rest;

import org.openmrs.concepts.domain.ConceptStopWord;
import org.openmrs.concepts.repository.ConceptStopWordRepository;
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
 * REST controller for managing {@link org.openmrs.concepts.domain.ConceptStopWord}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ConceptStopWordResource {

    private final Logger log = LoggerFactory.getLogger(ConceptStopWordResource.class);

    private static final String ENTITY_NAME = "conceptsConceptStopWord";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ConceptStopWordRepository conceptStopWordRepository;

    public ConceptStopWordResource(ConceptStopWordRepository conceptStopWordRepository) {
        this.conceptStopWordRepository = conceptStopWordRepository;
    }

    /**
     * {@code POST  /concept-stop-words} : Create a new conceptStopWord.
     *
     * @param conceptStopWord the conceptStopWord to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new conceptStopWord, or with status {@code 400 (Bad Request)} if the conceptStopWord has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/concept-stop-words")
    public ResponseEntity<ConceptStopWord> createConceptStopWord(@Valid @RequestBody ConceptStopWord conceptStopWord) throws URISyntaxException {
        log.debug("REST request to save ConceptStopWord : {}", conceptStopWord);
        if (conceptStopWord.getId() != null) {
            throw new BadRequestAlertException("A new conceptStopWord cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ConceptStopWord result = conceptStopWordRepository.save(conceptStopWord);
        return ResponseEntity.created(new URI("/api/concept-stop-words/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /concept-stop-words} : Updates an existing conceptStopWord.
     *
     * @param conceptStopWord the conceptStopWord to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated conceptStopWord,
     * or with status {@code 400 (Bad Request)} if the conceptStopWord is not valid,
     * or with status {@code 500 (Internal Server Error)} if the conceptStopWord couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/concept-stop-words")
    public ResponseEntity<ConceptStopWord> updateConceptStopWord(@Valid @RequestBody ConceptStopWord conceptStopWord) throws URISyntaxException {
        log.debug("REST request to update ConceptStopWord : {}", conceptStopWord);
        if (conceptStopWord.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ConceptStopWord result = conceptStopWordRepository.save(conceptStopWord);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, conceptStopWord.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /concept-stop-words} : get all the conceptStopWords.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of conceptStopWords in body.
     */
    @GetMapping("/concept-stop-words")
    public List<ConceptStopWord> getAllConceptStopWords() {
        log.debug("REST request to get all ConceptStopWords");
        return conceptStopWordRepository.findAll();
    }

    /**
     * {@code GET  /concept-stop-words/:id} : get the "id" conceptStopWord.
     *
     * @param id the id of the conceptStopWord to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the conceptStopWord, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/concept-stop-words/{id}")
    public ResponseEntity<ConceptStopWord> getConceptStopWord(@PathVariable Long id) {
        log.debug("REST request to get ConceptStopWord : {}", id);
        Optional<ConceptStopWord> conceptStopWord = conceptStopWordRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(conceptStopWord);
    }

    /**
     * {@code DELETE  /concept-stop-words/:id} : delete the "id" conceptStopWord.
     *
     * @param id the id of the conceptStopWord to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/concept-stop-words/{id}")
    public ResponseEntity<Void> deleteConceptStopWord(@PathVariable Long id) {
        log.debug("REST request to delete ConceptStopWord : {}", id);
        conceptStopWordRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
