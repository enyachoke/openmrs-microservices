package org.openmrs.concepts.web.rest;

import org.openmrs.concepts.ConceptsApp;
import org.openmrs.concepts.domain.ConceptNumeric;
import org.openmrs.concepts.repository.ConceptNumericRepository;

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
 * Integration tests for the {@link ConceptNumericResource} REST controller.
 */
@SpringBootTest(classes = ConceptsApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ConceptNumericResourceIT {

    private static final UUID DEFAULT_UUID = UUID.randomUUID();
    private static final UUID UPDATED_UUID = UUID.randomUUID();

    private static final Double DEFAULT_HI_ABSOLUTE = 1D;
    private static final Double UPDATED_HI_ABSOLUTE = 2D;

    private static final Double DEFAULT_HI_NORMAL = 1D;
    private static final Double UPDATED_HI_NORMAL = 2D;

    private static final Double DEFAULT_HI_CRITICAL = 1D;
    private static final Double UPDATED_HI_CRITICAL = 2D;

    private static final Double DEFAULT_LOW_ABSOLUTE = 1D;
    private static final Double UPDATED_LOW_ABSOLUTE = 2D;

    private static final Double DEFAULT_LOW_NORMAL = 1D;
    private static final Double UPDATED_LOW_NORMAL = 2D;

    private static final Double DEFAULT_LOW_CRITICAL = 1D;
    private static final Double UPDATED_LOW_CRITICAL = 2D;

    private static final String DEFAULT_UNITS = "AAAAAAAAAA";
    private static final String UPDATED_UNITS = "BBBBBBBBBB";

    private static final Boolean DEFAULT_PRECISE = false;
    private static final Boolean UPDATED_PRECISE = true;

    @Autowired
    private ConceptNumericRepository conceptNumericRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restConceptNumericMockMvc;

    private ConceptNumeric conceptNumeric;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ConceptNumeric createEntity(EntityManager em) {
        ConceptNumeric conceptNumeric = new ConceptNumeric()
            .uuid(DEFAULT_UUID)
            .hiAbsolute(DEFAULT_HI_ABSOLUTE)
            .hiNormal(DEFAULT_HI_NORMAL)
            .hiCritical(DEFAULT_HI_CRITICAL)
            .lowAbsolute(DEFAULT_LOW_ABSOLUTE)
            .lowNormal(DEFAULT_LOW_NORMAL)
            .lowCritical(DEFAULT_LOW_CRITICAL)
            .units(DEFAULT_UNITS)
            .precise(DEFAULT_PRECISE);
        return conceptNumeric;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ConceptNumeric createUpdatedEntity(EntityManager em) {
        ConceptNumeric conceptNumeric = new ConceptNumeric()
            .uuid(UPDATED_UUID)
            .hiAbsolute(UPDATED_HI_ABSOLUTE)
            .hiNormal(UPDATED_HI_NORMAL)
            .hiCritical(UPDATED_HI_CRITICAL)
            .lowAbsolute(UPDATED_LOW_ABSOLUTE)
            .lowNormal(UPDATED_LOW_NORMAL)
            .lowCritical(UPDATED_LOW_CRITICAL)
            .units(UPDATED_UNITS)
            .precise(UPDATED_PRECISE);
        return conceptNumeric;
    }

    @BeforeEach
    public void initTest() {
        conceptNumeric = createEntity(em);
    }

    @Test
    @Transactional
    public void createConceptNumeric() throws Exception {
        int databaseSizeBeforeCreate = conceptNumericRepository.findAll().size();
        // Create the ConceptNumeric
        restConceptNumericMockMvc.perform(post("/api/concept-numerics")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(conceptNumeric)))
            .andExpect(status().isCreated());

        // Validate the ConceptNumeric in the database
        List<ConceptNumeric> conceptNumericList = conceptNumericRepository.findAll();
        assertThat(conceptNumericList).hasSize(databaseSizeBeforeCreate + 1);
        ConceptNumeric testConceptNumeric = conceptNumericList.get(conceptNumericList.size() - 1);
        assertThat(testConceptNumeric.getUuid()).isEqualTo(DEFAULT_UUID);
        assertThat(testConceptNumeric.getHiAbsolute()).isEqualTo(DEFAULT_HI_ABSOLUTE);
        assertThat(testConceptNumeric.getHiNormal()).isEqualTo(DEFAULT_HI_NORMAL);
        assertThat(testConceptNumeric.getHiCritical()).isEqualTo(DEFAULT_HI_CRITICAL);
        assertThat(testConceptNumeric.getLowAbsolute()).isEqualTo(DEFAULT_LOW_ABSOLUTE);
        assertThat(testConceptNumeric.getLowNormal()).isEqualTo(DEFAULT_LOW_NORMAL);
        assertThat(testConceptNumeric.getLowCritical()).isEqualTo(DEFAULT_LOW_CRITICAL);
        assertThat(testConceptNumeric.getUnits()).isEqualTo(DEFAULT_UNITS);
        assertThat(testConceptNumeric.isPrecise()).isEqualTo(DEFAULT_PRECISE);
    }

    @Test
    @Transactional
    public void createConceptNumericWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = conceptNumericRepository.findAll().size();

        // Create the ConceptNumeric with an existing ID
        conceptNumeric.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restConceptNumericMockMvc.perform(post("/api/concept-numerics")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(conceptNumeric)))
            .andExpect(status().isBadRequest());

        // Validate the ConceptNumeric in the database
        List<ConceptNumeric> conceptNumericList = conceptNumericRepository.findAll();
        assertThat(conceptNumericList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkUuidIsRequired() throws Exception {
        int databaseSizeBeforeTest = conceptNumericRepository.findAll().size();
        // set the field null
        conceptNumeric.setUuid(null);

        // Create the ConceptNumeric, which fails.


        restConceptNumericMockMvc.perform(post("/api/concept-numerics")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(conceptNumeric)))
            .andExpect(status().isBadRequest());

        List<ConceptNumeric> conceptNumericList = conceptNumericRepository.findAll();
        assertThat(conceptNumericList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllConceptNumerics() throws Exception {
        // Initialize the database
        conceptNumericRepository.saveAndFlush(conceptNumeric);

        // Get all the conceptNumericList
        restConceptNumericMockMvc.perform(get("/api/concept-numerics?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(conceptNumeric.getId().intValue())))
            .andExpect(jsonPath("$.[*].uuid").value(hasItem(DEFAULT_UUID.toString())))
            .andExpect(jsonPath("$.[*].hiAbsolute").value(hasItem(DEFAULT_HI_ABSOLUTE.doubleValue())))
            .andExpect(jsonPath("$.[*].hiNormal").value(hasItem(DEFAULT_HI_NORMAL.doubleValue())))
            .andExpect(jsonPath("$.[*].hiCritical").value(hasItem(DEFAULT_HI_CRITICAL.doubleValue())))
            .andExpect(jsonPath("$.[*].lowAbsolute").value(hasItem(DEFAULT_LOW_ABSOLUTE.doubleValue())))
            .andExpect(jsonPath("$.[*].lowNormal").value(hasItem(DEFAULT_LOW_NORMAL.doubleValue())))
            .andExpect(jsonPath("$.[*].lowCritical").value(hasItem(DEFAULT_LOW_CRITICAL.doubleValue())))
            .andExpect(jsonPath("$.[*].units").value(hasItem(DEFAULT_UNITS)))
            .andExpect(jsonPath("$.[*].precise").value(hasItem(DEFAULT_PRECISE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getConceptNumeric() throws Exception {
        // Initialize the database
        conceptNumericRepository.saveAndFlush(conceptNumeric);

        // Get the conceptNumeric
        restConceptNumericMockMvc.perform(get("/api/concept-numerics/{id}", conceptNumeric.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(conceptNumeric.getId().intValue()))
            .andExpect(jsonPath("$.uuid").value(DEFAULT_UUID.toString()))
            .andExpect(jsonPath("$.hiAbsolute").value(DEFAULT_HI_ABSOLUTE.doubleValue()))
            .andExpect(jsonPath("$.hiNormal").value(DEFAULT_HI_NORMAL.doubleValue()))
            .andExpect(jsonPath("$.hiCritical").value(DEFAULT_HI_CRITICAL.doubleValue()))
            .andExpect(jsonPath("$.lowAbsolute").value(DEFAULT_LOW_ABSOLUTE.doubleValue()))
            .andExpect(jsonPath("$.lowNormal").value(DEFAULT_LOW_NORMAL.doubleValue()))
            .andExpect(jsonPath("$.lowCritical").value(DEFAULT_LOW_CRITICAL.doubleValue()))
            .andExpect(jsonPath("$.units").value(DEFAULT_UNITS))
            .andExpect(jsonPath("$.precise").value(DEFAULT_PRECISE.booleanValue()));
    }
    @Test
    @Transactional
    public void getNonExistingConceptNumeric() throws Exception {
        // Get the conceptNumeric
        restConceptNumericMockMvc.perform(get("/api/concept-numerics/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateConceptNumeric() throws Exception {
        // Initialize the database
        conceptNumericRepository.saveAndFlush(conceptNumeric);

        int databaseSizeBeforeUpdate = conceptNumericRepository.findAll().size();

        // Update the conceptNumeric
        ConceptNumeric updatedConceptNumeric = conceptNumericRepository.findById(conceptNumeric.getId()).get();
        // Disconnect from session so that the updates on updatedConceptNumeric are not directly saved in db
        em.detach(updatedConceptNumeric);
        updatedConceptNumeric
            .uuid(UPDATED_UUID)
            .hiAbsolute(UPDATED_HI_ABSOLUTE)
            .hiNormal(UPDATED_HI_NORMAL)
            .hiCritical(UPDATED_HI_CRITICAL)
            .lowAbsolute(UPDATED_LOW_ABSOLUTE)
            .lowNormal(UPDATED_LOW_NORMAL)
            .lowCritical(UPDATED_LOW_CRITICAL)
            .units(UPDATED_UNITS)
            .precise(UPDATED_PRECISE);

        restConceptNumericMockMvc.perform(put("/api/concept-numerics")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedConceptNumeric)))
            .andExpect(status().isOk());

        // Validate the ConceptNumeric in the database
        List<ConceptNumeric> conceptNumericList = conceptNumericRepository.findAll();
        assertThat(conceptNumericList).hasSize(databaseSizeBeforeUpdate);
        ConceptNumeric testConceptNumeric = conceptNumericList.get(conceptNumericList.size() - 1);
        assertThat(testConceptNumeric.getUuid()).isEqualTo(UPDATED_UUID);
        assertThat(testConceptNumeric.getHiAbsolute()).isEqualTo(UPDATED_HI_ABSOLUTE);
        assertThat(testConceptNumeric.getHiNormal()).isEqualTo(UPDATED_HI_NORMAL);
        assertThat(testConceptNumeric.getHiCritical()).isEqualTo(UPDATED_HI_CRITICAL);
        assertThat(testConceptNumeric.getLowAbsolute()).isEqualTo(UPDATED_LOW_ABSOLUTE);
        assertThat(testConceptNumeric.getLowNormal()).isEqualTo(UPDATED_LOW_NORMAL);
        assertThat(testConceptNumeric.getLowCritical()).isEqualTo(UPDATED_LOW_CRITICAL);
        assertThat(testConceptNumeric.getUnits()).isEqualTo(UPDATED_UNITS);
        assertThat(testConceptNumeric.isPrecise()).isEqualTo(UPDATED_PRECISE);
    }

    @Test
    @Transactional
    public void updateNonExistingConceptNumeric() throws Exception {
        int databaseSizeBeforeUpdate = conceptNumericRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restConceptNumericMockMvc.perform(put("/api/concept-numerics")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(conceptNumeric)))
            .andExpect(status().isBadRequest());

        // Validate the ConceptNumeric in the database
        List<ConceptNumeric> conceptNumericList = conceptNumericRepository.findAll();
        assertThat(conceptNumericList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteConceptNumeric() throws Exception {
        // Initialize the database
        conceptNumericRepository.saveAndFlush(conceptNumeric);

        int databaseSizeBeforeDelete = conceptNumericRepository.findAll().size();

        // Delete the conceptNumeric
        restConceptNumericMockMvc.perform(delete("/api/concept-numerics/{id}", conceptNumeric.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ConceptNumeric> conceptNumericList = conceptNumericRepository.findAll();
        assertThat(conceptNumericList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
