package org.openmrs.orders.web.rest;

import org.openmrs.orders.OrdersApp;
import org.openmrs.orders.domain.Order;
import org.openmrs.orders.repository.OrderRepository;

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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link OrderResource} REST controller.
 */
@SpringBootTest(classes = OrdersApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class OrderResourceIT {

    private static final UUID DEFAULT_UUID = UUID.randomUUID();
    private static final UUID UPDATED_UUID = UUID.randomUUID();

    private static final UUID DEFAULT_CONCEPT_UUID = UUID.randomUUID();
    private static final UUID UPDATED_CONCEPT_UUID = UUID.randomUUID();

    private static final UUID DEFAULT_ORDERER_UUID = UUID.randomUUID();
    private static final UUID UPDATED_ORDERER_UUID = UUID.randomUUID();

    private static final UUID DEFAULT_ENCOUNTER_UUID = UUID.randomUUID();
    private static final UUID UPDATED_ENCOUNTER_UUID = UUID.randomUUID();

    private static final String DEFAULT_INSTRUCTIONS = "AAAAAAAAAA";
    private static final String UPDATED_INSTRUCTIONS = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_START_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_START_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_AUTO_EXPIRE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_AUTO_EXPIRE_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Boolean DEFAULT_DISCONTINUED = false;
    private static final Boolean UPDATED_DISCONTINUED = true;

    private static final LocalDate DEFAULT_DISCONTINUED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DISCONTINUED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_ACCESSION_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_ACCESSION_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_DISCONTINUED_REASON_NON_CODED = "AAAAAAAAAA";
    private static final String UPDATED_DISCONTINUED_REASON_NON_CODED = "BBBBBBBBBB";

    private static final UUID DEFAULT_PATIENT_UUID = UUID.randomUUID();
    private static final UUID UPDATED_PATIENT_UUID = UUID.randomUUID();

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restOrderMockMvc;

    private Order order;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Order createEntity(EntityManager em) {
        Order order = new Order()
            .uuid(DEFAULT_UUID)
            .conceptUuid(DEFAULT_CONCEPT_UUID)
            .ordererUuid(DEFAULT_ORDERER_UUID)
            .encounterUuid(DEFAULT_ENCOUNTER_UUID)
            .instructions(DEFAULT_INSTRUCTIONS)
            .startDate(DEFAULT_START_DATE)
            .autoExpireDate(DEFAULT_AUTO_EXPIRE_DATE)
            .discontinued(DEFAULT_DISCONTINUED)
            .discontinuedDate(DEFAULT_DISCONTINUED_DATE)
            .accessionNumber(DEFAULT_ACCESSION_NUMBER)
            .discontinuedReasonNonCoded(DEFAULT_DISCONTINUED_REASON_NON_CODED)
            .patientUuid(DEFAULT_PATIENT_UUID);
        return order;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Order createUpdatedEntity(EntityManager em) {
        Order order = new Order()
            .uuid(UPDATED_UUID)
            .conceptUuid(UPDATED_CONCEPT_UUID)
            .ordererUuid(UPDATED_ORDERER_UUID)
            .encounterUuid(UPDATED_ENCOUNTER_UUID)
            .instructions(UPDATED_INSTRUCTIONS)
            .startDate(UPDATED_START_DATE)
            .autoExpireDate(UPDATED_AUTO_EXPIRE_DATE)
            .discontinued(UPDATED_DISCONTINUED)
            .discontinuedDate(UPDATED_DISCONTINUED_DATE)
            .accessionNumber(UPDATED_ACCESSION_NUMBER)
            .discontinuedReasonNonCoded(UPDATED_DISCONTINUED_REASON_NON_CODED)
            .patientUuid(UPDATED_PATIENT_UUID);
        return order;
    }

    @BeforeEach
    public void initTest() {
        order = createEntity(em);
    }

    @Test
    @Transactional
    public void createOrder() throws Exception {
        int databaseSizeBeforeCreate = orderRepository.findAll().size();
        // Create the Order
        restOrderMockMvc.perform(post("/api/orders")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(order)))
            .andExpect(status().isCreated());

        // Validate the Order in the database
        List<Order> orderList = orderRepository.findAll();
        assertThat(orderList).hasSize(databaseSizeBeforeCreate + 1);
        Order testOrder = orderList.get(orderList.size() - 1);
        assertThat(testOrder.getUuid()).isEqualTo(DEFAULT_UUID);
        assertThat(testOrder.getConceptUuid()).isEqualTo(DEFAULT_CONCEPT_UUID);
        assertThat(testOrder.getOrdererUuid()).isEqualTo(DEFAULT_ORDERER_UUID);
        assertThat(testOrder.getEncounterUuid()).isEqualTo(DEFAULT_ENCOUNTER_UUID);
        assertThat(testOrder.getInstructions()).isEqualTo(DEFAULT_INSTRUCTIONS);
        assertThat(testOrder.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testOrder.getAutoExpireDate()).isEqualTo(DEFAULT_AUTO_EXPIRE_DATE);
        assertThat(testOrder.isDiscontinued()).isEqualTo(DEFAULT_DISCONTINUED);
        assertThat(testOrder.getDiscontinuedDate()).isEqualTo(DEFAULT_DISCONTINUED_DATE);
        assertThat(testOrder.getAccessionNumber()).isEqualTo(DEFAULT_ACCESSION_NUMBER);
        assertThat(testOrder.getDiscontinuedReasonNonCoded()).isEqualTo(DEFAULT_DISCONTINUED_REASON_NON_CODED);
        assertThat(testOrder.getPatientUuid()).isEqualTo(DEFAULT_PATIENT_UUID);
    }

    @Test
    @Transactional
    public void createOrderWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = orderRepository.findAll().size();

        // Create the Order with an existing ID
        order.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOrderMockMvc.perform(post("/api/orders")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(order)))
            .andExpect(status().isBadRequest());

        // Validate the Order in the database
        List<Order> orderList = orderRepository.findAll();
        assertThat(orderList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkUuidIsRequired() throws Exception {
        int databaseSizeBeforeTest = orderRepository.findAll().size();
        // set the field null
        order.setUuid(null);

        // Create the Order, which fails.


        restOrderMockMvc.perform(post("/api/orders")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(order)))
            .andExpect(status().isBadRequest());

        List<Order> orderList = orderRepository.findAll();
        assertThat(orderList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllOrders() throws Exception {
        // Initialize the database
        orderRepository.saveAndFlush(order);

        // Get all the orderList
        restOrderMockMvc.perform(get("/api/orders?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(order.getId().intValue())))
            .andExpect(jsonPath("$.[*].uuid").value(hasItem(DEFAULT_UUID.toString())))
            .andExpect(jsonPath("$.[*].conceptUuid").value(hasItem(DEFAULT_CONCEPT_UUID.toString())))
            .andExpect(jsonPath("$.[*].ordererUuid").value(hasItem(DEFAULT_ORDERER_UUID.toString())))
            .andExpect(jsonPath("$.[*].encounterUuid").value(hasItem(DEFAULT_ENCOUNTER_UUID.toString())))
            .andExpect(jsonPath("$.[*].instructions").value(hasItem(DEFAULT_INSTRUCTIONS)))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].autoExpireDate").value(hasItem(DEFAULT_AUTO_EXPIRE_DATE.toString())))
            .andExpect(jsonPath("$.[*].discontinued").value(hasItem(DEFAULT_DISCONTINUED.booleanValue())))
            .andExpect(jsonPath("$.[*].discontinuedDate").value(hasItem(DEFAULT_DISCONTINUED_DATE.toString())))
            .andExpect(jsonPath("$.[*].accessionNumber").value(hasItem(DEFAULT_ACCESSION_NUMBER)))
            .andExpect(jsonPath("$.[*].discontinuedReasonNonCoded").value(hasItem(DEFAULT_DISCONTINUED_REASON_NON_CODED)))
            .andExpect(jsonPath("$.[*].patientUuid").value(hasItem(DEFAULT_PATIENT_UUID.toString())));
    }
    
    @Test
    @Transactional
    public void getOrder() throws Exception {
        // Initialize the database
        orderRepository.saveAndFlush(order);

        // Get the order
        restOrderMockMvc.perform(get("/api/orders/{id}", order.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(order.getId().intValue()))
            .andExpect(jsonPath("$.uuid").value(DEFAULT_UUID.toString()))
            .andExpect(jsonPath("$.conceptUuid").value(DEFAULT_CONCEPT_UUID.toString()))
            .andExpect(jsonPath("$.ordererUuid").value(DEFAULT_ORDERER_UUID.toString()))
            .andExpect(jsonPath("$.encounterUuid").value(DEFAULT_ENCOUNTER_UUID.toString()))
            .andExpect(jsonPath("$.instructions").value(DEFAULT_INSTRUCTIONS))
            .andExpect(jsonPath("$.startDate").value(DEFAULT_START_DATE.toString()))
            .andExpect(jsonPath("$.autoExpireDate").value(DEFAULT_AUTO_EXPIRE_DATE.toString()))
            .andExpect(jsonPath("$.discontinued").value(DEFAULT_DISCONTINUED.booleanValue()))
            .andExpect(jsonPath("$.discontinuedDate").value(DEFAULT_DISCONTINUED_DATE.toString()))
            .andExpect(jsonPath("$.accessionNumber").value(DEFAULT_ACCESSION_NUMBER))
            .andExpect(jsonPath("$.discontinuedReasonNonCoded").value(DEFAULT_DISCONTINUED_REASON_NON_CODED))
            .andExpect(jsonPath("$.patientUuid").value(DEFAULT_PATIENT_UUID.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingOrder() throws Exception {
        // Get the order
        restOrderMockMvc.perform(get("/api/orders/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOrder() throws Exception {
        // Initialize the database
        orderRepository.saveAndFlush(order);

        int databaseSizeBeforeUpdate = orderRepository.findAll().size();

        // Update the order
        Order updatedOrder = orderRepository.findById(order.getId()).get();
        // Disconnect from session so that the updates on updatedOrder are not directly saved in db
        em.detach(updatedOrder);
        updatedOrder
            .uuid(UPDATED_UUID)
            .conceptUuid(UPDATED_CONCEPT_UUID)
            .ordererUuid(UPDATED_ORDERER_UUID)
            .encounterUuid(UPDATED_ENCOUNTER_UUID)
            .instructions(UPDATED_INSTRUCTIONS)
            .startDate(UPDATED_START_DATE)
            .autoExpireDate(UPDATED_AUTO_EXPIRE_DATE)
            .discontinued(UPDATED_DISCONTINUED)
            .discontinuedDate(UPDATED_DISCONTINUED_DATE)
            .accessionNumber(UPDATED_ACCESSION_NUMBER)
            .discontinuedReasonNonCoded(UPDATED_DISCONTINUED_REASON_NON_CODED)
            .patientUuid(UPDATED_PATIENT_UUID);

        restOrderMockMvc.perform(put("/api/orders")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedOrder)))
            .andExpect(status().isOk());

        // Validate the Order in the database
        List<Order> orderList = orderRepository.findAll();
        assertThat(orderList).hasSize(databaseSizeBeforeUpdate);
        Order testOrder = orderList.get(orderList.size() - 1);
        assertThat(testOrder.getUuid()).isEqualTo(UPDATED_UUID);
        assertThat(testOrder.getConceptUuid()).isEqualTo(UPDATED_CONCEPT_UUID);
        assertThat(testOrder.getOrdererUuid()).isEqualTo(UPDATED_ORDERER_UUID);
        assertThat(testOrder.getEncounterUuid()).isEqualTo(UPDATED_ENCOUNTER_UUID);
        assertThat(testOrder.getInstructions()).isEqualTo(UPDATED_INSTRUCTIONS);
        assertThat(testOrder.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testOrder.getAutoExpireDate()).isEqualTo(UPDATED_AUTO_EXPIRE_DATE);
        assertThat(testOrder.isDiscontinued()).isEqualTo(UPDATED_DISCONTINUED);
        assertThat(testOrder.getDiscontinuedDate()).isEqualTo(UPDATED_DISCONTINUED_DATE);
        assertThat(testOrder.getAccessionNumber()).isEqualTo(UPDATED_ACCESSION_NUMBER);
        assertThat(testOrder.getDiscontinuedReasonNonCoded()).isEqualTo(UPDATED_DISCONTINUED_REASON_NON_CODED);
        assertThat(testOrder.getPatientUuid()).isEqualTo(UPDATED_PATIENT_UUID);
    }

    @Test
    @Transactional
    public void updateNonExistingOrder() throws Exception {
        int databaseSizeBeforeUpdate = orderRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOrderMockMvc.perform(put("/api/orders")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(order)))
            .andExpect(status().isBadRequest());

        // Validate the Order in the database
        List<Order> orderList = orderRepository.findAll();
        assertThat(orderList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteOrder() throws Exception {
        // Initialize the database
        orderRepository.saveAndFlush(order);

        int databaseSizeBeforeDelete = orderRepository.findAll().size();

        // Delete the order
        restOrderMockMvc.perform(delete("/api/orders/{id}", order.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Order> orderList = orderRepository.findAll();
        assertThat(orderList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
