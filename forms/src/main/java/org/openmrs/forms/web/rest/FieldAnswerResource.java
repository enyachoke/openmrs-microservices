package org.openmrs.forms.web.rest;

import org.openmrs.forms.domain.FieldAnswer;
import org.openmrs.forms.repository.FieldAnswerRepository;
import org.openmrs.forms.web.rest.errors.BadRequestAlertException;

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
 * REST controller for managing {@link org.openmrs.forms.domain.FieldAnswer}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class FieldAnswerResource {

    private final Logger log = LoggerFactory.getLogger(FieldAnswerResource.class);

    private static final String ENTITY_NAME = "formsFieldAnswer";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FieldAnswerRepository fieldAnswerRepository;

    public FieldAnswerResource(FieldAnswerRepository fieldAnswerRepository) {
        this.fieldAnswerRepository = fieldAnswerRepository;
    }

    /**
     * {@code POST  /field-answers} : Create a new fieldAnswer.
     *
     * @param fieldAnswer the fieldAnswer to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new fieldAnswer, or with status {@code 400 (Bad Request)} if the fieldAnswer has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/field-answers")
    public ResponseEntity<FieldAnswer> createFieldAnswer(@Valid @RequestBody FieldAnswer fieldAnswer) throws URISyntaxException {
        log.debug("REST request to save FieldAnswer : {}", fieldAnswer);
        if (fieldAnswer.getId() != null) {
            throw new BadRequestAlertException("A new fieldAnswer cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FieldAnswer result = fieldAnswerRepository.save(fieldAnswer);
        return ResponseEntity.created(new URI("/api/field-answers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /field-answers} : Updates an existing fieldAnswer.
     *
     * @param fieldAnswer the fieldAnswer to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated fieldAnswer,
     * or with status {@code 400 (Bad Request)} if the fieldAnswer is not valid,
     * or with status {@code 500 (Internal Server Error)} if the fieldAnswer couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/field-answers")
    public ResponseEntity<FieldAnswer> updateFieldAnswer(@Valid @RequestBody FieldAnswer fieldAnswer) throws URISyntaxException {
        log.debug("REST request to update FieldAnswer : {}", fieldAnswer);
        if (fieldAnswer.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        FieldAnswer result = fieldAnswerRepository.save(fieldAnswer);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, fieldAnswer.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /field-answers} : get all the fieldAnswers.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of fieldAnswers in body.
     */
    @GetMapping("/field-answers")
    public List<FieldAnswer> getAllFieldAnswers() {
        log.debug("REST request to get all FieldAnswers");
        return fieldAnswerRepository.findAll();
    }

    /**
     * {@code GET  /field-answers/:id} : get the "id" fieldAnswer.
     *
     * @param id the id of the fieldAnswer to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the fieldAnswer, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/field-answers/{id}")
    public ResponseEntity<FieldAnswer> getFieldAnswer(@PathVariable Long id) {
        log.debug("REST request to get FieldAnswer : {}", id);
        Optional<FieldAnswer> fieldAnswer = fieldAnswerRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(fieldAnswer);
    }

    /**
     * {@code DELETE  /field-answers/:id} : delete the "id" fieldAnswer.
     *
     * @param id the id of the fieldAnswer to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/field-answers/{id}")
    public ResponseEntity<Void> deleteFieldAnswer(@PathVariable Long id) {
        log.debug("REST request to delete FieldAnswer : {}", id);
        fieldAnswerRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
