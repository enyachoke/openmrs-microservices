package org.openmrs.concepts.web.rest;

import org.openmrs.concepts.ConceptsApp;
import org.openmrs.concepts.domain.ConceptProposal;
import org.openmrs.concepts.repository.ConceptProposalRepository;

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
 * Integration tests for the {@link ConceptProposalResource} REST controller.
 */
@SpringBootTest(classes = ConceptsApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ConceptProposalResourceIT {

    private static final UUID DEFAULT_UUID = UUID.randomUUID();
    private static final UUID UPDATED_UUID = UUID.randomUUID();

    private static final UUID DEFAULT_ENCOUNTER = UUID.randomUUID();
    private static final UUID UPDATED_ENCOUNTER = UUID.randomUUID();

    private static final String DEFAULT_ORIGINAL_TEXT = "AAAAAAAAAA";
    private static final String UPDATED_ORIGINAL_TEXT = "BBBBBBBBBB";

    private static final String DEFAULT_FINAL_TEXT = "AAAAAAAAAA";
    private static final String UPDATED_FINAL_TEXT = "BBBBBBBBBB";

    private static final UUID DEFAULT_OBS_UUID = UUID.randomUUID();
    private static final UUID UPDATED_OBS_UUID = UUID.randomUUID();

    private static final UUID DEFAULT_OBS_CONCEPT_UUID = UUID.randomUUID();
    private static final UUID UPDATED_OBS_CONCEPT_UUID = UUID.randomUUID();

    private static final String DEFAULT_STATE = "AAAAAAAAAA";
    private static final String UPDATED_STATE = "BBBBBBBBBB";

    private static final String DEFAULT_COMMENTS = "AAAAAAAAAA";
    private static final String UPDATED_COMMENTS = "BBBBBBBBBB";

    private static final String DEFAULT_LOCALE = "AAAAAAAAAA";
    private static final String UPDATED_LOCALE = "BBBBBBBBBB";

    @Autowired
    private ConceptProposalRepository conceptProposalRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restConceptProposalMockMvc;

    private ConceptProposal conceptProposal;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ConceptProposal createEntity(EntityManager em) {
        ConceptProposal conceptProposal = new ConceptProposal()
            .uuid(DEFAULT_UUID)
            .encounter(DEFAULT_ENCOUNTER)
            .originalText(DEFAULT_ORIGINAL_TEXT)
            .finalText(DEFAULT_FINAL_TEXT)
            .obsUuid(DEFAULT_OBS_UUID)
            .obsConceptUuid(DEFAULT_OBS_CONCEPT_UUID)
            .state(DEFAULT_STATE)
            .comments(DEFAULT_COMMENTS)
            .locale(DEFAULT_LOCALE);
        return conceptProposal;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ConceptProposal createUpdatedEntity(EntityManager em) {
        ConceptProposal conceptProposal = new ConceptProposal()
            .uuid(UPDATED_UUID)
            .encounter(UPDATED_ENCOUNTER)
            .originalText(UPDATED_ORIGINAL_TEXT)
            .finalText(UPDATED_FINAL_TEXT)
            .obsUuid(UPDATED_OBS_UUID)
            .obsConceptUuid(UPDATED_OBS_CONCEPT_UUID)
            .state(UPDATED_STATE)
            .comments(UPDATED_COMMENTS)
            .locale(UPDATED_LOCALE);
        return conceptProposal;
    }

    @BeforeEach
    public void initTest() {
        conceptProposal = createEntity(em);
    }

    @Test
    @Transactional
    public void createConceptProposal() throws Exception {
        int databaseSizeBeforeCreate = conceptProposalRepository.findAll().size();
        // Create the ConceptProposal
        restConceptProposalMockMvc.perform(post("/api/concept-proposals")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(conceptProposal)))
            .andExpect(status().isCreated());

        // Validate the ConceptProposal in the database
        List<ConceptProposal> conceptProposalList = conceptProposalRepository.findAll();
        assertThat(conceptProposalList).hasSize(databaseSizeBeforeCreate + 1);
        ConceptProposal testConceptProposal = conceptProposalList.get(conceptProposalList.size() - 1);
        assertThat(testConceptProposal.getUuid()).isEqualTo(DEFAULT_UUID);
        assertThat(testConceptProposal.getEncounter()).isEqualTo(DEFAULT_ENCOUNTER);
        assertThat(testConceptProposal.getOriginalText()).isEqualTo(DEFAULT_ORIGINAL_TEXT);
        assertThat(testConceptProposal.getFinalText()).isEqualTo(DEFAULT_FINAL_TEXT);
        assertThat(testConceptProposal.getObsUuid()).isEqualTo(DEFAULT_OBS_UUID);
        assertThat(testConceptProposal.getObsConceptUuid()).isEqualTo(DEFAULT_OBS_CONCEPT_UUID);
        assertThat(testConceptProposal.getState()).isEqualTo(DEFAULT_STATE);
        assertThat(testConceptProposal.getComments()).isEqualTo(DEFAULT_COMMENTS);
        assertThat(testConceptProposal.getLocale()).isEqualTo(DEFAULT_LOCALE);
    }

    @Test
    @Transactional
    public void createConceptProposalWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = conceptProposalRepository.findAll().size();

        // Create the ConceptProposal with an existing ID
        conceptProposal.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restConceptProposalMockMvc.perform(post("/api/concept-proposals")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(conceptProposal)))
            .andExpect(status().isBadRequest());

        // Validate the ConceptProposal in the database
        List<ConceptProposal> conceptProposalList = conceptProposalRepository.findAll();
        assertThat(conceptProposalList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkUuidIsRequired() throws Exception {
        int databaseSizeBeforeTest = conceptProposalRepository.findAll().size();
        // set the field null
        conceptProposal.setUuid(null);

        // Create the ConceptProposal, which fails.


        restConceptProposalMockMvc.perform(post("/api/concept-proposals")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(conceptProposal)))
            .andExpect(status().isBadRequest());

        List<ConceptProposal> conceptProposalList = conceptProposalRepository.findAll();
        assertThat(conceptProposalList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllConceptProposals() throws Exception {
        // Initialize the database
        conceptProposalRepository.saveAndFlush(conceptProposal);

        // Get all the conceptProposalList
        restConceptProposalMockMvc.perform(get("/api/concept-proposals?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(conceptProposal.getId().intValue())))
            .andExpect(jsonPath("$.[*].uuid").value(hasItem(DEFAULT_UUID.toString())))
            .andExpect(jsonPath("$.[*].encounter").value(hasItem(DEFAULT_ENCOUNTER.toString())))
            .andExpect(jsonPath("$.[*].originalText").value(hasItem(DEFAULT_ORIGINAL_TEXT)))
            .andExpect(jsonPath("$.[*].finalText").value(hasItem(DEFAULT_FINAL_TEXT)))
            .andExpect(jsonPath("$.[*].obsUuid").value(hasItem(DEFAULT_OBS_UUID.toString())))
            .andExpect(jsonPath("$.[*].obsConceptUuid").value(hasItem(DEFAULT_OBS_CONCEPT_UUID.toString())))
            .andExpect(jsonPath("$.[*].state").value(hasItem(DEFAULT_STATE)))
            .andExpect(jsonPath("$.[*].comments").value(hasItem(DEFAULT_COMMENTS)))
            .andExpect(jsonPath("$.[*].locale").value(hasItem(DEFAULT_LOCALE)));
    }
    
    @Test
    @Transactional
    public void getConceptProposal() throws Exception {
        // Initialize the database
        conceptProposalRepository.saveAndFlush(conceptProposal);

        // Get the conceptProposal
        restConceptProposalMockMvc.perform(get("/api/concept-proposals/{id}", conceptProposal.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(conceptProposal.getId().intValue()))
            .andExpect(jsonPath("$.uuid").value(DEFAULT_UUID.toString()))
            .andExpect(jsonPath("$.encounter").value(DEFAULT_ENCOUNTER.toString()))
            .andExpect(jsonPath("$.originalText").value(DEFAULT_ORIGINAL_TEXT))
            .andExpect(jsonPath("$.finalText").value(DEFAULT_FINAL_TEXT))
            .andExpect(jsonPath("$.obsUuid").value(DEFAULT_OBS_UUID.toString()))
            .andExpect(jsonPath("$.obsConceptUuid").value(DEFAULT_OBS_CONCEPT_UUID.toString()))
            .andExpect(jsonPath("$.state").value(DEFAULT_STATE))
            .andExpect(jsonPath("$.comments").value(DEFAULT_COMMENTS))
            .andExpect(jsonPath("$.locale").value(DEFAULT_LOCALE));
    }
    @Test
    @Transactional
    public void getNonExistingConceptProposal() throws Exception {
        // Get the conceptProposal
        restConceptProposalMockMvc.perform(get("/api/concept-proposals/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateConceptProposal() throws Exception {
        // Initialize the database
        conceptProposalRepository.saveAndFlush(conceptProposal);

        int databaseSizeBeforeUpdate = conceptProposalRepository.findAll().size();

        // Update the conceptProposal
        ConceptProposal updatedConceptProposal = conceptProposalRepository.findById(conceptProposal.getId()).get();
        // Disconnect from session so that the updates on updatedConceptProposal are not directly saved in db
        em.detach(updatedConceptProposal);
        updatedConceptProposal
            .uuid(UPDATED_UUID)
            .encounter(UPDATED_ENCOUNTER)
            .originalText(UPDATED_ORIGINAL_TEXT)
            .finalText(UPDATED_FINAL_TEXT)
            .obsUuid(UPDATED_OBS_UUID)
            .obsConceptUuid(UPDATED_OBS_CONCEPT_UUID)
            .state(UPDATED_STATE)
            .comments(UPDATED_COMMENTS)
            .locale(UPDATED_LOCALE);

        restConceptProposalMockMvc.perform(put("/api/concept-proposals")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedConceptProposal)))
            .andExpect(status().isOk());

        // Validate the ConceptProposal in the database
        List<ConceptProposal> conceptProposalList = conceptProposalRepository.findAll();
        assertThat(conceptProposalList).hasSize(databaseSizeBeforeUpdate);
        ConceptProposal testConceptProposal = conceptProposalList.get(conceptProposalList.size() - 1);
        assertThat(testConceptProposal.getUuid()).isEqualTo(UPDATED_UUID);
        assertThat(testConceptProposal.getEncounter()).isEqualTo(UPDATED_ENCOUNTER);
        assertThat(testConceptProposal.getOriginalText()).isEqualTo(UPDATED_ORIGINAL_TEXT);
        assertThat(testConceptProposal.getFinalText()).isEqualTo(UPDATED_FINAL_TEXT);
        assertThat(testConceptProposal.getObsUuid()).isEqualTo(UPDATED_OBS_UUID);
        assertThat(testConceptProposal.getObsConceptUuid()).isEqualTo(UPDATED_OBS_CONCEPT_UUID);
        assertThat(testConceptProposal.getState()).isEqualTo(UPDATED_STATE);
        assertThat(testConceptProposal.getComments()).isEqualTo(UPDATED_COMMENTS);
        assertThat(testConceptProposal.getLocale()).isEqualTo(UPDATED_LOCALE);
    }

    @Test
    @Transactional
    public void updateNonExistingConceptProposal() throws Exception {
        int databaseSizeBeforeUpdate = conceptProposalRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restConceptProposalMockMvc.perform(put("/api/concept-proposals")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(conceptProposal)))
            .andExpect(status().isBadRequest());

        // Validate the ConceptProposal in the database
        List<ConceptProposal> conceptProposalList = conceptProposalRepository.findAll();
        assertThat(conceptProposalList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteConceptProposal() throws Exception {
        // Initialize the database
        conceptProposalRepository.saveAndFlush(conceptProposal);

        int databaseSizeBeforeDelete = conceptProposalRepository.findAll().size();

        // Delete the conceptProposal
        restConceptProposalMockMvc.perform(delete("/api/concept-proposals/{id}", conceptProposal.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ConceptProposal> conceptProposalList = conceptProposalRepository.findAll();
        assertThat(conceptProposalList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
