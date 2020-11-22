package org.openmrs.forms.web.rest;

import org.openmrs.forms.FormsApp;
import org.openmrs.forms.domain.Form;
import org.openmrs.forms.repository.FormRepository;

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
 * Integration tests for the {@link FormResource} REST controller.
 */
@SpringBootTest(classes = FormsApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class FormResourceIT {

    private static final UUID DEFAULT_UUID = UUID.randomUUID();
    private static final UUID UPDATED_UUID = UUID.randomUUID();

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_VERSION = "AAAAAAAAAA";
    private static final String UPDATED_VERSION = "BBBBBBBBBB";

    private static final Integer DEFAULT_BUILD = 1;
    private static final Integer UPDATED_BUILD = 2;

    private static final Boolean DEFAULT_PUBLISHED = false;
    private static final Boolean UPDATED_PUBLISHED = true;

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final UUID DEFAULT_ENCOUNTER_TYPE = UUID.randomUUID();
    private static final UUID UPDATED_ENCOUNTER_TYPE = UUID.randomUUID();

    private static final String DEFAULT_TEMPLATE = "AAAAAAAAAA";
    private static final String UPDATED_TEMPLATE = "BBBBBBBBBB";

    private static final String DEFAULT_XSLT = "AAAAAAAAAA";
    private static final String UPDATED_XSLT = "BBBBBBBBBB";

    @Autowired
    private FormRepository formRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFormMockMvc;

    private Form form;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Form createEntity(EntityManager em) {
        Form form = new Form()
            .uuid(DEFAULT_UUID)
            .name(DEFAULT_NAME)
            .version(DEFAULT_VERSION)
            .build(DEFAULT_BUILD)
            .published(DEFAULT_PUBLISHED)
            .description(DEFAULT_DESCRIPTION)
            .encounterType(DEFAULT_ENCOUNTER_TYPE)
            .template(DEFAULT_TEMPLATE)
            .xslt(DEFAULT_XSLT);
        return form;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Form createUpdatedEntity(EntityManager em) {
        Form form = new Form()
            .uuid(UPDATED_UUID)
            .name(UPDATED_NAME)
            .version(UPDATED_VERSION)
            .build(UPDATED_BUILD)
            .published(UPDATED_PUBLISHED)
            .description(UPDATED_DESCRIPTION)
            .encounterType(UPDATED_ENCOUNTER_TYPE)
            .template(UPDATED_TEMPLATE)
            .xslt(UPDATED_XSLT);
        return form;
    }

    @BeforeEach
    public void initTest() {
        form = createEntity(em);
    }

    @Test
    @Transactional
    public void createForm() throws Exception {
        int databaseSizeBeforeCreate = formRepository.findAll().size();
        // Create the Form
        restFormMockMvc.perform(post("/api/forms")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(form)))
            .andExpect(status().isCreated());

        // Validate the Form in the database
        List<Form> formList = formRepository.findAll();
        assertThat(formList).hasSize(databaseSizeBeforeCreate + 1);
        Form testForm = formList.get(formList.size() - 1);
        assertThat(testForm.getUuid()).isEqualTo(DEFAULT_UUID);
        assertThat(testForm.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testForm.getVersion()).isEqualTo(DEFAULT_VERSION);
        assertThat(testForm.getBuild()).isEqualTo(DEFAULT_BUILD);
        assertThat(testForm.isPublished()).isEqualTo(DEFAULT_PUBLISHED);
        assertThat(testForm.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testForm.getEncounterType()).isEqualTo(DEFAULT_ENCOUNTER_TYPE);
        assertThat(testForm.getTemplate()).isEqualTo(DEFAULT_TEMPLATE);
        assertThat(testForm.getXslt()).isEqualTo(DEFAULT_XSLT);
    }

    @Test
    @Transactional
    public void createFormWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = formRepository.findAll().size();

        // Create the Form with an existing ID
        form.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFormMockMvc.perform(post("/api/forms")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(form)))
            .andExpect(status().isBadRequest());

        // Validate the Form in the database
        List<Form> formList = formRepository.findAll();
        assertThat(formList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkUuidIsRequired() throws Exception {
        int databaseSizeBeforeTest = formRepository.findAll().size();
        // set the field null
        form.setUuid(null);

        // Create the Form, which fails.


        restFormMockMvc.perform(post("/api/forms")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(form)))
            .andExpect(status().isBadRequest());

        List<Form> formList = formRepository.findAll();
        assertThat(formList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEncounterTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = formRepository.findAll().size();
        // set the field null
        form.setEncounterType(null);

        // Create the Form, which fails.


        restFormMockMvc.perform(post("/api/forms")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(form)))
            .andExpect(status().isBadRequest());

        List<Form> formList = formRepository.findAll();
        assertThat(formList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllForms() throws Exception {
        // Initialize the database
        formRepository.saveAndFlush(form);

        // Get all the formList
        restFormMockMvc.perform(get("/api/forms?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(form.getId().intValue())))
            .andExpect(jsonPath("$.[*].uuid").value(hasItem(DEFAULT_UUID.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION)))
            .andExpect(jsonPath("$.[*].build").value(hasItem(DEFAULT_BUILD)))
            .andExpect(jsonPath("$.[*].published").value(hasItem(DEFAULT_PUBLISHED.booleanValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].encounterType").value(hasItem(DEFAULT_ENCOUNTER_TYPE.toString())))
            .andExpect(jsonPath("$.[*].template").value(hasItem(DEFAULT_TEMPLATE)))
            .andExpect(jsonPath("$.[*].xslt").value(hasItem(DEFAULT_XSLT)));
    }
    
    @Test
    @Transactional
    public void getForm() throws Exception {
        // Initialize the database
        formRepository.saveAndFlush(form);

        // Get the form
        restFormMockMvc.perform(get("/api/forms/{id}", form.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(form.getId().intValue()))
            .andExpect(jsonPath("$.uuid").value(DEFAULT_UUID.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.version").value(DEFAULT_VERSION))
            .andExpect(jsonPath("$.build").value(DEFAULT_BUILD))
            .andExpect(jsonPath("$.published").value(DEFAULT_PUBLISHED.booleanValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.encounterType").value(DEFAULT_ENCOUNTER_TYPE.toString()))
            .andExpect(jsonPath("$.template").value(DEFAULT_TEMPLATE))
            .andExpect(jsonPath("$.xslt").value(DEFAULT_XSLT));
    }
    @Test
    @Transactional
    public void getNonExistingForm() throws Exception {
        // Get the form
        restFormMockMvc.perform(get("/api/forms/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateForm() throws Exception {
        // Initialize the database
        formRepository.saveAndFlush(form);

        int databaseSizeBeforeUpdate = formRepository.findAll().size();

        // Update the form
        Form updatedForm = formRepository.findById(form.getId()).get();
        // Disconnect from session so that the updates on updatedForm are not directly saved in db
        em.detach(updatedForm);
        updatedForm
            .uuid(UPDATED_UUID)
            .name(UPDATED_NAME)
            .version(UPDATED_VERSION)
            .build(UPDATED_BUILD)
            .published(UPDATED_PUBLISHED)
            .description(UPDATED_DESCRIPTION)
            .encounterType(UPDATED_ENCOUNTER_TYPE)
            .template(UPDATED_TEMPLATE)
            .xslt(UPDATED_XSLT);

        restFormMockMvc.perform(put("/api/forms")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedForm)))
            .andExpect(status().isOk());

        // Validate the Form in the database
        List<Form> formList = formRepository.findAll();
        assertThat(formList).hasSize(databaseSizeBeforeUpdate);
        Form testForm = formList.get(formList.size() - 1);
        assertThat(testForm.getUuid()).isEqualTo(UPDATED_UUID);
        assertThat(testForm.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testForm.getVersion()).isEqualTo(UPDATED_VERSION);
        assertThat(testForm.getBuild()).isEqualTo(UPDATED_BUILD);
        assertThat(testForm.isPublished()).isEqualTo(UPDATED_PUBLISHED);
        assertThat(testForm.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testForm.getEncounterType()).isEqualTo(UPDATED_ENCOUNTER_TYPE);
        assertThat(testForm.getTemplate()).isEqualTo(UPDATED_TEMPLATE);
        assertThat(testForm.getXslt()).isEqualTo(UPDATED_XSLT);
    }

    @Test
    @Transactional
    public void updateNonExistingForm() throws Exception {
        int databaseSizeBeforeUpdate = formRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFormMockMvc.perform(put("/api/forms")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(form)))
            .andExpect(status().isBadRequest());

        // Validate the Form in the database
        List<Form> formList = formRepository.findAll();
        assertThat(formList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteForm() throws Exception {
        // Initialize the database
        formRepository.saveAndFlush(form);

        int databaseSizeBeforeDelete = formRepository.findAll().size();

        // Delete the form
        restFormMockMvc.perform(delete("/api/forms/{id}", form.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Form> formList = formRepository.findAll();
        assertThat(formList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
