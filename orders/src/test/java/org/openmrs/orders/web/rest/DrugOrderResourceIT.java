package org.openmrs.orders.web.rest;

import org.openmrs.orders.OrdersApp;
import org.openmrs.orders.domain.DrugOrder;
import org.openmrs.orders.repository.DrugOrderRepository;

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
 * Integration tests for the {@link DrugOrderResource} REST controller.
 */
@SpringBootTest(classes = OrdersApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class DrugOrderResourceIT {

    private static final UUID DEFAULT_UUID = UUID.randomUUID();
    private static final UUID UPDATED_UUID = UUID.randomUUID();

    private static final UUID DEFAULT_DRUG_INVENTORY_UUID = UUID.randomUUID();
    private static final UUID UPDATED_DRUG_INVENTORY_UUID = UUID.randomUUID();

    private static final Double DEFAULT_DOSE = 1D;
    private static final Double UPDATED_DOSE = 2D;

    private static final Double DEFAULT_EQUIVALENT_DAILY_DOSE = 1D;
    private static final Double UPDATED_EQUIVALENT_DAILY_DOSE = 2D;

    private static final String DEFAULT_UNITS = "AAAAAAAAAA";
    private static final String UPDATED_UNITS = "BBBBBBBBBB";

    private static final String DEFAULT_FREQUENCY = "AAAAAAAAAA";
    private static final String UPDATED_FREQUENCY = "BBBBBBBBBB";

    private static final Boolean DEFAULT_PRN = false;
    private static final Boolean UPDATED_PRN = true;

    private static final Boolean DEFAULT_COMPLEX = false;
    private static final Boolean UPDATED_COMPLEX = true;

    private static final Integer DEFAULT_QUANTITY = 1;
    private static final Integer UPDATED_QUANTITY = 2;

    @Autowired
    private DrugOrderRepository drugOrderRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDrugOrderMockMvc;

    private DrugOrder drugOrder;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DrugOrder createEntity(EntityManager em) {
        DrugOrder drugOrder = new DrugOrder()
            .uuid(DEFAULT_UUID)
            .drugInventoryUuid(DEFAULT_DRUG_INVENTORY_UUID)
            .dose(DEFAULT_DOSE)
            .equivalentDailyDose(DEFAULT_EQUIVALENT_DAILY_DOSE)
            .units(DEFAULT_UNITS)
            .frequency(DEFAULT_FREQUENCY)
            .prn(DEFAULT_PRN)
            .complex(DEFAULT_COMPLEX)
            .quantity(DEFAULT_QUANTITY);
        return drugOrder;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DrugOrder createUpdatedEntity(EntityManager em) {
        DrugOrder drugOrder = new DrugOrder()
            .uuid(UPDATED_UUID)
            .drugInventoryUuid(UPDATED_DRUG_INVENTORY_UUID)
            .dose(UPDATED_DOSE)
            .equivalentDailyDose(UPDATED_EQUIVALENT_DAILY_DOSE)
            .units(UPDATED_UNITS)
            .frequency(UPDATED_FREQUENCY)
            .prn(UPDATED_PRN)
            .complex(UPDATED_COMPLEX)
            .quantity(UPDATED_QUANTITY);
        return drugOrder;
    }

    @BeforeEach
    public void initTest() {
        drugOrder = createEntity(em);
    }

    @Test
    @Transactional
    public void createDrugOrder() throws Exception {
        int databaseSizeBeforeCreate = drugOrderRepository.findAll().size();
        // Create the DrugOrder
        restDrugOrderMockMvc.perform(post("/api/drug-orders")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(drugOrder)))
            .andExpect(status().isCreated());

        // Validate the DrugOrder in the database
        List<DrugOrder> drugOrderList = drugOrderRepository.findAll();
        assertThat(drugOrderList).hasSize(databaseSizeBeforeCreate + 1);
        DrugOrder testDrugOrder = drugOrderList.get(drugOrderList.size() - 1);
        assertThat(testDrugOrder.getUuid()).isEqualTo(DEFAULT_UUID);
        assertThat(testDrugOrder.getDrugInventoryUuid()).isEqualTo(DEFAULT_DRUG_INVENTORY_UUID);
        assertThat(testDrugOrder.getDose()).isEqualTo(DEFAULT_DOSE);
        assertThat(testDrugOrder.getEquivalentDailyDose()).isEqualTo(DEFAULT_EQUIVALENT_DAILY_DOSE);
        assertThat(testDrugOrder.getUnits()).isEqualTo(DEFAULT_UNITS);
        assertThat(testDrugOrder.getFrequency()).isEqualTo(DEFAULT_FREQUENCY);
        assertThat(testDrugOrder.isPrn()).isEqualTo(DEFAULT_PRN);
        assertThat(testDrugOrder.isComplex()).isEqualTo(DEFAULT_COMPLEX);
        assertThat(testDrugOrder.getQuantity()).isEqualTo(DEFAULT_QUANTITY);
    }

    @Test
    @Transactional
    public void createDrugOrderWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = drugOrderRepository.findAll().size();

        // Create the DrugOrder with an existing ID
        drugOrder.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDrugOrderMockMvc.perform(post("/api/drug-orders")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(drugOrder)))
            .andExpect(status().isBadRequest());

        // Validate the DrugOrder in the database
        List<DrugOrder> drugOrderList = drugOrderRepository.findAll();
        assertThat(drugOrderList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkUuidIsRequired() throws Exception {
        int databaseSizeBeforeTest = drugOrderRepository.findAll().size();
        // set the field null
        drugOrder.setUuid(null);

        // Create the DrugOrder, which fails.


        restDrugOrderMockMvc.perform(post("/api/drug-orders")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(drugOrder)))
            .andExpect(status().isBadRequest());

        List<DrugOrder> drugOrderList = drugOrderRepository.findAll();
        assertThat(drugOrderList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDrugOrders() throws Exception {
        // Initialize the database
        drugOrderRepository.saveAndFlush(drugOrder);

        // Get all the drugOrderList
        restDrugOrderMockMvc.perform(get("/api/drug-orders?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(drugOrder.getId().intValue())))
            .andExpect(jsonPath("$.[*].uuid").value(hasItem(DEFAULT_UUID.toString())))
            .andExpect(jsonPath("$.[*].drugInventoryUuid").value(hasItem(DEFAULT_DRUG_INVENTORY_UUID.toString())))
            .andExpect(jsonPath("$.[*].dose").value(hasItem(DEFAULT_DOSE.doubleValue())))
            .andExpect(jsonPath("$.[*].equivalentDailyDose").value(hasItem(DEFAULT_EQUIVALENT_DAILY_DOSE.doubleValue())))
            .andExpect(jsonPath("$.[*].units").value(hasItem(DEFAULT_UNITS)))
            .andExpect(jsonPath("$.[*].frequency").value(hasItem(DEFAULT_FREQUENCY)))
            .andExpect(jsonPath("$.[*].prn").value(hasItem(DEFAULT_PRN.booleanValue())))
            .andExpect(jsonPath("$.[*].complex").value(hasItem(DEFAULT_COMPLEX.booleanValue())))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY)));
    }
    
    @Test
    @Transactional
    public void getDrugOrder() throws Exception {
        // Initialize the database
        drugOrderRepository.saveAndFlush(drugOrder);

        // Get the drugOrder
        restDrugOrderMockMvc.perform(get("/api/drug-orders/{id}", drugOrder.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(drugOrder.getId().intValue()))
            .andExpect(jsonPath("$.uuid").value(DEFAULT_UUID.toString()))
            .andExpect(jsonPath("$.drugInventoryUuid").value(DEFAULT_DRUG_INVENTORY_UUID.toString()))
            .andExpect(jsonPath("$.dose").value(DEFAULT_DOSE.doubleValue()))
            .andExpect(jsonPath("$.equivalentDailyDose").value(DEFAULT_EQUIVALENT_DAILY_DOSE.doubleValue()))
            .andExpect(jsonPath("$.units").value(DEFAULT_UNITS))
            .andExpect(jsonPath("$.frequency").value(DEFAULT_FREQUENCY))
            .andExpect(jsonPath("$.prn").value(DEFAULT_PRN.booleanValue()))
            .andExpect(jsonPath("$.complex").value(DEFAULT_COMPLEX.booleanValue()))
            .andExpect(jsonPath("$.quantity").value(DEFAULT_QUANTITY));
    }
    @Test
    @Transactional
    public void getNonExistingDrugOrder() throws Exception {
        // Get the drugOrder
        restDrugOrderMockMvc.perform(get("/api/drug-orders/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDrugOrder() throws Exception {
        // Initialize the database
        drugOrderRepository.saveAndFlush(drugOrder);

        int databaseSizeBeforeUpdate = drugOrderRepository.findAll().size();

        // Update the drugOrder
        DrugOrder updatedDrugOrder = drugOrderRepository.findById(drugOrder.getId()).get();
        // Disconnect from session so that the updates on updatedDrugOrder are not directly saved in db
        em.detach(updatedDrugOrder);
        updatedDrugOrder
            .uuid(UPDATED_UUID)
            .drugInventoryUuid(UPDATED_DRUG_INVENTORY_UUID)
            .dose(UPDATED_DOSE)
            .equivalentDailyDose(UPDATED_EQUIVALENT_DAILY_DOSE)
            .units(UPDATED_UNITS)
            .frequency(UPDATED_FREQUENCY)
            .prn(UPDATED_PRN)
            .complex(UPDATED_COMPLEX)
            .quantity(UPDATED_QUANTITY);

        restDrugOrderMockMvc.perform(put("/api/drug-orders")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedDrugOrder)))
            .andExpect(status().isOk());

        // Validate the DrugOrder in the database
        List<DrugOrder> drugOrderList = drugOrderRepository.findAll();
        assertThat(drugOrderList).hasSize(databaseSizeBeforeUpdate);
        DrugOrder testDrugOrder = drugOrderList.get(drugOrderList.size() - 1);
        assertThat(testDrugOrder.getUuid()).isEqualTo(UPDATED_UUID);
        assertThat(testDrugOrder.getDrugInventoryUuid()).isEqualTo(UPDATED_DRUG_INVENTORY_UUID);
        assertThat(testDrugOrder.getDose()).isEqualTo(UPDATED_DOSE);
        assertThat(testDrugOrder.getEquivalentDailyDose()).isEqualTo(UPDATED_EQUIVALENT_DAILY_DOSE);
        assertThat(testDrugOrder.getUnits()).isEqualTo(UPDATED_UNITS);
        assertThat(testDrugOrder.getFrequency()).isEqualTo(UPDATED_FREQUENCY);
        assertThat(testDrugOrder.isPrn()).isEqualTo(UPDATED_PRN);
        assertThat(testDrugOrder.isComplex()).isEqualTo(UPDATED_COMPLEX);
        assertThat(testDrugOrder.getQuantity()).isEqualTo(UPDATED_QUANTITY);
    }

    @Test
    @Transactional
    public void updateNonExistingDrugOrder() throws Exception {
        int databaseSizeBeforeUpdate = drugOrderRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDrugOrderMockMvc.perform(put("/api/drug-orders")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(drugOrder)))
            .andExpect(status().isBadRequest());

        // Validate the DrugOrder in the database
        List<DrugOrder> drugOrderList = drugOrderRepository.findAll();
        assertThat(drugOrderList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDrugOrder() throws Exception {
        // Initialize the database
        drugOrderRepository.saveAndFlush(drugOrder);

        int databaseSizeBeforeDelete = drugOrderRepository.findAll().size();

        // Delete the drugOrder
        restDrugOrderMockMvc.perform(delete("/api/drug-orders/{id}", drugOrder.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DrugOrder> drugOrderList = drugOrderRepository.findAll();
        assertThat(drugOrderList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
