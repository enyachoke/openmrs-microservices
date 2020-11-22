package org.openmrs.concepts.web.rest;

import org.openmrs.concepts.ConceptsApp;
import org.openmrs.concepts.domain.Drug;
import org.openmrs.concepts.repository.DrugRepository;

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
 * Integration tests for the {@link DrugResource} REST controller.
 */
@SpringBootTest(classes = ConceptsApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class DrugResourceIT {

    private static final UUID DEFAULT_UUID = UUID.randomUUID();
    private static final UUID UPDATED_UUID = UUID.randomUUID();

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Boolean DEFAULT_COMBINATION = false;
    private static final Boolean UPDATED_COMBINATION = true;

    private static final Integer DEFAULT_DOSAGE_FORM = 1;
    private static final Integer UPDATED_DOSAGE_FORM = 2;

    private static final Double DEFAULT_DOSE_STRENGTH = 1D;
    private static final Double UPDATED_DOSE_STRENGTH = 2D;

    private static final Double DEFAULT_MAXIMUM_DAILY_DOSE = 1D;
    private static final Double UPDATED_MAXIMUM_DAILY_DOSE = 2D;

    private static final Double DEFAULT_MINIMUM_DAILY_DOSE = 1D;
    private static final Double UPDATED_MINIMUM_DAILY_DOSE = 2D;

    private static final Integer DEFAULT_ROUTE = 1;
    private static final Integer UPDATED_ROUTE = 2;

    private static final String DEFAULT_UNITS = "AAAAAAAAAA";
    private static final String UPDATED_UNITS = "BBBBBBBBBB";

    @Autowired
    private DrugRepository drugRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDrugMockMvc;

    private Drug drug;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Drug createEntity(EntityManager em) {
        Drug drug = new Drug()
            .uuid(DEFAULT_UUID)
            .name(DEFAULT_NAME)
            .combination(DEFAULT_COMBINATION)
            .dosageForm(DEFAULT_DOSAGE_FORM)
            .doseStrength(DEFAULT_DOSE_STRENGTH)
            .maximumDailyDose(DEFAULT_MAXIMUM_DAILY_DOSE)
            .minimumDailyDose(DEFAULT_MINIMUM_DAILY_DOSE)
            .route(DEFAULT_ROUTE)
            .units(DEFAULT_UNITS);
        return drug;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Drug createUpdatedEntity(EntityManager em) {
        Drug drug = new Drug()
            .uuid(UPDATED_UUID)
            .name(UPDATED_NAME)
            .combination(UPDATED_COMBINATION)
            .dosageForm(UPDATED_DOSAGE_FORM)
            .doseStrength(UPDATED_DOSE_STRENGTH)
            .maximumDailyDose(UPDATED_MAXIMUM_DAILY_DOSE)
            .minimumDailyDose(UPDATED_MINIMUM_DAILY_DOSE)
            .route(UPDATED_ROUTE)
            .units(UPDATED_UNITS);
        return drug;
    }

    @BeforeEach
    public void initTest() {
        drug = createEntity(em);
    }

    @Test
    @Transactional
    public void createDrug() throws Exception {
        int databaseSizeBeforeCreate = drugRepository.findAll().size();
        // Create the Drug
        restDrugMockMvc.perform(post("/api/drugs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(drug)))
            .andExpect(status().isCreated());

        // Validate the Drug in the database
        List<Drug> drugList = drugRepository.findAll();
        assertThat(drugList).hasSize(databaseSizeBeforeCreate + 1);
        Drug testDrug = drugList.get(drugList.size() - 1);
        assertThat(testDrug.getUuid()).isEqualTo(DEFAULT_UUID);
        assertThat(testDrug.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testDrug.isCombination()).isEqualTo(DEFAULT_COMBINATION);
        assertThat(testDrug.getDosageForm()).isEqualTo(DEFAULT_DOSAGE_FORM);
        assertThat(testDrug.getDoseStrength()).isEqualTo(DEFAULT_DOSE_STRENGTH);
        assertThat(testDrug.getMaximumDailyDose()).isEqualTo(DEFAULT_MAXIMUM_DAILY_DOSE);
        assertThat(testDrug.getMinimumDailyDose()).isEqualTo(DEFAULT_MINIMUM_DAILY_DOSE);
        assertThat(testDrug.getRoute()).isEqualTo(DEFAULT_ROUTE);
        assertThat(testDrug.getUnits()).isEqualTo(DEFAULT_UNITS);
    }

    @Test
    @Transactional
    public void createDrugWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = drugRepository.findAll().size();

        // Create the Drug with an existing ID
        drug.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDrugMockMvc.perform(post("/api/drugs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(drug)))
            .andExpect(status().isBadRequest());

        // Validate the Drug in the database
        List<Drug> drugList = drugRepository.findAll();
        assertThat(drugList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkUuidIsRequired() throws Exception {
        int databaseSizeBeforeTest = drugRepository.findAll().size();
        // set the field null
        drug.setUuid(null);

        // Create the Drug, which fails.


        restDrugMockMvc.perform(post("/api/drugs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(drug)))
            .andExpect(status().isBadRequest());

        List<Drug> drugList = drugRepository.findAll();
        assertThat(drugList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDrugs() throws Exception {
        // Initialize the database
        drugRepository.saveAndFlush(drug);

        // Get all the drugList
        restDrugMockMvc.perform(get("/api/drugs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(drug.getId().intValue())))
            .andExpect(jsonPath("$.[*].uuid").value(hasItem(DEFAULT_UUID.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].combination").value(hasItem(DEFAULT_COMBINATION.booleanValue())))
            .andExpect(jsonPath("$.[*].dosageForm").value(hasItem(DEFAULT_DOSAGE_FORM)))
            .andExpect(jsonPath("$.[*].doseStrength").value(hasItem(DEFAULT_DOSE_STRENGTH.doubleValue())))
            .andExpect(jsonPath("$.[*].maximumDailyDose").value(hasItem(DEFAULT_MAXIMUM_DAILY_DOSE.doubleValue())))
            .andExpect(jsonPath("$.[*].minimumDailyDose").value(hasItem(DEFAULT_MINIMUM_DAILY_DOSE.doubleValue())))
            .andExpect(jsonPath("$.[*].route").value(hasItem(DEFAULT_ROUTE)))
            .andExpect(jsonPath("$.[*].units").value(hasItem(DEFAULT_UNITS)));
    }
    
    @Test
    @Transactional
    public void getDrug() throws Exception {
        // Initialize the database
        drugRepository.saveAndFlush(drug);

        // Get the drug
        restDrugMockMvc.perform(get("/api/drugs/{id}", drug.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(drug.getId().intValue()))
            .andExpect(jsonPath("$.uuid").value(DEFAULT_UUID.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.combination").value(DEFAULT_COMBINATION.booleanValue()))
            .andExpect(jsonPath("$.dosageForm").value(DEFAULT_DOSAGE_FORM))
            .andExpect(jsonPath("$.doseStrength").value(DEFAULT_DOSE_STRENGTH.doubleValue()))
            .andExpect(jsonPath("$.maximumDailyDose").value(DEFAULT_MAXIMUM_DAILY_DOSE.doubleValue()))
            .andExpect(jsonPath("$.minimumDailyDose").value(DEFAULT_MINIMUM_DAILY_DOSE.doubleValue()))
            .andExpect(jsonPath("$.route").value(DEFAULT_ROUTE))
            .andExpect(jsonPath("$.units").value(DEFAULT_UNITS));
    }
    @Test
    @Transactional
    public void getNonExistingDrug() throws Exception {
        // Get the drug
        restDrugMockMvc.perform(get("/api/drugs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDrug() throws Exception {
        // Initialize the database
        drugRepository.saveAndFlush(drug);

        int databaseSizeBeforeUpdate = drugRepository.findAll().size();

        // Update the drug
        Drug updatedDrug = drugRepository.findById(drug.getId()).get();
        // Disconnect from session so that the updates on updatedDrug are not directly saved in db
        em.detach(updatedDrug);
        updatedDrug
            .uuid(UPDATED_UUID)
            .name(UPDATED_NAME)
            .combination(UPDATED_COMBINATION)
            .dosageForm(UPDATED_DOSAGE_FORM)
            .doseStrength(UPDATED_DOSE_STRENGTH)
            .maximumDailyDose(UPDATED_MAXIMUM_DAILY_DOSE)
            .minimumDailyDose(UPDATED_MINIMUM_DAILY_DOSE)
            .route(UPDATED_ROUTE)
            .units(UPDATED_UNITS);

        restDrugMockMvc.perform(put("/api/drugs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedDrug)))
            .andExpect(status().isOk());

        // Validate the Drug in the database
        List<Drug> drugList = drugRepository.findAll();
        assertThat(drugList).hasSize(databaseSizeBeforeUpdate);
        Drug testDrug = drugList.get(drugList.size() - 1);
        assertThat(testDrug.getUuid()).isEqualTo(UPDATED_UUID);
        assertThat(testDrug.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testDrug.isCombination()).isEqualTo(UPDATED_COMBINATION);
        assertThat(testDrug.getDosageForm()).isEqualTo(UPDATED_DOSAGE_FORM);
        assertThat(testDrug.getDoseStrength()).isEqualTo(UPDATED_DOSE_STRENGTH);
        assertThat(testDrug.getMaximumDailyDose()).isEqualTo(UPDATED_MAXIMUM_DAILY_DOSE);
        assertThat(testDrug.getMinimumDailyDose()).isEqualTo(UPDATED_MINIMUM_DAILY_DOSE);
        assertThat(testDrug.getRoute()).isEqualTo(UPDATED_ROUTE);
        assertThat(testDrug.getUnits()).isEqualTo(UPDATED_UNITS);
    }

    @Test
    @Transactional
    public void updateNonExistingDrug() throws Exception {
        int databaseSizeBeforeUpdate = drugRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDrugMockMvc.perform(put("/api/drugs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(drug)))
            .andExpect(status().isBadRequest());

        // Validate the Drug in the database
        List<Drug> drugList = drugRepository.findAll();
        assertThat(drugList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDrug() throws Exception {
        // Initialize the database
        drugRepository.saveAndFlush(drug);

        int databaseSizeBeforeDelete = drugRepository.findAll().size();

        // Delete the drug
        restDrugMockMvc.perform(delete("/api/drugs/{id}", drug.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Drug> drugList = drugRepository.findAll();
        assertThat(drugList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
