package org.openmrs.concepts.web.rest;

import org.openmrs.concepts.ConceptsApp;
import org.openmrs.concepts.domain.ConceptAnswer;
import org.openmrs.concepts.repository.ConceptAnswerRepository;

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
 * Integration tests for the {@link ConceptAnswerResource} REST controller.
 */
@SpringBootTest(classes = ConceptsApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ConceptAnswerResourceIT {

    private static final UUID DEFAULT_UUID = UUID.randomUUID();
    private static final UUID UPDATED_UUID = UUID.randomUUID();

    private static final Double DEFAULT_SORT_WEIGHT = 1D;
    private static final Double UPDATED_SORT_WEIGHT = 2D;

    @Autowired
    private ConceptAnswerRepository conceptAnswerRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restConceptAnswerMockMvc;

    private ConceptAnswer conceptAnswer;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ConceptAnswer createEntity(EntityManager em) {
        ConceptAnswer conceptAnswer = new ConceptAnswer()
            .uuid(DEFAULT_UUID)
            .sortWeight(DEFAULT_SORT_WEIGHT);
        return conceptAnswer;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ConceptAnswer createUpdatedEntity(EntityManager em) {
        ConceptAnswer conceptAnswer = new ConceptAnswer()
            .uuid(UPDATED_UUID)
            .sortWeight(UPDATED_SORT_WEIGHT);
        return conceptAnswer;
    }

    @BeforeEach
    public void initTest() {
        conceptAnswer = createEntity(em);
    }

    @Test
    @Transactional
    public void createConceptAnswer() throws Exception {
        int databaseSizeBeforeCreate = conceptAnswerRepository.findAll().size();
        // Create the ConceptAnswer
        restConceptAnswerMockMvc.perform(post("/api/concept-answers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(conceptAnswer)))
            .andExpect(status().isCreated());

        // Validate the ConceptAnswer in the database
        List<ConceptAnswer> conceptAnswerList = conceptAnswerRepository.findAll();
        assertThat(conceptAnswerList).hasSize(databaseSizeBeforeCreate + 1);
        ConceptAnswer testConceptAnswer = conceptAnswerList.get(conceptAnswerList.size() - 1);
        assertThat(testConceptAnswer.getUuid()).isEqualTo(DEFAULT_UUID);
        assertThat(testConceptAnswer.getSortWeight()).isEqualTo(DEFAULT_SORT_WEIGHT);
    }

    @Test
    @Transactional
    public void createConceptAnswerWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = conceptAnswerRepository.findAll().size();

        // Create the ConceptAnswer with an existing ID
        conceptAnswer.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restConceptAnswerMockMvc.perform(post("/api/concept-answers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(conceptAnswer)))
            .andExpect(status().isBadRequest());

        // Validate the ConceptAnswer in the database
        List<ConceptAnswer> conceptAnswerList = conceptAnswerRepository.findAll();
        assertThat(conceptAnswerList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkUuidIsRequired() throws Exception {
        int databaseSizeBeforeTest = conceptAnswerRepository.findAll().size();
        // set the field null
        conceptAnswer.setUuid(null);

        // Create the ConceptAnswer, which fails.


        restConceptAnswerMockMvc.perform(post("/api/concept-answers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(conceptAnswer)))
            .andExpect(status().isBadRequest());

        List<ConceptAnswer> conceptAnswerList = conceptAnswerRepository.findAll();
        assertThat(conceptAnswerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllConceptAnswers() throws Exception {
        // Initialize the database
        conceptAnswerRepository.saveAndFlush(conceptAnswer);

        // Get all the conceptAnswerList
        restConceptAnswerMockMvc.perform(get("/api/concept-answers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(conceptAnswer.getId().intValue())))
            .andExpect(jsonPath("$.[*].uuid").value(hasItem(DEFAULT_UUID.toString())))
            .andExpect(jsonPath("$.[*].sortWeight").value(hasItem(DEFAULT_SORT_WEIGHT.doubleValue())));
    }
    
    @Test
    @Transactional
    public void getConceptAnswer() throws Exception {
        // Initialize the database
        conceptAnswerRepository.saveAndFlush(conceptAnswer);

        // Get the conceptAnswer
        restConceptAnswerMockMvc.perform(get("/api/concept-answers/{id}", conceptAnswer.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(conceptAnswer.getId().intValue()))
            .andExpect(jsonPath("$.uuid").value(DEFAULT_UUID.toString()))
            .andExpect(jsonPath("$.sortWeight").value(DEFAULT_SORT_WEIGHT.doubleValue()));
    }
    @Test
    @Transactional
    public void getNonExistingConceptAnswer() throws Exception {
        // Get the conceptAnswer
        restConceptAnswerMockMvc.perform(get("/api/concept-answers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateConceptAnswer() throws Exception {
        // Initialize the database
        conceptAnswerRepository.saveAndFlush(conceptAnswer);

        int databaseSizeBeforeUpdate = conceptAnswerRepository.findAll().size();

        // Update the conceptAnswer
        ConceptAnswer updatedConceptAnswer = conceptAnswerRepository.findById(conceptAnswer.getId()).get();
        // Disconnect from session so that the updates on updatedConceptAnswer are not directly saved in db
        em.detach(updatedConceptAnswer);
        updatedConceptAnswer
            .uuid(UPDATED_UUID)
            .sortWeight(UPDATED_SORT_WEIGHT);

        restConceptAnswerMockMvc.perform(put("/api/concept-answers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedConceptAnswer)))
            .andExpect(status().isOk());

        // Validate the ConceptAnswer in the database
        List<ConceptAnswer> conceptAnswerList = conceptAnswerRepository.findAll();
        assertThat(conceptAnswerList).hasSize(databaseSizeBeforeUpdate);
        ConceptAnswer testConceptAnswer = conceptAnswerList.get(conceptAnswerList.size() - 1);
        assertThat(testConceptAnswer.getUuid()).isEqualTo(UPDATED_UUID);
        assertThat(testConceptAnswer.getSortWeight()).isEqualTo(UPDATED_SORT_WEIGHT);
    }

    @Test
    @Transactional
    public void updateNonExistingConceptAnswer() throws Exception {
        int databaseSizeBeforeUpdate = conceptAnswerRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restConceptAnswerMockMvc.perform(put("/api/concept-answers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(conceptAnswer)))
            .andExpect(status().isBadRequest());

        // Validate the ConceptAnswer in the database
        List<ConceptAnswer> conceptAnswerList = conceptAnswerRepository.findAll();
        assertThat(conceptAnswerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteConceptAnswer() throws Exception {
        // Initialize the database
        conceptAnswerRepository.saveAndFlush(conceptAnswer);

        int databaseSizeBeforeDelete = conceptAnswerRepository.findAll().size();

        // Delete the conceptAnswer
        restConceptAnswerMockMvc.perform(delete("/api/concept-answers/{id}", conceptAnswer.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ConceptAnswer> conceptAnswerList = conceptAnswerRepository.findAll();
        assertThat(conceptAnswerList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
