package org.openmrs.concepts.web.rest;

import org.openmrs.concepts.ConceptsApp;
import org.openmrs.concepts.domain.ConceptProposalReferenceMap;
import org.openmrs.concepts.repository.ConceptProposalReferenceMapRepository;

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
 * Integration tests for the {@link ConceptProposalReferenceMapResource} REST controller.
 */
@SpringBootTest(classes = ConceptsApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ConceptProposalReferenceMapResourceIT {

    private static final UUID DEFAULT_UUID = UUID.randomUUID();
    private static final UUID UPDATED_UUID = UUID.randomUUID();

    @Autowired
    private ConceptProposalReferenceMapRepository conceptProposalReferenceMapRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restConceptProposalReferenceMapMockMvc;

    private ConceptProposalReferenceMap conceptProposalReferenceMap;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ConceptProposalReferenceMap createEntity(EntityManager em) {
        ConceptProposalReferenceMap conceptProposalReferenceMap = new ConceptProposalReferenceMap()
            .uuid(DEFAULT_UUID);
        return conceptProposalReferenceMap;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ConceptProposalReferenceMap createUpdatedEntity(EntityManager em) {
        ConceptProposalReferenceMap conceptProposalReferenceMap = new ConceptProposalReferenceMap()
            .uuid(UPDATED_UUID);
        return conceptProposalReferenceMap;
    }

    @BeforeEach
    public void initTest() {
        conceptProposalReferenceMap = createEntity(em);
    }

    @Test
    @Transactional
    public void createConceptProposalReferenceMap() throws Exception {
        int databaseSizeBeforeCreate = conceptProposalReferenceMapRepository.findAll().size();
        // Create the ConceptProposalReferenceMap
        restConceptProposalReferenceMapMockMvc.perform(post("/api/concept-proposal-reference-maps")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(conceptProposalReferenceMap)))
            .andExpect(status().isCreated());

        // Validate the ConceptProposalReferenceMap in the database
        List<ConceptProposalReferenceMap> conceptProposalReferenceMapList = conceptProposalReferenceMapRepository.findAll();
        assertThat(conceptProposalReferenceMapList).hasSize(databaseSizeBeforeCreate + 1);
        ConceptProposalReferenceMap testConceptProposalReferenceMap = conceptProposalReferenceMapList.get(conceptProposalReferenceMapList.size() - 1);
        assertThat(testConceptProposalReferenceMap.getUuid()).isEqualTo(DEFAULT_UUID);
    }

    @Test
    @Transactional
    public void createConceptProposalReferenceMapWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = conceptProposalReferenceMapRepository.findAll().size();

        // Create the ConceptProposalReferenceMap with an existing ID
        conceptProposalReferenceMap.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restConceptProposalReferenceMapMockMvc.perform(post("/api/concept-proposal-reference-maps")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(conceptProposalReferenceMap)))
            .andExpect(status().isBadRequest());

        // Validate the ConceptProposalReferenceMap in the database
        List<ConceptProposalReferenceMap> conceptProposalReferenceMapList = conceptProposalReferenceMapRepository.findAll();
        assertThat(conceptProposalReferenceMapList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkUuidIsRequired() throws Exception {
        int databaseSizeBeforeTest = conceptProposalReferenceMapRepository.findAll().size();
        // set the field null
        conceptProposalReferenceMap.setUuid(null);

        // Create the ConceptProposalReferenceMap, which fails.


        restConceptProposalReferenceMapMockMvc.perform(post("/api/concept-proposal-reference-maps")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(conceptProposalReferenceMap)))
            .andExpect(status().isBadRequest());

        List<ConceptProposalReferenceMap> conceptProposalReferenceMapList = conceptProposalReferenceMapRepository.findAll();
        assertThat(conceptProposalReferenceMapList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllConceptProposalReferenceMaps() throws Exception {
        // Initialize the database
        conceptProposalReferenceMapRepository.saveAndFlush(conceptProposalReferenceMap);

        // Get all the conceptProposalReferenceMapList
        restConceptProposalReferenceMapMockMvc.perform(get("/api/concept-proposal-reference-maps?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(conceptProposalReferenceMap.getId().intValue())))
            .andExpect(jsonPath("$.[*].uuid").value(hasItem(DEFAULT_UUID.toString())));
    }
    
    @Test
    @Transactional
    public void getConceptProposalReferenceMap() throws Exception {
        // Initialize the database
        conceptProposalReferenceMapRepository.saveAndFlush(conceptProposalReferenceMap);

        // Get the conceptProposalReferenceMap
        restConceptProposalReferenceMapMockMvc.perform(get("/api/concept-proposal-reference-maps/{id}", conceptProposalReferenceMap.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(conceptProposalReferenceMap.getId().intValue()))
            .andExpect(jsonPath("$.uuid").value(DEFAULT_UUID.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingConceptProposalReferenceMap() throws Exception {
        // Get the conceptProposalReferenceMap
        restConceptProposalReferenceMapMockMvc.perform(get("/api/concept-proposal-reference-maps/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateConceptProposalReferenceMap() throws Exception {
        // Initialize the database
        conceptProposalReferenceMapRepository.saveAndFlush(conceptProposalReferenceMap);

        int databaseSizeBeforeUpdate = conceptProposalReferenceMapRepository.findAll().size();

        // Update the conceptProposalReferenceMap
        ConceptProposalReferenceMap updatedConceptProposalReferenceMap = conceptProposalReferenceMapRepository.findById(conceptProposalReferenceMap.getId()).get();
        // Disconnect from session so that the updates on updatedConceptProposalReferenceMap are not directly saved in db
        em.detach(updatedConceptProposalReferenceMap);
        updatedConceptProposalReferenceMap
            .uuid(UPDATED_UUID);

        restConceptProposalReferenceMapMockMvc.perform(put("/api/concept-proposal-reference-maps")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedConceptProposalReferenceMap)))
            .andExpect(status().isOk());

        // Validate the ConceptProposalReferenceMap in the database
        List<ConceptProposalReferenceMap> conceptProposalReferenceMapList = conceptProposalReferenceMapRepository.findAll();
        assertThat(conceptProposalReferenceMapList).hasSize(databaseSizeBeforeUpdate);
        ConceptProposalReferenceMap testConceptProposalReferenceMap = conceptProposalReferenceMapList.get(conceptProposalReferenceMapList.size() - 1);
        assertThat(testConceptProposalReferenceMap.getUuid()).isEqualTo(UPDATED_UUID);
    }

    @Test
    @Transactional
    public void updateNonExistingConceptProposalReferenceMap() throws Exception {
        int databaseSizeBeforeUpdate = conceptProposalReferenceMapRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restConceptProposalReferenceMapMockMvc.perform(put("/api/concept-proposal-reference-maps")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(conceptProposalReferenceMap)))
            .andExpect(status().isBadRequest());

        // Validate the ConceptProposalReferenceMap in the database
        List<ConceptProposalReferenceMap> conceptProposalReferenceMapList = conceptProposalReferenceMapRepository.findAll();
        assertThat(conceptProposalReferenceMapList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteConceptProposalReferenceMap() throws Exception {
        // Initialize the database
        conceptProposalReferenceMapRepository.saveAndFlush(conceptProposalReferenceMap);

        int databaseSizeBeforeDelete = conceptProposalReferenceMapRepository.findAll().size();

        // Delete the conceptProposalReferenceMap
        restConceptProposalReferenceMapMockMvc.perform(delete("/api/concept-proposal-reference-maps/{id}", conceptProposalReferenceMap.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ConceptProposalReferenceMap> conceptProposalReferenceMapList = conceptProposalReferenceMapRepository.findAll();
        assertThat(conceptProposalReferenceMapList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
