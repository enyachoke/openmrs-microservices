package org.openmrs.forms.web.rest;

import org.openmrs.forms.FormsApp;
import org.openmrs.forms.domain.Field;
import org.openmrs.forms.repository.FieldRepository;

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
 * Integration tests for the {@link FieldResource} REST controller.
 */
@SpringBootTest(classes = FormsApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class FieldResourceIT {

    private static final UUID DEFAULT_UUID = UUID.randomUUID();
    private static final UUID UPDATED_UUID = UUID.randomUUID();

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final UUID DEFAULT_CONCEPT_UUID = UUID.randomUUID();
    private static final UUID UPDATED_CONCEPT_UUID = UUID.randomUUID();

    private static final String DEFAULT_TABLE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_TABLE_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_ATTRIBUTES_NAME = "AAAAAAAAAA";
    private static final String UPDATED_ATTRIBUTES_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_DEFAULT_VALUE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_SELECT_MULTIPLE = false;
    private static final Boolean UPDATED_SELECT_MULTIPLE = true;

    @Autowired
    private FieldRepository fieldRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFieldMockMvc;

    private Field field;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Field createEntity(EntityManager em) {
        Field field = new Field()
            .uuid(DEFAULT_UUID)
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .conceptUuid(DEFAULT_CONCEPT_UUID)
            .tableName(DEFAULT_TABLE_NAME)
            .attributesName(DEFAULT_ATTRIBUTES_NAME)
            .defaultValue(DEFAULT_DEFAULT_VALUE)
            .selectMultiple(DEFAULT_SELECT_MULTIPLE);
        return field;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Field createUpdatedEntity(EntityManager em) {
        Field field = new Field()
            .uuid(UPDATED_UUID)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .conceptUuid(UPDATED_CONCEPT_UUID)
            .tableName(UPDATED_TABLE_NAME)
            .attributesName(UPDATED_ATTRIBUTES_NAME)
            .defaultValue(UPDATED_DEFAULT_VALUE)
            .selectMultiple(UPDATED_SELECT_MULTIPLE);
        return field;
    }

    @BeforeEach
    public void initTest() {
        field = createEntity(em);
    }

    @Test
    @Transactional
    public void createField() throws Exception {
        int databaseSizeBeforeCreate = fieldRepository.findAll().size();
        // Create the Field
        restFieldMockMvc.perform(post("/api/fields")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(field)))
            .andExpect(status().isCreated());

        // Validate the Field in the database
        List<Field> fieldList = fieldRepository.findAll();
        assertThat(fieldList).hasSize(databaseSizeBeforeCreate + 1);
        Field testField = fieldList.get(fieldList.size() - 1);
        assertThat(testField.getUuid()).isEqualTo(DEFAULT_UUID);
        assertThat(testField.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testField.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testField.getConceptUuid()).isEqualTo(DEFAULT_CONCEPT_UUID);
        assertThat(testField.getTableName()).isEqualTo(DEFAULT_TABLE_NAME);
        assertThat(testField.getAttributesName()).isEqualTo(DEFAULT_ATTRIBUTES_NAME);
        assertThat(testField.getDefaultValue()).isEqualTo(DEFAULT_DEFAULT_VALUE);
        assertThat(testField.isSelectMultiple()).isEqualTo(DEFAULT_SELECT_MULTIPLE);
    }

    @Test
    @Transactional
    public void createFieldWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = fieldRepository.findAll().size();

        // Create the Field with an existing ID
        field.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFieldMockMvc.perform(post("/api/fields")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(field)))
            .andExpect(status().isBadRequest());

        // Validate the Field in the database
        List<Field> fieldList = fieldRepository.findAll();
        assertThat(fieldList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkUuidIsRequired() throws Exception {
        int databaseSizeBeforeTest = fieldRepository.findAll().size();
        // set the field null
        field.setUuid(null);

        // Create the Field, which fails.


        restFieldMockMvc.perform(post("/api/fields")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(field)))
            .andExpect(status().isBadRequest());

        List<Field> fieldList = fieldRepository.findAll();
        assertThat(fieldList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllFields() throws Exception {
        // Initialize the database
        fieldRepository.saveAndFlush(field);

        // Get all the fieldList
        restFieldMockMvc.perform(get("/api/fields?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(field.getId().intValue())))
            .andExpect(jsonPath("$.[*].uuid").value(hasItem(DEFAULT_UUID.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].conceptUuid").value(hasItem(DEFAULT_CONCEPT_UUID.toString())))
            .andExpect(jsonPath("$.[*].tableName").value(hasItem(DEFAULT_TABLE_NAME)))
            .andExpect(jsonPath("$.[*].attributesName").value(hasItem(DEFAULT_ATTRIBUTES_NAME)))
            .andExpect(jsonPath("$.[*].defaultValue").value(hasItem(DEFAULT_DEFAULT_VALUE)))
            .andExpect(jsonPath("$.[*].selectMultiple").value(hasItem(DEFAULT_SELECT_MULTIPLE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getField() throws Exception {
        // Initialize the database
        fieldRepository.saveAndFlush(field);

        // Get the field
        restFieldMockMvc.perform(get("/api/fields/{id}", field.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(field.getId().intValue()))
            .andExpect(jsonPath("$.uuid").value(DEFAULT_UUID.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.conceptUuid").value(DEFAULT_CONCEPT_UUID.toString()))
            .andExpect(jsonPath("$.tableName").value(DEFAULT_TABLE_NAME))
            .andExpect(jsonPath("$.attributesName").value(DEFAULT_ATTRIBUTES_NAME))
            .andExpect(jsonPath("$.defaultValue").value(DEFAULT_DEFAULT_VALUE))
            .andExpect(jsonPath("$.selectMultiple").value(DEFAULT_SELECT_MULTIPLE.booleanValue()));
    }
    @Test
    @Transactional
    public void getNonExistingField() throws Exception {
        // Get the field
        restFieldMockMvc.perform(get("/api/fields/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateField() throws Exception {
        // Initialize the database
        fieldRepository.saveAndFlush(field);

        int databaseSizeBeforeUpdate = fieldRepository.findAll().size();

        // Update the field
        Field updatedField = fieldRepository.findById(field.getId()).get();
        // Disconnect from session so that the updates on updatedField are not directly saved in db
        em.detach(updatedField);
        updatedField
            .uuid(UPDATED_UUID)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .conceptUuid(UPDATED_CONCEPT_UUID)
            .tableName(UPDATED_TABLE_NAME)
            .attributesName(UPDATED_ATTRIBUTES_NAME)
            .defaultValue(UPDATED_DEFAULT_VALUE)
            .selectMultiple(UPDATED_SELECT_MULTIPLE);

        restFieldMockMvc.perform(put("/api/fields")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedField)))
            .andExpect(status().isOk());

        // Validate the Field in the database
        List<Field> fieldList = fieldRepository.findAll();
        assertThat(fieldList).hasSize(databaseSizeBeforeUpdate);
        Field testField = fieldList.get(fieldList.size() - 1);
        assertThat(testField.getUuid()).isEqualTo(UPDATED_UUID);
        assertThat(testField.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testField.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testField.getConceptUuid()).isEqualTo(UPDATED_CONCEPT_UUID);
        assertThat(testField.getTableName()).isEqualTo(UPDATED_TABLE_NAME);
        assertThat(testField.getAttributesName()).isEqualTo(UPDATED_ATTRIBUTES_NAME);
        assertThat(testField.getDefaultValue()).isEqualTo(UPDATED_DEFAULT_VALUE);
        assertThat(testField.isSelectMultiple()).isEqualTo(UPDATED_SELECT_MULTIPLE);
    }

    @Test
    @Transactional
    public void updateNonExistingField() throws Exception {
        int databaseSizeBeforeUpdate = fieldRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFieldMockMvc.perform(put("/api/fields")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(field)))
            .andExpect(status().isBadRequest());

        // Validate the Field in the database
        List<Field> fieldList = fieldRepository.findAll();
        assertThat(fieldList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteField() throws Exception {
        // Initialize the database
        fieldRepository.saveAndFlush(field);

        int databaseSizeBeforeDelete = fieldRepository.findAll().size();

        // Delete the field
        restFieldMockMvc.perform(delete("/api/fields/{id}", field.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Field> fieldList = fieldRepository.findAll();
        assertThat(fieldList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
