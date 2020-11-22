package org.openmrs.concepts.web.rest;

import org.openmrs.concepts.ConceptsApp;
import org.openmrs.concepts.domain.ConceptStopWord;
import org.openmrs.concepts.repository.ConceptStopWordRepository;

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
 * Integration tests for the {@link ConceptStopWordResource} REST controller.
 */
@SpringBootTest(classes = ConceptsApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ConceptStopWordResourceIT {

    private static final UUID DEFAULT_UUID = UUID.randomUUID();
    private static final UUID UPDATED_UUID = UUID.randomUUID();

    private static final String DEFAULT_WORD = "AAAAAAAAAA";
    private static final String UPDATED_WORD = "BBBBBBBBBB";

    private static final String DEFAULT_LOCALE = "AAAAAAAAAA";
    private static final String UPDATED_LOCALE = "BBBBBBBBBB";

    @Autowired
    private ConceptStopWordRepository conceptStopWordRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restConceptStopWordMockMvc;

    private ConceptStopWord conceptStopWord;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ConceptStopWord createEntity(EntityManager em) {
        ConceptStopWord conceptStopWord = new ConceptStopWord()
            .uuid(DEFAULT_UUID)
            .word(DEFAULT_WORD)
            .locale(DEFAULT_LOCALE);
        return conceptStopWord;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ConceptStopWord createUpdatedEntity(EntityManager em) {
        ConceptStopWord conceptStopWord = new ConceptStopWord()
            .uuid(UPDATED_UUID)
            .word(UPDATED_WORD)
            .locale(UPDATED_LOCALE);
        return conceptStopWord;
    }

    @BeforeEach
    public void initTest() {
        conceptStopWord = createEntity(em);
    }

    @Test
    @Transactional
    public void createConceptStopWord() throws Exception {
        int databaseSizeBeforeCreate = conceptStopWordRepository.findAll().size();
        // Create the ConceptStopWord
        restConceptStopWordMockMvc.perform(post("/api/concept-stop-words")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(conceptStopWord)))
            .andExpect(status().isCreated());

        // Validate the ConceptStopWord in the database
        List<ConceptStopWord> conceptStopWordList = conceptStopWordRepository.findAll();
        assertThat(conceptStopWordList).hasSize(databaseSizeBeforeCreate + 1);
        ConceptStopWord testConceptStopWord = conceptStopWordList.get(conceptStopWordList.size() - 1);
        assertThat(testConceptStopWord.getUuid()).isEqualTo(DEFAULT_UUID);
        assertThat(testConceptStopWord.getWord()).isEqualTo(DEFAULT_WORD);
        assertThat(testConceptStopWord.getLocale()).isEqualTo(DEFAULT_LOCALE);
    }

    @Test
    @Transactional
    public void createConceptStopWordWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = conceptStopWordRepository.findAll().size();

        // Create the ConceptStopWord with an existing ID
        conceptStopWord.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restConceptStopWordMockMvc.perform(post("/api/concept-stop-words")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(conceptStopWord)))
            .andExpect(status().isBadRequest());

        // Validate the ConceptStopWord in the database
        List<ConceptStopWord> conceptStopWordList = conceptStopWordRepository.findAll();
        assertThat(conceptStopWordList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkUuidIsRequired() throws Exception {
        int databaseSizeBeforeTest = conceptStopWordRepository.findAll().size();
        // set the field null
        conceptStopWord.setUuid(null);

        // Create the ConceptStopWord, which fails.


        restConceptStopWordMockMvc.perform(post("/api/concept-stop-words")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(conceptStopWord)))
            .andExpect(status().isBadRequest());

        List<ConceptStopWord> conceptStopWordList = conceptStopWordRepository.findAll();
        assertThat(conceptStopWordList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllConceptStopWords() throws Exception {
        // Initialize the database
        conceptStopWordRepository.saveAndFlush(conceptStopWord);

        // Get all the conceptStopWordList
        restConceptStopWordMockMvc.perform(get("/api/concept-stop-words?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(conceptStopWord.getId().intValue())))
            .andExpect(jsonPath("$.[*].uuid").value(hasItem(DEFAULT_UUID.toString())))
            .andExpect(jsonPath("$.[*].word").value(hasItem(DEFAULT_WORD)))
            .andExpect(jsonPath("$.[*].locale").value(hasItem(DEFAULT_LOCALE)));
    }
    
    @Test
    @Transactional
    public void getConceptStopWord() throws Exception {
        // Initialize the database
        conceptStopWordRepository.saveAndFlush(conceptStopWord);

        // Get the conceptStopWord
        restConceptStopWordMockMvc.perform(get("/api/concept-stop-words/{id}", conceptStopWord.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(conceptStopWord.getId().intValue()))
            .andExpect(jsonPath("$.uuid").value(DEFAULT_UUID.toString()))
            .andExpect(jsonPath("$.word").value(DEFAULT_WORD))
            .andExpect(jsonPath("$.locale").value(DEFAULT_LOCALE));
    }
    @Test
    @Transactional
    public void getNonExistingConceptStopWord() throws Exception {
        // Get the conceptStopWord
        restConceptStopWordMockMvc.perform(get("/api/concept-stop-words/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateConceptStopWord() throws Exception {
        // Initialize the database
        conceptStopWordRepository.saveAndFlush(conceptStopWord);

        int databaseSizeBeforeUpdate = conceptStopWordRepository.findAll().size();

        // Update the conceptStopWord
        ConceptStopWord updatedConceptStopWord = conceptStopWordRepository.findById(conceptStopWord.getId()).get();
        // Disconnect from session so that the updates on updatedConceptStopWord are not directly saved in db
        em.detach(updatedConceptStopWord);
        updatedConceptStopWord
            .uuid(UPDATED_UUID)
            .word(UPDATED_WORD)
            .locale(UPDATED_LOCALE);

        restConceptStopWordMockMvc.perform(put("/api/concept-stop-words")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedConceptStopWord)))
            .andExpect(status().isOk());

        // Validate the ConceptStopWord in the database
        List<ConceptStopWord> conceptStopWordList = conceptStopWordRepository.findAll();
        assertThat(conceptStopWordList).hasSize(databaseSizeBeforeUpdate);
        ConceptStopWord testConceptStopWord = conceptStopWordList.get(conceptStopWordList.size() - 1);
        assertThat(testConceptStopWord.getUuid()).isEqualTo(UPDATED_UUID);
        assertThat(testConceptStopWord.getWord()).isEqualTo(UPDATED_WORD);
        assertThat(testConceptStopWord.getLocale()).isEqualTo(UPDATED_LOCALE);
    }

    @Test
    @Transactional
    public void updateNonExistingConceptStopWord() throws Exception {
        int databaseSizeBeforeUpdate = conceptStopWordRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restConceptStopWordMockMvc.perform(put("/api/concept-stop-words")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(conceptStopWord)))
            .andExpect(status().isBadRequest());

        // Validate the ConceptStopWord in the database
        List<ConceptStopWord> conceptStopWordList = conceptStopWordRepository.findAll();
        assertThat(conceptStopWordList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteConceptStopWord() throws Exception {
        // Initialize the database
        conceptStopWordRepository.saveAndFlush(conceptStopWord);

        int databaseSizeBeforeDelete = conceptStopWordRepository.findAll().size();

        // Delete the conceptStopWord
        restConceptStopWordMockMvc.perform(delete("/api/concept-stop-words/{id}", conceptStopWord.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ConceptStopWord> conceptStopWordList = conceptStopWordRepository.findAll();
        assertThat(conceptStopWordList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
