package org.openmrs.concepts.web.rest;

import org.openmrs.concepts.ConceptsApp;
import org.openmrs.concepts.domain.ConceptProposalTagMap;
import org.openmrs.concepts.repository.ConceptProposalTagMapRepository;

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
 * Integration tests for the {@link ConceptProposalTagMapResource} REST controller.
 */
@SpringBootTest(classes = ConceptsApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ConceptProposalTagMapResourceIT {

    private static final UUID DEFAULT_UUID = UUID.randomUUID();
    private static final UUID UPDATED_UUID = UUID.randomUUID();

    @Autowired
    private ConceptProposalTagMapRepository conceptProposalTagMapRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restConceptProposalTagMapMockMvc;

    private ConceptProposalTagMap conceptProposalTagMap;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ConceptProposalTagMap createEntity(EntityManager em) {
        ConceptProposalTagMap conceptProposalTagMap = new ConceptProposalTagMap()
            .uuid(DEFAULT_UUID);
        return conceptProposalTagMap;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ConceptProposalTagMap createUpdatedEntity(EntityManager em) {
        ConceptProposalTagMap conceptProposalTagMap = new ConceptProposalTagMap()
            .uuid(UPDATED_UUID);
        return conceptProposalTagMap;
    }

    @BeforeEach
    public void initTest() {
        conceptProposalTagMap = createEntity(em);
    }

    @Test
    @Transactional
    public void createConceptProposalTagMap() throws Exception {
        int databaseSizeBeforeCreate = conceptProposalTagMapRepository.findAll().size();
        // Create the ConceptProposalTagMap
        restConceptProposalTagMapMockMvc.perform(post("/api/concept-proposal-tag-maps")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(conceptProposalTagMap)))
            .andExpect(status().isCreated());

        // Validate the ConceptProposalTagMap in the database
        List<ConceptProposalTagMap> conceptProposalTagMapList = conceptProposalTagMapRepository.findAll();
        assertThat(conceptProposalTagMapList).hasSize(databaseSizeBeforeCreate + 1);
        ConceptProposalTagMap testConceptProposalTagMap = conceptProposalTagMapList.get(conceptProposalTagMapList.size() - 1);
        assertThat(testConceptProposalTagMap.getUuid()).isEqualTo(DEFAULT_UUID);
    }

    @Test
    @Transactional
    public void createConceptProposalTagMapWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = conceptProposalTagMapRepository.findAll().size();

        // Create the ConceptProposalTagMap with an existing ID
        conceptProposalTagMap.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restConceptProposalTagMapMockMvc.perform(post("/api/concept-proposal-tag-maps")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(conceptProposalTagMap)))
            .andExpect(status().isBadRequest());

        // Validate the ConceptProposalTagMap in the database
        List<ConceptProposalTagMap> conceptProposalTagMapList = conceptProposalTagMapRepository.findAll();
        assertThat(conceptProposalTagMapList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkUuidIsRequired() throws Exception {
        int databaseSizeBeforeTest = conceptProposalTagMapRepository.findAll().size();
        // set the field null
        conceptProposalTagMap.setUuid(null);

        // Create the ConceptProposalTagMap, which fails.


        restConceptProposalTagMapMockMvc.perform(post("/api/concept-proposal-tag-maps")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(conceptProposalTagMap)))
            .andExpect(status().isBadRequest());

        List<ConceptProposalTagMap> conceptProposalTagMapList = conceptProposalTagMapRepository.findAll();
        assertThat(conceptProposalTagMapList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllConceptProposalTagMaps() throws Exception {
        // Initialize the database
        conceptProposalTagMapRepository.saveAndFlush(conceptProposalTagMap);

        // Get all the conceptProposalTagMapList
        restConceptProposalTagMapMockMvc.perform(get("/api/concept-proposal-tag-maps?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(conceptProposalTagMap.getId().intValue())))
            .andExpect(jsonPath("$.[*].uuid").value(hasItem(DEFAULT_UUID.toString())));
    }
    
    @Test
    @Transactional
    public void getConceptProposalTagMap() throws Exception {
        // Initialize the database
        conceptProposalTagMapRepository.saveAndFlush(conceptProposalTagMap);

        // Get the conceptProposalTagMap
        restConceptProposalTagMapMockMvc.perform(get("/api/concept-proposal-tag-maps/{id}", conceptProposalTagMap.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(conceptProposalTagMap.getId().intValue()))
            .andExpect(jsonPath("$.uuid").value(DEFAULT_UUID.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingConceptProposalTagMap() throws Exception {
        // Get the conceptProposalTagMap
        restConceptProposalTagMapMockMvc.perform(get("/api/concept-proposal-tag-maps/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateConceptProposalTagMap() throws Exception {
        // Initialize the database
        conceptProposalTagMapRepository.saveAndFlush(conceptProposalTagMap);

        int databaseSizeBeforeUpdate = conceptProposalTagMapRepository.findAll().size();

        // Update the conceptProposalTagMap
        ConceptProposalTagMap updatedConceptProposalTagMap = conceptProposalTagMapRepository.findById(conceptProposalTagMap.getId()).get();
        // Disconnect from session so that the updates on updatedConceptProposalTagMap are not directly saved in db
        em.detach(updatedConceptProposalTagMap);
        updatedConceptProposalTagMap
            .uuid(UPDATED_UUID);

        restConceptProposalTagMapMockMvc.perform(put("/api/concept-proposal-tag-maps")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedConceptProposalTagMap)))
            .andExpect(status().isOk());

        // Validate the ConceptProposalTagMap in the database
        List<ConceptProposalTagMap> conceptProposalTagMapList = conceptProposalTagMapRepository.findAll();
        assertThat(conceptProposalTagMapList).hasSize(databaseSizeBeforeUpdate);
        ConceptProposalTagMap testConceptProposalTagMap = conceptProposalTagMapList.get(conceptProposalTagMapList.size() - 1);
        assertThat(testConceptProposalTagMap.getUuid()).isEqualTo(UPDATED_UUID);
    }

    @Test
    @Transactional
    public void updateNonExistingConceptProposalTagMap() throws Exception {
        int databaseSizeBeforeUpdate = conceptProposalTagMapRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restConceptProposalTagMapMockMvc.perform(put("/api/concept-proposal-tag-maps")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(conceptProposalTagMap)))
            .andExpect(status().isBadRequest());

        // Validate the ConceptProposalTagMap in the database
        List<ConceptProposalTagMap> conceptProposalTagMapList = conceptProposalTagMapRepository.findAll();
        assertThat(conceptProposalTagMapList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteConceptProposalTagMap() throws Exception {
        // Initialize the database
        conceptProposalTagMapRepository.saveAndFlush(conceptProposalTagMap);

        int databaseSizeBeforeDelete = conceptProposalTagMapRepository.findAll().size();

        // Delete the conceptProposalTagMap
        restConceptProposalTagMapMockMvc.perform(delete("/api/concept-proposal-tag-maps/{id}", conceptProposalTagMap.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ConceptProposalTagMap> conceptProposalTagMapList = conceptProposalTagMapRepository.findAll();
        assertThat(conceptProposalTagMapList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
