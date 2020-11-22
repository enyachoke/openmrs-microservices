package org.openmrs.forms.web.rest;

import org.openmrs.forms.FormsApp;
import org.openmrs.forms.domain.FieldAnswer;
import org.openmrs.forms.repository.FieldAnswerRepository;

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
 * Integration tests for the {@link FieldAnswerResource} REST controller.
 */
@SpringBootTest(classes = FormsApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class FieldAnswerResourceIT {

    private static final UUID DEFAULT_UUID = UUID.randomUUID();
    private static final UUID UPDATED_UUID = UUID.randomUUID();

    @Autowired
    private FieldAnswerRepository fieldAnswerRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFieldAnswerMockMvc;

    private FieldAnswer fieldAnswer;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FieldAnswer createEntity(EntityManager em) {
        FieldAnswer fieldAnswer = new FieldAnswer()
            .uuid(DEFAULT_UUID);
        return fieldAnswer;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FieldAnswer createUpdatedEntity(EntityManager em) {
        FieldAnswer fieldAnswer = new FieldAnswer()
            .uuid(UPDATED_UUID);
        return fieldAnswer;
    }

    @BeforeEach
    public void initTest() {
        fieldAnswer = createEntity(em);
    }

    @Test
    @Transactional
    public void createFieldAnswer() throws Exception {
        int databaseSizeBeforeCreate = fieldAnswerRepository.findAll().size();
        // Create the FieldAnswer
        restFieldAnswerMockMvc.perform(post("/api/field-answers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(fieldAnswer)))
            .andExpect(status().isCreated());

        // Validate the FieldAnswer in the database
        List<FieldAnswer> fieldAnswerList = fieldAnswerRepository.findAll();
        assertThat(fieldAnswerList).hasSize(databaseSizeBeforeCreate + 1);
        FieldAnswer testFieldAnswer = fieldAnswerList.get(fieldAnswerList.size() - 1);
        assertThat(testFieldAnswer.getUuid()).isEqualTo(DEFAULT_UUID);
    }

    @Test
    @Transactional
    public void createFieldAnswerWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = fieldAnswerRepository.findAll().size();

        // Create the FieldAnswer with an existing ID
        fieldAnswer.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFieldAnswerMockMvc.perform(post("/api/field-answers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(fieldAnswer)))
            .andExpect(status().isBadRequest());

        // Validate the FieldAnswer in the database
        List<FieldAnswer> fieldAnswerList = fieldAnswerRepository.findAll();
        assertThat(fieldAnswerList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkUuidIsRequired() throws Exception {
        int databaseSizeBeforeTest = fieldAnswerRepository.findAll().size();
        // set the field null
        fieldAnswer.setUuid(null);

        // Create the FieldAnswer, which fails.


        restFieldAnswerMockMvc.perform(post("/api/field-answers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(fieldAnswer)))
            .andExpect(status().isBadRequest());

        List<FieldAnswer> fieldAnswerList = fieldAnswerRepository.findAll();
        assertThat(fieldAnswerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllFieldAnswers() throws Exception {
        // Initialize the database
        fieldAnswerRepository.saveAndFlush(fieldAnswer);

        // Get all the fieldAnswerList
        restFieldAnswerMockMvc.perform(get("/api/field-answers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fieldAnswer.getId().intValue())))
            .andExpect(jsonPath("$.[*].uuid").value(hasItem(DEFAULT_UUID.toString())));
    }
    
    @Test
    @Transactional
    public void getFieldAnswer() throws Exception {
        // Initialize the database
        fieldAnswerRepository.saveAndFlush(fieldAnswer);

        // Get the fieldAnswer
        restFieldAnswerMockMvc.perform(get("/api/field-answers/{id}", fieldAnswer.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(fieldAnswer.getId().intValue()))
            .andExpect(jsonPath("$.uuid").value(DEFAULT_UUID.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingFieldAnswer() throws Exception {
        // Get the fieldAnswer
        restFieldAnswerMockMvc.perform(get("/api/field-answers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFieldAnswer() throws Exception {
        // Initialize the database
        fieldAnswerRepository.saveAndFlush(fieldAnswer);

        int databaseSizeBeforeUpdate = fieldAnswerRepository.findAll().size();

        // Update the fieldAnswer
        FieldAnswer updatedFieldAnswer = fieldAnswerRepository.findById(fieldAnswer.getId()).get();
        // Disconnect from session so that the updates on updatedFieldAnswer are not directly saved in db
        em.detach(updatedFieldAnswer);
        updatedFieldAnswer
            .uuid(UPDATED_UUID);

        restFieldAnswerMockMvc.perform(put("/api/field-answers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedFieldAnswer)))
            .andExpect(status().isOk());

        // Validate the FieldAnswer in the database
        List<FieldAnswer> fieldAnswerList = fieldAnswerRepository.findAll();
        assertThat(fieldAnswerList).hasSize(databaseSizeBeforeUpdate);
        FieldAnswer testFieldAnswer = fieldAnswerList.get(fieldAnswerList.size() - 1);
        assertThat(testFieldAnswer.getUuid()).isEqualTo(UPDATED_UUID);
    }

    @Test
    @Transactional
    public void updateNonExistingFieldAnswer() throws Exception {
        int databaseSizeBeforeUpdate = fieldAnswerRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFieldAnswerMockMvc.perform(put("/api/field-answers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(fieldAnswer)))
            .andExpect(status().isBadRequest());

        // Validate the FieldAnswer in the database
        List<FieldAnswer> fieldAnswerList = fieldAnswerRepository.findAll();
        assertThat(fieldAnswerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteFieldAnswer() throws Exception {
        // Initialize the database
        fieldAnswerRepository.saveAndFlush(fieldAnswer);

        int databaseSizeBeforeDelete = fieldAnswerRepository.findAll().size();

        // Delete the fieldAnswer
        restFieldAnswerMockMvc.perform(delete("/api/field-answers/{id}", fieldAnswer.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<FieldAnswer> fieldAnswerList = fieldAnswerRepository.findAll();
        assertThat(fieldAnswerList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
