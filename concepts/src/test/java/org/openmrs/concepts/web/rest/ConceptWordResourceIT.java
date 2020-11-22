package org.openmrs.concepts.web.rest;

import org.openmrs.concepts.ConceptsApp;
import org.openmrs.concepts.domain.ConceptWord;
import org.openmrs.concepts.repository.ConceptWordRepository;

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
 * Integration tests for the {@link ConceptWordResource} REST controller.
 */
@SpringBootTest(classes = ConceptsApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ConceptWordResourceIT {

    private static final UUID DEFAULT_UUID = UUID.randomUUID();
    private static final UUID UPDATED_UUID = UUID.randomUUID();

    private static final String DEFAULT_WORD = "AAAAAAAAAA";
    private static final String UPDATED_WORD = "BBBBBBBBBB";

    private static final String DEFAULT_LOCALE = "AAAAAAAAAA";
    private static final String UPDATED_LOCALE = "BBBBBBBBBB";

    private static final Double DEFAULT_WEIGHT = 1D;
    private static final Double UPDATED_WEIGHT = 2D;

    @Autowired
    private ConceptWordRepository conceptWordRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restConceptWordMockMvc;

    private ConceptWord conceptWord;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ConceptWord createEntity(EntityManager em) {
        ConceptWord conceptWord = new ConceptWord()
            .uuid(DEFAULT_UUID)
            .word(DEFAULT_WORD)
            .locale(DEFAULT_LOCALE)
            .weight(DEFAULT_WEIGHT);
        return conceptWord;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ConceptWord createUpdatedEntity(EntityManager em) {
        ConceptWord conceptWord = new ConceptWord()
            .uuid(UPDATED_UUID)
            .word(UPDATED_WORD)
            .locale(UPDATED_LOCALE)
            .weight(UPDATED_WEIGHT);
        return conceptWord;
    }

    @BeforeEach
    public void initTest() {
        conceptWord = createEntity(em);
    }

    @Test
    @Transactional
    public void createConceptWord() throws Exception {
        int databaseSizeBeforeCreate = conceptWordRepository.findAll().size();
        // Create the ConceptWord
        restConceptWordMockMvc.perform(post("/api/concept-words")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(conceptWord)))
            .andExpect(status().isCreated());

        // Validate the ConceptWord in the database
        List<ConceptWord> conceptWordList = conceptWordRepository.findAll();
        assertThat(conceptWordList).hasSize(databaseSizeBeforeCreate + 1);
        ConceptWord testConceptWord = conceptWordList.get(conceptWordList.size() - 1);
        assertThat(testConceptWord.getUuid()).isEqualTo(DEFAULT_UUID);
        assertThat(testConceptWord.getWord()).isEqualTo(DEFAULT_WORD);
        assertThat(testConceptWord.getLocale()).isEqualTo(DEFAULT_LOCALE);
        assertThat(testConceptWord.getWeight()).isEqualTo(DEFAULT_WEIGHT);
    }

    @Test
    @Transactional
    public void createConceptWordWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = conceptWordRepository.findAll().size();

        // Create the ConceptWord with an existing ID
        conceptWord.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restConceptWordMockMvc.perform(post("/api/concept-words")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(conceptWord)))
            .andExpect(status().isBadRequest());

        // Validate the ConceptWord in the database
        List<ConceptWord> conceptWordList = conceptWordRepository.findAll();
        assertThat(conceptWordList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkUuidIsRequired() throws Exception {
        int databaseSizeBeforeTest = conceptWordRepository.findAll().size();
        // set the field null
        conceptWord.setUuid(null);

        // Create the ConceptWord, which fails.


        restConceptWordMockMvc.perform(post("/api/concept-words")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(conceptWord)))
            .andExpect(status().isBadRequest());

        List<ConceptWord> conceptWordList = conceptWordRepository.findAll();
        assertThat(conceptWordList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllConceptWords() throws Exception {
        // Initialize the database
        conceptWordRepository.saveAndFlush(conceptWord);

        // Get all the conceptWordList
        restConceptWordMockMvc.perform(get("/api/concept-words?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(conceptWord.getId().intValue())))
            .andExpect(jsonPath("$.[*].uuid").value(hasItem(DEFAULT_UUID.toString())))
            .andExpect(jsonPath("$.[*].word").value(hasItem(DEFAULT_WORD)))
            .andExpect(jsonPath("$.[*].locale").value(hasItem(DEFAULT_LOCALE)))
            .andExpect(jsonPath("$.[*].weight").value(hasItem(DEFAULT_WEIGHT.doubleValue())));
    }
    
    @Test
    @Transactional
    public void getConceptWord() throws Exception {
        // Initialize the database
        conceptWordRepository.saveAndFlush(conceptWord);

        // Get the conceptWord
        restConceptWordMockMvc.perform(get("/api/concept-words/{id}", conceptWord.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(conceptWord.getId().intValue()))
            .andExpect(jsonPath("$.uuid").value(DEFAULT_UUID.toString()))
            .andExpect(jsonPath("$.word").value(DEFAULT_WORD))
            .andExpect(jsonPath("$.locale").value(DEFAULT_LOCALE))
            .andExpect(jsonPath("$.weight").value(DEFAULT_WEIGHT.doubleValue()));
    }
    @Test
    @Transactional
    public void getNonExistingConceptWord() throws Exception {
        // Get the conceptWord
        restConceptWordMockMvc.perform(get("/api/concept-words/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateConceptWord() throws Exception {
        // Initialize the database
        conceptWordRepository.saveAndFlush(conceptWord);

        int databaseSizeBeforeUpdate = conceptWordRepository.findAll().size();

        // Update the conceptWord
        ConceptWord updatedConceptWord = conceptWordRepository.findById(conceptWord.getId()).get();
        // Disconnect from session so that the updates on updatedConceptWord are not directly saved in db
        em.detach(updatedConceptWord);
        updatedConceptWord
            .uuid(UPDATED_UUID)
            .word(UPDATED_WORD)
            .locale(UPDATED_LOCALE)
            .weight(UPDATED_WEIGHT);

        restConceptWordMockMvc.perform(put("/api/concept-words")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedConceptWord)))
            .andExpect(status().isOk());

        // Validate the ConceptWord in the database
        List<ConceptWord> conceptWordList = conceptWordRepository.findAll();
        assertThat(conceptWordList).hasSize(databaseSizeBeforeUpdate);
        ConceptWord testConceptWord = conceptWordList.get(conceptWordList.size() - 1);
        assertThat(testConceptWord.getUuid()).isEqualTo(UPDATED_UUID);
        assertThat(testConceptWord.getWord()).isEqualTo(UPDATED_WORD);
        assertThat(testConceptWord.getLocale()).isEqualTo(UPDATED_LOCALE);
        assertThat(testConceptWord.getWeight()).isEqualTo(UPDATED_WEIGHT);
    }

    @Test
    @Transactional
    public void updateNonExistingConceptWord() throws Exception {
        int databaseSizeBeforeUpdate = conceptWordRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restConceptWordMockMvc.perform(put("/api/concept-words")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(conceptWord)))
            .andExpect(status().isBadRequest());

        // Validate the ConceptWord in the database
        List<ConceptWord> conceptWordList = conceptWordRepository.findAll();
        assertThat(conceptWordList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteConceptWord() throws Exception {
        // Initialize the database
        conceptWordRepository.saveAndFlush(conceptWord);

        int databaseSizeBeforeDelete = conceptWordRepository.findAll().size();

        // Delete the conceptWord
        restConceptWordMockMvc.perform(delete("/api/concept-words/{id}", conceptWord.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ConceptWord> conceptWordList = conceptWordRepository.findAll();
        assertThat(conceptWordList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
