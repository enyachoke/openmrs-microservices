package org.openmrs.orders.web.rest;

import org.openmrs.orders.OrdersApp;
import org.openmrs.orders.domain.OrderType;
import org.openmrs.orders.repository.OrderTypeRepository;

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
 * Integration tests for the {@link OrderTypeResource} REST controller.
 */
@SpringBootTest(classes = OrdersApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class OrderTypeResourceIT {

    private static final UUID DEFAULT_UUID = UUID.randomUUID();
    private static final UUID UPDATED_UUID = UUID.randomUUID();

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private OrderTypeRepository orderTypeRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restOrderTypeMockMvc;

    private OrderType orderType;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OrderType createEntity(EntityManager em) {
        OrderType orderType = new OrderType()
            .uuid(DEFAULT_UUID)
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION);
        return orderType;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OrderType createUpdatedEntity(EntityManager em) {
        OrderType orderType = new OrderType()
            .uuid(UPDATED_UUID)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);
        return orderType;
    }

    @BeforeEach
    public void initTest() {
        orderType = createEntity(em);
    }

    @Test
    @Transactional
    public void createOrderType() throws Exception {
        int databaseSizeBeforeCreate = orderTypeRepository.findAll().size();
        // Create the OrderType
        restOrderTypeMockMvc.perform(post("/api/order-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(orderType)))
            .andExpect(status().isCreated());

        // Validate the OrderType in the database
        List<OrderType> orderTypeList = orderTypeRepository.findAll();
        assertThat(orderTypeList).hasSize(databaseSizeBeforeCreate + 1);
        OrderType testOrderType = orderTypeList.get(orderTypeList.size() - 1);
        assertThat(testOrderType.getUuid()).isEqualTo(DEFAULT_UUID);
        assertThat(testOrderType.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testOrderType.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createOrderTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = orderTypeRepository.findAll().size();

        // Create the OrderType with an existing ID
        orderType.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOrderTypeMockMvc.perform(post("/api/order-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(orderType)))
            .andExpect(status().isBadRequest());

        // Validate the OrderType in the database
        List<OrderType> orderTypeList = orderTypeRepository.findAll();
        assertThat(orderTypeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkUuidIsRequired() throws Exception {
        int databaseSizeBeforeTest = orderTypeRepository.findAll().size();
        // set the field null
        orderType.setUuid(null);

        // Create the OrderType, which fails.


        restOrderTypeMockMvc.perform(post("/api/order-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(orderType)))
            .andExpect(status().isBadRequest());

        List<OrderType> orderTypeList = orderTypeRepository.findAll();
        assertThat(orderTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllOrderTypes() throws Exception {
        // Initialize the database
        orderTypeRepository.saveAndFlush(orderType);

        // Get all the orderTypeList
        restOrderTypeMockMvc.perform(get("/api/order-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(orderType.getId().intValue())))
            .andExpect(jsonPath("$.[*].uuid").value(hasItem(DEFAULT_UUID.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }
    
    @Test
    @Transactional
    public void getOrderType() throws Exception {
        // Initialize the database
        orderTypeRepository.saveAndFlush(orderType);

        // Get the orderType
        restOrderTypeMockMvc.perform(get("/api/order-types/{id}", orderType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(orderType.getId().intValue()))
            .andExpect(jsonPath("$.uuid").value(DEFAULT_UUID.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }
    @Test
    @Transactional
    public void getNonExistingOrderType() throws Exception {
        // Get the orderType
        restOrderTypeMockMvc.perform(get("/api/order-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOrderType() throws Exception {
        // Initialize the database
        orderTypeRepository.saveAndFlush(orderType);

        int databaseSizeBeforeUpdate = orderTypeRepository.findAll().size();

        // Update the orderType
        OrderType updatedOrderType = orderTypeRepository.findById(orderType.getId()).get();
        // Disconnect from session so that the updates on updatedOrderType are not directly saved in db
        em.detach(updatedOrderType);
        updatedOrderType
            .uuid(UPDATED_UUID)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);

        restOrderTypeMockMvc.perform(put("/api/order-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedOrderType)))
            .andExpect(status().isOk());

        // Validate the OrderType in the database
        List<OrderType> orderTypeList = orderTypeRepository.findAll();
        assertThat(orderTypeList).hasSize(databaseSizeBeforeUpdate);
        OrderType testOrderType = orderTypeList.get(orderTypeList.size() - 1);
        assertThat(testOrderType.getUuid()).isEqualTo(UPDATED_UUID);
        assertThat(testOrderType.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testOrderType.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingOrderType() throws Exception {
        int databaseSizeBeforeUpdate = orderTypeRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOrderTypeMockMvc.perform(put("/api/order-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(orderType)))
            .andExpect(status().isBadRequest());

        // Validate the OrderType in the database
        List<OrderType> orderTypeList = orderTypeRepository.findAll();
        assertThat(orderTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteOrderType() throws Exception {
        // Initialize the database
        orderTypeRepository.saveAndFlush(orderType);

        int databaseSizeBeforeDelete = orderTypeRepository.findAll().size();

        // Delete the orderType
        restOrderTypeMockMvc.perform(delete("/api/order-types/{id}", orderType.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<OrderType> orderTypeList = orderTypeRepository.findAll();
        assertThat(orderTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
