package org.openmrs.concepts.web.rest;

import org.openmrs.concepts.ConceptsApp;
import org.openmrs.concepts.domain.ConceptDescription;
import org.openmrs.concepts.repository.ConceptDescriptionRepository;

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
 * Integration tests for the {@link ConceptDescriptionResource} REST controller.
 */
@SpringBootTest(classes = ConceptsApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ConceptDescriptionResourceIT {

    private static final UUID DEFAULT_UUID = UUID.randomUUID();
    private static final UUID UPDATED_UUID = UUID.randomUUID();

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_LOCALE = "AAAAAAAAAA";
    private static final String UPDATED_LOCALE = "BBBBBBBBBB";

    @Autowired
    private ConceptDescriptionRepository conceptDescriptionRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restConceptDescriptionMockMvc;

    private ConceptDescription conceptDescription;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ConceptDescription createEntity(EntityManager em) {
        ConceptDescription conceptDescription = new ConceptDescription()
            .uuid(DEFAULT_UUID)
            .description(DEFAULT_DESCRIPTION)
            .locale(DEFAULT_LOCALE);
        return conceptDescription;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ConceptDescription createUpdatedEntity(EntityManager em) {
        ConceptDescription conceptDescription = new ConceptDescription()
            .uuid(UPDATED_UUID)
            .description(UPDATED_DESCRIPTION)
            .locale(UPDATED_LOCALE);
        return conceptDescription;
    }

    @BeforeEach
    public void initTest() {
        conceptDescription = createEntity(em);
    }

    @Test
    @Transactional
    public void createConceptDescription() throws Exception {
        int databaseSizeBeforeCreate = conceptDescriptionRepository.findAll().size();
        // Create the ConceptDescription
        restConceptDescriptionMockMvc.perform(post("/api/concept-descriptions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(conceptDescription)))
            .andExpect(status().isCreated());

        // Validate the ConceptDescription in the database
        List<ConceptDescription> conceptDescriptionList = conceptDescriptionRepository.findAll();
        assertThat(conceptDescriptionList).hasSize(databaseSizeBeforeCreate + 1);
        ConceptDescription testConceptDescription = conceptDescriptionList.get(conceptDescriptionList.size() - 1);
        assertThat(testConceptDescription.getUuid()).isEqualTo(DEFAULT_UUID);
        assertThat(testConceptDescription.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testConceptDescription.getLocale()).isEqualTo(DEFAULT_LOCALE);
    }

    @Test
    @Transactional
    public void createConceptDescriptionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = conceptDescriptionRepository.findAll().size();

        // Create the ConceptDescription with an existing ID
        conceptDescription.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restConceptDescriptionMockMvc.perform(post("/api/concept-descriptions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(conceptDescription)))
            .andExpect(status().isBadRequest());

        // Validate the ConceptDescription in the database
        List<ConceptDescription> conceptDescriptionList = conceptDescriptionRepository.findAll();
        assertThat(conceptDescriptionList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkUuidIsRequired() throws Exception {
        int databaseSizeBeforeTest = conceptDescriptionRepository.findAll().size();
        // set the field null
        conceptDescription.setUuid(null);

        // Create the ConceptDescription, which fails.


        restConceptDescriptionMockMvc.perform(post("/api/concept-descriptions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(conceptDescription)))
            .andExpect(status().isBadRequest());

        List<ConceptDescription> conceptDescriptionList = conceptDescriptionRepository.findAll();
        assertThat(conceptDescriptionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllConceptDescriptions() throws Exception {
        // Initialize the database
        conceptDescriptionRepository.saveAndFlush(conceptDescription);

        // Get all the conceptDescriptionList
        restConceptDescriptionMockMvc.perform(get("/api/concept-descriptions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(conceptDescription.getId().intValue())))
            .andExpect(jsonPath("$.[*].uuid").value(hasItem(DEFAULT_UUID.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].locale").value(hasItem(DEFAULT_LOCALE)));
    }
    
    @Test
    @Transactional
    public void getConceptDescription() throws Exception {
        // Initialize the database
        conceptDescriptionRepository.saveAndFlush(conceptDescription);

        // Get the conceptDescription
        restConceptDescriptionMockMvc.perform(get("/api/concept-descriptions/{id}", conceptDescription.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(conceptDescription.getId().intValue()))
            .andExpect(jsonPath("$.uuid").value(DEFAULT_UUID.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.locale").value(DEFAULT_LOCALE));
    }
    @Test
    @Transactional
    public void getNonExistingConceptDescription() throws Exception {
        // Get the conceptDescription
        restConceptDescriptionMockMvc.perform(get("/api/concept-descriptions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateConceptDescription() throws Exception {
        // Initialize the database
        conceptDescriptionRepository.saveAndFlush(conceptDescription);

        int databaseSizeBeforeUpdate = conceptDescriptionRepository.findAll().size();

        // Update the conceptDescription
        ConceptDescription updatedConceptDescription = conceptDescriptionRepository.findById(conceptDescription.getId()).get();
        // Disconnect from session so that the updates on updatedConceptDescription are not directly saved in db
        em.detach(updatedConceptDescription);
        updatedConceptDescription
            .uuid(UPDATED_UUID)
            .description(UPDATED_DESCRIPTION)
            .locale(UPDATED_LOCALE);

        restConceptDescriptionMockMvc.perform(put("/api/concept-descriptions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedConceptDescription)))
            .andExpect(status().isOk());

        // Validate the ConceptDescription in the database
        List<ConceptDescription> conceptDescriptionList = conceptDescriptionRepository.findAll();
        assertThat(conceptDescriptionList).hasSize(databaseSizeBeforeUpdate);
        ConceptDescription testConceptDescription = conceptDescriptionList.get(conceptDescriptionList.size() - 1);
        assertThat(testConceptDescription.getUuid()).isEqualTo(UPDATED_UUID);
        assertThat(testConceptDescription.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testConceptDescription.getLocale()).isEqualTo(UPDATED_LOCALE);
    }

    @Test
    @Transactional
    public void updateNonExistingConceptDescription() throws Exception {
        int databaseSizeBeforeUpdate = conceptDescriptionRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restConceptDescriptionMockMvc.perform(put("/api/concept-descriptions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(conceptDescription)))
            .andExpect(status().isBadRequest());

        // Validate the ConceptDescription in the database
        List<ConceptDescription> conceptDescriptionList = conceptDescriptionRepository.findAll();
        assertThat(conceptDescriptionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteConceptDescription() throws Exception {
        // Initialize the database
        conceptDescriptionRepository.saveAndFlush(conceptDescription);

        int databaseSizeBeforeDelete = conceptDescriptionRepository.findAll().size();

        // Delete the conceptDescription
        restConceptDescriptionMockMvc.perform(delete("/api/concept-descriptions/{id}", conceptDescription.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ConceptDescription> conceptDescriptionList = conceptDescriptionRepository.findAll();
        assertThat(conceptDescriptionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
