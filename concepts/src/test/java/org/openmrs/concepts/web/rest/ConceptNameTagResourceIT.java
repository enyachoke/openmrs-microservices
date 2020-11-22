package org.openmrs.concepts.web.rest;

import org.openmrs.concepts.ConceptsApp;
import org.openmrs.concepts.domain.ConceptNameTag;
import org.openmrs.concepts.repository.ConceptNameTagRepository;

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
 * Integration tests for the {@link ConceptNameTagResource} REST controller.
 */
@SpringBootTest(classes = ConceptsApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ConceptNameTagResourceIT {

    private static final UUID DEFAULT_UUID = UUID.randomUUID();
    private static final UUID UPDATED_UUID = UUID.randomUUID();

    private static final String DEFAULT_TAG = "AAAAAAAAAA";
    private static final String UPDATED_TAG = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private ConceptNameTagRepository conceptNameTagRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restConceptNameTagMockMvc;

    private ConceptNameTag conceptNameTag;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ConceptNameTag createEntity(EntityManager em) {
        ConceptNameTag conceptNameTag = new ConceptNameTag()
            .uuid(DEFAULT_UUID)
            .tag(DEFAULT_TAG)
            .description(DEFAULT_DESCRIPTION);
        return conceptNameTag;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ConceptNameTag createUpdatedEntity(EntityManager em) {
        ConceptNameTag conceptNameTag = new ConceptNameTag()
            .uuid(UPDATED_UUID)
            .tag(UPDATED_TAG)
            .description(UPDATED_DESCRIPTION);
        return conceptNameTag;
    }

    @BeforeEach
    public void initTest() {
        conceptNameTag = createEntity(em);
    }

    @Test
    @Transactional
    public void createConceptNameTag() throws Exception {
        int databaseSizeBeforeCreate = conceptNameTagRepository.findAll().size();
        // Create the ConceptNameTag
        restConceptNameTagMockMvc.perform(post("/api/concept-name-tags")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(conceptNameTag)))
            .andExpect(status().isCreated());

        // Validate the ConceptNameTag in the database
        List<ConceptNameTag> conceptNameTagList = conceptNameTagRepository.findAll();
        assertThat(conceptNameTagList).hasSize(databaseSizeBeforeCreate + 1);
        ConceptNameTag testConceptNameTag = conceptNameTagList.get(conceptNameTagList.size() - 1);
        assertThat(testConceptNameTag.getUuid()).isEqualTo(DEFAULT_UUID);
        assertThat(testConceptNameTag.getTag()).isEqualTo(DEFAULT_TAG);
        assertThat(testConceptNameTag.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createConceptNameTagWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = conceptNameTagRepository.findAll().size();

        // Create the ConceptNameTag with an existing ID
        conceptNameTag.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restConceptNameTagMockMvc.perform(post("/api/concept-name-tags")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(conceptNameTag)))
            .andExpect(status().isBadRequest());

        // Validate the ConceptNameTag in the database
        List<ConceptNameTag> conceptNameTagList = conceptNameTagRepository.findAll();
        assertThat(conceptNameTagList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkUuidIsRequired() throws Exception {
        int databaseSizeBeforeTest = conceptNameTagRepository.findAll().size();
        // set the field null
        conceptNameTag.setUuid(null);

        // Create the ConceptNameTag, which fails.


        restConceptNameTagMockMvc.perform(post("/api/concept-name-tags")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(conceptNameTag)))
            .andExpect(status().isBadRequest());

        List<ConceptNameTag> conceptNameTagList = conceptNameTagRepository.findAll();
        assertThat(conceptNameTagList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllConceptNameTags() throws Exception {
        // Initialize the database
        conceptNameTagRepository.saveAndFlush(conceptNameTag);

        // Get all the conceptNameTagList
        restConceptNameTagMockMvc.perform(get("/api/concept-name-tags?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(conceptNameTag.getId().intValue())))
            .andExpect(jsonPath("$.[*].uuid").value(hasItem(DEFAULT_UUID.toString())))
            .andExpect(jsonPath("$.[*].tag").value(hasItem(DEFAULT_TAG)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }
    
    @Test
    @Transactional
    public void getConceptNameTag() throws Exception {
        // Initialize the database
        conceptNameTagRepository.saveAndFlush(conceptNameTag);

        // Get the conceptNameTag
        restConceptNameTagMockMvc.perform(get("/api/concept-name-tags/{id}", conceptNameTag.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(conceptNameTag.getId().intValue()))
            .andExpect(jsonPath("$.uuid").value(DEFAULT_UUID.toString()))
            .andExpect(jsonPath("$.tag").value(DEFAULT_TAG))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }
    @Test
    @Transactional
    public void getNonExistingConceptNameTag() throws Exception {
        // Get the conceptNameTag
        restConceptNameTagMockMvc.perform(get("/api/concept-name-tags/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateConceptNameTag() throws Exception {
        // Initialize the database
        conceptNameTagRepository.saveAndFlush(conceptNameTag);

        int databaseSizeBeforeUpdate = conceptNameTagRepository.findAll().size();

        // Update the conceptNameTag
        ConceptNameTag updatedConceptNameTag = conceptNameTagRepository.findById(conceptNameTag.getId()).get();
        // Disconnect from session so that the updates on updatedConceptNameTag are not directly saved in db
        em.detach(updatedConceptNameTag);
        updatedConceptNameTag
            .uuid(UPDATED_UUID)
            .tag(UPDATED_TAG)
            .description(UPDATED_DESCRIPTION);

        restConceptNameTagMockMvc.perform(put("/api/concept-name-tags")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedConceptNameTag)))
            .andExpect(status().isOk());

        // Validate the ConceptNameTag in the database
        List<ConceptNameTag> conceptNameTagList = conceptNameTagRepository.findAll();
        assertThat(conceptNameTagList).hasSize(databaseSizeBeforeUpdate);
        ConceptNameTag testConceptNameTag = conceptNameTagList.get(conceptNameTagList.size() - 1);
        assertThat(testConceptNameTag.getUuid()).isEqualTo(UPDATED_UUID);
        assertThat(testConceptNameTag.getTag()).isEqualTo(UPDATED_TAG);
        assertThat(testConceptNameTag.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingConceptNameTag() throws Exception {
        int databaseSizeBeforeUpdate = conceptNameTagRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restConceptNameTagMockMvc.perform(put("/api/concept-name-tags")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(conceptNameTag)))
            .andExpect(status().isBadRequest());

        // Validate the ConceptNameTag in the database
        List<ConceptNameTag> conceptNameTagList = conceptNameTagRepository.findAll();
        assertThat(conceptNameTagList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteConceptNameTag() throws Exception {
        // Initialize the database
        conceptNameTagRepository.saveAndFlush(conceptNameTag);

        int databaseSizeBeforeDelete = conceptNameTagRepository.findAll().size();

        // Delete the conceptNameTag
        restConceptNameTagMockMvc.perform(delete("/api/concept-name-tags/{id}", conceptNameTag.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ConceptNameTag> conceptNameTagList = conceptNameTagRepository.findAll();
        assertThat(conceptNameTagList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
