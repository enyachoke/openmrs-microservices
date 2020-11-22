package org.openmrs.concepts.web.rest;

import org.openmrs.concepts.ConceptsApp;
import org.openmrs.concepts.domain.ConceptName;
import org.openmrs.concepts.repository.ConceptNameRepository;

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
 * Integration tests for the {@link ConceptNameResource} REST controller.
 */
@SpringBootTest(classes = ConceptsApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ConceptNameResourceIT {

    private static final UUID DEFAULT_UUID = UUID.randomUUID();
    private static final UUID UPDATED_UUID = UUID.randomUUID();

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LOCALE = "AAAAAAAAAA";
    private static final String UPDATED_LOCALE = "BBBBBBBBBB";

    private static final String DEFAULT_CONCEPT_NAME_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_CONCEPT_NAME_TYPE = "BBBBBBBBBB";

    @Autowired
    private ConceptNameRepository conceptNameRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restConceptNameMockMvc;

    private ConceptName conceptName;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ConceptName createEntity(EntityManager em) {
        ConceptName conceptName = new ConceptName()
            .uuid(DEFAULT_UUID)
            .name(DEFAULT_NAME)
            .locale(DEFAULT_LOCALE)
            .conceptNameType(DEFAULT_CONCEPT_NAME_TYPE);
        return conceptName;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ConceptName createUpdatedEntity(EntityManager em) {
        ConceptName conceptName = new ConceptName()
            .uuid(UPDATED_UUID)
            .name(UPDATED_NAME)
            .locale(UPDATED_LOCALE)
            .conceptNameType(UPDATED_CONCEPT_NAME_TYPE);
        return conceptName;
    }

    @BeforeEach
    public void initTest() {
        conceptName = createEntity(em);
    }

    @Test
    @Transactional
    public void createConceptName() throws Exception {
        int databaseSizeBeforeCreate = conceptNameRepository.findAll().size();
        // Create the ConceptName
        restConceptNameMockMvc.perform(post("/api/concept-names")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(conceptName)))
            .andExpect(status().isCreated());

        // Validate the ConceptName in the database
        List<ConceptName> conceptNameList = conceptNameRepository.findAll();
        assertThat(conceptNameList).hasSize(databaseSizeBeforeCreate + 1);
        ConceptName testConceptName = conceptNameList.get(conceptNameList.size() - 1);
        assertThat(testConceptName.getUuid()).isEqualTo(DEFAULT_UUID);
        assertThat(testConceptName.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testConceptName.getLocale()).isEqualTo(DEFAULT_LOCALE);
        assertThat(testConceptName.getConceptNameType()).isEqualTo(DEFAULT_CONCEPT_NAME_TYPE);
    }

    @Test
    @Transactional
    public void createConceptNameWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = conceptNameRepository.findAll().size();

        // Create the ConceptName with an existing ID
        conceptName.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restConceptNameMockMvc.perform(post("/api/concept-names")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(conceptName)))
            .andExpect(status().isBadRequest());

        // Validate the ConceptName in the database
        List<ConceptName> conceptNameList = conceptNameRepository.findAll();
        assertThat(conceptNameList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkUuidIsRequired() throws Exception {
        int databaseSizeBeforeTest = conceptNameRepository.findAll().size();
        // set the field null
        conceptName.setUuid(null);

        // Create the ConceptName, which fails.


        restConceptNameMockMvc.perform(post("/api/concept-names")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(conceptName)))
            .andExpect(status().isBadRequest());

        List<ConceptName> conceptNameList = conceptNameRepository.findAll();
        assertThat(conceptNameList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllConceptNames() throws Exception {
        // Initialize the database
        conceptNameRepository.saveAndFlush(conceptName);

        // Get all the conceptNameList
        restConceptNameMockMvc.perform(get("/api/concept-names?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(conceptName.getId().intValue())))
            .andExpect(jsonPath("$.[*].uuid").value(hasItem(DEFAULT_UUID.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].locale").value(hasItem(DEFAULT_LOCALE)))
            .andExpect(jsonPath("$.[*].conceptNameType").value(hasItem(DEFAULT_CONCEPT_NAME_TYPE)));
    }
    
    @Test
    @Transactional
    public void getConceptName() throws Exception {
        // Initialize the database
        conceptNameRepository.saveAndFlush(conceptName);

        // Get the conceptName
        restConceptNameMockMvc.perform(get("/api/concept-names/{id}", conceptName.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(conceptName.getId().intValue()))
            .andExpect(jsonPath("$.uuid").value(DEFAULT_UUID.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.locale").value(DEFAULT_LOCALE))
            .andExpect(jsonPath("$.conceptNameType").value(DEFAULT_CONCEPT_NAME_TYPE));
    }
    @Test
    @Transactional
    public void getNonExistingConceptName() throws Exception {
        // Get the conceptName
        restConceptNameMockMvc.perform(get("/api/concept-names/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateConceptName() throws Exception {
        // Initialize the database
        conceptNameRepository.saveAndFlush(conceptName);

        int databaseSizeBeforeUpdate = conceptNameRepository.findAll().size();

        // Update the conceptName
        ConceptName updatedConceptName = conceptNameRepository.findById(conceptName.getId()).get();
        // Disconnect from session so that the updates on updatedConceptName are not directly saved in db
        em.detach(updatedConceptName);
        updatedConceptName
            .uuid(UPDATED_UUID)
            .name(UPDATED_NAME)
            .locale(UPDATED_LOCALE)
            .conceptNameType(UPDATED_CONCEPT_NAME_TYPE);

        restConceptNameMockMvc.perform(put("/api/concept-names")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedConceptName)))
            .andExpect(status().isOk());

        // Validate the ConceptName in the database
        List<ConceptName> conceptNameList = conceptNameRepository.findAll();
        assertThat(conceptNameList).hasSize(databaseSizeBeforeUpdate);
        ConceptName testConceptName = conceptNameList.get(conceptNameList.size() - 1);
        assertThat(testConceptName.getUuid()).isEqualTo(UPDATED_UUID);
        assertThat(testConceptName.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testConceptName.getLocale()).isEqualTo(UPDATED_LOCALE);
        assertThat(testConceptName.getConceptNameType()).isEqualTo(UPDATED_CONCEPT_NAME_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingConceptName() throws Exception {
        int databaseSizeBeforeUpdate = conceptNameRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restConceptNameMockMvc.perform(put("/api/concept-names")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(conceptName)))
            .andExpect(status().isBadRequest());

        // Validate the ConceptName in the database
        List<ConceptName> conceptNameList = conceptNameRepository.findAll();
        assertThat(conceptNameList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteConceptName() throws Exception {
        // Initialize the database
        conceptNameRepository.saveAndFlush(conceptName);

        int databaseSizeBeforeDelete = conceptNameRepository.findAll().size();

        // Delete the conceptName
        restConceptNameMockMvc.perform(delete("/api/concept-names/{id}", conceptName.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ConceptName> conceptNameList = conceptNameRepository.findAll();
        assertThat(conceptNameList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
