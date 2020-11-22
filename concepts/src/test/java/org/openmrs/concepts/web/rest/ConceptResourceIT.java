package org.openmrs.concepts.web.rest;

import org.openmrs.concepts.ConceptsApp;
import org.openmrs.concepts.domain.Concept;
import org.openmrs.concepts.repository.ConceptRepository;

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
 * Integration tests for the {@link ConceptResource} REST controller.
 */
@SpringBootTest(classes = ConceptsApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ConceptResourceIT {

    private static final UUID DEFAULT_UUID = UUID.randomUUID();
    private static final UUID UPDATED_UUID = UUID.randomUUID();

    private static final String DEFAULT_SHORT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_SHORT_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_FORM_TEXT = "AAAAAAAAAA";
    private static final String UPDATED_FORM_TEXT = "BBBBBBBBBB";

    private static final String DEFAULT_VERSION = "AAAAAAAAAA";
    private static final String UPDATED_VERSION = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_SET = false;
    private static final Boolean UPDATED_IS_SET = true;

    @Autowired
    private ConceptRepository conceptRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restConceptMockMvc;

    private Concept concept;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Concept createEntity(EntityManager em) {
        Concept concept = new Concept()
            .uuid(DEFAULT_UUID)
            .shortName(DEFAULT_SHORT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .formText(DEFAULT_FORM_TEXT)
            .version(DEFAULT_VERSION)
            .isSet(DEFAULT_IS_SET);
        return concept;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Concept createUpdatedEntity(EntityManager em) {
        Concept concept = new Concept()
            .uuid(UPDATED_UUID)
            .shortName(UPDATED_SHORT_NAME)
            .description(UPDATED_DESCRIPTION)
            .formText(UPDATED_FORM_TEXT)
            .version(UPDATED_VERSION)
            .isSet(UPDATED_IS_SET);
        return concept;
    }

    @BeforeEach
    public void initTest() {
        concept = createEntity(em);
    }

    @Test
    @Transactional
    public void createConcept() throws Exception {
        int databaseSizeBeforeCreate = conceptRepository.findAll().size();
        // Create the Concept
        restConceptMockMvc.perform(post("/api/concepts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(concept)))
            .andExpect(status().isCreated());

        // Validate the Concept in the database
        List<Concept> conceptList = conceptRepository.findAll();
        assertThat(conceptList).hasSize(databaseSizeBeforeCreate + 1);
        Concept testConcept = conceptList.get(conceptList.size() - 1);
        assertThat(testConcept.getUuid()).isEqualTo(DEFAULT_UUID);
        assertThat(testConcept.getShortName()).isEqualTo(DEFAULT_SHORT_NAME);
        assertThat(testConcept.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testConcept.getFormText()).isEqualTo(DEFAULT_FORM_TEXT);
        assertThat(testConcept.getVersion()).isEqualTo(DEFAULT_VERSION);
        assertThat(testConcept.isIsSet()).isEqualTo(DEFAULT_IS_SET);
    }

    @Test
    @Transactional
    public void createConceptWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = conceptRepository.findAll().size();

        // Create the Concept with an existing ID
        concept.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restConceptMockMvc.perform(post("/api/concepts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(concept)))
            .andExpect(status().isBadRequest());

        // Validate the Concept in the database
        List<Concept> conceptList = conceptRepository.findAll();
        assertThat(conceptList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkUuidIsRequired() throws Exception {
        int databaseSizeBeforeTest = conceptRepository.findAll().size();
        // set the field null
        concept.setUuid(null);

        // Create the Concept, which fails.


        restConceptMockMvc.perform(post("/api/concepts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(concept)))
            .andExpect(status().isBadRequest());

        List<Concept> conceptList = conceptRepository.findAll();
        assertThat(conceptList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllConcepts() throws Exception {
        // Initialize the database
        conceptRepository.saveAndFlush(concept);

        // Get all the conceptList
        restConceptMockMvc.perform(get("/api/concepts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(concept.getId().intValue())))
            .andExpect(jsonPath("$.[*].uuid").value(hasItem(DEFAULT_UUID.toString())))
            .andExpect(jsonPath("$.[*].shortName").value(hasItem(DEFAULT_SHORT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].formText").value(hasItem(DEFAULT_FORM_TEXT)))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION)))
            .andExpect(jsonPath("$.[*].isSet").value(hasItem(DEFAULT_IS_SET.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getConcept() throws Exception {
        // Initialize the database
        conceptRepository.saveAndFlush(concept);

        // Get the concept
        restConceptMockMvc.perform(get("/api/concepts/{id}", concept.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(concept.getId().intValue()))
            .andExpect(jsonPath("$.uuid").value(DEFAULT_UUID.toString()))
            .andExpect(jsonPath("$.shortName").value(DEFAULT_SHORT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.formText").value(DEFAULT_FORM_TEXT))
            .andExpect(jsonPath("$.version").value(DEFAULT_VERSION))
            .andExpect(jsonPath("$.isSet").value(DEFAULT_IS_SET.booleanValue()));
    }
    @Test
    @Transactional
    public void getNonExistingConcept() throws Exception {
        // Get the concept
        restConceptMockMvc.perform(get("/api/concepts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateConcept() throws Exception {
        // Initialize the database
        conceptRepository.saveAndFlush(concept);

        int databaseSizeBeforeUpdate = conceptRepository.findAll().size();

        // Update the concept
        Concept updatedConcept = conceptRepository.findById(concept.getId()).get();
        // Disconnect from session so that the updates on updatedConcept are not directly saved in db
        em.detach(updatedConcept);
        updatedConcept
            .uuid(UPDATED_UUID)
            .shortName(UPDATED_SHORT_NAME)
            .description(UPDATED_DESCRIPTION)
            .formText(UPDATED_FORM_TEXT)
            .version(UPDATED_VERSION)
            .isSet(UPDATED_IS_SET);

        restConceptMockMvc.perform(put("/api/concepts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedConcept)))
            .andExpect(status().isOk());

        // Validate the Concept in the database
        List<Concept> conceptList = conceptRepository.findAll();
        assertThat(conceptList).hasSize(databaseSizeBeforeUpdate);
        Concept testConcept = conceptList.get(conceptList.size() - 1);
        assertThat(testConcept.getUuid()).isEqualTo(UPDATED_UUID);
        assertThat(testConcept.getShortName()).isEqualTo(UPDATED_SHORT_NAME);
        assertThat(testConcept.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testConcept.getFormText()).isEqualTo(UPDATED_FORM_TEXT);
        assertThat(testConcept.getVersion()).isEqualTo(UPDATED_VERSION);
        assertThat(testConcept.isIsSet()).isEqualTo(UPDATED_IS_SET);
    }

    @Test
    @Transactional
    public void updateNonExistingConcept() throws Exception {
        int databaseSizeBeforeUpdate = conceptRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restConceptMockMvc.perform(put("/api/concepts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(concept)))
            .andExpect(status().isBadRequest());

        // Validate the Concept in the database
        List<Concept> conceptList = conceptRepository.findAll();
        assertThat(conceptList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteConcept() throws Exception {
        // Initialize the database
        conceptRepository.saveAndFlush(concept);

        int databaseSizeBeforeDelete = conceptRepository.findAll().size();

        // Delete the concept
        restConceptMockMvc.perform(delete("/api/concepts/{id}", concept.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Concept> conceptList = conceptRepository.findAll();
        assertThat(conceptList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
