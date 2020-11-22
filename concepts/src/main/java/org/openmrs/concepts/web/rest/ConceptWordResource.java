package org.openmrs.concepts.web.rest;

import org.openmrs.concepts.domain.ConceptWord;
import org.openmrs.concepts.repository.ConceptWordRepository;
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
 * REST controller for managing {@link org.openmrs.concepts.domain.ConceptWord}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ConceptWordResource {

    private final Logger log = LoggerFactory.getLogger(ConceptWordResource.class);

    private static final String ENTITY_NAME = "conceptsConceptWord";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ConceptWordRepository conceptWordRepository;

    public ConceptWordResource(ConceptWordRepository conceptWordRepository) {
        this.conceptWordRepository = conceptWordRepository;
    }

    /**
     * {@code POST  /concept-words} : Create a new conceptWord.
     *
     * @param conceptWord the conceptWord to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new conceptWord, or with status {@code 400 (Bad Request)} if the conceptWord has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/concept-words")
    public ResponseEntity<ConceptWord> createConceptWord(@Valid @RequestBody ConceptWord conceptWord) throws URISyntaxException {
        log.debug("REST request to save ConceptWord : {}", conceptWord);
        if (conceptWord.getId() != null) {
            throw new BadRequestAlertException("A new conceptWord cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ConceptWord result = conceptWordRepository.save(conceptWord);
        return ResponseEntity.created(new URI("/api/concept-words/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /concept-words} : Updates an existing conceptWord.
     *
     * @param conceptWord the conceptWord to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated conceptWord,
     * or with status {@code 400 (Bad Request)} if the conceptWord is not valid,
     * or with status {@code 500 (Internal Server Error)} if the conceptWord couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/concept-words")
    public ResponseEntity<ConceptWord> updateConceptWord(@Valid @RequestBody ConceptWord conceptWord) throws URISyntaxException {
        log.debug("REST request to update ConceptWord : {}", conceptWord);
        if (conceptWord.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ConceptWord result = conceptWordRepository.save(conceptWord);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, conceptWord.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /concept-words} : get all the conceptWords.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of conceptWords in body.
     */
    @GetMapping("/concept-words")
    public List<ConceptWord> getAllConceptWords() {
        log.debug("REST request to get all ConceptWords");
        return conceptWordRepository.findAll();
    }

    /**
     * {@code GET  /concept-words/:id} : get the "id" conceptWord.
     *
     * @param id the id of the conceptWord to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the conceptWord, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/concept-words/{id}")
    public ResponseEntity<ConceptWord> getConceptWord(@PathVariable Long id) {
        log.debug("REST request to get ConceptWord : {}", id);
        Optional<ConceptWord> conceptWord = conceptWordRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(conceptWord);
    }

    /**
     * {@code DELETE  /concept-words/:id} : delete the "id" conceptWord.
     *
     * @param id the id of the conceptWord to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/concept-words/{id}")
    public ResponseEntity<Void> deleteConceptWord(@PathVariable Long id) {
        log.debug("REST request to delete ConceptWord : {}", id);
        conceptWordRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
