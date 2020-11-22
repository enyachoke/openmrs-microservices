package org.openmrs.concepts.web.rest;

import org.openmrs.concepts.ConceptsApp;
import org.openmrs.concepts.domain.ConceptReferenceTerm;
import org.openmrs.concepts.repository.ConceptReferenceTermRepository;

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
 * Integration tests for the {@link ConceptReferenceTermResource} REST controller.
 */
@SpringBootTest(classes = ConceptsApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ConceptReferenceTermResourceIT {

    private static final UUID DEFAULT_UUID = UUID.randomUUID();
    private static final UUID UPDATED_UUID = UUID.randomUUID();

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_VERSION = "AAAAAAAAAA";
    private static final String UPDATED_VERSION = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private ConceptReferenceTermRepository conceptReferenceTermRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restConceptReferenceTermMockMvc;

    private ConceptReferenceTerm conceptReferenceTerm;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ConceptReferenceTerm createEntity(EntityManager em) {
        ConceptReferenceTerm conceptReferenceTerm = new ConceptReferenceTerm()
            .uuid(DEFAULT_UUID)
            .name(DEFAULT_NAME)
            .code(DEFAULT_CODE)
            .version(DEFAULT_VERSION)
            .description(DEFAULT_DESCRIPTION);
        return conceptReferenceTerm;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ConceptReferenceTerm createUpdatedEntity(EntityManager em) {
        ConceptReferenceTerm conceptReferenceTerm = new ConceptReferenceTerm()
            .uuid(UPDATED_UUID)
            .name(UPDATED_NAME)
            .code(UPDATED_CODE)
            .version(UPDATED_VERSION)
            .description(UPDATED_DESCRIPTION);
        return conceptReferenceTerm;
    }

    @BeforeEach
    public void initTest() {
        conceptReferenceTerm = createEntity(em);
    }

    @Test
    @Transactional
    public void createConceptReferenceTerm() throws Exception {
        int databaseSizeBeforeCreate = conceptReferenceTermRepository.findAll().size();
        // Create the ConceptReferenceTerm
        restConceptReferenceTermMockMvc.perform(post("/api/concept-reference-terms")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(conceptReferenceTerm)))
            .andExpect(status().isCreated());

        // Validate the ConceptReferenceTerm in the database
        List<ConceptReferenceTerm> conceptReferenceTermList = conceptReferenceTermRepository.findAll();
        assertThat(conceptReferenceTermList).hasSize(databaseSizeBeforeCreate + 1);
        ConceptReferenceTerm testConceptReferenceTerm = conceptReferenceTermList.get(conceptReferenceTermList.size() - 1);
        assertThat(testConceptReferenceTerm.getUuid()).isEqualTo(DEFAULT_UUID);
        assertThat(testConceptReferenceTerm.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testConceptReferenceTerm.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testConceptReferenceTerm.getVersion()).isEqualTo(DEFAULT_VERSION);
        assertThat(testConceptReferenceTerm.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createConceptReferenceTermWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = conceptReferenceTermRepository.findAll().size();

        // Create the ConceptReferenceTerm with an existing ID
        conceptReferenceTerm.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restConceptReferenceTermMockMvc.perform(post("/api/concept-reference-terms")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(conceptReferenceTerm)))
            .andExpect(status().isBadRequest());

        // Validate the ConceptReferenceTerm in the database
        List<ConceptReferenceTerm> conceptReferenceTermList = conceptReferenceTermRepository.findAll();
        assertThat(conceptReferenceTermList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkUuidIsRequired() throws Exception {
        int databaseSizeBeforeTest = conceptReferenceTermRepository.findAll().size();
        // set the field null
        conceptReferenceTerm.setUuid(null);

        // Create the ConceptReferenceTerm, which fails.


        restConceptReferenceTermMockMvc.perform(post("/api/concept-reference-terms")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(conceptReferenceTerm)))
            .andExpect(status().isBadRequest());

        List<ConceptReferenceTerm> conceptReferenceTermList = conceptReferenceTermRepository.findAll();
        assertThat(conceptReferenceTermList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllConceptReferenceTerms() throws Exception {
        // Initialize the database
        conceptReferenceTermRepository.saveAndFlush(conceptReferenceTerm);

        // Get all the conceptReferenceTermList
        restConceptReferenceTermMockMvc.perform(get("/api/concept-reference-terms?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(conceptReferenceTerm.getId().intValue())))
            .andExpect(jsonPath("$.[*].uuid").value(hasItem(DEFAULT_UUID.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }
    
    @Test
    @Transactional
    public void getConceptReferenceTerm() throws Exception {
        // Initialize the database
        conceptReferenceTermRepository.saveAndFlush(conceptReferenceTerm);

        // Get the conceptReferenceTerm
        restConceptReferenceTermMockMvc.perform(get("/api/concept-reference-terms/{id}", conceptReferenceTerm.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(conceptReferenceTerm.getId().intValue()))
            .andExpect(jsonPath("$.uuid").value(DEFAULT_UUID.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.version").value(DEFAULT_VERSION))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }
    @Test
    @Transactional
    public void getNonExistingConceptReferenceTerm() throws Exception {
        // Get the conceptReferenceTerm
        restConceptReferenceTermMockMvc.perform(get("/api/concept-reference-terms/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateConceptReferenceTerm() throws Exception {
        // Initialize the database
        conceptReferenceTermRepository.saveAndFlush(conceptReferenceTerm);

        int databaseSizeBeforeUpdate = conceptReferenceTermRepository.findAll().size();

        // Update the conceptReferenceTerm
        ConceptReferenceTerm updatedConceptReferenceTerm = conceptReferenceTermRepository.findById(conceptReferenceTerm.getId()).get();
        // Disconnect from session so that the updates on updatedConceptReferenceTerm are not directly saved in db
        em.detach(updatedConceptReferenceTerm);
        updatedConceptReferenceTerm
            .uuid(UPDATED_UUID)
            .name(UPDATED_NAME)
            .code(UPDATED_CODE)
            .version(UPDATED_VERSION)
            .description(UPDATED_DESCRIPTION);

        restConceptReferenceTermMockMvc.perform(put("/api/concept-reference-terms")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedConceptReferenceTerm)))
            .andExpect(status().isOk());

        // Validate the ConceptReferenceTerm in the database
        List<ConceptReferenceTerm> conceptReferenceTermList = conceptReferenceTermRepository.findAll();
        assertThat(conceptReferenceTermList).hasSize(databaseSizeBeforeUpdate);
        ConceptReferenceTerm testConceptReferenceTerm = conceptReferenceTermList.get(conceptReferenceTermList.size() - 1);
        assertThat(testConceptReferenceTerm.getUuid()).isEqualTo(UPDATED_UUID);
        assertThat(testConceptReferenceTerm.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testConceptReferenceTerm.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testConceptReferenceTerm.getVersion()).isEqualTo(UPDATED_VERSION);
        assertThat(testConceptReferenceTerm.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingConceptReferenceTerm() throws Exception {
        int databaseSizeBeforeUpdate = conceptReferenceTermRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restConceptReferenceTermMockMvc.perform(put("/api/concept-reference-terms")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(conceptReferenceTerm)))
            .andExpect(status().isBadRequest());

        // Validate the ConceptReferenceTerm in the database
        List<ConceptReferenceTerm> conceptReferenceTermList = conceptReferenceTermRepository.findAll();
        assertThat(conceptReferenceTermList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteConceptReferenceTerm() throws Exception {
        // Initialize the database
        conceptReferenceTermRepository.saveAndFlush(conceptReferenceTerm);

        int databaseSizeBeforeDelete = conceptReferenceTermRepository.findAll().size();

        // Delete the conceptReferenceTerm
        restConceptReferenceTermMockMvc.perform(delete("/api/concept-reference-terms/{id}", conceptReferenceTerm.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ConceptReferenceTerm> conceptReferenceTermList = conceptReferenceTermRepository.findAll();
        assertThat(conceptReferenceTermList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
