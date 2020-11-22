package org.openmrs.concepts.web.rest;

import org.openmrs.concepts.ConceptsApp;
import org.openmrs.concepts.domain.ConceptSet;
import org.openmrs.concepts.repository.ConceptSetRepository;

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
 * Integration tests for the {@link ConceptSetResource} REST controller.
 */
@SpringBootTest(classes = ConceptsApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ConceptSetResourceIT {

    private static final UUID DEFAULT_UUID = UUID.randomUUID();
    private static final UUID UPDATED_UUID = UUID.randomUUID();

    private static final Double DEFAULT_SORT_WEIGHT = 1D;
    private static final Double UPDATED_SORT_WEIGHT = 2D;

    @Autowired
    private ConceptSetRepository conceptSetRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restConceptSetMockMvc;

    private ConceptSet conceptSet;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ConceptSet createEntity(EntityManager em) {
        ConceptSet conceptSet = new ConceptSet()
            .uuid(DEFAULT_UUID)
            .sortWeight(DEFAULT_SORT_WEIGHT);
        return conceptSet;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ConceptSet createUpdatedEntity(EntityManager em) {
        ConceptSet conceptSet = new ConceptSet()
            .uuid(UPDATED_UUID)
            .sortWeight(UPDATED_SORT_WEIGHT);
        return conceptSet;
    }

    @BeforeEach
    public void initTest() {
        conceptSet = createEntity(em);
    }

    @Test
    @Transactional
    public void createConceptSet() throws Exception {
        int databaseSizeBeforeCreate = conceptSetRepository.findAll().size();
        // Create the ConceptSet
        restConceptSetMockMvc.perform(post("/api/concept-sets")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(conceptSet)))
            .andExpect(status().isCreated());

        // Validate the ConceptSet in the database
        List<ConceptSet> conceptSetList = conceptSetRepository.findAll();
        assertThat(conceptSetList).hasSize(databaseSizeBeforeCreate + 1);
        ConceptSet testConceptSet = conceptSetList.get(conceptSetList.size() - 1);
        assertThat(testConceptSet.getUuid()).isEqualTo(DEFAULT_UUID);
        assertThat(testConceptSet.getSortWeight()).isEqualTo(DEFAULT_SORT_WEIGHT);
    }

    @Test
    @Transactional
    public void createConceptSetWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = conceptSetRepository.findAll().size();

        // Create the ConceptSet with an existing ID
        conceptSet.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restConceptSetMockMvc.perform(post("/api/concept-sets")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(conceptSet)))
            .andExpect(status().isBadRequest());

        // Validate the ConceptSet in the database
        List<ConceptSet> conceptSetList = conceptSetRepository.findAll();
        assertThat(conceptSetList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkUuidIsRequired() throws Exception {
        int databaseSizeBeforeTest = conceptSetRepository.findAll().size();
        // set the field null
        conceptSet.setUuid(null);

        // Create the ConceptSet, which fails.


        restConceptSetMockMvc.perform(post("/api/concept-sets")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(conceptSet)))
            .andExpect(status().isBadRequest());

        List<ConceptSet> conceptSetList = conceptSetRepository.findAll();
        assertThat(conceptSetList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllConceptSets() throws Exception {
        // Initialize the database
        conceptSetRepository.saveAndFlush(conceptSet);

        // Get all the conceptSetList
        restConceptSetMockMvc.perform(get("/api/concept-sets?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(conceptSet.getId().intValue())))
            .andExpect(jsonPath("$.[*].uuid").value(hasItem(DEFAULT_UUID.toString())))
            .andExpect(jsonPath("$.[*].sortWeight").value(hasItem(DEFAULT_SORT_WEIGHT.doubleValue())));
    }
    
    @Test
    @Transactional
    public void getConceptSet() throws Exception {
        // Initialize the database
        conceptSetRepository.saveAndFlush(conceptSet);

        // Get the conceptSet
        restConceptSetMockMvc.perform(get("/api/concept-sets/{id}", conceptSet.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(conceptSet.getId().intValue()))
            .andExpect(jsonPath("$.uuid").value(DEFAULT_UUID.toString()))
            .andExpect(jsonPath("$.sortWeight").value(DEFAULT_SORT_WEIGHT.doubleValue()));
    }
    @Test
    @Transactional
    public void getNonExistingConceptSet() throws Exception {
        // Get the conceptSet
        restConceptSetMockMvc.perform(get("/api/concept-sets/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateConceptSet() throws Exception {
        // Initialize the database
        conceptSetRepository.saveAndFlush(conceptSet);

        int databaseSizeBeforeUpdate = conceptSetRepository.findAll().size();

        // Update the conceptSet
        ConceptSet updatedConceptSet = conceptSetRepository.findById(conceptSet.getId()).get();
        // Disconnect from session so that the updates on updatedConceptSet are not directly saved in db
        em.detach(updatedConceptSet);
        updatedConceptSet
            .uuid(UPDATED_UUID)
            .sortWeight(UPDATED_SORT_WEIGHT);

        restConceptSetMockMvc.perform(put("/api/concept-sets")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedConceptSet)))
            .andExpect(status().isOk());

        // Validate the ConceptSet in the database
        List<ConceptSet> conceptSetList = conceptSetRepository.findAll();
        assertThat(conceptSetList).hasSize(databaseSizeBeforeUpdate);
        ConceptSet testConceptSet = conceptSetList.get(conceptSetList.size() - 1);
        assertThat(testConceptSet.getUuid()).isEqualTo(UPDATED_UUID);
        assertThat(testConceptSet.getSortWeight()).isEqualTo(UPDATED_SORT_WEIGHT);
    }

    @Test
    @Transactional
    public void updateNonExistingConceptSet() throws Exception {
        int databaseSizeBeforeUpdate = conceptSetRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restConceptSetMockMvc.perform(put("/api/concept-sets")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(conceptSet)))
            .andExpect(status().isBadRequest());

        // Validate the ConceptSet in the database
        List<ConceptSet> conceptSetList = conceptSetRepository.findAll();
        assertThat(conceptSetList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteConceptSet() throws Exception {
        // Initialize the database
        conceptSetRepository.saveAndFlush(conceptSet);

        int databaseSizeBeforeDelete = conceptSetRepository.findAll().size();

        // Delete the conceptSet
        restConceptSetMockMvc.perform(delete("/api/concept-sets/{id}", conceptSet.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ConceptSet> conceptSetList = conceptSetRepository.findAll();
        assertThat(conceptSetList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
