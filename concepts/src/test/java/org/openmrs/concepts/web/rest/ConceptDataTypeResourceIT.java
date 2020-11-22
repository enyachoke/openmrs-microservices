package org.openmrs.concepts.web.rest;

import org.openmrs.concepts.ConceptsApp;
import org.openmrs.concepts.domain.ConceptDataType;
import org.openmrs.concepts.repository.ConceptDataTypeRepository;

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
 * Integration tests for the {@link ConceptDataTypeResource} REST controller.
 */
@SpringBootTest(classes = ConceptsApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ConceptDataTypeResourceIT {

    private static final UUID DEFAULT_UUID = UUID.randomUUID();
    private static final UUID UPDATED_UUID = UUID.randomUUID();

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_HL_7_ABBREVIATION = "AAAAAAAAAA";
    private static final String UPDATED_HL_7_ABBREVIATION = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private ConceptDataTypeRepository conceptDataTypeRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restConceptDataTypeMockMvc;

    private ConceptDataType conceptDataType;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ConceptDataType createEntity(EntityManager em) {
        ConceptDataType conceptDataType = new ConceptDataType()
            .uuid(DEFAULT_UUID)
            .name(DEFAULT_NAME)
            .hl7Abbreviation(DEFAULT_HL_7_ABBREVIATION)
            .description(DEFAULT_DESCRIPTION);
        return conceptDataType;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ConceptDataType createUpdatedEntity(EntityManager em) {
        ConceptDataType conceptDataType = new ConceptDataType()
            .uuid(UPDATED_UUID)
            .name(UPDATED_NAME)
            .hl7Abbreviation(UPDATED_HL_7_ABBREVIATION)
            .description(UPDATED_DESCRIPTION);
        return conceptDataType;
    }

    @BeforeEach
    public void initTest() {
        conceptDataType = createEntity(em);
    }

    @Test
    @Transactional
    public void createConceptDataType() throws Exception {
        int databaseSizeBeforeCreate = conceptDataTypeRepository.findAll().size();
        // Create the ConceptDataType
        restConceptDataTypeMockMvc.perform(post("/api/concept-data-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(conceptDataType)))
            .andExpect(status().isCreated());

        // Validate the ConceptDataType in the database
        List<ConceptDataType> conceptDataTypeList = conceptDataTypeRepository.findAll();
        assertThat(conceptDataTypeList).hasSize(databaseSizeBeforeCreate + 1);
        ConceptDataType testConceptDataType = conceptDataTypeList.get(conceptDataTypeList.size() - 1);
        assertThat(testConceptDataType.getUuid()).isEqualTo(DEFAULT_UUID);
        assertThat(testConceptDataType.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testConceptDataType.getHl7Abbreviation()).isEqualTo(DEFAULT_HL_7_ABBREVIATION);
        assertThat(testConceptDataType.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createConceptDataTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = conceptDataTypeRepository.findAll().size();

        // Create the ConceptDataType with an existing ID
        conceptDataType.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restConceptDataTypeMockMvc.perform(post("/api/concept-data-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(conceptDataType)))
            .andExpect(status().isBadRequest());

        // Validate the ConceptDataType in the database
        List<ConceptDataType> conceptDataTypeList = conceptDataTypeRepository.findAll();
        assertThat(conceptDataTypeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkUuidIsRequired() throws Exception {
        int databaseSizeBeforeTest = conceptDataTypeRepository.findAll().size();
        // set the field null
        conceptDataType.setUuid(null);

        // Create the ConceptDataType, which fails.


        restConceptDataTypeMockMvc.perform(post("/api/concept-data-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(conceptDataType)))
            .andExpect(status().isBadRequest());

        List<ConceptDataType> conceptDataTypeList = conceptDataTypeRepository.findAll();
        assertThat(conceptDataTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllConceptDataTypes() throws Exception {
        // Initialize the database
        conceptDataTypeRepository.saveAndFlush(conceptDataType);

        // Get all the conceptDataTypeList
        restConceptDataTypeMockMvc.perform(get("/api/concept-data-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(conceptDataType.getId().intValue())))
            .andExpect(jsonPath("$.[*].uuid").value(hasItem(DEFAULT_UUID.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].hl7Abbreviation").value(hasItem(DEFAULT_HL_7_ABBREVIATION)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }
    
    @Test
    @Transactional
    public void getConceptDataType() throws Exception {
        // Initialize the database
        conceptDataTypeRepository.saveAndFlush(conceptDataType);

        // Get the conceptDataType
        restConceptDataTypeMockMvc.perform(get("/api/concept-data-types/{id}", conceptDataType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(conceptDataType.getId().intValue()))
            .andExpect(jsonPath("$.uuid").value(DEFAULT_UUID.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.hl7Abbreviation").value(DEFAULT_HL_7_ABBREVIATION))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }
    @Test
    @Transactional
    public void getNonExistingConceptDataType() throws Exception {
        // Get the conceptDataType
        restConceptDataTypeMockMvc.perform(get("/api/concept-data-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateConceptDataType() throws Exception {
        // Initialize the database
        conceptDataTypeRepository.saveAndFlush(conceptDataType);

        int databaseSizeBeforeUpdate = conceptDataTypeRepository.findAll().size();

        // Update the conceptDataType
        ConceptDataType updatedConceptDataType = conceptDataTypeRepository.findById(conceptDataType.getId()).get();
        // Disconnect from session so that the updates on updatedConceptDataType are not directly saved in db
        em.detach(updatedConceptDataType);
        updatedConceptDataType
            .uuid(UPDATED_UUID)
            .name(UPDATED_NAME)
            .hl7Abbreviation(UPDATED_HL_7_ABBREVIATION)
            .description(UPDATED_DESCRIPTION);

        restConceptDataTypeMockMvc.perform(put("/api/concept-data-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedConceptDataType)))
            .andExpect(status().isOk());

        // Validate the ConceptDataType in the database
        List<ConceptDataType> conceptDataTypeList = conceptDataTypeRepository.findAll();
        assertThat(conceptDataTypeList).hasSize(databaseSizeBeforeUpdate);
        ConceptDataType testConceptDataType = conceptDataTypeList.get(conceptDataTypeList.size() - 1);
        assertThat(testConceptDataType.getUuid()).isEqualTo(UPDATED_UUID);
        assertThat(testConceptDataType.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testConceptDataType.getHl7Abbreviation()).isEqualTo(UPDATED_HL_7_ABBREVIATION);
        assertThat(testConceptDataType.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingConceptDataType() throws Exception {
        int databaseSizeBeforeUpdate = conceptDataTypeRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restConceptDataTypeMockMvc.perform(put("/api/concept-data-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(conceptDataType)))
            .andExpect(status().isBadRequest());

        // Validate the ConceptDataType in the database
        List<ConceptDataType> conceptDataTypeList = conceptDataTypeRepository.findAll();
        assertThat(conceptDataTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteConceptDataType() throws Exception {
        // Initialize the database
        conceptDataTypeRepository.saveAndFlush(conceptDataType);

        int databaseSizeBeforeDelete = conceptDataTypeRepository.findAll().size();

        // Delete the conceptDataType
        restConceptDataTypeMockMvc.perform(delete("/api/concept-data-types/{id}", conceptDataType.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ConceptDataType> conceptDataTypeList = conceptDataTypeRepository.findAll();
        assertThat(conceptDataTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
