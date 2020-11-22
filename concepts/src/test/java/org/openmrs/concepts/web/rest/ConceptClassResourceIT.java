package org.openmrs.concepts.web.rest;

import org.openmrs.concepts.ConceptsApp;
import org.openmrs.concepts.domain.ConceptClass;
import org.openmrs.concepts.repository.ConceptClassRepository;

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
 * Integration tests for the {@link ConceptClassResource} REST controller.
 */
@SpringBootTest(classes = ConceptsApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ConceptClassResourceIT {

    private static final UUID DEFAULT_UUID = UUID.randomUUID();
    private static final UUID UPDATED_UUID = UUID.randomUUID();

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private ConceptClassRepository conceptClassRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restConceptClassMockMvc;

    private ConceptClass conceptClass;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ConceptClass createEntity(EntityManager em) {
        ConceptClass conceptClass = new ConceptClass()
            .uuid(DEFAULT_UUID)
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION);
        return conceptClass;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ConceptClass createUpdatedEntity(EntityManager em) {
        ConceptClass conceptClass = new ConceptClass()
            .uuid(UPDATED_UUID)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);
        return conceptClass;
    }

    @BeforeEach
    public void initTest() {
        conceptClass = createEntity(em);
    }

    @Test
    @Transactional
    public void createConceptClass() throws Exception {
        int databaseSizeBeforeCreate = conceptClassRepository.findAll().size();
        // Create the ConceptClass
        restConceptClassMockMvc.perform(post("/api/concept-classes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(conceptClass)))
            .andExpect(status().isCreated());

        // Validate the ConceptClass in the database
        List<ConceptClass> conceptClassList = conceptClassRepository.findAll();
        assertThat(conceptClassList).hasSize(databaseSizeBeforeCreate + 1);
        ConceptClass testConceptClass = conceptClassList.get(conceptClassList.size() - 1);
        assertThat(testConceptClass.getUuid()).isEqualTo(DEFAULT_UUID);
        assertThat(testConceptClass.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testConceptClass.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createConceptClassWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = conceptClassRepository.findAll().size();

        // Create the ConceptClass with an existing ID
        conceptClass.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restConceptClassMockMvc.perform(post("/api/concept-classes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(conceptClass)))
            .andExpect(status().isBadRequest());

        // Validate the ConceptClass in the database
        List<ConceptClass> conceptClassList = conceptClassRepository.findAll();
        assertThat(conceptClassList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkUuidIsRequired() throws Exception {
        int databaseSizeBeforeTest = conceptClassRepository.findAll().size();
        // set the field null
        conceptClass.setUuid(null);

        // Create the ConceptClass, which fails.


        restConceptClassMockMvc.perform(post("/api/concept-classes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(conceptClass)))
            .andExpect(status().isBadRequest());

        List<ConceptClass> conceptClassList = conceptClassRepository.findAll();
        assertThat(conceptClassList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllConceptClasses() throws Exception {
        // Initialize the database
        conceptClassRepository.saveAndFlush(conceptClass);

        // Get all the conceptClassList
        restConceptClassMockMvc.perform(get("/api/concept-classes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(conceptClass.getId().intValue())))
            .andExpect(jsonPath("$.[*].uuid").value(hasItem(DEFAULT_UUID.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }
    
    @Test
    @Transactional
    public void getConceptClass() throws Exception {
        // Initialize the database
        conceptClassRepository.saveAndFlush(conceptClass);

        // Get the conceptClass
        restConceptClassMockMvc.perform(get("/api/concept-classes/{id}", conceptClass.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(conceptClass.getId().intValue()))
            .andExpect(jsonPath("$.uuid").value(DEFAULT_UUID.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }
    @Test
    @Transactional
    public void getNonExistingConceptClass() throws Exception {
        // Get the conceptClass
        restConceptClassMockMvc.perform(get("/api/concept-classes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateConceptClass() throws Exception {
        // Initialize the database
        conceptClassRepository.saveAndFlush(conceptClass);

        int databaseSizeBeforeUpdate = conceptClassRepository.findAll().size();

        // Update the conceptClass
        ConceptClass updatedConceptClass = conceptClassRepository.findById(conceptClass.getId()).get();
        // Disconnect from session so that the updates on updatedConceptClass are not directly saved in db
        em.detach(updatedConceptClass);
        updatedConceptClass
            .uuid(UPDATED_UUID)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);

        restConceptClassMockMvc.perform(put("/api/concept-classes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedConceptClass)))
            .andExpect(status().isOk());

        // Validate the ConceptClass in the database
        List<ConceptClass> conceptClassList = conceptClassRepository.findAll();
        assertThat(conceptClassList).hasSize(databaseSizeBeforeUpdate);
        ConceptClass testConceptClass = conceptClassList.get(conceptClassList.size() - 1);
        assertThat(testConceptClass.getUuid()).isEqualTo(UPDATED_UUID);
        assertThat(testConceptClass.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testConceptClass.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingConceptClass() throws Exception {
        int databaseSizeBeforeUpdate = conceptClassRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restConceptClassMockMvc.perform(put("/api/concept-classes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(conceptClass)))
            .andExpect(status().isBadRequest());

        // Validate the ConceptClass in the database
        List<ConceptClass> conceptClassList = conceptClassRepository.findAll();
        assertThat(conceptClassList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteConceptClass() throws Exception {
        // Initialize the database
        conceptClassRepository.saveAndFlush(conceptClass);

        int databaseSizeBeforeDelete = conceptClassRepository.findAll().size();

        // Delete the conceptClass
        restConceptClassMockMvc.perform(delete("/api/concept-classes/{id}", conceptClass.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ConceptClass> conceptClassList = conceptClassRepository.findAll();
        assertThat(conceptClassList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
