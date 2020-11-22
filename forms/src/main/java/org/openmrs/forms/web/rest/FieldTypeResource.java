package org.openmrs.forms.web.rest;

import org.openmrs.forms.domain.FieldType;
import org.openmrs.forms.repository.FieldTypeRepository;
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
 * REST controller for managing {@link org.openmrs.forms.domain.FieldType}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class FieldTypeResource {

    private final Logger log = LoggerFactory.getLogger(FieldTypeResource.class);

    private static final String ENTITY_NAME = "formsFieldType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FieldTypeRepository fieldTypeRepository;

    public FieldTypeResource(FieldTypeRepository fieldTypeRepository) {
        this.fieldTypeRepository = fieldTypeRepository;
    }

    /**
     * {@code POST  /field-types} : Create a new fieldType.
     *
     * @param fieldType the fieldType to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new fieldType, or with status {@code 400 (Bad Request)} if the fieldType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/field-types")
    public ResponseEntity<FieldType> createFieldType(@Valid @RequestBody FieldType fieldType) throws URISyntaxException {
        log.debug("REST request to save FieldType : {}", fieldType);
        if (fieldType.getId() != null) {
            throw new BadRequestAlertException("A new fieldType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FieldType result = fieldTypeRepository.save(fieldType);
        return ResponseEntity.created(new URI("/api/field-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /field-types} : Updates an existing fieldType.
     *
     * @param fieldType the fieldType to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated fieldType,
     * or with status {@code 400 (Bad Request)} if the fieldType is not valid,
     * or with status {@code 500 (Internal Server Error)} if the fieldType couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/field-types")
    public ResponseEntity<FieldType> updateFieldType(@Valid @RequestBody FieldType fieldType) throws URISyntaxException {
        log.debug("REST request to update FieldType : {}", fieldType);
        if (fieldType.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        FieldType result = fieldTypeRepository.save(fieldType);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, fieldType.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /field-types} : get all the fieldTypes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of fieldTypes in body.
     */
    @GetMapping("/field-types")
    public List<FieldType> getAllFieldTypes() {
        log.debug("REST request to get all FieldTypes");
        return fieldTypeRepository.findAll();
    }

    /**
     * {@code GET  /field-types/:id} : get the "id" fieldType.
     *
     * @param id the id of the fieldType to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the fieldType, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/field-types/{id}")
    public ResponseEntity<FieldType> getFieldType(@PathVariable Long id) {
        log.debug("REST request to get FieldType : {}", id);
        Optional<FieldType> fieldType = fieldTypeRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(fieldType);
    }

    /**
     * {@code DELETE  /field-types/:id} : delete the "id" fieldType.
     *
     * @param id the id of the fieldType to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/field-types/{id}")
    public ResponseEntity<Void> deleteFieldType(@PathVariable Long id) {
        log.debug("REST request to delete FieldType : {}", id);
        fieldTypeRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
