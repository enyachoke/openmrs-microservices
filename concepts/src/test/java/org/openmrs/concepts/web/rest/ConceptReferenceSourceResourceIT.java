package org.openmrs.concepts.web.rest;

import org.openmrs.concepts.ConceptsApp;
import org.openmrs.concepts.domain.ConceptReferenceSource;
import org.openmrs.concepts.repository.ConceptReferenceSourceRepository;

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
 * Integration tests for the {@link ConceptReferenceSourceResource} REST controller.
 */
@SpringBootTest(classes = ConceptsApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ConceptReferenceSourceResourceIT {

    private static final UUID DEFAULT_UUID = UUID.randomUUID();
    private static final UUID UPDATED_UUID = UUID.randomUUID();

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_HL_7_CODE = "AAAAAAAAAA";
    private static final String UPDATED_HL_7_CODE = "BBBBBBBBBB";

    @Autowired
    private ConceptReferenceSourceRepository conceptReferenceSourceRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restConceptReferenceSourceMockMvc;

    private ConceptReferenceSource conceptReferenceSource;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ConceptReferenceSource createEntity(EntityManager em) {
        ConceptReferenceSource conceptReferenceSource = new ConceptReferenceSource()
            .uuid(DEFAULT_UUID)
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .hl7Code(DEFAULT_HL_7_CODE);
        return conceptReferenceSource;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ConceptReferenceSource createUpdatedEntity(EntityManager em) {
        ConceptReferenceSource conceptReferenceSource = new ConceptReferenceSource()
            .uuid(UPDATED_UUID)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .hl7Code(UPDATED_HL_7_CODE);
        return conceptReferenceSource;
    }

    @BeforeEach
    public void initTest() {
        conceptReferenceSource = createEntity(em);
    }

    @Test
    @Transactional
    public void createConceptReferenceSource() throws Exception {
        int databaseSizeBeforeCreate = conceptReferenceSourceRepository.findAll().size();
        // Create the ConceptReferenceSource
        restConceptReferenceSourceMockMvc.perform(post("/api/concept-reference-sources")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(conceptReferenceSource)))
            .andExpect(status().isCreated());

        // Validate the ConceptReferenceSource in the database
        List<ConceptReferenceSource> conceptReferenceSourceList = conceptReferenceSourceRepository.findAll();
        assertThat(conceptReferenceSourceList).hasSize(databaseSizeBeforeCreate + 1);
        ConceptReferenceSource testConceptReferenceSource = conceptReferenceSourceList.get(conceptReferenceSourceList.size() - 1);
        assertThat(testConceptReferenceSource.getUuid()).isEqualTo(DEFAULT_UUID);
        assertThat(testConceptReferenceSource.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testConceptReferenceSource.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testConceptReferenceSource.getHl7Code()).isEqualTo(DEFAULT_HL_7_CODE);
    }

    @Test
    @Transactional
    public void createConceptReferenceSourceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = conceptReferenceSourceRepository.findAll().size();

        // Create the ConceptReferenceSource with an existing ID
        conceptReferenceSource.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restConceptReferenceSourceMockMvc.perform(post("/api/concept-reference-sources")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(conceptReferenceSource)))
            .andExpect(status().isBadRequest());

        // Validate the ConceptReferenceSource in the database
        List<ConceptReferenceSource> conceptReferenceSourceList = conceptReferenceSourceRepository.findAll();
        assertThat(conceptReferenceSourceList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkUuidIsRequired() throws Exception {
        int databaseSizeBeforeTest = conceptReferenceSourceRepository.findAll().size();
        // set the field null
        conceptReferenceSource.setUuid(null);

        // Create the ConceptReferenceSource, which fails.


        restConceptReferenceSourceMockMvc.perform(post("/api/concept-reference-sources")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(conceptReferenceSource)))
            .andExpect(status().isBadRequest());

        List<ConceptReferenceSource> conceptReferenceSourceList = conceptReferenceSourceRepository.findAll();
        assertThat(conceptReferenceSourceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllConceptReferenceSources() throws Exception {
        // Initialize the database
        conceptReferenceSourceRepository.saveAndFlush(conceptReferenceSource);

        // Get all the conceptReferenceSourceList
        restConceptReferenceSourceMockMvc.perform(get("/api/concept-reference-sources?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(conceptReferenceSource.getId().intValue())))
            .andExpect(jsonPath("$.[*].uuid").value(hasItem(DEFAULT_UUID.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].hl7Code").value(hasItem(DEFAULT_HL_7_CODE)));
    }
    
    @Test
    @Transactional
    public void getConceptReferenceSource() throws Exception {
        // Initialize the database
        conceptReferenceSourceRepository.saveAndFlush(conceptReferenceSource);

        // Get the conceptReferenceSource
        restConceptReferenceSourceMockMvc.perform(get("/api/concept-reference-sources/{id}", conceptReferenceSource.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(conceptReferenceSource.getId().intValue()))
            .andExpect(jsonPath("$.uuid").value(DEFAULT_UUID.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.hl7Code").value(DEFAULT_HL_7_CODE));
    }
    @Test
    @Transactional
    public void getNonExistingConceptReferenceSource() throws Exception {
        // Get the conceptReferenceSource
        restConceptReferenceSourceMockMvc.perform(get("/api/concept-reference-sources/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateConceptReferenceSource() throws Exception {
        // Initialize the database
        conceptReferenceSourceRepository.saveAndFlush(conceptReferenceSource);

        int databaseSizeBeforeUpdate = conceptReferenceSourceRepository.findAll().size();

        // Update the conceptReferenceSource
        ConceptReferenceSource updatedConceptReferenceSource = conceptReferenceSourceRepository.findById(conceptReferenceSource.getId()).get();
        // Disconnect from session so that the updates on updatedConceptReferenceSource are not directly saved in db
        em.detach(updatedConceptReferenceSource);
        updatedConceptReferenceSource
            .uuid(UPDATED_UUID)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .hl7Code(UPDATED_HL_7_CODE);

        restConceptReferenceSourceMockMvc.perform(put("/api/concept-reference-sources")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedConceptReferenceSource)))
            .andExpect(status().isOk());

        // Validate the ConceptReferenceSource in the database
        List<ConceptReferenceSource> conceptReferenceSourceList = conceptReferenceSourceRepository.findAll();
        assertThat(conceptReferenceSourceList).hasSize(databaseSizeBeforeUpdate);
        ConceptReferenceSource testConceptReferenceSource = conceptReferenceSourceList.get(conceptReferenceSourceList.size() - 1);
        assertThat(testConceptReferenceSource.getUuid()).isEqualTo(UPDATED_UUID);
        assertThat(testConceptReferenceSource.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testConceptReferenceSource.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testConceptReferenceSource.getHl7Code()).isEqualTo(UPDATED_HL_7_CODE);
    }

    @Test
    @Transactional
    public void updateNonExistingConceptReferenceSource() throws Exception {
        int databaseSizeBeforeUpdate = conceptReferenceSourceRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restConceptReferenceSourceMockMvc.perform(put("/api/concept-reference-sources")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(conceptReferenceSource)))
            .andExpect(status().isBadRequest());

        // Validate the ConceptReferenceSource in the database
        List<ConceptReferenceSource> conceptReferenceSourceList = conceptReferenceSourceRepository.findAll();
        assertThat(conceptReferenceSourceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteConceptReferenceSource() throws Exception {
        // Initialize the database
        conceptReferenceSourceRepository.saveAndFlush(conceptReferenceSource);

        int databaseSizeBeforeDelete = conceptReferenceSourceRepository.findAll().size();

        // Delete the conceptReferenceSource
        restConceptReferenceSourceMockMvc.perform(delete("/api/concept-reference-sources/{id}", conceptReferenceSource.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ConceptReferenceSource> conceptReferenceSourceList = conceptReferenceSourceRepository.findAll();
        assertThat(conceptReferenceSourceList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
