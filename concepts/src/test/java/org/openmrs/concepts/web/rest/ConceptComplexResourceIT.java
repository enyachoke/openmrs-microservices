package org.openmrs.concepts.web.rest;

import org.openmrs.concepts.ConceptsApp;
import org.openmrs.concepts.domain.ConceptComplex;
import org.openmrs.concepts.repository.ConceptComplexRepository;

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
 * Integration tests for the {@link ConceptComplexResource} REST controller.
 */
@SpringBootTest(classes = ConceptsApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ConceptComplexResourceIT {

    private static final UUID DEFAULT_UUID = UUID.randomUUID();
    private static final UUID UPDATED_UUID = UUID.randomUUID();

    private static final String DEFAULT_HANDLER = "AAAAAAAAAA";
    private static final String UPDATED_HANDLER = "BBBBBBBBBB";

    @Autowired
    private ConceptComplexRepository conceptComplexRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restConceptComplexMockMvc;

    private ConceptComplex conceptComplex;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ConceptComplex createEntity(EntityManager em) {
        ConceptComplex conceptComplex = new ConceptComplex()
            .uuid(DEFAULT_UUID)
            .handler(DEFAULT_HANDLER);
        return conceptComplex;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ConceptComplex createUpdatedEntity(EntityManager em) {
        ConceptComplex conceptComplex = new ConceptComplex()
            .uuid(UPDATED_UUID)
            .handler(UPDATED_HANDLER);
        return conceptComplex;
    }

    @BeforeEach
    public void initTest() {
        conceptComplex = createEntity(em);
    }

    @Test
    @Transactional
    public void createConceptComplex() throws Exception {
        int databaseSizeBeforeCreate = conceptComplexRepository.findAll().size();
        // Create the ConceptComplex
        restConceptComplexMockMvc.perform(post("/api/concept-complexes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(conceptComplex)))
            .andExpect(status().isCreated());

        // Validate the ConceptComplex in the database
        List<ConceptComplex> conceptComplexList = conceptComplexRepository.findAll();
        assertThat(conceptComplexList).hasSize(databaseSizeBeforeCreate + 1);
        ConceptComplex testConceptComplex = conceptComplexList.get(conceptComplexList.size() - 1);
        assertThat(testConceptComplex.getUuid()).isEqualTo(DEFAULT_UUID);
        assertThat(testConceptComplex.getHandler()).isEqualTo(DEFAULT_HANDLER);
    }

    @Test
    @Transactional
    public void createConceptComplexWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = conceptComplexRepository.findAll().size();

        // Create the ConceptComplex with an existing ID
        conceptComplex.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restConceptComplexMockMvc.perform(post("/api/concept-complexes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(conceptComplex)))
            .andExpect(status().isBadRequest());

        // Validate the ConceptComplex in the database
        List<ConceptComplex> conceptComplexList = conceptComplexRepository.findAll();
        assertThat(conceptComplexList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkUuidIsRequired() throws Exception {
        int databaseSizeBeforeTest = conceptComplexRepository.findAll().size();
        // set the field null
        conceptComplex.setUuid(null);

        // Create the ConceptComplex, which fails.


        restConceptComplexMockMvc.perform(post("/api/concept-complexes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(conceptComplex)))
            .andExpect(status().isBadRequest());

        List<ConceptComplex> conceptComplexList = conceptComplexRepository.findAll();
        assertThat(conceptComplexList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllConceptComplexes() throws Exception {
        // Initialize the database
        conceptComplexRepository.saveAndFlush(conceptComplex);

        // Get all the conceptComplexList
        restConceptComplexMockMvc.perform(get("/api/concept-complexes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(conceptComplex.getId().intValue())))
            .andExpect(jsonPath("$.[*].uuid").value(hasItem(DEFAULT_UUID.toString())))
            .andExpect(jsonPath("$.[*].handler").value(hasItem(DEFAULT_HANDLER)));
    }
    
    @Test
    @Transactional
    public void getConceptComplex() throws Exception {
        // Initialize the database
        conceptComplexRepository.saveAndFlush(conceptComplex);

        // Get the conceptComplex
        restConceptComplexMockMvc.perform(get("/api/concept-complexes/{id}", conceptComplex.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(conceptComplex.getId().intValue()))
            .andExpect(jsonPath("$.uuid").value(DEFAULT_UUID.toString()))
            .andExpect(jsonPath("$.handler").value(DEFAULT_HANDLER));
    }
    @Test
    @Transactional
    public void getNonExistingConceptComplex() throws Exception {
        // Get the conceptComplex
        restConceptComplexMockMvc.perform(get("/api/concept-complexes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateConceptComplex() throws Exception {
        // Initialize the database
        conceptComplexRepository.saveAndFlush(conceptComplex);

        int databaseSizeBeforeUpdate = conceptComplexRepository.findAll().size();

        // Update the conceptComplex
        ConceptComplex updatedConceptComplex = conceptComplexRepository.findById(conceptComplex.getId()).get();
        // Disconnect from session so that the updates on updatedConceptComplex are not directly saved in db
        em.detach(updatedConceptComplex);
        updatedConceptComplex
            .uuid(UPDATED_UUID)
            .handler(UPDATED_HANDLER);

        restConceptComplexMockMvc.perform(put("/api/concept-complexes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedConceptComplex)))
            .andExpect(status().isOk());

        // Validate the ConceptComplex in the database
        List<ConceptComplex> conceptComplexList = conceptComplexRepository.findAll();
        assertThat(conceptComplexList).hasSize(databaseSizeBeforeUpdate);
        ConceptComplex testConceptComplex = conceptComplexList.get(conceptComplexList.size() - 1);
        assertThat(testConceptComplex.getUuid()).isEqualTo(UPDATED_UUID);
        assertThat(testConceptComplex.getHandler()).isEqualTo(UPDATED_HANDLER);
    }

    @Test
    @Transactional
    public void updateNonExistingConceptComplex() throws Exception {
        int databaseSizeBeforeUpdate = conceptComplexRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restConceptComplexMockMvc.perform(put("/api/concept-complexes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(conceptComplex)))
            .andExpect(status().isBadRequest());

        // Validate the ConceptComplex in the database
        List<ConceptComplex> conceptComplexList = conceptComplexRepository.findAll();
        assertThat(conceptComplexList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteConceptComplex() throws Exception {
        // Initialize the database
        conceptComplexRepository.saveAndFlush(conceptComplex);

        int databaseSizeBeforeDelete = conceptComplexRepository.findAll().size();

        // Delete the conceptComplex
        restConceptComplexMockMvc.perform(delete("/api/concept-complexes/{id}", conceptComplex.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ConceptComplex> conceptComplexList = conceptComplexRepository.findAll();
        assertThat(conceptComplexList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
