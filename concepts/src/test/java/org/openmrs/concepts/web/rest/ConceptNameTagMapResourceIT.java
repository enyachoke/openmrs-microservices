package org.openmrs.concepts.web.rest;

import org.openmrs.concepts.ConceptsApp;
import org.openmrs.concepts.domain.ConceptNameTagMap;
import org.openmrs.concepts.repository.ConceptNameTagMapRepository;

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
 * Integration tests for the {@link ConceptNameTagMapResource} REST controller.
 */
@SpringBootTest(classes = ConceptsApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ConceptNameTagMapResourceIT {

    private static final UUID DEFAULT_UUID = UUID.randomUUID();
    private static final UUID UPDATED_UUID = UUID.randomUUID();

    @Autowired
    private ConceptNameTagMapRepository conceptNameTagMapRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restConceptNameTagMapMockMvc;

    private ConceptNameTagMap conceptNameTagMap;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ConceptNameTagMap createEntity(EntityManager em) {
        ConceptNameTagMap conceptNameTagMap = new ConceptNameTagMap()
            .uuid(DEFAULT_UUID);
        return conceptNameTagMap;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ConceptNameTagMap createUpdatedEntity(EntityManager em) {
        ConceptNameTagMap conceptNameTagMap = new ConceptNameTagMap()
            .uuid(UPDATED_UUID);
        return conceptNameTagMap;
    }

    @BeforeEach
    public void initTest() {
        conceptNameTagMap = createEntity(em);
    }

    @Test
    @Transactional
    public void createConceptNameTagMap() throws Exception {
        int databaseSizeBeforeCreate = conceptNameTagMapRepository.findAll().size();
        // Create the ConceptNameTagMap
        restConceptNameTagMapMockMvc.perform(post("/api/concept-name-tag-maps")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(conceptNameTagMap)))
            .andExpect(status().isCreated());

        // Validate the ConceptNameTagMap in the database
        List<ConceptNameTagMap> conceptNameTagMapList = conceptNameTagMapRepository.findAll();
        assertThat(conceptNameTagMapList).hasSize(databaseSizeBeforeCreate + 1);
        ConceptNameTagMap testConceptNameTagMap = conceptNameTagMapList.get(conceptNameTagMapList.size() - 1);
        assertThat(testConceptNameTagMap.getUuid()).isEqualTo(DEFAULT_UUID);
    }

    @Test
    @Transactional
    public void createConceptNameTagMapWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = conceptNameTagMapRepository.findAll().size();

        // Create the ConceptNameTagMap with an existing ID
        conceptNameTagMap.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restConceptNameTagMapMockMvc.perform(post("/api/concept-name-tag-maps")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(conceptNameTagMap)))
            .andExpect(status().isBadRequest());

        // Validate the ConceptNameTagMap in the database
        List<ConceptNameTagMap> conceptNameTagMapList = conceptNameTagMapRepository.findAll();
        assertThat(conceptNameTagMapList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkUuidIsRequired() throws Exception {
        int databaseSizeBeforeTest = conceptNameTagMapRepository.findAll().size();
        // set the field null
        conceptNameTagMap.setUuid(null);

        // Create the ConceptNameTagMap, which fails.


        restConceptNameTagMapMockMvc.perform(post("/api/concept-name-tag-maps")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(conceptNameTagMap)))
            .andExpect(status().isBadRequest());

        List<ConceptNameTagMap> conceptNameTagMapList = conceptNameTagMapRepository.findAll();
        assertThat(conceptNameTagMapList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllConceptNameTagMaps() throws Exception {
        // Initialize the database
        conceptNameTagMapRepository.saveAndFlush(conceptNameTagMap);

        // Get all the conceptNameTagMapList
        restConceptNameTagMapMockMvc.perform(get("/api/concept-name-tag-maps?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(conceptNameTagMap.getId().intValue())))
            .andExpect(jsonPath("$.[*].uuid").value(hasItem(DEFAULT_UUID.toString())));
    }
    
    @Test
    @Transactional
    public void getConceptNameTagMap() throws Exception {
        // Initialize the database
        conceptNameTagMapRepository.saveAndFlush(conceptNameTagMap);

        // Get the conceptNameTagMap
        restConceptNameTagMapMockMvc.perform(get("/api/concept-name-tag-maps/{id}", conceptNameTagMap.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(conceptNameTagMap.getId().intValue()))
            .andExpect(jsonPath("$.uuid").value(DEFAULT_UUID.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingConceptNameTagMap() throws Exception {
        // Get the conceptNameTagMap
        restConceptNameTagMapMockMvc.perform(get("/api/concept-name-tag-maps/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateConceptNameTagMap() throws Exception {
        // Initialize the database
        conceptNameTagMapRepository.saveAndFlush(conceptNameTagMap);

        int databaseSizeBeforeUpdate = conceptNameTagMapRepository.findAll().size();

        // Update the conceptNameTagMap
        ConceptNameTagMap updatedConceptNameTagMap = conceptNameTagMapRepository.findById(conceptNameTagMap.getId()).get();
        // Disconnect from session so that the updates on updatedConceptNameTagMap are not directly saved in db
        em.detach(updatedConceptNameTagMap);
        updatedConceptNameTagMap
            .uuid(UPDATED_UUID);

        restConceptNameTagMapMockMvc.perform(put("/api/concept-name-tag-maps")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedConceptNameTagMap)))
            .andExpect(status().isOk());

        // Validate the ConceptNameTagMap in the database
        List<ConceptNameTagMap> conceptNameTagMapList = conceptNameTagMapRepository.findAll();
        assertThat(conceptNameTagMapList).hasSize(databaseSizeBeforeUpdate);
        ConceptNameTagMap testConceptNameTagMap = conceptNameTagMapList.get(conceptNameTagMapList.size() - 1);
        assertThat(testConceptNameTagMap.getUuid()).isEqualTo(UPDATED_UUID);
    }

    @Test
    @Transactional
    public void updateNonExistingConceptNameTagMap() throws Exception {
        int databaseSizeBeforeUpdate = conceptNameTagMapRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restConceptNameTagMapMockMvc.perform(put("/api/concept-name-tag-maps")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(conceptNameTagMap)))
            .andExpect(status().isBadRequest());

        // Validate the ConceptNameTagMap in the database
        List<ConceptNameTagMap> conceptNameTagMapList = conceptNameTagMapRepository.findAll();
        assertThat(conceptNameTagMapList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteConceptNameTagMap() throws Exception {
        // Initialize the database
        conceptNameTagMapRepository.saveAndFlush(conceptNameTagMap);

        int databaseSizeBeforeDelete = conceptNameTagMapRepository.findAll().size();

        // Delete the conceptNameTagMap
        restConceptNameTagMapMockMvc.perform(delete("/api/concept-name-tag-maps/{id}", conceptNameTagMap.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ConceptNameTagMap> conceptNameTagMapList = conceptNameTagMapRepository.findAll();
        assertThat(conceptNameTagMapList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
