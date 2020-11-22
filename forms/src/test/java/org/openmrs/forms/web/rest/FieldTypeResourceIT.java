package org.openmrs.forms.web.rest;

import org.openmrs.forms.FormsApp;
import org.openmrs.forms.domain.FieldType;
import org.openmrs.forms.repository.FieldTypeRepository;

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
 * Integration tests for the {@link FieldTypeResource} REST controller.
 */
@SpringBootTest(classes = FormsApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class FieldTypeResourceIT {

    private static final UUID DEFAULT_UUID = UUID.randomUUID();
    private static final UUID UPDATED_UUID = UUID.randomUUID();

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_SET = false;
    private static final Boolean UPDATED_IS_SET = true;

    @Autowired
    private FieldTypeRepository fieldTypeRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFieldTypeMockMvc;

    private FieldType fieldType;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FieldType createEntity(EntityManager em) {
        FieldType fieldType = new FieldType()
            .uuid(DEFAULT_UUID)
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .isSet(DEFAULT_IS_SET);
        return fieldType;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FieldType createUpdatedEntity(EntityManager em) {
        FieldType fieldType = new FieldType()
            .uuid(UPDATED_UUID)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .isSet(UPDATED_IS_SET);
        return fieldType;
    }

    @BeforeEach
    public void initTest() {
        fieldType = createEntity(em);
    }

    @Test
    @Transactional
    public void createFieldType() throws Exception {
        int databaseSizeBeforeCreate = fieldTypeRepository.findAll().size();
        // Create the FieldType
        restFieldTypeMockMvc.perform(post("/api/field-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(fieldType)))
            .andExpect(status().isCreated());

        // Validate the FieldType in the database
        List<FieldType> fieldTypeList = fieldTypeRepository.findAll();
        assertThat(fieldTypeList).hasSize(databaseSizeBeforeCreate + 1);
        FieldType testFieldType = fieldTypeList.get(fieldTypeList.size() - 1);
        assertThat(testFieldType.getUuid()).isEqualTo(DEFAULT_UUID);
        assertThat(testFieldType.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testFieldType.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testFieldType.isIsSet()).isEqualTo(DEFAULT_IS_SET);
    }

    @Test
    @Transactional
    public void createFieldTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = fieldTypeRepository.findAll().size();

        // Create the FieldType with an existing ID
        fieldType.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFieldTypeMockMvc.perform(post("/api/field-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(fieldType)))
            .andExpect(status().isBadRequest());

        // Validate the FieldType in the database
        List<FieldType> fieldTypeList = fieldTypeRepository.findAll();
        assertThat(fieldTypeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkUuidIsRequired() throws Exception {
        int databaseSizeBeforeTest = fieldTypeRepository.findAll().size();
        // set the field null
        fieldType.setUuid(null);

        // Create the FieldType, which fails.


        restFieldTypeMockMvc.perform(post("/api/field-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(fieldType)))
            .andExpect(status().isBadRequest());

        List<FieldType> fieldTypeList = fieldTypeRepository.findAll();
        assertThat(fieldTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllFieldTypes() throws Exception {
        // Initialize the database
        fieldTypeRepository.saveAndFlush(fieldType);

        // Get all the fieldTypeList
        restFieldTypeMockMvc.perform(get("/api/field-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fieldType.getId().intValue())))
            .andExpect(jsonPath("$.[*].uuid").value(hasItem(DEFAULT_UUID.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].isSet").value(hasItem(DEFAULT_IS_SET.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getFieldType() throws Exception {
        // Initialize the database
        fieldTypeRepository.saveAndFlush(fieldType);

        // Get the fieldType
        restFieldTypeMockMvc.perform(get("/api/field-types/{id}", fieldType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(fieldType.getId().intValue()))
            .andExpect(jsonPath("$.uuid").value(DEFAULT_UUID.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.isSet").value(DEFAULT_IS_SET.booleanValue()));
    }
    @Test
    @Transactional
    public void getNonExistingFieldType() throws Exception {
        // Get the fieldType
        restFieldTypeMockMvc.perform(get("/api/field-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFieldType() throws Exception {
        // Initialize the database
        fieldTypeRepository.saveAndFlush(fieldType);

        int databaseSizeBeforeUpdate = fieldTypeRepository.findAll().size();

        // Update the fieldType
        FieldType updatedFieldType = fieldTypeRepository.findById(fieldType.getId()).get();
        // Disconnect from session so that the updates on updatedFieldType are not directly saved in db
        em.detach(updatedFieldType);
        updatedFieldType
            .uuid(UPDATED_UUID)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .isSet(UPDATED_IS_SET);

        restFieldTypeMockMvc.perform(put("/api/field-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedFieldType)))
            .andExpect(status().isOk());

        // Validate the FieldType in the database
        List<FieldType> fieldTypeList = fieldTypeRepository.findAll();
        assertThat(fieldTypeList).hasSize(databaseSizeBeforeUpdate);
        FieldType testFieldType = fieldTypeList.get(fieldTypeList.size() - 1);
        assertThat(testFieldType.getUuid()).isEqualTo(UPDATED_UUID);
        assertThat(testFieldType.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testFieldType.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testFieldType.isIsSet()).isEqualTo(UPDATED_IS_SET);
    }

    @Test
    @Transactional
    public void updateNonExistingFieldType() throws Exception {
        int databaseSizeBeforeUpdate = fieldTypeRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFieldTypeMockMvc.perform(put("/api/field-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(fieldType)))
            .andExpect(status().isBadRequest());

        // Validate the FieldType in the database
        List<FieldType> fieldTypeList = fieldTypeRepository.findAll();
        assertThat(fieldTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteFieldType() throws Exception {
        // Initialize the database
        fieldTypeRepository.saveAndFlush(fieldType);

        int databaseSizeBeforeDelete = fieldTypeRepository.findAll().size();

        // Delete the fieldType
        restFieldTypeMockMvc.perform(delete("/api/field-types/{id}", fieldType.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<FieldType> fieldTypeList = fieldTypeRepository.findAll();
        assertThat(fieldTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
