package org.openmrs.forms.web.rest;

import org.openmrs.forms.domain.FormField;
import org.openmrs.forms.repository.FormFieldRepository;
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
 * REST controller for managing {@link org.openmrs.forms.domain.FormField}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class FormFieldResource {

    private final Logger log = LoggerFactory.getLogger(FormFieldResource.class);

    private static final String ENTITY_NAME = "formsFormField";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FormFieldRepository formFieldRepository;

    public FormFieldResource(FormFieldRepository formFieldRepository) {
        this.formFieldRepository = formFieldRepository;
    }

    /**
     * {@code POST  /form-fields} : Create a new formField.
     *
     * @param formField the formField to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new formField, or with status {@code 400 (Bad Request)} if the formField has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/form-fields")
    public ResponseEntity<FormField> createFormField(@Valid @RequestBody FormField formField) throws URISyntaxException {
        log.debug("REST request to save FormField : {}", formField);
        if (formField.getId() != null) {
            throw new BadRequestAlertException("A new formField cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FormField result = formFieldRepository.save(formField);
        return ResponseEntity.created(new URI("/api/form-fields/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /form-fields} : Updates an existing formField.
     *
     * @param formField the formField to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated formField,
     * or with status {@code 400 (Bad Request)} if the formField is not valid,
     * or with status {@code 500 (Internal Server Error)} if the formField couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/form-fields")
    public ResponseEntity<FormField> updateFormField(@Valid @RequestBody FormField formField) throws URISyntaxException {
        log.debug("REST request to update FormField : {}", formField);
        if (formField.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        FormField result = formFieldRepository.save(formField);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, formField.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /form-fields} : get all the formFields.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of formFields in body.
     */
    @GetMapping("/form-fields")
    public List<FormField> getAllFormFields() {
        log.debug("REST request to get all FormFields");
        return formFieldRepository.findAll();
    }

    /**
     * {@code GET  /form-fields/:id} : get the "id" formField.
     *
     * @param id the id of the formField to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the formField, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/form-fields/{id}")
    public ResponseEntity<FormField> getFormField(@PathVariable Long id) {
        log.debug("REST request to get FormField : {}", id);
        Optional<FormField> formField = formFieldRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(formField);
    }

    /**
     * {@code DELETE  /form-fields/:id} : delete the "id" formField.
     *
     * @param id the id of the formField to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/form-fields/{id}")
    public ResponseEntity<Void> deleteFormField(@PathVariable Long id) {
        log.debug("REST request to delete FormField : {}", id);
        formFieldRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
