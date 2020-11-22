package org.openmrs.forms.web.rest;

import org.openmrs.forms.FormsApp;
import org.openmrs.forms.domain.FormField;
import org.openmrs.forms.repository.FormFieldRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link FormFieldResource} REST controller.
 */
@SpringBootTest(classes = FormsApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class FormFieldResourceIT {

    private static final UUID DEFAULT_UUID = UUID.randomUUID();
    private static final UUID UPDATED_UUID = UUID.randomUUID();

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_FIELD_NUMBER = 1;
    private static final Integer UPDATED_FIELD_NUMBER = 2;

    private static final String DEFAULT_FIELD_PART = "AAAAAAAAAA";
    private static final String UPDATED_FIELD_PART = "BBBBBBBBBB";

    private static final Integer DEFAULT_PAGE_NUMBER = 1;
    private static final Integer UPDATED_PAGE_NUMBER = 2;

    private static final Integer DEFAULT_MIN_OCCURS = 1;
    private static final Integer UPDATED_MIN_OCCURS = 2;

    private static final Integer DEFAULT_MAX_OCCURS = 1;
    private static final Integer UPDATED_MAX_OCCURS = 2;

    private static final Boolean DEFAULT_IS_REQUIRED = false;
    private static final Boolean UPDATED_IS_REQUIRED = true;

    private static final Float DEFAULT_SORT_WEIGHT = 1F;
    private static final Float UPDATED_SORT_WEIGHT = 2F;

    @Autowired
    private FormFieldRepository formFieldRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFormFieldMockMvc;

    private FormField formField;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FormField createEntity(EntityManager em) {
        FormField formField = new FormField()
            .uuid(DEFAULT_UUID)
            .name(DEFAULT_NAME)
            .fieldNumber(DEFAULT_FIELD_NUMBER)
            .fieldPart(DEFAULT_FIELD_PART)
            .pageNumber(DEFAULT_PAGE_NUMBER)
            .minOccurs(DEFAULT_MIN_OCCURS)
            .maxOccurs(DEFAULT_MAX_OCCURS)
            .isRequired(DEFAULT_IS_REQUIRED)
            .sortWeight(DEFAULT_SORT_WEIGHT);
        return formField;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FormField createUpdatedEntity(EntityManager em) {
        FormField formField = new FormField()
            .uuid(UPDATED_UUID)
            .name(UPDATED_NAME)
            .fieldNumber(UPDATED_FIELD_NUMBER)
            .fieldPart(UPDATED_FIELD_PART)
            .pageNumber(UPDATED_PAGE_NUMBER)
            .minOccurs(UPDATED_MIN_OCCURS)
            .maxOccurs(UPDATED_MAX_OCCURS)
            .isRequired(UPDATED_IS_REQUIRED)
            .sortWeight(UPDATED_SORT_WEIGHT);
        return formField;
    }

    @BeforeEach
    public void initTest() {
        formField = createEntity(em);
    }

    @Test
    @Transactional
    public void createFormField() throws Exception {
        int databaseSizeBeforeCreate = formFieldRepository.findAll().size();
        // Create the FormField
        restFormFieldMockMvc.perform(post("/api/form-fields")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(formField)))
            .andExpect(status().isCreated());

        // Validate the FormField in the database
        List<FormField> formFieldList = formFieldRepository.findAll();
        assertThat(formFieldList).hasSize(databaseSizeBeforeCreate + 1);
        FormField testFormField = formFieldList.get(formFieldList.size() - 1);
        assertThat(testFormField.getUuid()).isEqualTo(DEFAULT_UUID);
        assertThat(testFormField.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testFormField.getFieldNumber()).isEqualTo(DEFAULT_FIELD_NUMBER);
        assertThat(testFormField.getFieldPart()).isEqualTo(DEFAULT_FIELD_PART);
        assertThat(testFormField.getPageNumber()).isEqualTo(DEFAULT_PAGE_NUMBER);
        assertThat(testFormField.getMinOccurs()).isEqualTo(DEFAULT_MIN_OCCURS);
        assertThat(testFormField.getMaxOccurs()).isEqualTo(DEFAULT_MAX_OCCURS);
        assertThat(testFormField.isIsRequired()).isEqualTo(DEFAULT_IS_REQUIRED);
        assertThat(testFormField.getSortWeight()).isEqualTo(DEFAULT_SORT_WEIGHT);
    }

    @Test
    @Transactional
    public void createFormFieldWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = formFieldRepository.findAll().size();

        // Create the FormField with an existing ID
        formField.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFormFieldMockMvc.perform(post("/api/form-fields")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(formField)))
            .andExpect(status().isBadRequest());

        // Validate the FormField in the database
        List<FormField> formFieldList = formFieldRepository.findAll();
        assertThat(formFieldList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkUuidIsRequired() throws Exception {
        int databaseSizeBeforeTest = formFieldRepository.findAll().size();
        // set the field null
        formField.setUuid(null);

        // Create the FormField, which fails.


        restFormFieldMockMvc.perform(post("/api/form-fields")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(formField)))
            .andExpect(status().isBadRequest());

        List<FormField> formFieldList = formFieldRepository.findAll();
        assertThat(formFieldList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllFormFields() throws Exception {
        // Initialize the database
        formFieldRepository.saveAndFlush(formField);

        // Get all the formFieldList
        restFormFieldMockMvc.perform(get("/api/form-fields?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(formField.getId().intValue())))
            .andExpect(jsonPath("$.[*].uuid").value(hasItem(DEFAULT_UUID.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].fieldNumber").value(hasItem(DEFAULT_FIELD_NUMBER)))
            .andExpect(jsonPath("$.[*].fieldPart").value(hasItem(DEFAULT_FIELD_PART)))
            .andExpect(jsonPath("$.[*].pageNumber").value(hasItem(DEFAULT_PAGE_NUMBER)))
            .andExpect(jsonPath("$.[*].minOccurs").value(hasItem(DEFAULT_MIN_OCCURS)))
            .andExpect(jsonPath("$.[*].maxOccurs").value(hasItem(DEFAULT_MAX_OCCURS)))
            .andExpect(jsonPath("$.[*].isRequired").value(hasItem(DEFAULT_IS_REQUIRED.booleanValue())))
            .andExpect(jsonPath("$.[*].sortWeight").value(hasItem(DEFAULT_SORT_WEIGHT.doubleValue())));
    }
    
    @Test
    @Transactional
    public void getFormField() throws Exception {
        // Initialize the database
        formFieldRepository.saveAndFlush(formField);

        // Get the formField
        restFormFieldMockMvc.perform(get("/api/form-fields/{id}", formField.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(formField.getId().intValue()))
            .andExpect(jsonPath("$.uuid").value(DEFAULT_UUID.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.fieldNumber").value(DEFAULT_FIELD_NUMBER))
            .andExpect(jsonPath("$.fieldPart").value(DEFAULT_FIELD_PART))
            .andExpect(jsonPath("$.pageNumber").value(DEFAULT_PAGE_NUMBER))
            .andExpect(jsonPath("$.minOccurs").value(DEFAULT_MIN_OCCURS))
            .andExpect(jsonPath("$.maxOccurs").value(DEFAULT_MAX_OCCURS))
            .andExpect(jsonPath("$.isRequired").value(DEFAULT_IS_REQUIRED.booleanValue()))
            .andExpect(jsonPath("$.sortWeight").value(DEFAULT_SORT_WEIGHT.doubleValue()));
    }
    @Test
    @Transactional
    public void getNonExistingFormField() throws Exception {
        // Get the formField
        restFormFieldMockMvc.perform(get("/api/form-fields/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFormField() throws Exception {
        // Initialize the database
        formFieldRepository.saveAndFlush(formField);

        int databaseSizeBeforeUpdate = formFieldRepository.findAll().size();

        // Update the formField
        FormField updatedFormField = formFieldRepository.findById(formField.getId()).get();
        // Disconnect from session so that the updates on updatedFormField are not directly saved in db
        em.detach(updatedFormField);
        updatedFormField
            .uuid(UPDATED_UUID)
            .name(UPDATED_NAME)
            .fieldNumber(UPDATED_FIELD_NUMBER)
            .fieldPart(UPDATED_FIELD_PART)
            .pageNumber(UPDATED_PAGE_NUMBER)
            .minOccurs(UPDATED_MIN_OCCURS)
            .maxOccurs(UPDATED_MAX_OCCURS)
            .isRequired(UPDATED_IS_REQUIRED)
            .sortWeight(UPDATED_SORT_WEIGHT);

        restFormFieldMockMvc.perform(put("/api/form-fields")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedFormField)))
            .andExpect(status().isOk());

        // Validate the FormField in the database
        List<FormField> formFieldList = formFieldRepository.findAll();
        assertThat(formFieldList).hasSize(databaseSizeBeforeUpdate);
        FormField testFormField = formFieldList.get(formFieldList.size() - 1);
        assertThat(testFormField.getUuid()).isEqualTo(UPDATED_UUID);
        assertThat(testFormField.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testFormField.getFieldNumber()).isEqualTo(UPDATED_FIELD_NUMBER);
        assertThat(testFormField.getFieldPart()).isEqualTo(UPDATED_FIELD_PART);
        assertThat(testFormField.getPageNumber()).isEqualTo(UPDATED_PAGE_NUMBER);
        assertThat(testFormField.getMinOccurs()).isEqualTo(UPDATED_MIN_OCCURS);
        assertThat(testFormField.getMaxOccurs()).isEqualTo(UPDATED_MAX_OCCURS);
        assertThat(testFormField.isIsRequired()).isEqualTo(UPDATED_IS_REQUIRED);
        assertThat(testFormField.getSortWeight()).isEqualTo(UPDATED_SORT_WEIGHT);
    }

    @Test
    @Transactional
    public void updateNonExistingFormField() throws Exception {
        int databaseSizeBeforeUpdate = formFieldRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFormFieldMockMvc.perform(put("/api/form-fields")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(formField)))
            .andExpect(status().isBadRequest());

        // Validate the FormField in the database
        List<FormField> formFieldList = formFieldRepository.findAll();
        assertThat(formFieldList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteFormField() throws Exception {
        // Initialize the database
        formFieldRepository.saveAndFlush(formField);

        int databaseSizeBeforeDelete = formFieldRepository.findAll().size();

        // Delete the formField
        restFormFieldMockMvc.perform(delete("/api/form-fields/{id}", formField.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<FormField> formFieldList = formFieldRepository.findAll();
        assertThat(formFieldList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
